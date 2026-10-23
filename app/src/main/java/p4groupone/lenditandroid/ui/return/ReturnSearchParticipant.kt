package p4groupone.lenditandroid.ui.`return`

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import p4groupone.lenditandroid.ui.dashboard.DashboardViewScreen

@Composable
fun ReturnSearchParticipantScreen(navController: NavController) {

}

@Preview(showBackground = true)
@Composable
fun ReturnSearchParticipantScreenPreview() {
    val navController = rememberNavController()

    ReturnSearchParticipantScreen(
        navController = navController
    )
}