package com.f4.mypet.data.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.f4.mypet.data.db.daos.FrequencyDAO
import com.f4.mypet.data.db.daos.MedRecordDAO
import com.f4.mypet.data.db.daos.PetDAO
import com.f4.mypet.data.db.daos.PrTitleDAO
import com.f4.mypet.data.db.daos.ProcedureDAO
import com.f4.mypet.data.db.entities.Frequency
import com.f4.mypet.data.db.entities.MedRecord
import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.data.db.entities.Procedure
import com.f4.mypet.data.db.entities.ProcedureTitle
import com.f4.mypet.data.db.entities.ProcedureType


@Database(
    entities = [
        Pet::class,
        Procedure::class,
        MedRecord::class,
        ProcedureTitle::class,
        ProcedureType::class,
        Frequency::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PetDatabase : RoomDatabase() {
    abstract fun petDAO(): PetDAO
    abstract fun procedureDAO(): ProcedureDAO
    abstract fun prTitleDAO(): PrTitleDAO
    abstract fun medRecordDAO(): MedRecordDAO
    abstract fun frequencyDAO(): FrequencyDAO

    companion object {
        @Volatile
        private var Instance: PetDatabase? = null

        fun getDatabase(app: Application): PetDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    app.applicationContext,
                    PetDatabase::class.java,
                    "pet_database"
                )
                    .createFromAsset("databases/initial_db.db")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
