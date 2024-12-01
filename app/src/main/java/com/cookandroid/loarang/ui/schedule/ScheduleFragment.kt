package com.cookandroid.loarang.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.cookandroid.loarang.base.BaseFragment
import com.cookandroid.loarang.databinding.FragmentScheduleBinding
import com.cookandroid.loarang.ui.MainActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class ScheduleFragment : BaseFragment() {
    companion object {
        fun newInstance() = ScheduleFragment()
        const val TAG = "ScheduleFragment"
    }

    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!
    lateinit var context: MainActivity

    private lateinit var scheduleAdapter: ScheduleAdapter
    private var scheduleList: ArrayList<ScheduleModel> = ArrayList()

    var firebaseDatabase: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)

        scheduleAdapter = ScheduleAdapter(scheduleList, context)
        binding.scheduleList.adapter = scheduleAdapter

        lifecycleScope.launch {
            firebaseDatabase = FirebaseDatabase.getInstance()
            databaseReference = firebaseDatabase!!.getReference("content")
            databaseReference!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    scheduleList.clear()
                    for (snapshot1 in snapshot.children) {
                        val scheduleModel = snapshot1.getValue(
                            ScheduleModel::class.java
                        )
                        if (scheduleModel != null) {
                            scheduleList.add(scheduleModel)
                        }
                    }
                    scheduleAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }

        return binding.root
    }
}