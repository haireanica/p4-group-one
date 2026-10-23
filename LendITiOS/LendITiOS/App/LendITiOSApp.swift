//
//  LendITiOSApp.swift
//  LendITiOS
//
//  Created by Steven Petocz on 9/16/26.
//

import SwiftUI

@main
struct LendITiOSApp: App {

    @State private var navigation = AppNavigation()

    var body: some Scene {
        WindowGroup {
            NavigationStack(path: $navigation.path) {
                LoginView()
                    .navigationDestination(for: AppRoute.self) { route in
                       
                        switch route {
                        case .dashboard:
                            DashboardView()
                            
                        case .inventorySearch:
                            InventorySearch()
                            
                        case .inventorySearchResults:
                            InventorySearchResults(
                                devices: Device.sampleData
                            )
                            
                        case .newLoanSearchParticipant:
                            NewLoanSearchParticipant()

                        case .newLoanSelectDetails:
                            NewLoanSelectDetails()
                        
                        case .newLoanSelectDevice:
                            NewLoanSelectDevice(devices: Device.sampleData)
                            
                        case .newLoanSelectParticipant:
                            NewLoanSelectParticipant(participants: Participant.sampleData)
                            
                        case .forgotPassword:
                            ForgotPasswordView()
                            
                        case .forgotPasswordReset:
                            ForgotPasswordReset()
                            
                        case .login:
                            LoginView()
                            
                        case .participantEditInfo:
                            ParticipantEditInfoView()
                            
                        case .participantSearchForm:
                            ParticipantSearchFormView()

                        case .signUp:
                            SignUpView()

                        case .participantSearchResults:
                            ParticipantSearchResultsView(
                                participants: Participant.sampleData
                            )
                            
                        case .participantViewLoans:
                            ParticipantViewLoans(loans: Loan.sampleData)

                        case .returnSelectParticipant:
                            ReturnSelectParticipant(participants: Participant.sampleData)

                        case .returnSelectDevice:
                            ReturnSelectDevice(
                                devices: Device.sampleData
                            )
                            
                        case .returnSearchParticipant:
                            ReturnSearchParticipant()
                        }
                    }
            }
            .environment(navigation)
        }
    }
}
