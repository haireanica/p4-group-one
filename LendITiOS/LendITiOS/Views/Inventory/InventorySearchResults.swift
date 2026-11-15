

import SwiftUI

struct InventorySearchResults: View {
    @Environment(AppNavigation.self) private var navigation

    let deviceType: String
    let center: String
    let status: String

    @State private var devices: [Device] = []
    @State private var selectedDeviceID: String? = nil
    @State private var isLoading = false
    @State private var loadError: String?
    
    private var filteredDevices: [Device] {
        devices.filter { device in

            let matchesType = deviceType == "All" ||
                device.deviceType.localizedCaseInsensitiveContains(deviceType)

            let matchesCenter = center == "All" ||
                device.centerLocation.localizedCaseInsensitiveContains(center)

            let matchesStatus = status == "All" ||
                device.status.localizedCaseInsensitiveContains(status)

            return matchesType && matchesCenter && matchesStatus
        }
    }
    
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
                        Text("Search Results")
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
                    
                    ScrollView {
                        LazyVStack(spacing: 12) {

                            if isLoading {
                                ProgressView("Loading inventory...")
                                    .foregroundStyle(.white)
                                    .padding()

                            } else if let loadError {
                                Text(loadError)
                                    .foregroundStyle(.white)
                                    .font(.custom("Open Sans Bold", size: 16))
                                    .padding()

                            } else if filteredDevices.isEmpty {
                                Text("No devices found.")
                                    .foregroundStyle(.white)
                                    .font(.custom("Open Sans Bold", size: 16))
                                    .padding()

                            } else {
                            
                            ForEach(filteredDevices) { device in
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
                                 }
                                 .padding(10)
                    }
                    .frame(maxHeight: 400)
                    .background(Color.gray.opacity(0.5))
                    .clipShape(RoundedRectangle(cornerRadius: 25))
                    .padding(.top, 20)
                    .padding(.horizontal, 20)
                    
                
                    
                }
                Spacer()
                
            }
        }
        .ignoresSafeArea(edges: .top)
        .task {
            await loadInventory()
        }
            }
    @MainActor
    private func loadInventory() async {

        isLoading = true
        loadError = nil

        defer {
            isLoading = false
        }

        do {
            let assets = try await APIService.shared.getAssets()

            devices = assets.map { $0.toDevice() }

        } catch {
            loadError = error.localizedDescription
        }
    }
}


#Preview {
    InventorySearchResults(
        deviceType: "All",
        center: "All",
        status: "All"
    )
        .environment(AppNavigation())
}
