package p4groupone.lenditandroid.ui.participants

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import p4groupone.lenditandroid.ui.dashboard.DashboardViewScreen

@Composable
fun ParticipantSearchResultsScreen(navController: NavController) {

}

@Preview(showBackground = true)
@Composable
fun ParticipantSearchResultsScreenPreview() {
    val navController = rememberNavController()

    ParticipantSearchResultsScreen(
        navController = navController
    )
}