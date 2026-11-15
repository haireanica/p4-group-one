

import SwiftUI

enum AppRoute: Hashable {
    
    case dashboard
    case inventorySearch
    case inventorySearchResults(deviceType: String, center: String, status: String)
    case newLoanSearchParticipant
    case newLoanSelectDetails(participant: Participant)
    case newLoanSelectDevice(
        participant: Participant,
        deviceType: String,
        center: String,
        days: Int,
        reason: String
    )
    case newLoanSelectParticipant(participants: [Participant])
    case login
    case forgotPassword
    case forgotPasswordReset
    case participantEditInfo(participant: Participant)
    case participantSearchForm
    case participantSearchResults(lastName: String, phoneNumber: String)
    case participantViewLoans(participant: Participant)
    case signUp
    case returnSearchParticipant
    case returnSelectParticipant(participants: [Participant])
    case returnSelectDevice(participant: Participant)
}

@Observable
final class AppNavigation {
    var path = NavigationPath()

    func navigate(to route: AppRoute) {
        print("Navigating to: \(route)")
        path.append(route)
        print("Navigation path updated")
    }

    func goToDashboard() {
        path = NavigationPath()
        path.append(AppRoute.dashboard)
    }

    func goBack() {
        guard !path.isEmpty else { return }
        path.removeLast()
    }
    func signOut() {
        path = NavigationPath()
    }
}
