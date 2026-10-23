//
//  ReturnSelectDevice.swift
//  LendITiOS
//
//  Created by Steven Petocz on 10/14/26.
//

import SwiftUI

struct ReturnSelectDevice: View {
    @Environment(AppNavigation.self) private var navigation
    let devices: [Device]
    @State private var selectedDeviceID: String? = nil
    @State private var selectedDeviceCondition = "Good"
    private let deviceCondition = [
        "Good",
        "Dirty",
        "Broken"
    ]
    @State private var selectedDeviceAccessories = "Returned"
    private let deviceAccessories = [
        "Returned",
        "Not Returned",
        "Broken"
    ]
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
                    .frame(height: 180)
                    
                    UnevenRoundedRectangle(
                        topLeadingRadius: 0,
                        bottomLeadingRadius: 0,
                        bottomTrailingRadius: 100,
                        topTrailingRadius: 0
                    )
                    .fill(Color("Secondary Color"))
                    .frame(maxWidth: .infinity)
                    .frame(height: 170)
                    VStack{
                        Spacer()
                        Text("Return")
                            .foregroundStyle(.white)
                            .font(.custom("Open Sans Bold", size: 70))
                        Spacer()
                    }
                }
                .frame(height: 180)

                // Main content
                VStack(spacing: 0){
                    HStack{
                        Text("Select Device")
                            .font(.custom("Open Sans Bold", size: 20))
                            .foregroundStyle(.white)
                        Spacer()
                        Button {
                            navigation.goToDashboard()
                        } label: {
                            Text("Main Menu")
                                .font(.custom("Open Sans Bold", size: 24))
                                .foregroundStyle(.white)
                                .frame(width: 160)
                                .frame(height: 30)
                                .background(Color("Secondary Color"))
                                .clipShape(RoundedRectangle(cornerRadius: 50))
                        }
                    
                    }.padding(.top, 15)
                        .padding(.horizontal, 20)
                    ScrollView {
                        LazyVStack(spacing: 12) {
                            
                            
                            ForEach(devices) { device in
                                let isSelected = selectedDeviceID == device.id

                                Button {
                                    selectedDeviceID = device.id
                                } label: {
                                    VStack(alignment: .center, spacing: 5) {
                                        Text(device.deviceType)
                                            .font(.custom("Open Sans Bold", size: 18))
                                            .foregroundStyle(.black)

                                        Text(device.id)
                                            .font(.custom("Open Sans Bold", size: 18))
                                            .foregroundStyle(.black)

                                        Text(device.centerLocation)
                                            .font(.custom("Open Sans Bold", size: 18))
                                            .foregroundStyle(.black)
                                        Text(device.status)
                                            .font(.custom("Open Sans Bold", size: 18))
                                            .foregroundStyle(.black)
                                    }
                                    .frame(maxWidth: .infinity)
                                    .padding(12)
                                    .background(.white)
                                    .clipShape(RoundedRectangle(cornerRadius: 20))
                                    .overlay {
                                        RoundedRectangle(cornerRadius: 20)
                                            .stroke(
                                                isSelected ? Color.black : Color.clear,
                                                lineWidth: 4
                                            )
                                    }
                                    .contentShape(RoundedRectangle(cornerRadius: 20))
                                }
                                .buttonStyle(.plain)
                            }
                        }
                        .padding(10)
                    }
                    .frame(maxHeight: 250)
                    .background(Color.gray.opacity(0.5))
                    .clipShape(RoundedRectangle(cornerRadius: 25))
                    .padding(.top, 20)
                    .padding(.horizontal, 20)
                    .padding(.bottom, 15)
                    
                    VStack{
                        HStack{
                            Text("Device Condition")
                                .multilineTextAlignment(.leading)
                                .font(.custom("Open Sans Bold", size: 20))
                                .foregroundStyle(.white)
                            Spacer()
                        }
                        Picker("Device Condition", selection: $selectedDeviceCondition) {
                            ForEach(deviceCondition, id: \.self) { device in
                                Text(device)
                                    .tag(device)
                            }
                        }
                        .pickerStyle(.menu)
                        .font(.custom("Open Sans Bold", size: 25))
                        .frame(maxWidth: .infinity)
                        .frame(height: 45)
                        .background(.white)
                        .foregroundStyle(.black)
                        .clipShape(RoundedRectangle(cornerRadius: 50))
                              
                    
                      HStack{
                          Text("Device Accessories")
                              .multilineTextAlignment(.leading)
                              .font(.custom("Open Sans Bold", size: 20))
                              .foregroundStyle(.white)
                          Spacer()
                      }
                      Picker("Device Accessories", selection: $selectedDeviceAccessories) {
                          ForEach(deviceAccessories, id: \.self) { device in
                              Text(device)
                                  .tag(device)
                          }
                      }
                      .pickerStyle(.menu)
                      .font(.custom("Open Sans Bold", size: 25))
                      .frame(maxWidth: .infinity)
                      .frame(height: 45)
                      .background(.white)
                      .foregroundStyle(.black)
                      .clipShape(RoundedRectangle(cornerRadius: 50))
                              
                    }.padding(.horizontal, 20)
                    
                    Button {
                        //need to add return function and need to add return table to db
                        navigation.goToDashboard()
                        
                    } label: {
                        Text("Complete Return")
                            .font(.custom("Open Sans Bold", size: 30))
                            .foregroundStyle(.white)
                            .frame(maxWidth: .infinity)
                            .frame(height: 60)
                            .background(Color("Secondary Color"))
                            .clipShape(RoundedRectangle(cornerRadius: 50))
                    }.padding(.top, 25)
                        .padding(.horizontal, 20)
                    
                    
                }
                Spacer()
                
            }
        }
        .ignoresSafeArea(edges: .top)
            }
}


#Preview {
    ReturnSelectDevice(devices: Device.sampleData)
        .environment(AppNavigation())
}
