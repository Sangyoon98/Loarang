package com.cookandroid.loarang.ui.character

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
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

        this.onBackPressedDispatcher.addCallback(this, callback)

        val nickname = intent.extras!!.getString("nickname")
        val url = "https://lostark.game.onstove.com/Profile/Character/$nickname"

        binding.progressBar.visibility = View.GONE // 로딩바 가리기 (로딩 때만 보여야 함)

        lifecycleScope.launch {
            binding.webContainer.webViewClient = WebViewClient()
            binding.webContainer.settings.javaScriptEnabled = true
            binding.webContainer.loadUrl(url)
        }
    }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (binding.webContainer.canGoBack()) {
                binding.webContainer.goBack()
            } else finish()
        }
    }
}
