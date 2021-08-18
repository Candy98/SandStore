package com.sanddev.sandstore.firestore

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.sanddev.sandstore.activities.LoginActivity
import com.sanddev.sandstore.activities.SignUpActivity
import com.sanddev.sandstore.models.User
import com.sanddev.sandstore.utils.Constants
import com.sanddev.sandstore.utils.Utilities.Companion.showToast

class FireStoreUtils {

    private val mFireStore=FirebaseFirestore.getInstance()


    fun registerUserToFirestore(activity: SignUpActivity,userInfo:User){
            mFireStore.collection(Constants.USERS)
                .document(userInfo.id)
                .set(userInfo, SetOptions.merge())
                .addOnSuccessListener {
                    activity.registerUserSuccess()

                }
                .addOnFailureListener {
                     activity.hideProgressDialog()
                    println("I am reg error ${it.message}")
                }



        }
        fun getCurrentUserId():String{
            val currUser=FirebaseAuth.getInstance().currentUser
            var currUserId=""
            if (currUser!=null){
                currUserId=currUser.uid
            }
            return currUserId
        }
fun getUserDetails(activity: Activity){
    mFireStore.collection(Constants.USERS)
        .document(getCurrentUserId())
        .get()
        .addOnSuccessListener {
            try {
                val user=it.toObject(User::class.java)!!
                when(activity){
                    is LoginActivity->{
                        activity.userLoggedInSuccess(user)
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
                println("I am user details exception: ${e.message}")
            }

        }
}



}
