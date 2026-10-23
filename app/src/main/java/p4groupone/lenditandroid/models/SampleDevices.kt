package p4groupone.lenditandroid.models

import p4groupone.lenditandroid.models.Device

object SampleDevices {
    val devices = listOf(
        Device(
            id = "LAP-001",
            deviceType = "Laptop",
            centerLocation = "Office for Local Aassistance (OLA)",
            status = "Available"
        ),
        Device(
            id = "TAB-001",
            deviceType = "Tablet",
            centerLocation = "Office for Local Aassistance (OLA)",
            status = "Available"
        ),
        Device(
            id = "HOT-001",
            deviceType = "Hotspot",
            centerLocation = "Office for Local Aassistance (OLA)",
            status = "Available"
        )
    )
}