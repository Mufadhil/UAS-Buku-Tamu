package com.example.mufadhilarrahman_19100067_bukutamu
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mufadhilarrahman_19100067_bukutamu.adapter.ListContent
import com.example.mufadhilarrahman_19100067_bukutamu.model.ResponseActionBukutamu
import com.example.mufadhilarrahman_19100067_bukutamu.model.ResponseBukutamu
import com.example.mufadhilarrahman_19100067_bukutamu.network.Koneksi
import kotlinx.android.synthetic.main.activity_insert_data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class InsertDataActivity: AppCompatActivity()  {
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_data)
        toolbar.title = "INSERT DATA"
        toolbar.setTitleTextColor(Color.WHITE)

        btn_submit.setOnClickListener {
            val etNamaBarang = et_nama_barang.text
            val etJmlBarang = et_jumlah_barang.text
            if (etJmlBarang.isEmpty()) {
                Toast.makeText(
                    this@InsertDataActivity,
                    "Jumlah Barang Tidak Boleh Kosong",
                    Toast.LENGTH_LONG
                ).show()
            } else if (etNamaBarang.isEmpty()) {
                Toast.makeText(
                    this@InsertDataActivity,
                    "Nama Barang Tidak Boleh Kosong",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                actionData(etNamaBarang.toString(), etJmlBarang.toString())
            }
        }

        btn_clean.setOnClickListener {
            formClear()
        }
        getData()
    }

    fun formClear() {
        et_nama_barang.text.clear()
        et_jumlah_barang.text.clear()

    }

    fun actionData(namaBarang: String, jmlBarang: String) {
        Koneksi.service.insertBarang(namaBarang, jmlBarang)
            .enqueue(object : Callback<ResponseActionBukutamu> {
                override fun onFailure(call: Call<ResponseActionBukutamu>, t: Throwable) {
                    Log.d("pesan1", t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<ResponseActionBukutamu>,
                    response: Response<ResponseActionBukutamu>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@InsertDataActivity,
                            "data berhasil disimpan",
                            Toast.LENGTH_LONG
                        ).show()
                        formClear()
                        getData()
                    }
                }
            })
    }

    fun getData() {
        Koneksi.service.getBarang().enqueue(object : Callback<ResponseBukutamu> {
            override fun onFailure(call: Call<ResponseBukutamu>, t: Throwable) {
                Log.d("pesan1", t.localizedMessage)
            }

            override fun onResponse(
                call: Call<ResponseBukutamu>,
                response: Response<ResponseBukutamu>
            ) {
                if (response.isSuccessful) {
                    val dataBody = response.body()
                    val datacontent = dataBody!!.data

                    val rvAdapter = ListContent(datacontent, this@InsertDataActivity, "InsertDataActivity")

                    rv_data_barang.apply {
                        adapter = rvAdapter
                        layoutManager = LinearLayoutManager(this@InsertDataActivity)
                    }
                }
            }
        })
    }
}