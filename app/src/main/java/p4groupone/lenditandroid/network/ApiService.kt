
package p4groupone.lenditandroid.network

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import p4groupone.lenditandroid.network.LoanDto
import retrofit2.http.Path

interface ApiService {

    @POST("api/users/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("api/users/logout")
    suspend fun logout(): Response<Unit>

    @GET("api/loanees")
    suspend fun getLoanees(): List<LoaneeDto>

    @GET("api/loans")
    suspend fun getLoans(): List<LoanDto>

    @GET("api/assets")
    suspend fun getAssets(): List<AssetDto>

    @POST("api/loanees")
    suspend fun registerLoanee(
        @Body request: LoaneeRegistration
    ): LoaneeDto

    @PUT("api/loanees/{id}")
    suspend fun updateLoanee(
        @Path("id") id: Int,
        @Body request: LoaneeRegistration
    ): LoaneeDto

    @POST("api/loans/checkout")
    suspend fun checkoutLoan(
        @Body request: LoanRequest
    ): LoanDto

    @POST("api/loans/{loanId}/return")
    suspend fun returnLoan(
        @Path("loanId") loanId: Int,
        @Body request: ReturnRequest
    ): LoanDto

}