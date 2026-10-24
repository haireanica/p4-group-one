package p4groupone.lenditandroid.models

data class Participant(
    val id: String,
    var firstName: String,
    var lastName: String,
    var phoneNumber: String,
    var addressLine1: String,
    var addressLine2: String,
    var city: String,
    var zipcode: String,
){
    val fullName: String
        get() = "$firstName $lastName"

    val fullAddress: String
        get() = "$addressLine1 $addressLine2"
}