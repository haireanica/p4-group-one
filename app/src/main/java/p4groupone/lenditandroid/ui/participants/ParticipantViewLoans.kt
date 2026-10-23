package p4groupone.lenditandroid.ui.participants

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import p4groupone.lenditandroid.ui.dashboard.DashboardViewScreen

@Composable
fun ParticipantViewLoansScreen(navController: NavController) {

}

@Preview(showBackground = true)
@Composable
fun ParticipantViewLoansScreenPreview() {
    val navController = rememberNavController()

    ParticipantViewLoansScreen(
        navController = navController
    )
}