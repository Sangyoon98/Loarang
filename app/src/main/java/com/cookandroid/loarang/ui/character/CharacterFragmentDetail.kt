package com.cookandroid.loarang.ui.character

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.lifecycleScope
import com.cookandroid.loarang.base.BaseActivity
import com.cookandroid.loarang.databinding.FragmentCharacterDetailBinding
import kotlinx.coroutines.launch

class CharacterFragmentDetail : BaseActivity() {
    private val binding by lazy { FragmentCharacterDetailBinding.inflate(layoutInflater) }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val nickname = intent.extras!!.getString("nickname")
        val url = "https://lostark.game.onstove.com/Profile/Character/$nickname"

        binding.pBar.visibility = View.GONE // 로딩바 가리기 (로딩때만 보여야 함)

        lifecycleScope.launch {
            binding.wView.webViewClient = WebViewClient()
            binding.wView.settings.javaScriptEnabled = true
            binding.wView.loadUrl(url)
        }
    }

    // 뒤로가기 동작 컨트롤
    override fun onBackPressed() {
        if (binding.wView.canGoBack()) {      // 이전 페이지가 존재하면
            binding.wView.goBack() // 이전 페이지로 돌아가고
        } else {
            super.onBackPressed() // 없으면 앱 종료
        }
    }
}
