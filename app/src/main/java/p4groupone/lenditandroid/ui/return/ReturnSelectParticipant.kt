package p4groupone.lenditandroid.ui.`return`

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import p4groupone.lenditandroid.ui.dashboard.DashboardViewScreen

@Composable
fun ReturnSelectParticipantScreen(navController: NavController) {

}

@Preview(showBackground = true)
@Composable
fun ReturnSelectParticipantScreenPreview() {
    val navController = rememberNavController()

    ReturnSelectParticipantScreen(
        navController = navController
    )
}