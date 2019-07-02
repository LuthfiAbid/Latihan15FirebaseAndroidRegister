package com.abid.latihanandroid15_firebasedb

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class BukuAdapter : RecyclerView.Adapter<BukuAdapter.BukuViewHolder> {
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
//        p0.tv_deskripsi.text = bukuModel.getDeskripsi()
        p0.rc_view2.setOnClickListener {
            Toast.makeText(mContext, "Contoh touch listener", Toast.LENGTH_SHORT).show()
        }
    }

    var mContext: Context
    var itemBuku: List<BukuModel>

    constructor(mContext: Context, list: List<BukuModel>) {
        this.mContext = mContext
        this.itemBuku = list
    }

    inner class BukuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var rc_view2: LinearLayout
        var tv_nama: TextView
        var tv_tanggal: TextView
        var tv_judul: TextView
        var tv_deskripsi: TextView


        init {
            rc_view2 = itemView.findViewById(R.id.ll_content)
            tv_nama = itemView.findViewById(R.id.tv_nama)
            tv_tanggal = itemView.findViewById(R.id.tv_tanggal)
            tv_judul = itemView.findViewById(R.id.tv_title)
            tv_deskripsi = itemView.findViewById(R.id.tv_desc)
        }
    }
}