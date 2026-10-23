//  InventorySearch.swift
//  LendITiOS
//
//  Created by Steven Petocz on 10/14/26.
//

import SwiftUI

struct InventorySearch: View {
    @Environment(AppNavigation.self) private var navigation
    @State private var selectedDeviceType = "Laptop"
    private let deviceType = [
        "Laptop",
        "Tablet",
        "Hotspot"
    ]
    @State private var selectedCenter = "Office for Local Aassistance (OLA)"
    private let center = [
        "Dr. James R. Smith Neighborhood Center",
        "Engelwood Neighborhood Center",
        "John H. Jackson Neighborhood Center",
        "Rosemont Neighborhood Center",
        "L. Claudia Allen Senior Center",
        "Office for Local Aassistance (OLA)",
        "Dr. I Sylvester Hankins Park Neighborhood Center",
        "Callahan Neighborhood Center",
        "Northwest Neighborhood Center",
        "Dover Shores Neighborhood Center",
        "Grand Avenue Neighborhood Center",
        "Ivey Lane Neighborhood Center",
        "Langford Neighborhood Center",
        "Rock Lake Neighborhood Center",
        "Citrus Square Neighborhood Center",
        "RISE Employment and Training Office"
    ]
    @State private var selectedState = "Available"
    private let state = [
        "Available",
        "Loaned Out",
        "Under Repair"
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
                        Text("Inventory Search")
                            .foregroundStyle(.white)
                            .font(.custom("Open Sans Bold", size: 55))
                        Spacer()
                    }
                }
                .frame(height: 180)

                // Main content
                VStack(spacing: 0){
                    HStack{
                        Text("Select Details")
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
                    
                    }.padding(.top, 35)
                        .padding(.horizontal, 20)
                        .padding(.bottom, 20)
                    
                    VStack(alignment: .leading, spacing: 15) {
                        Text("Device Type")
                            .font(.custom("Open Sans Bold", size: 20))
                            .foregroundStyle(.white)

                        Picker("Select Device", selection: $selectedDeviceType) {
                            ForEach(deviceType, id: \.self) { device in
                                Text(device)
                                    .tag(device)
                            }
                        }
                        .pickerStyle(.menu)
                        .font(.custom("Open Sans Bold", size: 20))
                        .frame(maxWidth: .infinity)
                        .frame(height: 45)
                        .background(.white)
                        .foregroundStyle(.black)
                        .clipShape(RoundedRectangle(cornerRadius: 50))
                        
                        Text("Center Location")
                            .font(.custom("Open Sans Bold", size: 20))
                            .foregroundStyle(.white)

                        Picker("Select Center", selection: $selectedCenter) {
                            ForEach(center, id: \.self) { center in
                                Text(center)
                                    .tag(center)
                            }
                        }
                        .pickerStyle(.menu)
                        .font(.custom("Open Sans Bold", size: 20))
                        .frame(maxWidth: .infinity)
                        .frame(height: 45)
                        .background(.white)
                        .foregroundStyle(.black)
                        .clipShape(RoundedRectangle(cornerRadius: 50))
                        
                        Text("Status")
                            .font(.custom("Open Sans Bold", size: 20))
                            .foregroundStyle(.white)

                        Picker("Select Status", selection: $selectedState) {
                            ForEach(state, id: \.self) { state in
                                Text(state)
                                    .tag(state)
                            }
                        }
                        .pickerStyle(.menu)
                        .font(.custom("Open Sans Bold", size: 20))
                        .frame(maxWidth: .infinity)
                        .frame(height: 45)
                        .background(.white)
                        .foregroundStyle(.black)
                        .clipShape(RoundedRectangle(cornerRadius: 50))
                        
                        
                        Button {
                            navigation.navigate(to: .inventorySearchResults)
                        } label: {
                            Text("Check Inventory")
                                .font(.custom("Open Sans Bold", size: 30))
                                .foregroundStyle(.white)
                                .frame(maxWidth: .infinity)
                                .frame(height: 60)
                                .background(Color("Secondary Color"))
                                .clipShape(RoundedRectangle(cornerRadius: 50))
                        }.padding(.top, 20)
                        
                        
                    }
                    .padding(.horizontal, 20)

                    
                }
                Spacer()
                
            }
        }
        .ignoresSafeArea(edges: .top)
        }
    }

#Preview {
    InventorySearch()
        .environment(AppNavigation())
}
