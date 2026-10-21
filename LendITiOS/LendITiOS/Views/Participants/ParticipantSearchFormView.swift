//
//  ParticipantSearchFormView.swift
//  LendITiOS
//
//  Created by Steven Petocz on 10/14/26.
//
import SwiftUI

struct ParticipantSearchFormView: View {
    @State private var lastName = ""
    @State private var phoneNumber = ""
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
                    .frame(height: 230)
                    
                    UnevenRoundedRectangle(
                        topLeadingRadius: 0,
                        bottomLeadingRadius: 0,
                        bottomTrailingRadius: 100,
                        topTrailingRadius: 0
                    )
                    .fill(Color("Secondary Color"))
                    .frame(maxWidth: .infinity)
                    .frame(height: 220)
                    VStack{
                        Spacer()
                        Spacer()
                        Spacer()
                        Text("Participant Search")
                            .foregroundStyle(.white)
                            .font(.custom("Open Sans Bold", size: 55))
                        Spacer()
                    }
                }
                .frame(height: 180)

                
                // Main content
                VStack(spacing: 10){
                    HStack{
                        Text("Search Participant")
                            .font(.custom("Open Sans Bold", size: 25))
                            .foregroundStyle(.white)
                        Spacer()
                        Button {
                            //need to get the main menu button working
                        } label: {
                            Text("Main Menu")
                                .font(.custom("Open Sans Bold", size: 20))
                                .foregroundStyle(.white)
                                .frame(width: 140)
                                .frame(height: 30)
                                .background(Color("Secondary Color"))
                                .clipShape(RoundedRectangle(cornerRadius: 50))
                        }
                        
                    
                    }.padding(.top, 35)
                        .padding(.horizontal, 10)
                        .padding(.bottom, 15)
                    
                    VStack{
                        Text("Last Name")
                            .font(.custom("Open Sans Bold", size: 20))
                            .foregroundStyle(.white)
                            .frame(maxWidth: .infinity, alignment: .leading)
                        
                        TextField("", text: $lastName)
                            .multilineTextAlignment(.center)
                            .font(.custom("Open Sans", size: 20))
                            .padding()
                            .background(.white)
                            .clipShape(RoundedRectangle(cornerRadius: 50))
                            .overlay {
                                if lastName.isEmpty {
                                    Text("Enter Last Name")
                                        .font(.custom("Open Sans", size: 20))
                                        .foregroundStyle(.black.opacity(0.3))
                                        .allowsHitTesting(false)
                                }
                            }
                        
                        Text("Phone Number")
                            .font(.custom("Open Sans Bold", size: 20))
                            .foregroundStyle(.white)
                            .frame(maxWidth: .infinity, alignment: .leading)
                        
                        TextField("", text: $phoneNumber)
                            .keyboardType(.phonePad)
                            .multilineTextAlignment(.center)
                            .font(.custom("Open Sans", size: 20))
                            .padding()
                            .background(.white)
                            .clipShape(RoundedRectangle(cornerRadius: 50))
                            .overlay {
                                if phoneNumber.isEmpty {
                                    Text("Enter Phone Number")
                                        .font(.custom("Open Sans", size: 20))
                                        .foregroundStyle(.black.opacity(0.3))
                                        .allowsHitTesting(false)
                                }
                            }
                        Button {
                            
                        } label: {
                            Text("Search")
                                .font(.custom("Open Sans Bold", size: 40))
                                .foregroundStyle(.white)
                                .frame(maxWidth: .infinity)
                                .frame(height: 60)
                                .background(Color("Secondary Color"))
                                .clipShape(RoundedRectangle(cornerRadius: 50))
                        }.padding(.top, 20)
                        
                    }.padding(.horizontal, 20)
                    
                }
                Spacer()
                
            }
        }
        .ignoresSafeArea(edges: .top)
            }
}


#Preview {
    ParticipantSearchFormView()
}
