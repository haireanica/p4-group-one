//
//  DashboardView.swift
//  LendITiOS
//
//  Created by Steven Petocz on 9/16/26.
//

import SwiftUI

struct DashboardView: View {
    @Environment(AppNavigation.self) private var navigation
    
    
    var body: some View {
            VStack{
                ZStack(){
                    UnevenRoundedRectangle(
                        topLeadingRadius: 0,
                        bottomLeadingRadius: 0,
                        bottomTrailingRadius: 100,
                        topTrailingRadius: 0
                    )
                    
                    .fill(Color(Color.white))
                    .frame(maxWidth: .infinity)
                    .frame(height: 280)
                    .ignoresSafeArea()
                    
                    UnevenRoundedRectangle(
                        topLeadingRadius: 0,
                        bottomLeadingRadius: 0,
                        bottomTrailingRadius: 100,
                        topTrailingRadius: 0
                    )
                    
                    .fill(Color("Primary Color"))
                    .frame(maxWidth: .infinity)
                    .frame(height: 270)
                    .ignoresSafeArea()
                    
                    VStack{
                        Spacer()
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
                        Spacer()
                        Spacer()
                        Spacer()
                        Spacer()
                    }
                    
                    
                    .font(.custom("Open Sans Bold", size: 80))
                }
                .frame(maxWidth: .infinity, alignment: .top)
                
                VStack(spacing: 30){
                    
                    HStack(spacing: 30){
                        
                        Button{
                            navigation.navigate(to: .signUp)
                        } label:{
                            ZStack{
                                UnevenRoundedRectangle(
                                    topLeadingRadius: 20,
                                    bottomLeadingRadius: 20,
                                    bottomTrailingRadius: 20,
                                    topTrailingRadius: 20
                                )
                                .fill(Color(Color.white))
                                .frame(width: 150, height: 150)
                                .ignoresSafeArea()
                                .shadow(
                                    color: .black.opacity(0.5),
                                    radius: 4,
                                    x: 5,
                                    y: 4
                                )
                                Image("sign_up_icon")
                                    .resizable()
                                    .scaledToFit()
                                    .frame(width: 80, height: 80)
                                    .offset(y: -20)
                                Text("Sign Up")
                                    .font(.custom("Open Sans Bold", size: 20))
                                    .offset(y: 40)
                            }
                        }.buttonStyle(.plain)
                        
                        Button{
                            navigation.navigate(to: .participantSearchForm)
                        } label: {
                            ZStack{
                                UnevenRoundedRectangle(
                                    topLeadingRadius: 20,
                                    bottomLeadingRadius: 20,
                                    bottomTrailingRadius: 20,
                                    topTrailingRadius: 20
                                )
                                .fill(Color(Color.white))
                                .frame(width: 150, height: 150)
                                .ignoresSafeArea()
                                .shadow(
                                    color: .black.opacity(0.5),
                                    radius: 4,
                                    x: 5,
                                    y: 4
                                )
                                Image("participants_icon")
                                    .resizable()
                                    .scaledToFit()
                                    .frame(width: 80, height: 80)
                                    .offset(y: -20)
                                Text("Participants")
                                    .font(.custom("Open Sans Bold", size: 20))
                                    .offset(y: 40)
                            }
                        }.buttonStyle(.plain)
                    }
                    HStack(spacing: 30){
                        Button{
                            navigation.navigate(to: .newLoanSearchParticipant)
                        } label: {
                            ZStack{
                                UnevenRoundedRectangle(
                                    topLeadingRadius: 20,
                                    bottomLeadingRadius: 20,
                                    bottomTrailingRadius: 20,
                                    topTrailingRadius: 20
                                )
                                .fill(Color(Color.white))
                                .frame(width: 150, height: 150)
                                .ignoresSafeArea()
                                .shadow(
                                    color: .black.opacity(0.5),
                                    radius: 4,
                                    x: 5,
                                    y: 4
                                )
                                Image("loan_icon")
                                    .resizable()
                                    .scaledToFit()
                                    .frame(width: 80, height: 80)
                                    .offset(y: -20)
                                Text("Loan")
                                    .font(.custom("Open Sans Bold", size: 20))
                                    .offset(y: 40)
                            }
                        }.buttonStyle(.plain)
                        
                        Button {
                            navigation.navigate(to: .returnSearchParticipant)
                        } label: {
                            ZStack{
                                UnevenRoundedRectangle(
                                    topLeadingRadius: 20,
                                    bottomLeadingRadius: 20,
                                    bottomTrailingRadius: 20,
                                    topTrailingRadius: 20
                                )
                                .fill(Color(Color.white))
                                .frame(width: 150, height: 150)
                                .ignoresSafeArea()
                                .shadow(
                                    color: .black.opacity(0.5),
                                    radius: 4,
                                    x: 5,
                                    y: 4
                                )
                                Image("return_icon")
                                    .resizable()
                                    .scaledToFit()
                                    .frame(width: 80, height: 80)
                                    .offset(y: -20)
                                Text("Return")
                                    .font(.custom("Open Sans Bold", size: 20))
                                    .offset(y: 40)
                            }
                        }.buttonStyle(.plain)
                    }
                    
                    HStack(spacing: 30){
                        
                        Button {
                            navigation.navigate(to: .inventorySearch)
                        } label: {
                            ZStack{
                                UnevenRoundedRectangle(
                                    topLeadingRadius: 20,
                                    bottomLeadingRadius: 20,
                                    bottomTrailingRadius: 20,
                                    topTrailingRadius: 20
                                )
                                .fill(Color(Color.white))
                                .frame(width: 150, height: 150)
                                .ignoresSafeArea()
                                .shadow(
                                    color: .black.opacity(0.5),
                                    radius: 4,
                                    x: 5,
                                    y: 4
                                )
                                Image("search_icon")
                                    .resizable()
                                    .scaledToFit()
                                    .frame(width: 80, height: 80)
                                    .offset(y: -20)
                                Text("Inventory")
                                    .font(.custom("Open Sans Bold", size: 20))
                                    .offset(y: 40)
                            }
                        }.buttonStyle(.plain)
                        
                        Button{
                            navigation.navigate(to: .login)
                            navigation.signOut()
                        }label: {
                            ZStack{
                                UnevenRoundedRectangle(
                                    topLeadingRadius: 20,
                                    bottomLeadingRadius: 20,
                                    bottomTrailingRadius: 20,
                                    topTrailingRadius: 20
                                )
                                .fill(Color(Color.white))
                                .frame(width: 150, height: 150)
                                .ignoresSafeArea()
                                .shadow(
                                    color: .black.opacity(0.5),
                                    radius: 4,
                                    x: 5,
                                    y: 4
                                )
                                Image("log_out_icon")
                                    .resizable()
                                    .scaledToFit()
                                    .frame(width: 80, height: 80)
                                    .offset(y: -20)
                                Text("Log Out")
                                    .font(.custom("Open Sans Bold", size: 20))
                                    .offset(y: 40)
                            }
                        }.buttonStyle(.plain)
                    }
                    Spacer()
                    Spacer()
                    
                }
            
                
            }.frame(maxWidth: .infinity, maxHeight: .infinity)
                .background(Color("Secondary Color"))
        
        }
    
    }

#Preview {
    DashboardView()
        .environment(AppNavigation())
}
