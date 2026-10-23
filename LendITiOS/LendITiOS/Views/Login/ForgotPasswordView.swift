//
//  ForgotPasswordView.swift
//  LendITiOS
//
//  Created by Steven Petocz on 10/20/26.
//

import SwiftUI

struct ForgotPasswordView: View {
    @State private var username = ""
    @State private var email = ""

    @Environment(AppNavigation.self) private var navigation
    var body: some View {
        ZStack {
            Color("Primary Color")
                .ignoresSafeArea()

            VStack(spacing: 0) {

                // Header
                ZStack(alignment: .top) {
                    UnevenRoundedRectangle(
                        topLeadingRadius: 0,
                        bottomLeadingRadius: 0,
                        bottomTrailingRadius: 100,
                        topTrailingRadius: 0
                    )
                    .fill(Color.white)
                    .frame(maxWidth: .infinity)
                    .frame(height: 220)
                    
                    UnevenRoundedRectangle(
                        topLeadingRadius: 0,
                        bottomLeadingRadius: 0,
                        bottomTrailingRadius: 100,
                        topTrailingRadius: 0
                    )
                    .fill(Color("Secondary Color"))
                    .frame(maxWidth: .infinity)
                    .frame(height: 210)
                    VStack{
                        Spacer()
                        Spacer()
                        Spacer()
                        Text("Forgot Password")
                            .foregroundStyle(.white)
                            .font(.custom("Open Sans Bold", size: 55))
                        Spacer()
                    }
                }
                .frame(height: 180)

                // Main content
                VStack(spacing: 0){
                    HStack{
                        Text("Reset Password")
                            .font(.custom("Open Sans Bold", size: 20))
                            .foregroundStyle(.white)
                        Spacer()
                        Button {
                            navigation.navigate(to: .login)
                        } label: {
                            Text("Return")
                                .font(.custom("Open Sans Bold", size: 24))
                                .foregroundStyle(.white)
                                .frame(width: 140)
                                .frame(height: 30)
                                .background(Color("Secondary Color"))
                                .clipShape(RoundedRectangle(cornerRadius: 50))
                        }
                    
                    }.padding(.top, 35)
                        .padding(.horizontal, 20)
                    
                    VStack{
                        
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
                        Text("Email")
                            .font(.custom("Open Sans Bold", size: 20))
                            .foregroundStyle(.white)
                            .frame(
                                maxWidth: .infinity,
                                alignment: .leading
                            )
                        
                        TextField("Enter Email", text: $email)
                            .multilineTextAlignment(.center)
                            .font(.custom("Open Sans", size: 25))
                            .padding()
                            .background(.white)
                            .clipShape(
                                RoundedRectangle(cornerRadius: 50)
                            )
                            .overlay {
                                if email.isEmpty {
                                    Text("Enter Email")
                                        .font(
                                            .custom("Open Sans", size: 25)
                                        )
                                        .foregroundStyle(.black.opacity(0.3))
                                        .allowsHitTesting(false)
                                }
                            }
                        Button {
                            navigation.navigate(to: .forgotPasswordReset)
                        } label: {
                            Text("Reset Password")
                                .font(.custom("Open Sans Bold", size: 30))
                                .foregroundStyle(.white)
                                .frame(maxWidth: .infinity)
                                .frame(height: 60)
                                .background(Color("Secondary Color"))
                                .clipShape(
                                    RoundedRectangle(cornerRadius: 50)
                                )
                        }
                        .padding(.top, 25)
                    }.padding(.horizontal, 20)
                        .padding(.top, 20)
                    
                }
                
                Spacer()
                
            }
        }
        .ignoresSafeArea(edges: .top)
            }
}


#Preview {
    ForgotPasswordView()
        .environment(AppNavigation())
}
