//
//  LoginView.swift
//  LendITiOS
//
//  Created by Steven Petocz on 9/16/26.
//

import SwiftUI

struct LoginView: View {
    
    @State private var username = ""
    @State private var password = ""
    @State private var rememberMe = false
    
    var body: some View {
        ZStack {
            Color("Primary Color")
                .ignoresSafeArea()

            VStack(spacing: 0) {

                // Top colored section
                ZStack {
                    UnevenRoundedRectangle(
                        topLeadingRadius: 0,
                        bottomLeadingRadius: 0,
                        bottomTrailingRadius: 100,
                        topTrailingRadius: 0
                    )
                    .fill(Color("Secondary Color"))
                    .frame(maxWidth: .infinity)
                    .frame(height: 270)
                    .position(x: 200, y: 70)

                    // Logo + Branding
                    VStack(spacing: 0) {
                        Image("OrlandoLogo")
                            .resizable()
                            .scaledToFit()
                            .frame(width: 200, height: 200)
                        Spacer()
                            .frame(height: 20)

                        Text("Orlando")
                            .foregroundStyle(.white)

                        HStack(spacing: 0) {
                            Text("Lend ")
                                .foregroundStyle(.white)

                            Text("IT")
                                .foregroundStyle(Color("Primary Color"))
                                .background(
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
                                )
                        }
                    }
                    .font(.custom("Open Sans Bold", size: 50))
                }

                // Login form
                VStack(spacing: 8) {
                    
                    Text("Username")
                        .font(.custom("Open Sans Bold", size: 20))
                        .foregroundStyle(.white)
                        .frame(maxWidth: .infinity, alignment: .leading)
                    
                    TextField("Enter Username", text: $username)
                        .multilineTextAlignment(.center)
                        .font(.custom("Open Sans", size: 25))
                        .padding()
                        .background(.white)
                        .clipShape(RoundedRectangle(cornerRadius: 50))
                        .overlay {
                                if username.isEmpty {
                                    Text("Enter Username")
                                        .font(.custom("Open Sans", size: 25))
                                        .foregroundStyle(.black.opacity(0.3))
                                        .allowsHitTesting(false)
                                }
                            }
                    
                    Text("Password")
                        .font(.custom("Open Sans Bold", size: 20))
                        .foregroundStyle(.white)
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .padding(.top, 10)
                    
                    SecureField("Enter Password", text: $password)
                        .multilineTextAlignment(.center)
                        .font(.custom("Open Sans", size: 25))
                        .padding()
                        .background(.white)
                        .clipShape(RoundedRectangle(cornerRadius: 50))
                        .overlay {
                                if username.isEmpty {
                                    Text("Enter Password")
                                        .font(.custom("Open Sans", size: 25))
                                        .foregroundStyle(.black.opacity(0.3))
                                        .allowsHitTesting(false)
                                }
                            }
                    
                    HStack {
                        Button {
                            //toggle remember m
                            rememberMe.toggle()
                        } label: {
                            Image(systemName: rememberMe ? "checkmark.square.fill" : "square")
                                .font(.system(size: 25))
                                .foregroundColor(Color.white)
                        }
                        Text("Remember Me")
                            .font(.custom("Open Sans Bold", size: 20))
                            .foregroundStyle(.white)
                        
                        Spacer()
                    }
                    .padding(.top, 10)
                    
                    Button {
                        // log in
                    } label: {
                        Text("Log In")
                            .font(.custom("Open Sans Bold", size: 30))
                            .foregroundStyle(.white)
                            .frame(maxWidth: .infinity)
                            .frame(height: 60)
                            .background(Color("Secondary Color"))
                            .clipShape(RoundedRectangle(cornerRadius: 50))
                    }
                    .padding(.top, 15)
                    
                    
                    Button {
                        //forgot password
                    } label: {
                        Text("Forgot Password")
                            .font(.custom("Open Sans Bold", size: 20))
                            .foregroundStyle(.white)
                            .underline()
                            .padding(.top, 10)
                    }
                }
                .padding(.horizontal, 30)
                .padding(.top, 30)

                Spacer()
            }
        }
    }
}

#Preview {
    LoginView()
}
