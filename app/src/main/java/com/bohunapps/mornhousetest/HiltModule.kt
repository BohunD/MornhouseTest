package com.bohunapps.mornhousetest

import android.content.Context
import androidx.room.Room
import com.bohunapps.mornhousetest.Constants.DB
import com.bohunapps.mornhousetest.retrofit.ApiService
import com.bohunapps.mornhousetest.retrofit.NumsApiRepo
import com.bohunapps.mornhousetest.room.DBRepo
import com.bohunapps.mornhousetest.room.DbRepoImpl
import com.bohunapps.mornhousetest.room.NumbersDB
import com.bohunapps.mornhousetest.room.NumbersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {
    @Provides
    fun provideApiRepo() = NumsApiRepo(ApiService.api)

    @Provides
    fun provideNumsDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, NumbersDB::class.java, DB).build()

    @Provides
    fun provideCharacterDao(numbersDB: NumbersDB) = numbersDB.numbersDao()

    @Provides
    fun provideDbRepoImpl(numbersDao: NumbersDao): DBRepo =
        DbRepoImpl(numbersDao)
}