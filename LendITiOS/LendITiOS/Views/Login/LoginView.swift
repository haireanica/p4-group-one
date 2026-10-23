//
//  LoginView.swift
//  LendITiOS
//
//  Created by Steven Petocz on 9/16/26.
//

import SwiftUI

struct LoginView: View {
    @Environment(AppNavigation.self) private var navigation

    @State private var username = ""
    @State private var password = ""
    @State private var rememberMe = false

    var body: some View {
        GeometryReader { geometry in
            ZStack(alignment: .top) {
                Color("Primary Color")
                    .ignoresSafeArea()

                VStack(spacing: 0) {

                    ZStack(alignment: .top) {
                        UnevenRoundedRectangle(
                            topLeadingRadius: 0,
                            bottomLeadingRadius: 0,
                            bottomTrailingRadius: 100,
                            topTrailingRadius: 0
                        )
                        .fill(.white)
                        .frame(height: 280)

                        UnevenRoundedRectangle(
                            topLeadingRadius: 0,
                            bottomLeadingRadius: 0,
                            bottomTrailingRadius: 100,
                            topTrailingRadius: 0
                        )
                        .fill(Color("Secondary Color"))
                        .frame(height: 270)
                        
                        VStack(spacing: 0) {
                            Spacer()
                            Spacer()
                            Spacer()
                            Spacer()
                            Image("OrlandoLogo")
                                .resizable()
                                .scaledToFit()
                                .frame(width: 230, height: 230)
                            Spacer()

                        }
                        
                    }
                    .frame(height: 280)
                    .frame(maxWidth: .infinity)

                    VStack(spacing: 5) {
                        VStack{
                            Text("Orlando")
                                .foregroundStyle(.white)
                            
                            HStack(spacing: 0) {
                                Text("Lend ")
                                    .foregroundStyle(.white)
                                
                                Text("IT")
                                    .foregroundStyle(Color("Primary Color"))
                                    .background {
                                        ZStack {
                                            Text("IT")
                                                .offset(x: 2, y: 2)
                                            Text("IT")
                                                .offset(x: -2, y: -2)
                                            Text("IT")
                                                .offset(x: 2, y: -2)
                                            Text("IT")
                                                .offset(x: -2, y: 2)
                                        }
                                        .foregroundStyle(.white)
                                    }
                            }
                        }.font(.custom("Open Sans Bold", size: 64))
                        Text("Username")
                            .font(.custom("Open Sans Bold", size: 20))
                            .foregroundStyle(.white)
                            .frame(
                                maxWidth: .infinity,
                                alignment: .leading
                            )

                        TextField("Enter Username", text: $username)
                            .multilineTextAlignment(.center)
                            .font(.custom("Open Sans", size: 25))
                            .padding()
                            .background(.white)
                            .clipShape(
                                RoundedRectangle(cornerRadius: 50)
                            )
                            .overlay {
                                if username.isEmpty {
                                    Text("Enter Username")
                                        .font(
                                            .custom("Open Sans", size: 25)
                                        )
                                        .foregroundStyle(.black.opacity(0.3))
                                        .allowsHitTesting(false)
                                }
                            }

                        Text("Password")
                            .font(.custom("Open Sans Bold", size: 20))
                            .foregroundStyle(.white)
                            .frame(
                                maxWidth: .infinity,
                                alignment: .leading
                            )
                            .padding(.top, 10)

                        //need to add toggle for plain text (eyeball thingy)
                        SecureField("Enter Password", text: $password)
                            .multilineTextAlignment(.center)
                            .font(.custom("Open Sans", size: 25))
                            .padding()
                            .background(.white)
                            .clipShape(
                                RoundedRectangle(cornerRadius: 50)
                            )
                            .overlay {
                                if password.isEmpty {
                                    Text("Enter Password")
                                        .font(
                                            .custom("Open Sans", size: 25)
                                        )
                                        .foregroundStyle(.black.opacity(0.3))
                                        .allowsHitTesting(false)
                                }
                            }

                        HStack(spacing: 8) {
                            Button {
                                rememberMe.toggle()
                            } label: {
                                Image(
                                    systemName: rememberMe
                                        ? "checkmark.square.fill"
                                        : "square"
                                )
                                .font(.system(size: 25))
                                .foregroundStyle(.white)
                            }

                            Text("Remember Me")
                                .font(.custom("Open Sans Bold", size: 20))
                                .foregroundStyle(.white)

                            Spacer()
                        }
                        .padding(.top, 10)

                        Button {
                            navigation.goToDashboard()
                        } label: {
                            Text("Log In")
                                .font(.custom("Open Sans Bold", size: 30))
                                .foregroundStyle(.white)
                                .frame(maxWidth: .infinity)
                                .frame(height: 60)
                                .background(Color("Secondary Color"))
                                .clipShape(
                                    RoundedRectangle(cornerRadius: 50)
                                )
                        }
                        .padding(.top, 15)

                        Button {
                            navigation.navigate(to: .forgotPassword)
                        } label: {
                            Text("Forgot Password")
                                .font(.custom("Open Sans Bold", size: 20))
                                .foregroundStyle(.white)
                                .underline()
                                .padding(.top, 10)
                        }
                    }
                    .padding(.horizontal, 30)
                    .padding(.top, 20)

                    Spacer(minLength: 0)
                }
                .frame(
                    width: geometry.size.width,
                    height: .infinity,
                    alignment: .top
                )
            }
            .frame(
                width: geometry.size.width,
                height: .infinity,
                alignment: .top
            )
            .ignoresSafeArea(edges: .top)
        }
    }
}

// MARK: Preview

#Preview {
    LendITiOSAppPreview()
}

private struct LendITiOSAppPreview: View {
    @State private var navigation = AppNavigation()

    var body: some View {
        NavigationStack(path: $navigation.path) {
            LoginView()
                .navigationDestination(for: AppRoute.self) { route in
                    switch route {
                    case .dashboard:
                        DashboardView()

                    case .forgotPassword:
                        ForgotPasswordView()
                        
                    case .forgotPasswordReset:
                        ForgotPasswordReset()

                    case .login:
                        LoginView()

                    case .signUp:
                        SignUpView()

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
                        NewLoanSelectDevice(
                            devices: Device.sampleData
                        )

                    case .newLoanSelectParticipant:
                        NewLoanSelectParticipant(
                            participants: Participant.sampleData
                        )
                        
                    case .participantViewLoans:
                        ParticipantViewLoans(loans: Loan.sampleData)

                    case .participantEditInfo:
                        ParticipantEditInfoView()

                    case .participantSearchForm:
                        ParticipantSearchFormView()

                    case .participantSearchResults:
                        ParticipantSearchResultsView(
                            participants: Participant.sampleData
                        )

                    case .returnSearchParticipant:
                        ReturnSearchParticipant()

                    case .returnSelectParticipant:
                        ReturnSelectParticipant(
                            participants: Participant.sampleData
                        )

                    case .returnSelectDevice:
                        ReturnSelectDevice(
                            devices: Device.sampleData
                        )
                    }
                }
        }
        .environment(navigation)
    }
}
