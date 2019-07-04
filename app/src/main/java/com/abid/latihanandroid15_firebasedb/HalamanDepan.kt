package com.abid.latihanandroid15_firebasedb

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log.e
import android.widget.Toast
import com.abid.latihanandroid15_firebasedb.adapter.BukuAdapter
import com.abid.latihanandroid15_firebasedb.model.BukuModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_halaman_depan.*

class HalamanDepan : AppCompatActivity(), BukuAdapter.FirebaseDataListener {

    private var bukuAdapter: BukuAdapter? = null
    private var rcView: RecyclerView? = null
    private var list: MutableList<BukuModel>? = null
    lateinit var dbref: DatabaseReference
    lateinit var helperPref: PrefsHelper
    private lateinit var fAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaman_depan)
        helperPref = PrefsHelper(this)
        rcView = findViewById(R.id.rc_view)
        list = mutableListOf()
        rcView!!.layoutManager = LinearLayoutManager(this)
        rcView!!.setHasFixedSize(true)
        fAuth = FirebaseAuth.getInstance()

        dbref = FirebaseDatabase.getInstance().getReference("dataBuku/${helperPref.getUID()}")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                e("TAG_ERROR", p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    list = ArrayList()
                    for (dataSnapshot in p0.children) {
                        val addDataAll = dataSnapshot.getValue(BukuModel::class.java)
                        addDataAll!!.setKey(dataSnapshot.key!!)
                        list!!.add(addDataAll!!)
                        bukuAdapter =
                            BukuAdapter(this@HalamanDepan, list!!)
                    }
                }
                rcView!!.adapter = bukuAdapter
            }
        })
        fab.setOnClickListener {
            startActivity(Intent(this, TambahData::class.java))
        }
        upload_Storage.setOnClickListener {
            startActivity(Intent(this,UploadFirestorage::class.java))
        }
        btn_logout.setOnClickListener {
            Toast.makeText(this, "Anda telah logout!", Toast.LENGTH_SHORT).show()
            fAuth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onDeleteData(buku: BukuModel, position: Int) {
        dbref = FirebaseDatabase.getInstance().getReference("dataBuku/${helperPref.getUID()}")
        if (dbref != null) {
            dbref.child(buku.getKey()).removeValue().addOnSuccessListener {
                Toast.makeText(this, "Data berhasil dihapus!", Toast.LENGTH_SHORT).show()
                bukuAdapter!!.notifyDataSetChanged()
            }
        }
    }

    override fun onUpdateData(buku: BukuModel, position: Int) {
        var datax = buku.getKey()
        val intent = Intent(this, TambahData::class.java)
        intent.putExtra("kode", datax)
        startActivity(intent)
    }
}
