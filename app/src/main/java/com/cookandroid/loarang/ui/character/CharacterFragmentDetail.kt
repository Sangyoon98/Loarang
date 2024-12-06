package com.cookandroid.loarang.ui.character

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.cookandroid.loarang.base.BaseActivity
import com.cookandroid.loarang.databinding.FragmentCharacterDetailBinding

class CharacterFragmentDetail : BaseActivity() {
    private val binding by lazy { FragmentCharacterDetailBinding.inflate(layoutInflater) }
    var URL: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val nickname = intent.extras!!.getString("nickname")
        URL = "https://lostark.game.onstove.com/Profile/Character/"
        URL += nickname

        binding.pBar.visibility = View.GONE // 로딩바 가리기 (로딩때만 보여야 함)

        initWebView() // 웹뷰 초기화
    }

    // 웹뷰 초기화 함수
    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        // 1. 웹뷰클라이언트 연결 (로딩 시작/끝 받아오기)
        binding.wView.webViewClient = object : WebViewClient() {
            // 1) 로딩 시작
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                super.onPageStarted(view, url, favicon)
                binding.pBar.visibility = View.VISIBLE // 로딩이 시작되면 로딩바 보이기
            }

            // 2) 로딩 끝
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                binding.pBar.visibility = View.GONE // 로딩이 끝나면 로딩바 없애기
            }

            // 3) 외부 브라우저가 아닌 웹뷰 자체에서 url 호출
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
        // 2. WebSettings: 웹뷰의 각종 설정을 정할 수 있다.
        binding.wView.settings.javaScriptEnabled = true // 자바스크립트 사용 허가
        // 3. 웹페이지 호출
        binding.wView.loadUrl(URL!!)
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
