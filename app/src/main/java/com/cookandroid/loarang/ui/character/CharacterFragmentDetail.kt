package com.cookandroid.loarang.ui.character

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.cookandroid.loarang.base.BaseActivity
import com.cookandroid.loarang.ui.theme.AppTheme

class CharacterFragmentDetail : BaseActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val nickname = intent.extras!!.getString("nickname")

        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (nickname != null) {
                        CharacterDetailScreen(nickname)
                    }
                }
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun CharacterDetailScreen(nickname: String) {
    var isLoading by remember { mutableStateOf(true) }
    val url = "https://lostark.game.onstove.com/Profile/Character/$nickname"
    var backEnabled by remember { mutableStateOf(false) }
    var webView: WebView? = null

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    //this.webViewClient = webViewClient
                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                            backEnabled = view.canGoBack()
                        }
                    }
                    loadUrl(url)
                    isLoading = false
                    webView = this
                }
            },
            update = {
                webView = it
            },
            modifier = Modifier.fillMaxSize()
        )

        BackHandler(enabled = backEnabled) {
            webView?.goBack()
        }

        // ProgressBar
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.run {
                    align(Alignment.Center)
                        .size(64.dp)
                },
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
