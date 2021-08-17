package com.sanddev.sandstore.utils

import android.widget.TextView
import java.util.regex.Pattern

class Utilities {
    companion object {
        private const val namePattern = "^[a-zA-z]+([a-zA-Z]+)*"
        private val NamePattern = Pattern.compile(namePattern)
        fun CharSequence.isValidName() = !isNullOrEmpty() && NamePattern.matcher(this).matches()
        fun TextView.getTrimmedText() = this.text.toString().trim{ it <= ' ' }

    }
}