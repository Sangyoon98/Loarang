package com.cookandroid.loarang.ui.setting.notice

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class NoticeModel(
    val context_notice: String? = null,
    val name: String? = null
)