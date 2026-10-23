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
import p4groupone.lenditandroid.ui.components.LendITDropdown
import p4groupone.lenditandroid.ui.dashboard.DashboardViewScreen
import p4groupone.lenditandroid.ui.theme.PrimaryColor
import p4groupone.lenditandroid.ui.theme.SecondaryColor
import p4groupone.lenditandroid.ui.theme.openSans

@Composable
fun NewLoanSelectDetailsScreen(navController: NavController) {
    var selectedDeviceType by remember {
        mutableStateOf("Laptop")
    }

    val deviceTypes = listOf(
        "Laptop",
        "Tablet",
        "Hotspot"
    )
    var selectedCenter by remember {
        mutableStateOf("Office for Local Aassistance (OLA)")
    }
    val centers = listOf(
        "Dr. James R. Smith Neighborhood Center",
        "Engelwood Neighborhood Center",
        "John H. Jackson Neighborhood Center",
        "Rosemont Neighborhood Center",
        "L. Claudia Allen Senior Center",
        "Office for Local Aassistance (OLA)",
        "Dr. I Sylvester Hankins Park Neighborhood Center",
        "Callahan Neighborhood Center",
        "Northwest Neighborhood Center",
        "Dover Shores Neighborhood Center",
        "Grand Avenue Neighborhood Center",
        "Ivey Lane Neighborhood Center",
        "Langford Neighborhood Center",
        "Rock Lake Neighborhood Center",
        "Citrus Square Neighborhood Center",
        "RISE Employment and Training Office"
    )

    var selectedDuration by remember {
        mutableStateOf("1 Week")
    }
    val durations = listOf(
        "1 Week",
        "2 Weeks"
    )

    var selectedReason by remember {
        mutableStateOf("Education")
    }
    val reasons = listOf(
        "Education",
        "Employment",
        "Personal Use"
    )

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
                        text = "Select Details",
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
                .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                LendITDropdown(
                    label = "Device Type",
                    options = deviceTypes,
                    selectedValue = selectedDeviceType,
                    onValueChange = { selectedDeviceType = it }
                )

                LendITDropdown(
                    label = "Center Location",
                    options = centers,
                    selectedValue = selectedCenter,
                    onValueChange = { selectedCenter = it }
                )

                LendITDropdown(
                    label = "Loan Duration",
                    options = durations,
                    selectedValue = selectedDuration,
                    onValueChange = { selectedDuration = it }
                )

                LendITDropdown(
                    label = "Reason",
                    options = reasons,
                    selectedValue = selectedReason,
                    onValueChange = { selectedReason = it }
                )

                Button(
                    onClick = {
                        navController.navigate("newLoanSelectDevice")
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
                        text = "Check Inventory",
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

@Preview(showBackground = true)
@Composable
fun NewLoanSelectDetailsScreenPreview() {
    val navController = rememberNavController()

    NewLoanSelectDetailsScreen(
        navController = navController
    )
}