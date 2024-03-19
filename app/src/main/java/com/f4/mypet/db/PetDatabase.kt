package com.f4.mypet.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.f4.mypet.db.daos.MedRecordDAO
import com.f4.mypet.db.daos.PetDAO
import com.f4.mypet.db.daos.PrTitleDAO
import com.f4.mypet.db.daos.ProcedureDAO
import com.f4.mypet.db.entities.MedRecord
import com.f4.mypet.db.entities.Pet
import com.f4.mypet.db.entities.Procedure
import com.f4.mypet.db.entities.ProcedureTitle
import com.f4.mypet.db.entities.ProcedureType


@Database(
    entities = [
        Pet::class,
        Procedure::class,
        MedRecord::class,
        ProcedureTitle::class,
        ProcedureType::class],
    version = 1,
    exportSchema = false
)
abstract class PetDatabase : RoomDatabase() {
    abstract fun petDAO(): PetDAO
    abstract fun procedureDAO(): ProcedureDAO
    abstract fun prTitleDAO(): PrTitleDAO
    abstract fun medRecord(): MedRecordDAO

    companion object {
        @Volatile
        private var Instance: PetDatabase? = null

        fun getDatabase(context: Context): PetDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, PetDatabase::class.java, "item_database")
                    .createFromAsset("databases/initial_db.db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}