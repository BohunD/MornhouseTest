package com.bohunapps.mornhousetest.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NumbersEntity::class], version = 1)
abstract class NumbersDB: RoomDatabase() {
    abstract fun numbersDao(): NumbersDao
}