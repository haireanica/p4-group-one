package p4groupone.lenditandroid.ui.participants

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import p4groupone.lenditandroid.ui.dashboard.DashboardViewScreen

@Composable
fun ParticipantSearchFormScreen(navController: NavController) {

}

@Preview(showBackground = true)
@Composable
fun ParticipantSearchFormScreenPreview() {
    val navController = rememberNavController()

    ParticipantSearchFormScreen(
        navController = navController
    )
}