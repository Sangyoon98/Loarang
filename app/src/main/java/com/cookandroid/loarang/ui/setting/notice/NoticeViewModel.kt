package com.cookandroid.loarang.ui.setting.notice

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NoticeViewModel : ViewModel() {
    private val _noticeList = MutableStateFlow<List<NoticeModel>>(emptyList())
    val noticeList: StateFlow<List<NoticeModel>> get() = _noticeList

    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference: DatabaseReference = firebaseDatabase.getReference("notice")

    init {
        fetchNoticeData()
    }

    private fun fetchNoticeData() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tempList = mutableListOf<NoticeModel>()
                for (snapshot1 in snapshot.children) {
                    val noticeModel = snapshot1.getValue(NoticeModel::class.java)
                    if (noticeModel != null) {
                        tempList.add(noticeModel)
                    }
                }
                _noticeList.value = tempList
            }

            override fun onCancelled(error: DatabaseError) {
                // 에러 처리 (필요 시)
            }
        })
    }
}