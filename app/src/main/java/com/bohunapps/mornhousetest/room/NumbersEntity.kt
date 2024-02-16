package com.bohunapps.mornhousetest.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bohunapps.mornhousetest.Constants
import com.bohunapps.mornhousetest.Constants.NUMS_TABLE

@Entity(tableName = NUMS_TABLE)
data class NumbersEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val info: String?
        )