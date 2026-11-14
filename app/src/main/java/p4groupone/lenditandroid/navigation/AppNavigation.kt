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
import androidx.navigation.NavType
import androidx.navigation.navArgument

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

        composable("inventorySearchResults/{deviceType}/{center}/{status}") { backStackEntry ->

            val deviceType = backStackEntry.arguments?.getString("deviceType") ?: ""
            val center = backStackEntry.arguments?.getString("center") ?: ""
            val status = backStackEntry.arguments?.getString("status") ?: ""

            InventorySearchResultsScreen(
                navController = navController,
                deviceType = deviceType,
                center = center,
                status = status
            )
        }

        composable("newLoanSearchParticipant") {
            NewLoanSearchParticipantScreen(navController)
        }

        composable("newLoanSelectDetails/{participantId}") { backStackEntry ->

            val participantId = backStackEntry.arguments
                ?.getString("participantId")
                ?.toIntOrNull()

            if (participantId != null) {
                NewLoanSelectDetailsScreen(
                    navController = navController,
                    participantId = participantId
                )
            }
        }

        composable(
            "newLoanSelectDevice/{participantId}/{deviceType}/{center}/{duration}/{reason}"
        ) { backStackEntry ->

            val participantId = backStackEntry.arguments
                ?.getString("participantId")
                ?.toIntOrNull()

            val deviceType = backStackEntry.arguments
                ?.getString("deviceType") ?: "All"

            val center = backStackEntry.arguments
                ?.getString("center") ?: "All"

            val duration = backStackEntry.arguments
                ?.getString("duration") ?: "1 Week"

            val reason = backStackEntry.arguments
                ?.getString("reason") ?: "EDUCATION"

            if (participantId != null) {
                NewLoanSelectDeviceScreen(
                    navController = navController,
                    participantId = participantId,
                    deviceType = deviceType,
                    center = center,
                    duration = duration,
                    reason = reason
                )
            }
        }

        composable(
            route = "newLoanSelectParticipant/{lastName}/{phoneNumber}"
        ) { backStackEntry ->

            val lastName = backStackEntry.arguments?.getString("lastName") ?: ""
            val phoneNumber = backStackEntry.arguments?.getString("phoneNumber") ?: ""

            NewLoanSelectParticipantScreen(
                navController = navController,
                lastName = lastName,
                phoneNumber = phoneNumber
            )
        }

        composable("forgotPasswordReset") {
            ForgotPasswordResetScreen(navController)
        }

        composable("forgotPasswordView") {
            ForgotPasswordViewScreen(navController)
        }

        composable("participantEditInfo/{participantId}") { backStackEntry ->

            val participantId = backStackEntry.arguments
                ?.getString("participantId")
                ?.toIntOrNull()

            if (participantId != null) {
                ParticipantEditInfoScreen(
                    navController = navController,
                    participantId = participantId
                )
            }
        }

        composable("participantSearchForm") {
            ParticipantSearchFormScreen(navController)
        }

        composable(
            route = "participantSearchResults/{lastName}/{phoneNumber}"
        ) { backStackEntry ->

            val lastName = backStackEntry.arguments?.getString("lastName") ?: ""
            val phoneNumber = backStackEntry.arguments?.getString("phoneNumber") ?: ""

            ParticipantSearchResultsScreen(
                navController = navController,
                lastName = lastName,
                phoneNumber = phoneNumber
            )
        }

        composable(
            route = "participantViewLoans/{participantId}"
        ) { backStackEntry ->

            val participantId =
                backStackEntry.arguments?.getString("participantId") ?: ""

            ParticipantViewLoansScreen(
                navController = navController,
                participantId = participantId
            )
        }

        composable("signUpView") {
            SignUpViewScreen(navController)
        }

        composable("returnSearchParticipant") {
            ReturnSearchParticipantScreen(navController)
        }

        composable(
            route = "returnSelectDevice/{participantId}",
            arguments = listOf(
                navArgument("participantId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val participantId =
                backStackEntry.arguments?.getString("participantId")

            ReturnSelectDeviceScreen(
                navController = navController,
                participantId = participantId
            )
        }

        composable(
            route = "returnSelectParticipant/{lastName}/{phoneNumber}"
        ) { backStackEntry ->

            val lastName = backStackEntry.arguments?.getString("lastName") ?: ""
            val phoneNumber = backStackEntry.arguments?.getString("phoneNumber") ?: ""

            ReturnSelectParticipantScreen(
                navController = navController,
                lastName = lastName,
                phoneNumber = phoneNumber
            )
        }




    }
}