package p4groupone.lenditandroid.network

data class LoaneeRegistration(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val addressLine1: String,
    val addressLine2: String,
    val city: String,
    val state: String,
    val postalCode: String
)