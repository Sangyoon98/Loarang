package com.cookandroid.loarang.ui.schedule

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cookandroid.loarang.R
import com.cookandroid.loarang.base.BaseFragment
import com.cookandroid.loarang.databinding.FragmentScheduleBinding
import com.cookandroid.loarang.ui.main.MainActivity
import com.cookandroid.loarang.ui.theme.AppTheme
import com.cookandroid.loarang.ui.theme.AppTypography
import com.cookandroid.loarang.ui.theme.backgroundGrey
import com.cookandroid.loarang.ui.theme.backgroundListItem
import com.cookandroid.loarang.ui.theme.textColor
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

    private var scheduleList = mutableStateListOf<ScheduleModel>()
    private var firebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null

    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!
    lateinit var context: MainActivity

    private lateinit var scheduleAdapter: ScheduleAdapter
    private var scheduleListTemp: ArrayList<ScheduleModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)

        scheduleAdapter = ScheduleAdapter(scheduleListTemp, context)
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

@Composable
private fun ScheduleScreen(scheduleList: List<ScheduleModel>) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.backgroundGrey
    ) {
        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(scheduleList) { schedule ->
                ScheduleItem(schedule)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ScheduleItem(schedule: ScheduleModel) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .wrapContentHeight()
            .shadow(12.dp, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.backgroundListItem)
    ) {
        Row(modifier = Modifier
            .padding(16.dp)
        ) {
            GlideImage(
                model = schedule.image,
                contentDescription = context.getString(R.string.schedule_content_image),
                modifier = Modifier.size(70.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = schedule.location ?: "no location",
                    style = AppTypography.bodyMedium,
                    color = MaterialTheme.colorScheme.textColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = schedule.name ?: "no name",
                    style = AppTypography.bodyMedium,
                    color = MaterialTheme.colorScheme.textColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = schedule.time ?: "no time",
                    style = AppTypography.bodyMedium,
                    color = MaterialTheme.colorScheme.textColor
                )
            }
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES, name = "SchedulePreview (Dark)")
@Composable
private fun SchedulePreview() {
    AppTheme {
        ScheduleScreen(
            listOf(
                ScheduleModel(location = "출현 지역 : 베른 남부", name = "필드 보스", time = "12:00, 13:00, 14:00"),
                ScheduleModel(location = "출현 지역 : 베른 남부", name = "필드 보스", time = "12:00, 13:00, 14:00"),
                ScheduleModel(location = "출현 지역 : 베른 남부", name = "필드 보스", time = "12:00, 13:00, 14:00"),
                ScheduleModel(location = "출현 지역 : 베른 남부", name = "필드 보스", time = "12:00, 13:00, 14:00")
            )
        )
    }
}

@Preview
@Composable
private fun ScheduleItemPreview() {
    AppTheme {
        ScheduleItem(ScheduleModel(location = "출현 지역 : 베른 남부", name = "필드 보스", time = "12:00, 13:00, 14:00"))
    }
}