package com.bohunapps.mornhousetest.room


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class DbRepoImpl @Inject constructor(
    private val dao: NumbersDao
): DBRepo {

    override var nums: Flow<List<NumbersEntity>> =  MutableStateFlow(emptyList())

    override suspend fun insertToDb(info: String) {
        dao.insertNumber(NumbersEntity(0, info = info))
    }

    override suspend fun getNums(): Flow<List<NumbersEntity>> {
        val fl = dao.getAll()
        nums = fl
        return fl
    }

    override suspend fun getNum(id: Int): Flow<NumbersEntity> = dao.getNumberInfo(id)


}