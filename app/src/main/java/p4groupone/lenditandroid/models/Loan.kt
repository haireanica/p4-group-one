package p4groupone.lenditandroid.models

data class Loan (
    val id: String,
    var deviceType: String,
    var centerLocation: String,
    var loanStartDate: String,
    var loanDueDate: String
)