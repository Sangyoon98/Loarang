package com.cookandroid.loarang.ui.schedule

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ScheduleViewModel : ViewModel() {
    private val _scheduleList = MutableStateFlow<List<ScheduleModel>>(emptyList())
    val scheduleList: StateFlow<List<ScheduleModel>> get() = _scheduleList

    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference: DatabaseReference = firebaseDatabase.getReference("content")

    init {
        fetchScheduleData()
    }

    private fun fetchScheduleData() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tempList = mutableListOf<ScheduleModel>()
                for (snapshot1 in snapshot.children) {
                    val scheduleModel = snapshot1.getValue(ScheduleModel::class.java)
                    if (scheduleModel != null) {
                        tempList.add(scheduleModel)
                    }
                }
                _scheduleList.value = tempList
            }

            override fun onCancelled(error: DatabaseError) {
                // 에러 처리 (필요 시)
            }
        })
    }
}