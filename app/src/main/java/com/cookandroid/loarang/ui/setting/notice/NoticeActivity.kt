package com.cookandroid.loarang.ui.setting.notice

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.cookandroid.loarang.base.BaseActivity
import com.cookandroid.loarang.databinding.FragmentNoticeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class NoticeActivity : BaseActivity() {
    private val binding by lazy { FragmentNoticeBinding.inflate(layoutInflater) }

    private lateinit var noticeAdapter : NoticeAdapter
    private var noticeList : ArrayList<NoticeModel> = ArrayList()

    private var firebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        noticeAdapter = NoticeAdapter(noticeList, this)
        binding.noticeList.adapter = noticeAdapter

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
    }
}
