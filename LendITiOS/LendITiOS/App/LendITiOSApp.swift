
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
                            
                        case let .inventorySearchResults(deviceType, center, status):
                            InventorySearchResults(
                                deviceType: deviceType,
                                center: center,
                                status: status
                            )
                            
                        case .newLoanSearchParticipant:
                            NewLoanSearchParticipant()

                        case let .newLoanSelectDetails(participant):
                            NewLoanSelectDetails(participant: participant)
                        
                        case let .newLoanSelectDevice(participant, deviceType, center, days, reason):
                            NewLoanSelectDevice(
                                participant: participant,
                                deviceType: deviceType,
                                center: center,
                                days: days,
                                reason: reason
                            )
                            
                        case let .newLoanSelectParticipant(participants):
                            NewLoanSelectParticipant(participants: participants)
                            
                        case .forgotPassword:
                            ForgotPasswordView()
                            
                        case .forgotPasswordReset:
                            ForgotPasswordReset()
                            
                        case .login:
                            LoginView()
                            
                        case let .participantEditInfo(participant):
                            ParticipantEditInfoView(participant: participant)
                            
                        case .participantSearchForm:
                            ParticipantSearchFormView()

                        case .signUp:
                            SignUpView()

                        case let .participantSearchResults(lastName, phoneNumber):
                            ParticipantSearchResultsView(
                                lastName: lastName,
                                phoneNumber: phoneNumber
                            )
                            
                        case let .participantViewLoans(participant):
                            ParticipantViewLoans(participant: participant)

                        case let .returnSelectParticipant(participants):
                            ReturnSelectParticipant(participants: participants)

                        case let .returnSelectDevice(participant):
                            ReturnSelectDevice(participant: participant)
                            
                        case .returnSearchParticipant:
                            ReturnSearchParticipant()
                        }
                    }
            }
            .environment(navigation)
        }
    }
}
