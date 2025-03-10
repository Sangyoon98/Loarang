package com.cookandroid.loarang.ui.setting.patch

import androidx.lifecycle.ViewModel
import com.cookandroid.loarang.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PatchViewModel @Inject constructor(
    private val repository: FirebaseRepository
) : ViewModel() {
    val patchList: StateFlow<List<PatchModel>> = repository.getDataList("patch", PatchModel::class.java)
    /*private val _patchList = MutableStateFlow<List<PatchModel>>(emptyList())
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
    }*/
}