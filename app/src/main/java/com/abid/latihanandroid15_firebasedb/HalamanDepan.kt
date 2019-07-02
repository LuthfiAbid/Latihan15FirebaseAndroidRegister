package com.abid.latihanandroid15_firebasedb

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log.e
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_halaman_depan.*

class HalamanDepan : AppCompatActivity() {

    private var bukuAdapter: BukuAdapter? = null
    private var rcView: RecyclerView? = null
    private var list: MutableList<BukuModel> = ArrayList<BukuModel>()
    lateinit var dbref: DatabaseReference
    lateinit var helperPref: PrefsHelper
    private lateinit var fAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaman_depan)
        helperPref = PrefsHelper(this)
        rcView = findViewById(R.id.rc_view)
        rcView!!.layoutManager = LinearLayoutManager(this)
        rcView!!.setHasFixedSize(true)
        fAuth = FirebaseAuth.getInstance()

        dbref = FirebaseDatabase.getInstance().getReference("dataBuku/${helperPref.getUID()}")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                e("TAG_ERROR", p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (dataSnapshot in p0.children) {
                    val addDataAll = dataSnapshot.getValue(BukuModel::class.java)
                    list.add(addDataAll!!)
                }
                bukuAdapter = BukuAdapter(applicationContext, list)
                rcView!!.adapter = bukuAdapter
            }

        })
        fab.setOnClickListener {
            startActivity(Intent(this, TambahData::class.java))
        }
        btn_logout.setOnClickListener {
            fAuth.signOut()
            finish()
        }
    }
}
