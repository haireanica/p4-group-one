package p4groupone.lenditandroid.models

object SampleParticipants {
    val participants = listOf(
        Participant(
            id = 1,
            firstName = "John",
            lastName = "Smith",
            phoneNumber = "407-555-0123",
            addressLine1 = "123 Main Street",
            addressLine2 = "",
            city = "Orlando",
            zipcode = "32801"
        ),
        Participant(
            id = 2,
            firstName = "Jane",
            lastName = "Doe",
            phoneNumber = "407-555-0145",
            addressLine1 = "456 Oak Avenue",
            addressLine2 = "Apt 2B",
            city = "Kissimmee",
            zipcode = "34741"
        ),
        Participant(
            id = 3,
            firstName = "Michael",
            lastName = "Johnson",
            phoneNumber = "407-555-0178",
            addressLine1 = "789 Lake Street",
            addressLine2 = "",
            city = "Sanford",
            zipcode = "32771"
        )
    )
}