package com.cookandroid.loarang.ui.setting

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cookandroid.loarang.ui.setting.patch.PatchActivity
import com.cookandroid.loarang.base.BaseFragment
import com.cookandroid.loarang.databinding.FragmentSettingBinding
import com.cookandroid.loarang.ui.main.MainActivity
import com.cookandroid.loarang.ui.setting.notice.NoticeActivity

class SettingFragment : BaseFragment() {
    companion object {
        fun newInstance() = SettingFragment()
        const val TAG = "SettingFragment"
    }

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    lateinit var context: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)

        binding.notice.setOnClickListener {
            val intent = Intent(context, NoticeActivity::class.java)
            startActivity(intent)
        }

        binding.inquire.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            val address = arrayOf("qlrqod123123@gmail.com", "avt5560@gmail.com")

            intent.setType("*/*")
            intent.setPackage("com.google.android.gm")
            intent.putExtra(Intent.EXTRA_EMAIL, address) // 받는 사람 이메일
            intent.putExtra(Intent.EXTRA_SUBJECT, "") // 메일 제목
            intent.putExtra(
                Intent.EXTRA_TEXT,
                """
                    제조사 (Device Manufacturer): ${Build.MANUFACTURER}
                    기기명 (Device): ${Build.MODEL}
                    안드로이드 OS (Android OS): ${Build.VERSION.RELEASE}
                    내용 (Content): 
                    
                    """.trimIndent()
            ) // 메일 내용
            startActivity(intent)
        }

        binding.patch.setOnClickListener {
            val intent = Intent(context, PatchActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}
