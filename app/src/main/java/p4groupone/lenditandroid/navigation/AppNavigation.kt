package p4groupone.lenditandroid.navigation

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import p4groupone.lenditandroid.ui.dashboard.DashboardViewScreen
import p4groupone.lenditandroid.ui.inventory.InventorySearchResultsScreen
import p4groupone.lenditandroid.ui.inventory.InventorySearchScreen
import p4groupone.lenditandroid.ui.loans.NewLoanSearchParticipantScreen
import p4groupone.lenditandroid.ui.loans.NewLoanSelectDetailsScreen
import p4groupone.lenditandroid.ui.loans.NewLoanSelectDeviceScreen
import p4groupone.lenditandroid.ui.loans.NewLoanSelectParticipantScreen
import p4groupone.lenditandroid.ui.login.ForgotPasswordResetScreen
import p4groupone.lenditandroid.ui.login.ForgotPasswordViewScreen
import p4groupone.lenditandroid.ui.login.LoginViewScreen
import p4groupone.lenditandroid.ui.participants.ParticipantEditInfoScreen
import p4groupone.lenditandroid.ui.participants.ParticipantSearchFormScreen
import p4groupone.lenditandroid.ui.participants.ParticipantSearchResultsScreen
import p4groupone.lenditandroid.ui.participants.ParticipantViewLoansScreen
import p4groupone.lenditandroid.ui.participants.SignUpViewScreen
import p4groupone.lenditandroid.ui.`return`.ReturnSearchParticipantScreen
import p4groupone.lenditandroid.ui.`return`.ReturnSelectDeviceScreen
import p4groupone.lenditandroid.ui.`return`.ReturnSelectParticipantScreen

@Composable
fun AppNavigation(){

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login",
        enterTransition =
            { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ){
        composable("login"){
            LoginViewScreen(navController)
        }

        composable("dashboard"){
            DashboardViewScreen(navController)
        }

        composable("inventorySearch") {
            InventorySearchScreen(navController)
        }

        composable("inventorySearchResults") {
            InventorySearchResultsScreen(navController)
        }

        composable("newLoanSearchParticipant") {
            NewLoanSearchParticipantScreen(navController)
        }

        composable("newLoanSelectDetails") {
            NewLoanSelectDetailsScreen(navController)
        }

        composable("newLoanSelectDevice") {
            NewLoanSelectDeviceScreen(navController)
        }

        composable("newLoanSelectParticipant") {
            NewLoanSelectParticipantScreen(navController)
        }

        composable("forgotPasswordReset") {
            ForgotPasswordResetScreen(navController)
        }

        composable("forgotPasswordView") {
            ForgotPasswordViewScreen(navController)
        }

        composable("participantEditInfo") {
            ParticipantEditInfoScreen(navController)
        }

        composable("participantSearchForm") {
            ParticipantSearchFormScreen(navController)
        }

        composable("participantSearchResults") {
            ParticipantSearchResultsScreen(navController)
        }

        composable("participantViewLoans") {
            ParticipantViewLoansScreen(navController)
        }

        composable("signUpView") {
            SignUpViewScreen(navController)
        }

        composable("returnSearchParticipant") {
            ReturnSearchParticipantScreen(navController)
        }

        composable("returnSelectDevice") {
            ReturnSelectDeviceScreen(navController)
        }

        composable("returnSelectParticipant") {
            ReturnSelectParticipantScreen(navController)
        }




    }
}