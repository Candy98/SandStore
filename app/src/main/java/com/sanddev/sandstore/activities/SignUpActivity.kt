package com.sanddev.sandstore.activities

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.sanddev.sandstore.R
import com.sanddev.sandstore.firestore.FireStoreUtils
import com.sanddev.sandstore.models.User
import com.sanddev.sandstore.utils.Utilities.Companion.getTrimmedText
import com.sanddev.sandstore.utils.Utilities.Companion.showToast
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
        onBackPressed()
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
        showProgressDialog()
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(etEmail.getTrimmedText(), etPwd.getTrimmedText())
            .addOnCompleteListener {
                hideProgressDialog()
                if (it.isSuccessful){
                    Toast.makeText(this,"You are registered",Toast.LENGTH_LONG).show()
                    val fbUser=it.result?.user
                    val user = User(
                        fbUser!!.uid,
                        etName.text.toString().trim { it <= ' ' },
                        etEmail.text.toString().trim { it <= ' ' }
                    )

                    FireStoreUtils().registerUserToFirestore(this,user)
                }else{
                    hideProgressDialog()
                    showErrorSnackBar(it.exception?.message.toString(),true)
                }
            }
    }

     fun registerUserSuccess() {
        hideProgressDialog()
        "User Registration Success".showToast(this)


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
