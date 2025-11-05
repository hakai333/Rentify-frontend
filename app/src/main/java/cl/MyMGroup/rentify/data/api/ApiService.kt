package cl.MyMGroup.rentify.data.api

import cl.MyMGroup.rentify.data.entity.AuthResponse
import cl.MyMGroup.rentify.data.entity.LoginRequest
import cl.MyMGroup.rentify.data.entity.RegisterRequest
import cl.MyMGroup.rentify.data.entity.UsuarioEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("http://10.0.2.2:8080/api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @Headers("Content-Type: application/json")
    @POST("http://10.0.2.2:8080/api/auth/login")
    suspend fun login(@Body body: LoginRequest): Response<UsuarioEntity>
}
