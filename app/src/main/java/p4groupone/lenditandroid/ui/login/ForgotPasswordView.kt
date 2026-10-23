package p4groupone.lenditandroid.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import p4groupone.lenditandroid.ui.dashboard.DashboardViewScreen

@Composable
fun ForgotPasswordViewScreen(navController: NavController) {

}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordViewScreenPreview() {
    val navController = rememberNavController()

    ForgotPasswordViewScreen(
        navController = navController
    )
}