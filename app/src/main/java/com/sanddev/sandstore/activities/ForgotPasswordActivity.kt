package com.sanddev.sandstore.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.google.firebase.auth.FirebaseAuth
import com.sanddev.sandstore.R
import com.sanddev.sandstore.utils.Utilities.Companion.getTrimmedText
import com.sanddev.sandstore.utils.Utilities.Companion.showToast
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_forgot_password.btSubmit

class ForgotPasswordActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        onClicks()
    }

    private fun onClicks() {


        btSubmit.setOnClickListener {
            if(validateUserEntries()){
                reqSendResetPwdEmail()
            }
        }

    }

    private fun reqSendResetPwdEmail() {
        showProgressDialog()
        val email=etMail.getTrimmedText()
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnCompleteListener {
                hideProgressDialog()
                if (it.isSuccessful){
            "We Have Successfully Send A Email For Password Reset!".showToast(this)
                finish()
                }else{

                    it.exception?.message?.showToast(this)

                }
            }

    }

    private fun validateUserEntries(): Boolean {
        return when {

            TextUtils.isEmpty(etMail.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar("Please enter your email", true)
                false
            }



            else -> {
                showErrorSnackBar("Your details are valid", false)
                true
            }
        }

    }
}
