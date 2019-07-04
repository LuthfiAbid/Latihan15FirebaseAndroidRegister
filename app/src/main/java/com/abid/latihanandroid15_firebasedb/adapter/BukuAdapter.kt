package com.abid.latihanandroid15_firebasedb

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import java.text.FieldPosition

class BukuAdapter : RecyclerView.Adapter<BukuAdapter.BukuViewHolder> {
    lateinit var mContext: Context
    lateinit var itemBuku: List<BukuModel>
    lateinit var listener: FirebaseDataListener

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BukuViewHolder {
        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.activity_show_data, p0, false)
        val bukuViewHolder = BukuViewHolder(view)
        return bukuViewHolder
    }

    override fun getItemCount(): Int {
        return itemBuku.size
    }

    override fun onBindViewHolder(p0: BukuViewHolder, p1: Int) {
        val bukuModel: BukuModel = itemBuku.get(p1)
        p0.tv_nama.text = bukuModel.getNama()
        p0.tv_tanggal.text = bukuModel.getTanggal()
        p0.tv_judul.text = bukuModel.getJudul()
        p0.tv_deskripsi.text = bukuModel.getDesc()
        p0.ll_content.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                val builder = AlertDialog.Builder(mContext)
                builder.setMessage("Pilih Operasi Data")
                builder.setPositiveButton("Update") { dialog, i ->
                    listener.onUpdateData(bukuModel, p1)
                }
                builder.setNegativeButton("Delete") { dialog, i ->
                    listener.onDeleteData(bukuModel, p1)
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
                return true
            }
        })
        p0.rc_view2.setOnClickListener {
            Toast.makeText(mContext, "Contoh touch listener", Toast.LENGTH_SHORT).show()
        }
    }

    constructor(mContext: Context, list: List<BukuModel>) {
        this.mContext = mContext
        this.itemBuku = list
        listener = mContext as HalamanDepan
    }

    inner class BukuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var rc_view2: LinearLayout
        var tv_nama: TextView
        var tv_tanggal: TextView
        var tv_judul: TextView
        var tv_deskripsi: TextView
        var ll_content: LinearLayout

        init {
            rc_view2 = itemView.findViewById(R.id.ll_content)
            tv_nama = itemView.findViewById(R.id.tv_nama)
            tv_tanggal = itemView.findViewById(R.id.tv_tanggal)
            tv_judul = itemView.findViewById(R.id.tv_title)
            tv_deskripsi = itemView.findViewById(R.id.tv_desc)
            ll_content = itemView.findViewById(R.id.ll_content)
        }
    }

    interface FirebaseDataListener {
        fun onDeleteData(buku: BukuModel, position: Int)
        fun onUpdateData(buku: BukuModel, position: Int)
    }
}