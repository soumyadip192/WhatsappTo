package com.soumyadip.whatsappto

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.soumyadip.whatsappto.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setActionBar()
        setBackgroundImage()
        setListener()
    }

    private fun setActionBar() {
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    R.color.purple_500
                )
            )
        )
    }

    private fun setBackgroundImage() {
        Glide.with(this)
            .load(R.drawable.bg)
            .centerCrop()
            .into(mBinding.ivBackground)
    }

    private fun setListener() {
        with (mBinding) {
            tvAction.setOnClickListener {
                if (etPhone.text?.length != 10) {
                    showToast("Invalid phone no.")
                } else {
                    moveToWhatsapp(etPhone.text.toString(), etMessage.text.toString())
                }
            }

            tvReset.setOnClickListener {
                etPhone.clearText()
                etMessage.clearText()
            }
        }
    }

    private fun moveToWhatsapp(phone: String, text: String) {
        runCatching {
            val url = "https://api.whatsapp.com/send?phone=91$phone&text=$text"
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                setPackage("com.whatsapp")
                data = Uri.parse(url)
            })
        }.onFailure {
            showToast(it.localizedMessage ?: "Something went wrong")
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}