package com.abid.latihanandroid15_firebasedb

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.abid.latihanandroid15_firebasedb.model.BukuModel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_tambah_data.*

class TambahData : AppCompatActivity() {

    lateinit var dbRef: DatabaseReference
    lateinit var helperPref: PrefsHelper
    private var list: MutableList<BukuModel>? = null
    var datax: String? = null
    var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_data)

        datax = intent.getStringExtra("kode")
        helperPref = PrefsHelper(this)
        counter = helperPref.getCounterId()
        if (datax != null) {
            showDataFromDB()
            counter = datax!!.toInt()
        }

        btn_submit.setOnClickListener {
            val nama = et_nama.text.toString()
            val tgl = et_tanggal.text.toString()
            val judul = et_judul.text.toString()
            val desc = et_desc.text.toString()
            helperPref = PrefsHelper(this)
            if (nama.isNotEmpty() || judul.isNotEmpty() || desc.isNotEmpty() || tgl.isNotEmpty()) {
                simpanToFirebase(nama, judul, tgl, desc)
                if (datax == null) {
                    helperPref.saveCounterId(counter + 1)
                }
            } else {
                Toast.makeText(this, "Inputan harus diisi!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun simpanToFirebase(nama: String, judul: String, tgl: String, desc: String) {
        val uidUser = helperPref.getUID()
        dbRef = FirebaseDatabase.getInstance().getReference("dataBuku/$uidUser/$counter")
        dbRef.child("/id").setValue(uidUser)
        dbRef.child("/nama").setValue(nama)
        dbRef.child("/judul").setValue(judul)
        dbRef.child("/tanggal").setValue(tgl)
        dbRef.child("/desc").setValue(desc)
        Toast.makeText(this, "Data berhasil tambah!", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, HalamanDepan::class.java))
    }

    fun showDataFromDB() {
        dbRef = FirebaseDatabase.getInstance().getReference("dataBuku/${helperPref.getUID()}")
        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (data in p0.children) {
                    et_nama.setText(data.child("nama").value.toString())
                    et_judul.setText(data.child("judul").value.toString())
                    et_tanggal.setText(data.child("tanggal").value.toString())
                    et_desc.setText(data.child("desc").value.toString())
                }
            }
        })
    }
}
