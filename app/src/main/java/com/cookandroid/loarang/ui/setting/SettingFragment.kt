package com.cookandroid.loarang.ui.setting

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cookandroid.loarang.R
import com.cookandroid.loarang.ui.main.MainViewModel
import com.cookandroid.loarang.ui.setting.patch.PatchActivity
import com.cookandroid.loarang.ui.setting.notice.NoticeActivity
import com.cookandroid.loarang.ui.theme.AppTheme
import com.cookandroid.loarang.ui.theme.backgroundGrey
import com.cookandroid.loarang.ui.theme.component_green
import com.cookandroid.loarang.ui.theme.mainGreen

@Composable
fun SettingScreen(name: String, modifier: Modifier = Modifier, viewModel: MainViewModel = hiltViewModel()) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.backgroundGrey)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_main),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .shadow(12.dp, shape = RoundedCornerShape(8.dp))
                .background(
                    color = MaterialTheme.colorScheme.mainGreen,
                    shape = RoundedCornerShape(8.dp)
                )
        )

        Button(
            onClick = {
                val intent = Intent(context, NoticeActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .shadow(12.dp, shape = RoundedCornerShape(100.dp)),
            colors = ButtonDefaults.buttonColors(component_green)
        ) {
            Text(
                text = context.getString(R.string.setting_notice_title),
                color = MaterialTheme.colorScheme.backgroundGrey,
                fontSize = 18.sp
            )
        }

        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "*/*"
                    setPackage("com.google.android.gm")
                    putExtra(Intent.EXTRA_EMAIL, arrayOf("qlrqod123123@gmail.com", "avt5560@gmail.com"))
                    putExtra(Intent.EXTRA_SUBJECT, "")
                    putExtra(
                        Intent.EXTRA_TEXT,
                        """
                            제조사 (Device Manufacturer): ${Build.MANUFACTURER}
                            기기명 (Device): ${Build.MODEL}
                            안드로이드 OS (Android OS): ${Build.VERSION.RELEASE}
                            내용 (Content): 
                            
                        """.trimIndent()
                    )
                }
                context.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .shadow(12.dp, shape = RoundedCornerShape(100.dp)),
            colors = ButtonDefaults.buttonColors(component_green)
        ) {
            Text(
                text = context.getString(R.string.setting_inquire_title),
                color = MaterialTheme.colorScheme.backgroundGrey,
                fontSize = 18.sp
            )
        }

        Button(
            onClick = {
                val intent = Intent(context, PatchActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .shadow(12.dp, shape = RoundedCornerShape(100.dp)),
            colors = ButtonDefaults.buttonColors(component_green)
        ) {
            Text(
                text = context.getString(R.string.setting_patch_title),
                color = MaterialTheme.colorScheme.backgroundGrey,
                fontSize = 18.sp
            )
        }
    }
}


@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES, name = "SettingPreview (Dark)")
@Composable
private fun SettingPreview() {
    AppTheme {
        SettingScreen(name = "Setting")
    }
}
