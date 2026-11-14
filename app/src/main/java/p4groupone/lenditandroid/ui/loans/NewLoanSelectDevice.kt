
package p4groupone.lenditandroid.ui.loans

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch
import p4groupone.lenditandroid.models.Device
import p4groupone.lenditandroid.network.ApiClient
import p4groupone.lenditandroid.network.LoanRequest
import p4groupone.lenditandroid.ui.components.DeviceList
import p4groupone.lenditandroid.ui.theme.PrimaryColor
import p4groupone.lenditandroid.ui.theme.SecondaryColor
import p4groupone.lenditandroid.ui.theme.openSans

@Composable
fun NewLoanSelectDeviceScreen(
    navController: NavController,
    participantId: Int,
    deviceType: String,
    center: String,
    duration: String,
    reason: String
) {
    var selectedDeviceId by remember {
        mutableStateOf<String?>(null)
    }

    var devices by remember {
        mutableStateOf(emptyList<Device>())
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    var isSubmitting by remember {
        mutableStateOf(false)
    }

    var checkoutError by remember {
        mutableStateOf<String?>(null)
    }

    val coroutineScope = rememberCoroutineScope()

    // Load and filter available inventory.
    LaunchedEffect(deviceType, center) {
        try {
            isLoading = true
            errorMessage = null

            val assets = ApiClient.apiService.getAssets()

            devices = assets
                .filter { asset ->
                    val matchesType =
                        deviceType == "All" ||
                                asset.deviceType.equals(
                                    deviceType,
                                    ignoreCase = true
                                )

                    val assetCenter =
                        asset.inventory?.center?.name ?: ""

                    val matchesCenter =
                        center == "All" ||
                                assetCenter.equals(
                                    center,
                                    ignoreCase = true
                                )

                    val isAvailable =
                        asset.status.equals(
                            "AVAILABLE",
                            ignoreCase = true
                        )

                    !asset.assetId.isNullOrBlank() &&
                            matchesType &&
                            matchesCenter &&
                            isAvailable
                }
                .map { asset ->
                    Device(
                        id = asset.assetId ?: "",
                        deviceType = asset.deviceType ?: "",
                        centerLocation =
                            asset.inventory?.center?.name ?: "",
                        status = asset.status ?: ""
                    )
                }

        } catch (e: Exception) {
            errorMessage = e.localizedMessage
                ?: "Unable to load inventory."
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

        val headerHeight = 230.dp * scale
        val buttonTextSize = 32.sp * scale
        val buttonHeight = 60.dp * scale

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
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
                        .height(220.dp * scale)
                        .clip(
                            RoundedCornerShape(
                                bottomEnd = 100.dp * scale
                            )
                        )
                        .background(SecondaryColor),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Text(
                            text = "New Loan",
                            fontSize = 70.sp * scale,
                            fontFamily = openSans,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Left
                        )
                    }
                }
            }

            // Screen title and Main Menu button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
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

            // Device inventory
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                if (isLoading) {
                    Text(
                        text = "Loading inventory...",
                        color = Color.White
                    )
                } else if (errorMessage != null) {
                    Text(
                        text = errorMessage ?: "Unable to load inventory.",
                        color = Color.White
                    )
                } else if (devices.isEmpty()) {
                    Text(
                        text = "No available devices match your selections.",
                        color = Color.White
                    )
                } else {
                    DeviceList(
                        devices = devices,
                        selectedDeviceId = selectedDeviceId,
                        onDeviceClick = { device ->
                            selectedDeviceId = device.id
                            checkoutError = null
                        }
                    )
                }

                // Complete Loan
                Button(
                    onClick = {
                        val assetId = selectedDeviceId

                        if (assetId != null) {
                            coroutineScope.launch {
                                try {
                                    isSubmitting = true
                                    checkoutError = null

                                    val days = when (duration) {
                                        "1 Week" -> 7
                                        "2 Weeks" -> 14
                                        else -> 7
                                    }

                                    val request = LoanRequest(
                                        assetId = assetId,
                                        participantId = participantId,
                                        reason = reason,
                                        loanPurpose = reason,
                                        days = days
                                    )

                                    ApiClient.apiService.checkoutLoan(request)

                                    navController.navigate("dashboard") {
                                        popUpTo("dashboard") {
                                            inclusive = false
                                        }
                                        launchSingleTop = true
                                    }

                                } catch (e: Exception) {
                                    checkoutError = e.localizedMessage
                                        ?: "Unable to complete loan."
                                } finally {
                                    isSubmitting = false
                                }
                            }
                        }
                    },
                    enabled = selectedDeviceId != null &&
                            !isSubmitting &&
                            !isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(buttonHeight),
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SecondaryColor
                    )
                ) {
                    Text(
                        text = if (isSubmitting) {
                            "Processing..."
                        } else {
                            "Complete Loan"
                        },
                        fontFamily = openSans,
                        fontSize = buttonTextSize,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                checkoutError?.let { error ->
                    Text(
                        text = error,
                        color = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewLoanSelectDeviceScreenPreview() {
    val navController = rememberNavController()

    NewLoanSelectDeviceScreen(
        navController = navController,
        participantId = 1,
        deviceType = "All",
        center = "All",
        duration = "1 Week",
        reason = "EDUCATION"
    )
}
