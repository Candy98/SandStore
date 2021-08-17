package com.sanddev.sandstore.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sanddev.sandstore.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        onClicks()

    }

    private fun onClicks() {

        tvSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))

        }
        btSubmit.setOnClickListener {

        }
    }
}