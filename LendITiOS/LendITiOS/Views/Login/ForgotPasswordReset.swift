//
//  ForgotPasswordReset.swift
//  LendITiOS
//
//  Created by Steven Petocz on 10/22/26.
//

import SwiftUI

struct ForgotPasswordReset: View {
    @State private var password = ""
    @State private var confirmPassword = ""

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
                        
                        Text("New Password")
                            .font(.custom("Open Sans Bold", size: 20))
                            .foregroundStyle(.white)
                            .frame(
                                maxWidth: .infinity,
                                alignment: .leading
                            )
                        
                        //need to add toggle for plain text (eyeball thingy)
                        SecureField("New Password", text: $password)
                            .multilineTextAlignment(.center)
                            .font(.custom("Open Sans", size: 25))
                            .padding()
                            .background(.white)
                            .clipShape(
                                RoundedRectangle(cornerRadius: 50)
                            )
                            .overlay {
                                if password.isEmpty {
                                    Text("New Password")
                                        .font(
                                            .custom("Open Sans", size: 25)
                                        )
                                        .foregroundStyle(.black.opacity(0.3))
                                        .allowsHitTesting(false)
                                }
                            }
                        Text("Confirm Password")
                            .font(.custom("Open Sans Bold", size: 20))
                            .foregroundStyle(.white)
                            .frame(
                                maxWidth: .infinity,
                                alignment: .leading
                            )
                        //need to add toggle for plain text (eyeball thingy)
                        SecureField("Confirm Password", text: $confirmPassword)
                            .multilineTextAlignment(.center)
                            .font(.custom("Open Sans", size: 25))
                            .padding()
                            .background(.white)
                            .clipShape(
                                RoundedRectangle(cornerRadius: 50)
                            )
                            .overlay {
                                if confirmPassword.isEmpty {
                                    Text("Confirm Password")
                                        .font(
                                            .custom("Open Sans", size: 25)
                                        )
                                        .foregroundStyle(.black.opacity(0.3))
                                        .allowsHitTesting(false)
                                }
                            }
                        Button {
                            navigation.goToDashboard()
                        } label: {
                            Text("Change Password")
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
    ForgotPasswordReset()
        .environment(AppNavigation())
}

