package p4groupone.lenditandroid.ui.participants

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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import p4groupone.lenditandroid.ui.dashboard.DashboardViewScreen
import p4groupone.lenditandroid.ui.theme.PrimaryColor
import p4groupone.lenditandroid.ui.theme.SecondaryColor
import p4groupone.lenditandroid.ui.theme.openSans
import androidx.compose.runtime.LaunchedEffect
import p4groupone.lenditandroid.network.ApiClient
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import p4groupone.lenditandroid.network.LoaneeRegistration

@Composable
fun ParticipantEditInfoScreen(
    navController: NavController,
    participantId: Int
){
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

        var state by remember { mutableStateOf("") }
        var isLoading by remember { mutableStateOf(true) }
        var errorMessage by remember { mutableStateOf<String?>(null) }

        var firstName by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }
        var phoneNumber by remember { mutableStateOf("") }
        var addressLine1 by remember { mutableStateOf("") }
        var addressLine2 by remember { mutableStateOf("") }
        var city by remember { mutableStateOf("") }
        var zipcode by remember { mutableStateOf("") }
        var isSaving by remember { mutableStateOf(false) }
        var saveMessage by remember { mutableStateOf<String?>(null) }

        val coroutineScope = rememberCoroutineScope()


        LaunchedEffect(participantId) {
            try {
                isLoading = true
                errorMessage = null

                val participant = ApiClient.apiService
                    .getLoanees()
                    .firstOrNull { it.participantId == participantId }

                if (participant != null) {
                    firstName = participant.firstName ?: ""
                    lastName = participant.lastName ?: ""
                    phoneNumber = participant.phoneNumber ?: ""
                    addressLine1 = participant.addressLine1 ?: ""
                    addressLine2 = participant.addressLine2 ?: ""
                    city = participant.city ?: ""
                    state = participant.state ?: ""
                    zipcode = participant.postalCode ?: ""
                } else {
                    errorMessage = "Participant not found."
                }

            } catch (e: Exception) {
                errorMessage = "Unable to load participant: ${e.message}"
            } finally {
                isLoading = false
            }
        }

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
                            text = "Edit Info",
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
                        text = "Edit Participant",
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
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 0.dp)
                        .padding(horizontal = 15.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = "First Name",
                        fontFamily = openSans,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    BasicTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        singleLine = true,
                        textStyle = TextStyle(
                            fontFamily = openSans,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),

                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(50.dp))
                                    .background(Color.White)
                                    .padding(horizontal = 16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                if (firstName.isEmpty()) {
                                    Text(
                                        text = "Enter First Name",
                                        fontFamily = openSans,
                                        fontSize = 16.sp,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black.copy(alpha = 0.2f),
                                        maxLines = 1
                                    )
                                }

                                innerTextField()
                            }
                        }
                    )

                    Text(
                        text = "Last Name",
                        fontFamily = openSans,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    BasicTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        singleLine = true,
                        textStyle = TextStyle(
                            fontFamily = openSans,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),

                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(50.dp))
                                    .background(Color.White)
                                    .padding(horizontal = 16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                if (lastName.isEmpty()) {
                                    Text(
                                        text = "Enter Last Name",
                                        fontFamily = openSans,
                                        fontSize = 16.sp,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black.copy(alpha = 0.2f),
                                        maxLines = 1
                                    )
                                }

                                innerTextField()
                            }
                        }
                    )

                    Text(
                        text = "Phone Number",
                        fontFamily = openSans,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    BasicTextField(
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        singleLine = true,
                        textStyle = TextStyle(
                            fontFamily = openSans,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),

                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(50.dp))
                                    .background(Color.White)
                                    .padding(horizontal = 16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                if (phoneNumber.isEmpty()) {
                                    Text(
                                        text = "Enter Phone Number",
                                        fontFamily = openSans,
                                        fontSize = 16.sp,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black.copy(alpha = 0.2f),
                                        maxLines = 1
                                    )
                                }

                                innerTextField()
                            }
                        }
                    )

                    Text(
                        text = "Address",
                        fontFamily = openSans,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    BasicTextField(
                        value = addressLine1,
                        onValueChange = { addressLine1 = it },
                        singleLine = true,
                        textStyle = TextStyle(
                            fontFamily = openSans,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),

                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(50.dp))
                                    .background(Color.White)
                                    .padding(horizontal = 16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                if (addressLine1.isEmpty()) {
                                    Text(
                                        text = "Enter Address",
                                        fontFamily = openSans,
                                        fontSize = 18.sp,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black.copy(alpha = 0.2f),
                                        maxLines = 1
                                    )
                                }

                                innerTextField()
                            }
                        }
                    )
                    BasicTextField(
                        value = addressLine2,
                        onValueChange = { addressLine2 = it },
                        singleLine = true,
                        textStyle = TextStyle(
                            fontFamily = openSans,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),

                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(50.dp))
                                    .background(Color.White)
                                    .padding(horizontal = 16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                if (addressLine2.isEmpty()) {
                                    Text(
                                        text = "Enter Unit/Apt Number",
                                        fontFamily = openSans,
                                        fontSize = 18.sp,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black.copy(alpha = 0.2f),
                                        maxLines = 1
                                    )
                                }

                                innerTextField()
                            }
                        }
                    )
                    Text(
                        text = "City",
                        fontFamily = openSans,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    BasicTextField(
                        value = city
                        ,
                        onValueChange = { city = it },
                        singleLine = true,
                        textStyle = TextStyle(
                            fontFamily = openSans,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),

                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(50.dp))
                                    .background(Color.White)
                                    .padding(horizontal = 16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                if (city.isEmpty()) {
                                    Text(
                                        text = "Enter City",
                                        fontFamily = openSans,
                                        fontSize = 18.sp,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black.copy(alpha = 0.2f),
                                        maxLines = 1
                                    )
                                }

                                innerTextField()
                            }
                        }
                    )
                    Text(
                        text = "State",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontFamily = openSans,
                        fontSize = 18.sp
                    )

                    BasicTextField(
                        value = state,
                        onValueChange = { state = it },
                        singleLine = true,
                        textStyle = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = openSans,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(50.dp))
                            .padding(16.dp),
                        decorationBox = { innerTextField ->
                            Box(contentAlignment = Alignment.Center) {
                                if (state.isEmpty()) {
                                    Text("State (e.g. FL)",
                                        color = Color.Black.copy(alpha = 0.2f),
                                        fontSize = 18.sp,
                                        fontFamily = openSans,
                                        fontWeight = FontWeight.Bold)
                                }
                                innerTextField()
                            }
                        }
                    )
                    Text(
                        text = "Zipcode",
                        fontFamily = openSans,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    BasicTextField(
                        value = zipcode,
                        onValueChange = { zipcode = it },
                        singleLine = true,
                        textStyle = TextStyle(
                            fontFamily = openSans,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),

                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(50.dp))
                                    .background(Color.White)
                                    .padding(horizontal = 16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                if (zipcode.isEmpty()) {
                                    Text(
                                        text = "Enter Zipcode",
                                        fontFamily = openSans,
                                        fontSize = 18.sp,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black.copy(alpha = 0.2f),
                                        maxLines = 1
                                    )
                                }

                                innerTextField()
                            }
                        }
                    )
                    Button(
                        onClick = {
                                if (
                                    firstName.isBlank() ||
                                    lastName.isBlank() ||
                                    phoneNumber.isBlank() ||
                                    addressLine1.isBlank() ||
                                    city.isBlank() ||
                                    state.isBlank() ||
                                    zipcode.isBlank()
                                ) {
                                    saveMessage = "Please complete all required fields."
                                } else {
                                    coroutineScope.launch {
                                        isSaving = true
                                        saveMessage = null

                                        try {
                                            val request = LoaneeRegistration(
                                                firstName = firstName.trim(),
                                                lastName = lastName.trim(),
                                                phoneNumber = phoneNumber.trim(),
                                                addressLine1 = addressLine1.trim(),
                                                addressLine2 = addressLine2.trim(),
                                                city = city.trim(),
                                                state = state.trim().uppercase(),
                                                postalCode = zipcode.trim()
                                            )

                                            ApiClient.apiService.updateLoanee(
                                                id = participantId,
                                                request = request
                                            )

                                            saveMessage = "Participant information updated successfully!"
                                            navController.navigate("dashboard")
                                        } catch (e: Exception) {
                                            saveMessage = "Update failed: ${e.message}"
                                        } finally {
                                            isSaving = false
                                        }
                                    }
                                }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                            .height(buttonHeight),
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SecondaryColor
                        )
                    ) {

                        Text(
                            text = "Save Changes",
                            fontFamily = openSans,
                            fontSize = buttonTextSize,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    errorMessage?.let {
                        Text(
                            text = it,
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    }

                }


            }
        }
    }
}

