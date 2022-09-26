package dev.djakonystar.todolistapi.data.retrofit

import dev.djakonystar.todolistapi.core.loginRequest
import dev.djakonystar.todolistapi.core.loginResponse
import dev.djakonystar.todolistapi.core.updateRequest
import dev.djakonystar.todolistapi.core.updateResponse
import dev.djakonystar.todolistapi.data.models.request.Register
import dev.djakonystar.todolistapi.data.models.response.User
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("/user/register")
    suspend fun registerUser(
        @Body user: Register
    ): Response<loginResponse>

    @POST("/user/login")
    suspend fun login(
        @Body user: loginRequest
    ): Response<loginResponse>

    @GET("/user/me")
    suspend fun me(
        @Header("Authorization") token: String
    ): Response<User>

    @PUT("/user/me")
    suspend fun updateMe(
        @Header("Authorization") token: String,
        @Body user: updateRequest
    ): Response<updateResponse>

    @DELETE("/user/me")
    suspend fun deleteMe(
        @Header("Authorization") token: String
    ): Response<User>
}
