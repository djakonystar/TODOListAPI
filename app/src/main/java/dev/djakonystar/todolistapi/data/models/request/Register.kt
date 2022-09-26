package dev.djakonystar.todolistapi.data.models.request

data class Register(
    val name: String,
    val email: String,
    val password: String,
    val age: Int
)
