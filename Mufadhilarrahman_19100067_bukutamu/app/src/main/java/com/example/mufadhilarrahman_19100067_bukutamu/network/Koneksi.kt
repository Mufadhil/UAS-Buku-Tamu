package com.example.mufadhilarrahman_19100067_bukutamu.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Koneksi {
    companion object{
        var retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.43.11/buku/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var service: ApiService = retrofit.create(ApiService::class.java)
    }
}