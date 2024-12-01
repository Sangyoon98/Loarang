package com.cookandroid.loarang.ui.setting.patch

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class PatchModel(
    val context_patch: String? = null,
    val name: String? = null
)
