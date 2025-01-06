package com.cookandroid.loarang.ui.setting.notice

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
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

class NoticeActivity : ComponentActivity() {
    private var noticeList = mutableStateListOf<NoticeModel>()
    private var firebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                NoticeScreen(noticeList)
            }
        }

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
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }

    @Composable
    fun NoticeScreen(noticeList: List<NoticeModel>) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.backgroundGrey
        ) {
            LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                items(noticeList) { notice ->
                    NoticeItem(notice)
                }
            }
        }
    }

    @Composable
    fun NoticeItem(notice: NoticeModel) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .shadow(12.dp, RoundedCornerShape(8.dp)),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.backgroundListItem)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = notice.name ?: "no name",
                    style = AppTypography.titleLarge,
                    color = MaterialTheme.colorScheme.textColor
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = notice.context_notice ?: "no context",
                    style = AppTypography.bodyMedium,
                    color = MaterialTheme.colorScheme.textColor
                )
            }
        }
    }

    @Preview
    @Preview(
        uiMode = UI_MODE_NIGHT_YES,
        name = "NoticePreview (Dark)"
    )
    @Composable
    fun NoticePreview() {
        AppTheme {
            NoticeScreen(
                listOf(
                    NoticeModel(name = "[로아랑 공지]", context_notice = "공지 사항 내용 테스트"),
                    NoticeModel(name = "[로아랑 공지]", context_notice = "공지 사항 내용 테스트"),
                    NoticeModel(name = "[로아랑 공지]", context_notice = "공지 사항 내용 테스트"),
                    NoticeModel(name = "[로아랑 공지]", context_notice = "공지 사항 내용 테스트")
                )
            )
        }
    }

    @Preview
    @Composable
    fun NoticeItemPreview() {
        AppTheme {
            NoticeItem(NoticeModel(name = "[로아랑 공지]", context_notice = "공지 사항 내용 테스트"))
        }
    }
}
