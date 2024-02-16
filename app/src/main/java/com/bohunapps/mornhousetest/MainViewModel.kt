package com.bohunapps.mornhousetest

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bohunapps.mornhousetest.retrofit.NetworkResult
import com.bohunapps.mornhousetest.retrofit.NumsApiRepo
import com.bohunapps.mornhousetest.room.DBRepo
import com.bohunapps.mornhousetest.room.NumbersEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiRepo: NumsApiRepo,
    private val dbRepo: DBRepo,
) :
    ViewModel() {

    private val _currentNum = mutableStateOf("")
    val currentNum: State<String>
        get() = _currentNum

    private var _numInfo = mutableStateOf<String?>(null)
    val numInfo: State<String?>
        get() = _numInfo

    private val _numIsCorrect = mutableStateOf(true)
    val numIsCorrect: State<Boolean>
        get() = _numIsCorrect


    @SuppressLint("MutableCollectionMutableState")
    private var _numsFromDB = MutableStateFlow(listOf<NumbersEntity>())
    val numsFromDB: Flow<List<NumbersEntity?>>
        get() = _numsFromDB


    private fun isDigit(value: String): Boolean {
        return value.all { it.isDigit() }
    }

    private fun validateNumber(number: String) {
        _numIsCorrect.value = !(number.isEmpty() || !isDigit(number))
    }

    fun setCurrentNum(num: String) {
        _currentNum.value = num
        validateNumber(num)
    }

    fun setNumberInfo(info: String){
        _numInfo.value = info
    }


    fun clear(){
        _currentNum.value = ""
        _numInfo.value =""
        _numIsCorrect.value = true
    }

    fun getNumInfo() {
        viewModelScope.launch {
            try {
                apiRepo.getNumInfo(_currentNum.value)
                delay(100)
                insertToDB()
            } catch (e: Exception) {
                Log.e("ViewModel", "Failed to get num info", e)
            }

        }
    }

    suspend fun updateFromDB(){
        dbRepo.getNums()
        delay(500)
        dbRepo.nums.collect{
            _numsFromDB.value = it
        }
    }


    fun getRandomInfo() {
        viewModelScope.launch {
            try {
                apiRepo.getRandomInfo()
                delay(100)
                insertToDB()
            } catch (e: Exception) {
                Log.e("ViewModel", "Failed to get random num info", e)
            }
        }

    }

    private suspend fun insertToDB() {
        val info = apiRepo.numInfo
        var got = 0
        info.takeWhile { got!=1 }.collect { result ->
            if (result is NetworkResult.Success) {
                got = 1
                _numInfo.value = result.data
                _numInfo.value?.let { data ->
                    withContext(Dispatchers.IO) {
                        dbRepo.insertToDb(data)
                        apiRepo.clearFlow()
                    }
                }
            }
        }
    }
}