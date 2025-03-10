package com.cookandroid.loarang.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase
) {
    fun <T> getDataList(path: String, modelClass: Class<T>): StateFlow<List<T>> {
        val dataListFlow = MutableStateFlow<List<T>>(emptyList())
        val databaseReference = firebaseDatabase.getReference(path)

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tempList = snapshot.children.mapNotNull { it.getValue(modelClass) }
                dataListFlow.value = tempList
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        return dataListFlow
    }
}