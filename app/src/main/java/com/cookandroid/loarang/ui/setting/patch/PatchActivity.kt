package com.cookandroid.loarang.ui.setting.patch

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.cookandroid.loarang.base.BaseActivity
import com.cookandroid.loarang.databinding.FragmentPatchBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class PatchActivity : BaseActivity() {
    private val binding by lazy { FragmentPatchBinding.inflate(layoutInflater) }

    private lateinit var patchAdapter : PatchAdapter
    private var patchList : ArrayList<PatchModel> = ArrayList()

    private var firebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        patchAdapter = PatchAdapter(patchList, this)
        binding.patchList.adapter = patchAdapter

        lifecycleScope.launch {
            firebaseDatabase = FirebaseDatabase.getInstance()
            databaseReference = firebaseDatabase!!.getReference("patch")
            databaseReference!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    patchList.clear()
                    for (snapshot1 in snapshot.children) {
                        val patchModel = snapshot1.getValue(
                            PatchModel::class.java
                        )
                        if (patchModel != null) {
                            patchList.add(patchModel)
                        }
                    }
                    patchAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }
}
