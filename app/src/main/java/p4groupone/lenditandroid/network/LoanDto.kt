
package p4groupone.lenditandroid.network

import p4groupone.lenditandroid.models.Loan

data class LoanDto(
    val loanId: Int?,
    val assetId: String?,
    val assetName: String?,
    val center: String?,
    val participantId: Int?,
    val reason: String?,
    val loanPurpose: String?,
    val checkoutDate: String?,
    val dueDate: String?,
    val returnDate: String?,
    val status: String?
) {
    fun toLoan(): Loan {
        return Loan(
            id = loanId?.toString() ?: "",
            deviceType = assetName ?: "",
            centerLocation = center ?: "",
            loanStartDate = checkoutDate ?: "",
            loanDueDate = dueDate ?: ""
        )
    }
}
