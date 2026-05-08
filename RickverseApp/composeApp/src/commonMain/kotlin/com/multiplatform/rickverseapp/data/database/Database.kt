package com.multiplatform.rickverseapp.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.multiplatform.rickverseapp.data.database.dao.UserPreferencesDAO
import com.multiplatform.rickverseapp.data.database.entity.CharacterOfTheDayEntity

const val DATABASE_NAME = "rickverseapp_database.db"

expect object RickverseDbCtor : RoomDatabaseConstructor<RickverseDatabase> {
    override fun initialize(): RickverseDatabase
}

@Database(entities = [CharacterOfTheDayEntity::class], version = 2)
@ConstructedBy(RickverseDbCtor::class)
abstract class RickverseDatabase: RoomDatabase() {
    abstract fun getPreferencesDao(): UserPreferencesDAO
}
