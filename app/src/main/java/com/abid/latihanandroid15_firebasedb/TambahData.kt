package com.abid.latihanandroid15_firebasedb

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_tambah_data.*

class TambahData : AppCompatActivity() {

    lateinit var dbRef: DatabaseReference
    lateinit var helperPref: PrefsHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_data)

        btn_submit.setOnClickListener {
            val nama = et_nama.text.toString()
            val tgl = et_tanggal.text.toString()
            val judul = et_judul.text.toString()
            val desc = et_desc.text.toString()

            helperPref = PrefsHelper(this)
            if (nama.isNotEmpty() || judul.isNotEmpty() || desc.isNotEmpty() || tgl
                    .isNotEmpty()
            ) {
                simpanToFirebase(nama, judul, tgl, desc)
            } else {
                Toast.makeText(this, "Inputan harus diisi!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun simpanToFirebase(nama: String, judul: String, tgl: String, desc: String) {
        val uidUser = helperPref.getUID()
        val counterId = helperPref.getCounterId()
        dbRef = FirebaseDatabase.getInstance().getReference("dataBuku/$uidUser/$counterId")
        dbRef.child("/id").setValue(uidUser)
        dbRef.child("/nama").setValue(nama)
        dbRef.child("/judul").setValue(judul)
        dbRef.child("/tanggal").setValue(tgl)
        dbRef.child("/desc").setValue(desc)
        Toast.makeText(this, "Data berhasil tambah!", Toast.LENGTH_SHORT).show()
        helperPref.saveCounterId(counterId + 1)
        startActivity(Intent(this, HalamanDepan::class.java))
    }
}
