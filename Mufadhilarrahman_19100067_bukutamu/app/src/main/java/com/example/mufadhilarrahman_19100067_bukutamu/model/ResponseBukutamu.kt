package com.example.mufadhilarrahman_19100067_bukutamu.model


import com.google.gson.annotations.SerializedName

class ResponseBukutamu(

    @field:SerializedName( "pesan")
    val pesan: String? = null,

    @field:SerializedName("data")
    val data: List<DataItem?>? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)

class DataItem(

    @field:SerializedName("Nama_barang")
    val namaBarang: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("Jumlah_barang")
    val jumlahBarang: String? =null
)