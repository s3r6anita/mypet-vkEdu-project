package com.f4.mypet

import android.app.Application
import com.f4.mypet.data.db.DBRepository
import com.f4.mypet.data.db.PetDatabase
import com.f4.mypet.data.db.Repository
import com.f4.mypet.data.network.LoginService
import com.f4.mypet.data.network.NetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
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

    @Singleton
    @Provides
    fun provideDatabase(app: Application): PetDatabase {
        return PetDatabase.getDatabase(app)
    }


    private const val baseUrl = "http://mypet-backend-s3r6.amvera.io"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    private val LoginRetrofitService: LoginService by lazy {
        retrofit.create(LoginService::class.java)
    }

    val Repository: NetworkRepository by lazy {
        NetworkRepository(LoginRetrofitService)
    }
}
