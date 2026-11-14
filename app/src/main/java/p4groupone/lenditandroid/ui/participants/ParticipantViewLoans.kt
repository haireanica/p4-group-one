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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import p4groupone.lenditandroid.models.SampleDevices
import p4groupone.lenditandroid.models.SampleLoans
import p4groupone.lenditandroid.ui.components.DeviceList
import p4groupone.lenditandroid.ui.components.LoanList
import p4groupone.lenditandroid.ui.dashboard.DashboardViewScreen
import p4groupone.lenditandroid.ui.theme.PrimaryColor
import p4groupone.lenditandroid.ui.theme.SecondaryColor
import p4groupone.lenditandroid.ui.theme.openSans
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import p4groupone.lenditandroid.network.ApiClient
import p4groupone.lenditandroid.models.Loan

@Composable
fun ParticipantViewLoansScreen(
    navController: NavController,
    participantId: String
) {
    var selectedLoanId by remember {
        mutableStateOf<String?>(null)
    }

    var loans by remember {
        mutableStateOf(emptyList<Loan>())
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    LaunchedEffect(participantId) {
        try {
            isLoading = true
            errorMessage = null

            loans = ApiClient.apiService
                .getLoans()
                .filter { it.participantId?.toString() == participantId  &&
                    it.status.equals("CHECKED_OUT", ignoreCase = true)}
                .map { it.toLoan() }

        } catch (e: Exception) {
            errorMessage = e.message ?: "Unable to load loans."
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
                        .height(220.dp * scale)
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
                            text = "Participant",
                            fontSize = 70.sp * scale,
                            fontFamily = openSans,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Left
                        )
                        Text(
                            text = "Loans",
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
                        text = "Select Loan",
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
                        .height(350.dp)
                ) {
                    when {
                        isLoading -> {
                            CircularProgressIndicator(
                                color = Color.White
                            )
                        }

                        errorMessage != null -> {
                            Text(
                                text = "Error: $errorMessage",
                                color = Color.White,
                                fontFamily = openSans
                            )
                        }

                        loans.isEmpty() -> {
                            Text(
                                text = "No loans found for this participant.",
                                fontSize = 20.sp,
                                fontFamily = openSans,
                                color = Color.White,
                                modifier = Modifier.padding(top = 20.dp)
                            )
                        }

                        else -> {
                            LoanList(
                                loans = loans,
                                selectedLoanId = selectedLoanId,
                                onLoanClick = { loan ->
                                    selectedLoanId = loan.id
                                }
                            )
                        }
                    }
                }


            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun ParticipantViewLoansScreenPreview() {
    val navController = rememberNavController()

    fun ParticipantViewLoansScreen(
        navController: NavController,
        participantId: String
    ) {
    }
}