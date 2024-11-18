package com.cookandroid.loarang.ui.notice

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.cookandroid.loarang.base.BaseActivity
import com.cookandroid.loarang.databinding.FragmentNoticeBinding
import com.cookandroid.loarang.ui.setting.SettingFragment.Companion.TAG
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.launch

class NoticeActivity : BaseActivity() {
    private val binding by lazy { FragmentNoticeBinding.inflate(layoutInflater) }

    private lateinit var noticeAdapter : NoticeAdapter
    private var noticeList : ArrayList<NoticeModel> = ArrayList()

    private lateinit var database: DatabaseReference

    private var firebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        noticeAdapter = NoticeAdapter(noticeList, this)
        binding.noticeList.adapter = noticeAdapter

        /*database = Firebase.database.reference.child("notice")

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val post = snapshot.children
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", error.toException())
            }

        }

        database.addValueEventListener(postListener)*/

//        arrayList = ArrayList()
//
//        val linearLayoutManager = LinearLayoutManager(this)
//        binding.noticeList.setLayoutManager(linearLayoutManager)
//
//        adapter = SettingNoticeListItemAdapter(noticeList, this)
//        binding.noticeList.layoutManager = LinearLayoutManager(this)
//        binding.noticeList.adapter = adapter

        lifecycleScope.launch {
            firebaseDatabase = FirebaseDatabase.getInstance()
            databaseReference = firebaseDatabase!!.getReference("notice")
            databaseReference!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    noticeList.clear()
                    for (snapshot1 in snapshot.children) {
                        val noticeModel = snapshot1.getValue(
                            NoticeModel::class.java
                        )
                        if (noticeModel != null) {
                            noticeList.add(noticeModel)
                        }
                    }
                    noticeAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }

        /*adapter = SettingNoticeListItemAdapter(arrayList, this)
        binding.noticeList.setAdapter(adapter)*/
    }
}
