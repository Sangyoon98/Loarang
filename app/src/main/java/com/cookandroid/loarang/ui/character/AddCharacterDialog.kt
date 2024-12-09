package com.cookandroid.loarang.ui.character

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.cookandroid.loarang.databinding.DialogAddCharacterBinding

class AddCharacterDialog(
    val context: AppCompatActivity,
    val click: (String) -> Unit
): DialogFragment() {
    private lateinit var binding: DialogAddCharacterBinding
    private val dialog = Dialog(context)

    fun showDialog() {
        binding = DialogAddCharacterBinding.inflate(context.layoutInflater)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(binding.root)
        val params = dialog.window!!.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes = params
        dialog.setCancelable(false)
        dialog.show()

        binding.confirm.setOnClickListener {
            click(binding.content.text.toString())
            dialog.dismiss()
        }
    }
}