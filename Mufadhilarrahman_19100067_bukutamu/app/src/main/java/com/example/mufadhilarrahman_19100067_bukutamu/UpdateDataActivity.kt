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
import kotlinx.android.synthetic.main.activity_update_data.*
import kotlinx.android.synthetic.main.activity_update_data.et_jumlah_barang
import kotlinx.android.synthetic.main.activity_update_data.et_nama_barang
import kotlinx.android.synthetic.main.activity_update_data.rv_data_barang
import kotlinx.android.synthetic.main.activity_update_data.toolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class UpdateDataActivity : AppCompatActivity(){
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)
        toolbar.title = "UPDATE DATA"
        toolbar.setTitleTextColor(Color.WHITE)

        val i = intent
        val idBarang = i.getStringExtra("IDBARANG")
        val namaBarang = i.getStringExtra("NAMABARANG")
        val jumlahBarang = i.getStringExtra("JUMLAHBARANG")

        et_nama_barang.setText(namaBarang)
        et_jumlah_barang.setText(jumlahBarang)
        btn_submit.setOnClickListener {
            val etNamaBarang = et_nama_barang.text
            val etJmlBarang = et_jumlah_barang.text
            if (etJmlBarang.isEmpty()){
                Toast.makeText(this@UpdateDataActivity, "Jumlah Barang Tidak Boleh Kosong", Toast.LENGTH_LONG).show()
            }else if (etNamaBarang.isEmpty()){
                Toast.makeText(this@UpdateDataActivity, "Nama Barang Tidak Boleh Kosong", Toast.LENGTH_LONG).show()
            }else{
                actionData(idBarang.toString(), etNamaBarang.toString(), etJmlBarang.toString())
            }
        }
        btn_back.setOnClickListener {
            finish()
        }
        getData()
    }
    fun actionData(id : String, namaBarang : String, jmlBarang : String){
        Koneksi.service.updateBarang(id, namaBarang, jmlBarang).enqueue(object : Callback<ResponseActionBukutamu>{
            override fun onFailure(call: Call<ResponseActionBukutamu>, t: Throwable) {
                Log.d("pesan1", t.localizedMessage)
            }

            override fun onResponse(
                call: Call<ResponseActionBukutamu>,
                response: Response<ResponseActionBukutamu>
            ) {
                if (response.isSuccessful){
                    Toast.makeText(this@UpdateDataActivity, "data berhasil diupdate", Toast.LENGTH_LONG).show()
                    getData()
                }
            }
        })
    }
    fun getData(){
        Koneksi.service.getBarang().enqueue(object : Callback<ResponseBukutamu>{
            override fun onFailure(call: Call<ResponseBukutamu>, t: Throwable) {
                Log.d("pesan1", t.localizedMessage)
            }

            override fun onResponse(
                call: Call<ResponseBukutamu>,
                response: Response<ResponseBukutamu>
            ) {
                if (response.isSuccessful){
                    val dataBody = response.body()
                    val datacontent = dataBody!!.data

                    val rvAdapter = ListContent(datacontent, this@UpdateDataActivity, "UpdateDataActivity")


                    rv_data_barang.apply {
                        adapter = rvAdapter
                        layoutManager = LinearLayoutManager(this@UpdateDataActivity)
                    }

                }
            }

        })
    }

}