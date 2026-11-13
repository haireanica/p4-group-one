
package p4groupone.lenditandroid.network

import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("api/users/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse
}