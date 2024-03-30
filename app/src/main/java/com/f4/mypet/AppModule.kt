package com.f4.mypet

import android.app.Application
import com.f4.mypet.data.db.DBRepository
import com.f4.mypet.data.db.PetDatabase
import com.f4.mypet.data.db.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
}
