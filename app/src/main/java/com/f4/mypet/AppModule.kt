package com.f4.mypet

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
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
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

private const val AUTH_PREFERENCES = "auth_preferences.preferences_pb"

fun Context.preferencesDataStoreFile(name: String): File {
    return File(filesDir, name)
}

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
            jwtTokenManager = provideJwtTokenManager(provideDataStore(appContext))
        )
    }



    @[Provides Singleton]
    fun provideJwtTokenManager(dataStore: DataStore<Preferences>): JwtTokenManager {
        return JwtTokenDataStore(dataStore = dataStore)
    }

    @[Provides Singleton]
    fun provideDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(AUTH_PREFERENCES) }
        )
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
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    /** For requests requiring the access token  */
    @[Provides Singleton]
    fun provideAuthenticationApi(@AuthenticatedClient okHttpClient: OkHttpClient): PetService {
        val baseUrl = "https://mypet-backend-s3r6.amvera.io/"
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
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
//            .connectTimeout(30, TimeUnit.SECONDS)
//            .readTimeout(30, TimeUnit.SECONDS)
//            .writeTimeout(30, TimeUnit.SECONDS)
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
