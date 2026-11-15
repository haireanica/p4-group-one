

import SwiftUI

struct NewLoanSelectDevice: View {
    @Environment(AppNavigation.self) private var navigation
    let participant: Participant
    let deviceType: String
    let center: String
    let days: Int
    let reason: String
    
    @State private var devices: [Device] = []
    @State private var selectedDeviceID: String? = nil
    @State private var isLoading = true
    @State private var isSubmitting = false
    @State private var loadError = ""
    @State private var checkoutError = ""
    
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
                    
                    VStack {
                        Spacer()
                        Text("New Loan")
                            .foregroundStyle(.white)
                            .font(.custom("Open Sans Bold", size: 70))
                        Spacer()
                    }
                }
                .frame(height: 180)
                
                // Main content
                VStack(spacing: 0) {
                    HStack {
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
                    }
                    .padding(.top, 15)
                    .padding(.horizontal, 20)
                    
                    ScrollView {
                        LazyVStack(spacing: 12) {
                            if isLoading {
                                ProgressView("Loading inventory...")
                                    .tint(.white)
                                    .foregroundStyle(.white)
                            } else if !loadError.isEmpty {
                                Text(loadError)
                                    .foregroundStyle(.white)
                                    .multilineTextAlignment(.center)
                            } else if devices.isEmpty {
                                Text("No available devices match your selections.")
                                    .foregroundStyle(.white)
                                    .multilineTextAlignment(.center)
                            } else {
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
                        }
                        .padding(10)
                    }
                    .frame(maxHeight: 400)
                    .background(Color.gray.opacity(0.5))
                    .clipShape(RoundedRectangle(cornerRadius: 25))
                    .padding(.top, 20)
                    .padding(.horizontal, 20)
                    
                    if !checkoutError.isEmpty {
                        Text(checkoutError)
                            .foregroundStyle(.red)
                            .multilineTextAlignment(.center)
                            .padding(.horizontal, 20)
                            .padding(.top, 10)
                    }
                    
                    Button {
                        Task {
                            await completeLoan()
                        }
                    } label: {
                        Text(isSubmitting ? "Processing..." : "Complete Loan")
                            .font(.custom("Open Sans Bold", size: 30))
                            .foregroundStyle(.white)
                            .frame(maxWidth: .infinity)
                            .frame(height: 60)
                            .background(Color("Secondary Color"))
                            .clipShape(RoundedRectangle(cornerRadius: 50))
                    }
                    .disabled(selectedDeviceID == nil || isSubmitting || isLoading)
                    .opacity(selectedDeviceID == nil || isSubmitting || isLoading ? 0.5 : 1)
                    .padding(.top, 15)
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
        loadError = ""

        defer {
            isLoading = false
        }

        do {
            let assets = try await APIService.shared.getAssets()

            devices = assets
                .filter { asset in
                    let matchesType = asset.deviceType?
                        .localizedCaseInsensitiveCompare(deviceType) == .orderedSame

                    let assetCenter = asset.inventory?.center?.name ?? ""
                    let matchesCenter = assetCenter
                        .localizedCaseInsensitiveCompare(center) == .orderedSame

                    let isAvailable = asset.status?
                        .localizedCaseInsensitiveCompare("AVAILABLE") == .orderedSame

                    return asset.assetId != nil &&
                        !(asset.assetId ?? "").isEmpty &&
                        matchesType &&
                        matchesCenter &&
                        isAvailable
                }
                .map { $0.toDevice() }

        } catch {
            loadError = error.localizedDescription
        }
    }
    @MainActor
    private func completeLoan() async {
        guard let assetId = selectedDeviceID else {
            return
        }

        isSubmitting = true
        checkoutError = ""

        defer {
            isSubmitting = false
        }

        let request = LoanRequest(
            assetId: assetId,
            participantId: participant.id,
            reason: reason,
            loanPurpose: reason,
            days: days
        )

        do {
            try await APIService.shared.checkoutLoan(request: request)
            navigation.goToDashboard()
        } catch {
            checkoutError = error.localizedDescription
        }
    }
}


    #Preview {
        NewLoanSelectDevice(
            participant: Participant.sampleData[0],
            deviceType: "Laptop",
            center: "Office for Local Assistance (OLA)",
            days: 7,
            reason: "Education"
        )
        .environment(AppNavigation())
    }
