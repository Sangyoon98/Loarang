package com.cookandroid.loarang.ui.character

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cookandroid.loarang.base.BaseActivity
import com.cookandroid.loarang.databinding.FragmentCharacterDetailBinding
import com.cookandroid.loarang.ui.main.MainScreenView
import com.cookandroid.loarang.ui.main.MainViewModel
import com.cookandroid.loarang.ui.theme.AppTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharacterFragmentDetail : BaseActivity() {
    private val binding by lazy { FragmentCharacterDetailBinding.inflate(layoutInflater) }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

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

        /*this.onBackPressedDispatcher.addCallback(this, callback)

        //val nickname = intent.extras!!.getString("nickname")
        val url = "https://lostark.game.onstove.com/Profile/Character/$nickname"

        binding.progressBar.visibility = View.GONE // 로딩바 가리기 (로딩 때만 보여야 함)

        lifecycleScope.launch {
            binding.webContainer.webViewClient = WebViewClient()
            binding.webContainer.settings.javaScriptEnabled = true
            binding.webContainer.loadUrl(url)
        }*/
    }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (binding.webContainer.canGoBack()) {
                binding.webContainer.goBack()
            } else finish()
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun CharacterDetailScreen(nickname: String) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }
    val url = "https://lostark.game.onstove.com/Profile/Character/$nickname"

    val webViewClient = remember { WebViewClient() }


    Box(modifier = Modifier.fillMaxSize()) {
        // 웹뷰
        AndroidView(
            factory = { context ->
                android.webkit.WebView(context).apply {
                    settings.javaScriptEnabled = true
                    this.webViewClient = webViewClient
                    loadUrl(url)
                    isLoading = false
                }
            },
            modifier = Modifier.fillMaxSize()
        )

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
