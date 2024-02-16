package com.bohunapps.mornhousetest.room

import androidx.room.RoomDatabase
import com.bohunapps.mornhousetest.room.NumbersDB
import com.bohunapps.mornhousetest.room.NumbersDao
import kotlinx.coroutines.flow.Flow

interface DBRepo {
    var nums : Flow<List<NumbersEntity>>
    suspend fun insertToDb(info: String)
    suspend fun getNums(): Flow<List<NumbersEntity>>
    suspend fun getNum(id: Int): Flow<NumbersEntity>

}