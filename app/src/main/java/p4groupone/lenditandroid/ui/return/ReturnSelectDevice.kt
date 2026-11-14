package p4groupone.lenditandroid.ui.`return`

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import p4groupone.lenditandroid.models.SampleDevices
import p4groupone.lenditandroid.ui.components.DeviceList
import p4groupone.lenditandroid.ui.components.LendITDropdown
import p4groupone.lenditandroid.ui.dashboard.DashboardViewScreen
import p4groupone.lenditandroid.ui.theme.PrimaryColor
import p4groupone.lenditandroid.ui.theme.SecondaryColor
import p4groupone.lenditandroid.ui.theme.openSans
import androidx.compose.runtime.LaunchedEffect
import p4groupone.lenditandroid.models.Device
import p4groupone.lenditandroid.network.ApiClient
import p4groupone.lenditandroid.network.LoanDto
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import p4groupone.lenditandroid.network.ReturnRequest


@Composable
fun ReturnSelectDeviceScreen(
    navController: NavController,
    participantId: String?
) {
    val scope = rememberCoroutineScope()
    var selectedDeviceId by remember {
        mutableStateOf<String?>(null)
    }

    var activeDevices by remember {
        mutableStateOf<List<Device>>(emptyList())
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    var loadError by remember {
        mutableStateOf<String?>(null)
    }
    var selectedDeviceCondition by remember {
        mutableStateOf("Good")
    }

    var activeLoans by remember {
        mutableStateOf<List<LoanDto>>(emptyList())
    }

    var selectedLoanId by remember {
        mutableStateOf<Int?>(null)
    }

    var returnNotes by remember {
        mutableStateOf("")
    }

    var isSubmitting by remember {
        mutableStateOf(false)
    }

    var returnMessage by remember {
        mutableStateOf<String?>(null)
    }


    LaunchedEffect(participantId) {

        isLoading = true
        loadError = null

        try {
            val participantIdInt = participantId?.toIntOrNull()

            if (participantIdInt == null) {
                activeDevices = emptyList()
                loadError = "Invalid participant ID."
                return@LaunchedEffect
            }

            val loans = ApiClient.apiService.getLoans()

            activeDevices = loans
                .filter { loan ->
                    loan.participantId == participantIdInt &&
                            loan.returnDate.isNullOrBlank() &&
                            loan.loanId != null
                }
                .map { loan ->
                    Device(
                        id = loan.loanId.toString(),
                        deviceType = loan.assetName ?: "Unknown Device",
                        centerLocation = loan.center ?: "Unknown Center",
                        status = loan.status ?: "Active"
                    )
                }

        } catch (e: Exception) {
            loadError = e.message ?: "Unable to retrieve active loans."
            e.printStackTrace()

        } finally {
            isLoading = false
        }
    }

    val deviceCondition = listOf(
        "Good",
        "Minor Damage",
        "Major Damage",
        "Needs Repair"
    )
    var selectedDeviceAccessories by remember {
        mutableStateOf("Return")
    }
    var devices by remember {
        mutableStateOf<List<Device>>(emptyList())
    }

    LaunchedEffect(participantId) {
        try {
            val loans = ApiClient.apiService.getLoans()

            devices = loans
                .filter { loan ->
                    loan.participantId?.toString() == participantId &&
                            loan.status == "CHECKED_OUT"
                }
                .map { loan ->
                    Device(
                        id = loan.assetId.orEmpty(),
                        deviceType = loan.assetName.orEmpty(),
                        centerLocation = loan.center.orEmpty(),
                        status = loan.status.orEmpty()
                    )
                }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isLoading = false
        }
    }


    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColor)
    ) {

        val referenceHeight = 900.dp
        val scale = (maxHeight / referenceHeight)
            .coerceIn(0.75f, 1f)

        val headerHeight = 190.dp * scale
        val buttonTextSize = 32.sp * scale
        val buttonHeight = 60.dp * scale

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(headerHeight)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(headerHeight)
                        .clip(
                            RoundedCornerShape(
                                bottomEnd = 100.dp * scale
                            )
                        )
                        .background(Color.White)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp * scale)
                        .clip(
                            RoundedCornerShape(
                                bottomEnd = 100.dp * scale
                            )
                        )
                        .background(SecondaryColor),
                    contentAlignment = Alignment.Center
                ) {

                    Column(

                    ) {

                        Text(
                            text = "Return",
                            fontSize = 70.sp * scale,
                            fontFamily = openSans,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Left
                        )
                    }
                }
            }
            Column(
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 10.dp)
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Select Device",
                        fontSize = 20.sp * scale,
                        fontFamily = openSans,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Left
                    )
                    Button(
                        onClick = {
                            navController.navigate("dashboard")
                        },
                        modifier = Modifier
                            .width(163.dp)
                            .height(38.dp),
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SecondaryColor
                        )
                    ) {

                        Text(
                            text = "Main Menu",
                            fontFamily = openSans,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

            }
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .height(300.dp)
                ) {
                    if (isLoading) {
                        Text(
                            text = "Loading active loans...",
                            color = Color.White
                        )
                    } else if (devices.isEmpty()) {
                        Text(
                            text = "No active loans found for this participant.",
                            color = Color.White
                        )
                    } else {
                        if (isLoading) {

                            Text(
                                text = "Loading active loans...",
                                color = Color.White
                            )

                        } else if (loadError != null) {

                            Text(
                                text = loadError!!,
                                color = Color.White
                            )

                        } else if (activeDevices.isEmpty()) {

                            Text(
                                text = "No active loans found for this participant.",
                                color = Color.White
                            )

                        } else {

                            DeviceList(
                                devices = activeDevices,
                                selectedDeviceId = selectedDeviceId,
                                onDeviceClick = { device ->
                                    selectedDeviceId = device.id
                                }
                            )
                        }
                    }
                }

                LendITDropdown(
                    label = "Device Condition",
                    options = deviceCondition,
                    selectedValue = selectedDeviceCondition,
                    onValueChange = { selectedDeviceCondition = it }
                )

                TextField(
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    value = returnNotes,
                    onValueChange = {
                        returnNotes = it
                    },
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 10.dp),
                    label = {
                        Text("Return Notes (Optional)")
                    },
                    placeholder = {
                        Text("Describe any damage or issues...")
                    },
                    maxLines = 3,
                )

                Button(
                    onClick = {

                        val loanId = selectedDeviceId?.toIntOrNull()

                        if (loanId == null) {
                            returnMessage = "Please select a device."
                            return@Button
                        }

                        scope.launch {

                            isSubmitting = true
                            returnMessage = null

                            try {

                                val condition = selectedDeviceCondition
                                    .lowercase()
                                    .replace(" ", "-")

                                val request = ReturnRequest(
                                    condition = condition,
                                    notes = returnNotes.ifBlank { null }
                                )

                                ApiClient.apiService.returnLoan(
                                    loanId = loanId,
                                    request = request
                                )

                                navController.navigate("dashboard")

                            } catch (e: Exception) {

                                returnMessage = e.message
                                    ?: "Unable to complete return."

                            } finally {

                                isSubmitting = false

                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                        .height(buttonHeight),
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SecondaryColor
                    )
                ) {

                    Text(
                        text = "Complete Return",
                        fontFamily = openSans,
                        fontSize = buttonTextSize,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }


        }
    }
}
