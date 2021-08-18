package com.sanddev.sandstore.utils

import android.content.Context
import android.widget.TextView
import android.widget.Toast
import java.util.regex.Pattern

class Utilities {
    companion object {
        private const val namePattern = "^[a-zA-z]+([a-zA-Z]+)*"
        private val NamePattern = Pattern.compile(namePattern)
        fun CharSequence.isValidName() = !isNullOrEmpty() && NamePattern.matcher(this).matches()
        fun TextView.getTrimmedText() = this.text.toString().trim { it <= ' ' }
        fun String.showToast(context: Context) =
            Toast.makeText(context, this, Toast.LENGTH_LONG).show()
    }
}
