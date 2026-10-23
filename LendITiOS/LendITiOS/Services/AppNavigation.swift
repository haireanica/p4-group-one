//
//  AppNavigation.swift
//  LendITiOS
//
//  Created by Steven Petocz on 10/21/26.
//


import SwiftUI

enum AppRoute: Hashable {
    
    case dashboard
    case inventorySearch
    case inventorySearchResults
    case newLoanSearchParticipant
    case newLoanSelectDetails
    case newLoanSelectDevice
    case newLoanSelectParticipant
    case login
    case forgotPassword
    case forgotPasswordReset
    case participantEditInfo
    case participantSearchForm
    case participantSearchResults
    case participantViewLoans
    case signUp
    case returnSearchParticipant
    case returnSelectParticipant
    case returnSelectDevice
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
