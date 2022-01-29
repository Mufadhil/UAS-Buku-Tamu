package com.example.mufadhilarrahman_19100067_bukutamu.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mufadhilarrahman_19100067_bukutamu.InsertDataActivity
import com.example.mufadhilarrahman_19100067_bukutamu.MainActivity
import com.example.mufadhilarrahman_19100067_bukutamu.R
import com.example.mufadhilarrahman_19100067_bukutamu.UpdateDataActivity
import com.example.mufadhilarrahman_19100067_bukutamu.model.DataItem
import com.example.mufadhilarrahman_19100067_bukutamu.model.ResponseActionBukutamu
import com.example.mufadhilarrahman_19100067_bukutamu.network.Koneksi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListContent (val ldata : List<DataItem?>?, val context: Context, val kondisi : String) :
    RecyclerView.Adapter<ListContent.ViewHolder>() {
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        val namaBarang = view.findViewById<TextView>(R.id.tv_nama_barang)
        val jmlBarang = view.findViewById<TextView>(R.id.tv_jumlah_barang)
        val editBarang = view.findViewById<TextView>(R.id.tv_edit)
        val deleteBarang = view.findViewById<TextView>(R.id.tv_delete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_barang, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return ldata!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = ldata?.get(position)
        holder.namaBarang.text = model?.namaBarang
        holder.jmlBarang.text = model?.jumlahBarang
        holder.editBarang.setOnClickListener {
            val i = Intent(context, UpdateDataActivity::class.java)
            i.putExtra("IDBARANG", model?.id)
            i.putExtra("NAMABARANG", model?.namaBarang)
            i.putExtra("JMLBARANG", model?.jumlahBarang)
            context.startActivity(i)
        }
        holder.deleteBarang.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Delete" + model?.namaBarang)
                .setMessage("Apakah Anda Ingin Mengahapus Data Ini?")
                .setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->

                    Koneksi.service.deleteBarang(model?.id).enqueue(object : Callback<ResponseActionBukutamu>{
                        override fun onFailure(call: Call<ResponseActionBukutamu>, t: Throwable) {
                            Log.d("pesan1", t.localizedMessage)
                        }

                        override fun onResponse(
                            call: Call<ResponseActionBukutamu>,
                            response: Response<ResponseActionBukutamu>
                        ) {
                            if(response.isSuccessful){
                                Toast.makeText(context, "Data Berhasil Dihapus", Toast.LENGTH_LONG).show()
                                notifyDataSetChanged()
                                notifyItemRemoved(position)
                                notifyItemChanged(position)
                                notifyItemRangeChanged(position, ldata!!.size)

                                if(kondisi == " InsertDataActivity"){
                                    val activity = (context as InsertDataActivity)
                                    activity.getData()
                                }else if(kondisi == " UpdateDataActivity"){
                                    val activity = (context as UpdateDataActivity)
                                    activity.getData()
                                }else{
                                    val activity = (context as MainActivity)
                                    activity.getData()
                                }
                                Log.d("bpesan", response.body().toString())

                            }
                        }
                    })
                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                    dialog.cancel()
                })
                .show()
        }
    }


}

