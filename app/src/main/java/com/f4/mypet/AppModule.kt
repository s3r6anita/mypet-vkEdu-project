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
import com.f4.mypet.data.network.service.ProcedureService
import com.f4.mypet.util.LocalDateAdapter
import com.f4.mypet.util.LocalDateTimeAdapter
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
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

private const val UNAUTHENTICATED_TIMEOUT = 5L
private const val TIMEOUT = 10L
private const val AUTH_PREFERENCES = "my_preferences"
private const val BASE_URL = "https://mypet-backend-s3r6.amvera.io/"
const val VKPETS_ID = 160065516L

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

    @[Provides Singleton]
    fun getRepository(
        myDB: PetDatabase
    ): Repository {
        return DBRepository(
            myDB.petDAO(),
            myDB.medRecordDAO(),
            myDB.procedureDAO(),
            myDB.prTitleDAO(),
            myDB.frequencyDAO()
        )
    }

    @[Provides Singleton]
    fun provideDatabase(app: Application): PetDatabase {
        return PetDatabase.getDatabase(app)
    }

    @[Provides Singleton]
    @SuppressWarnings("LongParameterList")
    fun getNetworkRepository(
        @ApplicationContext appContext: Context,
        authService: AuthService,
        petService: PetService,
        procedureService: ProcedureService,
        jwtTokenManager: JwtTokenManager
    ): NetworkRepository {
        return NetworkRepositoryImpl(
            dataStore = appContext.dataStore,
            authService = authService,
            petService = petService,
            procedureService = procedureService,
            jwtTokenManager = jwtTokenManager
        )
    }


    @[Provides Singleton]
    fun providePetService(retrofit: Retrofit): PetService {
        return retrofit.create(PetService::class.java)
    }

    @[Provides Singleton]
    fun provideProcedureService(retrofit: Retrofit): ProcedureService {
        return retrofit.create(ProcedureService::class.java)
    }

    @[Provides Singleton]
    fun provideJwtTokenManager(@ApplicationContext appContext: Context): JwtTokenManager {
        return JwtTokenDataStore(appContext.dataStore)
    }

    @[Provides Singleton]
    fun provideAccessTokenInterceptor(jtm: JwtTokenManager): AccessTokenInterceptor {
        return AccessTokenInterceptor(tokenManager = jtm)
    }

    @[Provides Singleton]
    fun providesAuthAuthenticator(jtm: JwtTokenManager): AuthAuthenticator {
        return AuthAuthenticator(tokenManager = jtm)
    }

    /** For requests requiring the access token  */
    @[Provides Singleton AuthenticatedClient]
    fun provideAuthenticatedOkHttpClient(
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
    fun provideAuthenticationApi(@AuthenticatedClient okHttpClient: OkHttpClient): Retrofit {
        val gsonBuilder = GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
            .create()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .client(okHttpClient)
            .build()
    }


    /** For requests that don’t require authentication  */
    @[Provides Singleton PublicClient]
    fun provideUnauthenticatedOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(UNAUTHENTICATED_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(UNAUTHENTICATED_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(UNAUTHENTICATED_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    /** For API calls without authentication */
    @[Provides Singleton]
    fun provideNoAuthenticationApi(@PublicClient okHttpClient: OkHttpClient): AuthService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(AuthService::class.java)
    }
}
