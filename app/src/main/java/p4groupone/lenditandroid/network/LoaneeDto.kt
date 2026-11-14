package p4groupone.lenditandroid.network

import p4groupone.lenditandroid.models.Participant

data class LoaneeDto(
    val participantId: Int?,
    val firstName: String?,
    val lastName: String?,
    val phoneNumber: String?,
    val addressLine1: String?,
    val addressLine2: String?,
    val city: String?,
    val postalCode: String?,
    val state: String?
) {
    fun toParticipant(): Participant {
        return Participant(
            id = participantId?.toString() ?: "",
            firstName = firstName ?: "",
            lastName = lastName ?: "",
            phoneNumber = phoneNumber ?: "",
            addressLine1 = addressLine1 ?: "",
            addressLine2 = addressLine2 ?: "",
            city = city ?: "",
            zipcode = postalCode ?: ""
        )
    }
}