package com.sanddev.sandstore.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.google.firebase.auth.FirebaseAuth
import com.sanddev.sandstore.R
import com.sanddev.sandstore.firestore.FireStoreUtils
import com.sanddev.sandstore.models.User
import com.sanddev.sandstore.utils.Utilities.Companion.getTrimmedText
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
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
            if (validateUserEntries()) {
                reqUserLogin()
            }
        }
        tvFgPwd.setOnClickListener {
            startActivity(Intent(this,ForgotPasswordActivity::class.java))
        }
    }

    private fun reqUserLogin() {
        showProgressDialog()
        val email = etMail.getTrimmedText()
        val password = etPwd.getTrimmedText()
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showErrorSnackBar("You are logged in successfully", false)
                    FireStoreUtils().getUserDetails(this)

                } else {
                    hideProgressDialog()
                    showErrorSnackBar(it.exception?.message.toString(), true)

                }
            }
            .addOnCanceledListener {
                hideProgressDialog()
            }


    }

    private fun validateUserEntries(): Boolean {
        return when {

            TextUtils.isEmpty(etMail.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar("Please enter your email", true)
                false
            }

            TextUtils.isEmpty(etPwd.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar("Please enter your password", true)
                false
            }


            else -> {
                showErrorSnackBar("Your details are valid", false)
                true
            }
        }

    }

    fun userLoggedInSuccess(user: User?) {
        hideProgressDialog()
        println(" I am email : ${user?.email}")
        println(" I am name : ${user?.name}")


    }
}
