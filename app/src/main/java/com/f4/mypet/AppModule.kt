package com.f4.mypet

import android.app.Application
import androidx.room.Room
import com.f4.mypet.data.db.DBRepository
import com.f4.mypet.data.db.PetDatabase
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
    ): DBRepository {
        return DBRepository(
            myDB.petDAO(),
            myDB.medRecordDAO(),
            myDB.procedureDAO(),
            myDB.prTitleDAO()
        )
    }
//
//    @Singleton
//    @Provides
//    fun getPetDao(database: PetDatabase): PetDAO {
//        return database.petDAO()
//    }
//
//    @Singleton
//    @Provides
//    fun getProcedureDao(database: PetDatabase): ProcedureDAO {
//        return database.procedureDAO()
//    }
//
//    @Singleton
//    @Provides
//    fun getPrTitleDao(database: PetDatabase): PrTitleDAO {
//        return database.prTitleDAO()
//    }
//
//    @Singleton
//    @Provides
//    fun getMedRecordDao(database: PetDatabase): MedRecordDAO {
//        return database.medRecordDAO()
//    }

    @Singleton
    @Provides
    fun provideDatabase(app: Application): PetDatabase {
        return Room.databaseBuilder(
            app,
            PetDatabase::class.java,
            "pet_database"
        )
            .allowMainThreadQueries()
            .createFromAsset("databases/initial_db.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}
