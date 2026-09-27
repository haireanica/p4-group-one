//
//  DashboardView.swift
//  LendITiOS
//
//  Created by Steven Petocz on 9/16/26.
//

import SwiftUI

struct DashboardView: View {
    
    @Environment(\.dismiss) private var dismiss
    
    var body: some View {
        NavigationStack{
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
                    
                    .offset(y:-10)
                    .font(.custom("Open Sans Bold", size: 80))
                }
                .frame(maxWidth: .infinity, alignment: .top)
                
                VStack(spacing: 30){
                    
                    HStack(spacing: 30){
                        
                        //NavigationLink(destination: <#T##() -> View#>, label: <#T##() -> View#>)
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
                        
                        //NavigationLink(destination: <#T##() -> View#>, label: <#T##() -> View#>)
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
                    }
                    HStack(spacing: 30){
                        //NavigationLink(destination: <#T##() -> View#>, label: <#T##() -> View#>)
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
                        
                        //NavigationLink(destination: <#T##() -> View#>, label: <#T##() -> View#>)
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
                    }
                    HStack(spacing: 30){
                        
                        //NavigationLink(destination: T##() -> View, label: <#T##() -> View#>)
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
                        
                        Button{
                            dismiss()
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
                }.offset(y: -50)
            }.frame(maxWidth: .infinity, maxHeight: .infinity)
                .padding(.bottom, 20)
                .background(Color("Secondary Color"))
        }
    }
}

#Preview {
    DashboardView()
}
