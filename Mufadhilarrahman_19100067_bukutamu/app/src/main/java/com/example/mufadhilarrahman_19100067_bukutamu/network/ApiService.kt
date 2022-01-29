package com.example.mufadhilarrahman_19100067_bukutamu.network

import com.example.mufadhilarrahman_19100067_bukutamu.model.ResponseActionBukutamu
import com.example.mufadhilarrahman_19100067_bukutamu.model.ResponseAdmin
import com.example.mufadhilarrahman_19100067_bukutamu.model.ResponseBukutamu
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
interface ApiService {
    @GET("read.php")
    fun getBarang(): Call<ResponseBukutamu>

    @FormUrlEncoded
    @POST("create.php")
    fun insertBarang(
        @Field("Nama_barang") namaBarang: String?,
        @Field("Jumlah_barang") jmlBarang: String?
    ): Call<ResponseActionBukutamu>

    @FormUrlEncoded
    @POST("update.php")
    fun updateBarang(
        @Field("id") id: String?,
        @Field("Nama_barang") namaBarang: String?,
        @Field("Jumlah_barang") jmlBarang: String?
    ): Call<ResponseActionBukutamu>

    @FormUrlEncoded
    @POST("delete.php")
    fun deleteBarang(
        @Field("id") id: String?
    ): Call<ResponseActionBukutamu>

    @FormUrlEncoded
    @POST("login.php")
    fun logIn(
        @Field("Username") Username : String?,
        @Field("Password") Password : String?
    ):Call<ResponseAdmin>

    @FormUrlEncoded
    @POST("register.php")
    fun register(
        @Field("Username") Username : String?,
        @Field("Password") Password : String?
    ):Call<ResponseAdmin>
}