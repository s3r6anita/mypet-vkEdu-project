package com.f4.mypet.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.f4.mypet.data.db.daos.MedRecordDAO
import com.f4.mypet.data.db.daos.PetDAO
import com.f4.mypet.data.db.daos.PrTitleDAO
import com.f4.mypet.data.db.daos.ProcedureDAO
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
        ProcedureType::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PetDatabase : RoomDatabase() {
    abstract fun petDAO(): PetDAO
    abstract fun procedureDAO(): ProcedureDAO
    abstract fun prTitleDAO(): PrTitleDAO
    abstract fun medRecordDAO(): MedRecordDAO
}