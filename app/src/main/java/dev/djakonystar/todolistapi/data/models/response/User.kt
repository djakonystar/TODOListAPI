package dev.djakonystar.todolistapi.data.models.response

import com.google.gson.annotations.SerializedName

data class User(
    val age: Int,
    @SerializedName("_id") val id: String,
    val name: String,
    val email: String,
    val createdAt: String
)
