package com.sanddev.sandstore.utils

import android.content.Context
import com.sanddev.sandstore.BuildConfig
import com.sanddev.sandstore.models.User

class PrefUtils {
    companion object{
        fun clearAllData(context: Context) {

            val prefs =
                context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
            prefs.edit().clear().apply()

        }
        fun saveUser(context: Context, profile: User) {

            val prefs =
                context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
            prefs.edit().putString("profile", profile.toString()).apply()

        }
    }
}
