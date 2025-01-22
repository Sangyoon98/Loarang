package com.cookandroid.loarang.ui.setting.patch

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cookandroid.loarang.ui.theme.AppTheme
import com.cookandroid.loarang.ui.theme.AppTypography
import com.cookandroid.loarang.ui.theme.backgroundGrey
import com.cookandroid.loarang.ui.theme.backgroundListItem
import com.cookandroid.loarang.ui.theme.textColor

class PatchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                PatchScreen()
            }
        }
    }
}

@Composable
fun PatchScreen() {
    val viewModel: PatchViewModel = viewModel() // ViewModel 생성
    val patchList by viewModel.patchList.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.backgroundGrey
    ) {
        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(patchList) { patch ->
                PatchItem(patch)
            }
        }
    }
}

@Composable
private fun PatchItem(patch: PatchModel) {
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
                text = patch.name ?: "no name",
                style = AppTypography.titleLarge,
                color = MaterialTheme.colorScheme.textColor
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = patch.context_patch ?: "no context",
                style = AppTypography.bodyMedium,
                color = MaterialTheme.colorScheme.textColor
            )
        }
    }
}


@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES, name = "PatchPreview (Dark)")
@Composable
private fun PatchPreview() {
    AppTheme {
        PatchScreen()
    }
}

@Preview
@Composable
private fun PatchItemPreview() {
    AppTheme {
        PatchItem(PatchModel(name = "[업데이트]", context_patch = "업데이트 내역"))
    }
}
