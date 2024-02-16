package com.bohunapps.mornhousetest.retrofit

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NumsApiRepo(private val api: NumsApi) {
    val numInfo = MutableStateFlow<NetworkResult<String?>>(NetworkResult.Initial())

    fun getNumInfo(num: String){
        api.getNum(num).enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    numInfo.value = NetworkResult.Success(response.body())
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                t.printStackTrace()
                numInfo.value = t.message?.let { NetworkResult.Error(it) }!!
                Log.e("ERror(", t.message.toString())
            }

        })
    }

    fun clearFlow(){
        numInfo.value = NetworkResult.Initial()
    }

    fun getRandomInfo(){
        api.getRandom().enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    numInfo.value = NetworkResult.Success(response.body())
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                t.printStackTrace()
                numInfo.value = t.message?.let { NetworkResult.Error(it) }!!
                Log.e("ERror(", t.message.toString())
            }

        })
    }
}