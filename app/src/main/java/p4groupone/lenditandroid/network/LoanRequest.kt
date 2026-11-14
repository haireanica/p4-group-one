package p4groupone.lenditandroid.network

data class LoanRequest(
    val assetId: String,
    val participantId: Int,
    val reason: String,
    val loanPurpose: String,
    val days: Int
)