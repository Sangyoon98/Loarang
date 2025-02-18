package com.cookandroid.loarang.ui.setting.patch

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PatchViewModel : ViewModel() {
    private val _patchList = MutableStateFlow<List<PatchModel>>(emptyList())
    val patchList: StateFlow<List<PatchModel>> get() = _patchList

    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference: DatabaseReference = firebaseDatabase.getReference("patch")

    init {
        fetchPatchData()
    }

    private fun fetchPatchData() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tempList = mutableListOf<PatchModel>()
                for (snapshot1 in snapshot.children) {
                    val patchModel = snapshot1.getValue(PatchModel::class.java)
                    if (patchModel != null) {
                        tempList.add(patchModel)
                    }
                }
                _patchList.value = tempList
            }

            override fun onCancelled(error: DatabaseError) {
                // 에러 처리 (필요 시)
            }
        })
    }
}