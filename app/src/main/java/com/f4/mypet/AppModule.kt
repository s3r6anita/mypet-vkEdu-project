package com.f4.mypet

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.f4.mypet.data.db.DBRepository
import com.f4.mypet.data.db.PetDatabase
import com.f4.mypet.data.db.Repository
import com.f4.mypet.data.network.NetworkRepository
import com.f4.mypet.data.network.NetworkRepositoryImpl
import com.f4.mypet.data.network.authentication.AccessTokenInterceptor
import com.f4.mypet.data.network.authentication.AuthAuthenticator
import com.f4.mypet.data.network.authentication.JwtTokenDataStore
import com.f4.mypet.data.network.authentication.JwtTokenManager
import com.f4.mypet.data.network.service.AuthService
import com.f4.mypet.data.network.service.PetService
import com.f4.mypet.util.LocalDateAdapter
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton


private const val TIMEOUT = 30L
private const val AUTH_PREFERENCES = "my_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = AUTH_PREFERENCES)

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class PublicClient


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AuthenticatedClient


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @[Singleton Provides]
    fun getRepository(
        myDB: PetDatabase
    ): Repository {
        return DBRepository(
            myDB.petDAO(),
            myDB.medRecordDAO(),
            myDB.procedureDAO(),
            myDB.prTitleDAO()
        )
    }

    @[Singleton Provides]
    fun provideDatabase(app: Application): PetDatabase {
        return PetDatabase.getDatabase(app)
    }


    @[Singleton Provides]
    fun getNetworkRepository(@ApplicationContext appContext: Context): NetworkRepository {
        return NetworkRepositoryImpl(
            authService = provideNoAuthenticationApi(provideUnauthenticatedOkHttpClient()),
            petService = provideAuthenticationApi(
                provideAccessOkHttpClient(
                    provideAccessTokenInterceptor(appContext),
                    providesAuthAuthenticator(appContext)
                )
            ),
            jwtTokenManager = provideJwtTokenManager(appContext.dataStore)
        )
    }


    @[Provides Singleton]
    fun provideJwtTokenManager(dataStore: DataStore<Preferences>): JwtTokenManager {
        return JwtTokenDataStore(dataStore = dataStore)
    }

    // использование приводит к  java.lang.IllegalStateException: There are multiple DataStores active for the same file
//    @[Provides Singleton]
//    fun provideDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
//        return PreferenceDataStoreFactory.create(
//            corruptionHandler = ReplaceFileCorruptionHandler(
//                produceNewData = { emptyPreferences() }
//            ),
//            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
//            produceFile = { appContext.preferencesDataStoreFile(AUTH_PREFERENCES) }
//        )
//    }

    @[Provides Singleton]
    fun provideAccessTokenInterceptor(@ApplicationContext appContext: Context): AccessTokenInterceptor {
        return AccessTokenInterceptor(provideJwtTokenManager(appContext.dataStore))
    }

    @[Provides Singleton]
    fun providesAuthAuthenticator(@ApplicationContext appContext: Context): AuthAuthenticator {
        return AuthAuthenticator(provideJwtTokenManager(appContext.dataStore))
    }

    /** For requests requiring the access token  */
    @[Provides Singleton AuthenticatedClient]
    fun provideAccessOkHttpClient(
        accessTokenInterceptor: AccessTokenInterceptor,
        authAuthenticator: AuthAuthenticator
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .authenticator(authAuthenticator)
            .addInterceptor(accessTokenInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    /** For requests requiring the access token  */
    @[Provides Singleton]
    fun provideAuthenticationApi(@AuthenticatedClient okHttpClient: OkHttpClient): PetService {
        val baseUrl = "https://mypet-backend-s3r6.amvera.io/"
        val gsonBuilder = GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
            .create()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .client(okHttpClient)
            .build()
            .create(PetService::class.java)
    }


    /** For requests that don’t require authentication  */
    @[Provides Singleton PublicClient]
    fun provideUnauthenticatedOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    /** For API calls without authentication */
    @[Provides Singleton]
    fun provideNoAuthenticationApi(@PublicClient okHttpClient: OkHttpClient): AuthService {
        val baseUrl = "https://mypet-backend-s3r6.amvera.io/"
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(AuthService::class.java)
    }
}
