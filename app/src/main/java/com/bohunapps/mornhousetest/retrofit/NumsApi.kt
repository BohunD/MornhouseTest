package com.bohunapps.mornhousetest.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NumsApi {
    @GET("{num}")
    fun getNum(@Path("num") num: String): Call<String>

    @GET("random/math")
    fun getRandom(): Call<String>
}