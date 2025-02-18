package com.cookandroid.loarang.ui.schedule

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.cookandroid.loarang.R
import com.cookandroid.loarang.ui.theme.AppTheme
import com.cookandroid.loarang.ui.theme.AppTypography
import com.cookandroid.loarang.ui.theme.backgroundGrey
import com.cookandroid.loarang.ui.theme.backgroundListItem
import com.cookandroid.loarang.ui.theme.textColor

@Composable
fun ScheduleScreen(name: String, modifier: Modifier = Modifier) {
    val viewModel: ScheduleViewModel = viewModel() // ViewModel 생성
    val scheduleList by viewModel.scheduleList.collectAsState()

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
                modifier = Modifier.size(70.dp),
                contentScale = ContentScale.Crop
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
        ScheduleScreen(name = "Schedule")
    }
}

@Preview
@Composable
private fun ScheduleItemPreview() {
    AppTheme {
        ScheduleItem(ScheduleModel(location = "출현 지역 : 베른 남부", name = "필드 보스", time = "12:00, 13:00, 14:00"))
    }
}