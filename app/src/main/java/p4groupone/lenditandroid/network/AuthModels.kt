package p4groupone.lenditandroid.network

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val userId: Int,
    val username: String
)