package com.sanddev.sandstore.models

/**
 * A data model class for User with required fields.
 */
data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val image: String = "",
    val mobile: Long = 0,
    val gender: String = "",
    val profileCompleted: Int = 0)
