package dev.djakonystar.todolistapi.data.models.response

data class Login(
    val user: User,
    val token: String
)
