package com.sanddev.sandstore.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.sanddev.sandstore.R
import com.sanddev.sandstore.utils.Utilities.Companion.getTrimmedText
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        initUI()
        onClicks()

    }

    private fun initUI() {
        setSupportActionBar(toolBar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_round_arrow_back_24)

        }

    }

    private fun onClicks() {

        tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()

        }

        toolBar.setNavigationOnClickListener {
            onBackPressed()

        }
        btSubmit.setOnClickListener {
            if (validateUserEntries()) {
                reqRegisterWithFirebase()

            }

        }

    }

    private fun reqRegisterWithFirebase() {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(etEmail.getTrimmedText(), etPwd.getTrimmedText())
            .addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(this,"You are registered",Toast.LENGTH_LONG).show()
                    finishAffinity()
                    startActivity(Intent(this,HomeActivity::class.java))

                }
            }
            .addOnFailureListener {
                showErrorSnackBar(it.message.toString(),true)
            }
    }

    private fun validateUserEntries(): Boolean {
        return when {
            TextUtils.isEmpty(etName.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar("Please enter your name", true)
                false
            }
            TextUtils.isEmpty(etEmail.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar("Please enter your email", true)
                false
            }
            TextUtils.isEmpty(etEmail.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar("Please enter your email", true)
                false
            }
            TextUtils.isEmpty(etPwd.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar("Please enter your password", true)
                false
            }
            TextUtils.isEmpty(etConfPwd.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar("Please re-enter your password to confirm", true)
                false
            }
            etPwd.text.toString().trim { it <= ' ' } != etConfPwd.text.toString()
                .trim { it <= ' ' } -> {
                showErrorSnackBar("Entered confirmed password doesn't match", true)
                false
            }
            !cbTC.isChecked -> {
                showErrorSnackBar("Please accept terms and conditions to continue", true)
                false
            }

            else -> {
                showErrorSnackBar("Your details are valid", false)
                true
            }
        }

    }
}