package p4groupone.lenditandroid.ui.loans

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import p4groupone.lenditandroid.ui.dashboard.DashboardViewScreen

@Composable
fun NewLoanSelectDeviceScreen(navController: NavController) {

}

@Preview(showBackground = true)
@Composable
fun NewLoanSelectDeviceScreenPreview() {
    val navController = rememberNavController()

    NewLoanSelectDeviceScreen(
        navController = navController
    )
}