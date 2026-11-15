

import SwiftUI

struct ParticipantEditInfoView: View {
    @Environment(AppNavigation.self) private var navigation
    
    let participant: Participant
    
    @State private var firstName: String
    @State private var lastName: String
    @State private var phoneNumber: String
    @State private var addressLine1: String
    @State private var addressLine2: String
    @State private var city = "Orlando"
    @State private var state = "FL"
    @State private var zipcode: String
    
    init(participant: Participant) {
        self.participant = participant

        _firstName = State(initialValue: participant.firstName)
        _lastName = State(initialValue: participant.lastName)
        _phoneNumber = State(initialValue: participant.phoneNumber)
        _addressLine1 = State(initialValue: participant.addressLine1)
        _addressLine2 = State(initialValue: participant.addressLine2)
        _city = State(initialValue: participant.city)
        _zipcode = State(initialValue: participant.zipcode)
    }
    
    @State private var isSaving = false
    @State private var showAlert = false
    @State private var alertMessage = ""
    @State private var saveSucceeded = false
    
    private var isFormValid: Bool {
        !firstName.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty &&
        !lastName.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty &&
        !phoneNumber.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty &&
        !addressLine1.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty &&
        !city.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty &&
        !zipcode.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty
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
                    .frame(height: 140)
                    
                    UnevenRoundedRectangle(
                        topLeadingRadius: 0,
                        bottomLeadingRadius: 0,
                        bottomTrailingRadius: 100,
                        topTrailingRadius: 0
                    )
                    .fill(Color("Secondary Color"))
                    .frame(maxWidth: .infinity)
                    .frame(height: 130)
                    VStack{
                        Spacer()
                        Text("Edit Info")
                            .foregroundStyle(.white)
                            .font(.custom("Open Sans Bold", size: 70))
                        Spacer()
                    }
                }
                .frame(height: 140)

                // Main content
                VStack(spacing: 0){
                    HStack{
                        Text("Edit Participant")
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
                        
                    }.padding(.top, 10)
                        .padding(.horizontal, 10)
                    VStack{
                        Text("First Name")
                            .font(.custom("Open Sans Bold", size: 18))
                            .foregroundStyle(.white)
                            .frame(maxWidth: .infinity, alignment: .leading)
                        
                        TextField("", text: $firstName)
                            .multilineTextAlignment(.center)
                            .disableAutocorrection(true)
                            .frame(height: 35)
                            .font(.custom("Open Sans", size: 18))
                            .background(.white)
                            .clipShape(RoundedRectangle(cornerRadius: 50))
                            .overlay {
                                if firstName.isEmpty {
                                    Text("Enter First Name")
                                        .font(.custom("Open Sans", size: 18))
                                        .foregroundStyle(.black.opacity(0.3))
                                        .allowsHitTesting(false)
                                }
                            }
                        Text("Last Name")
                            .font(.custom("Open Sans Bold", size: 18))
                            .foregroundStyle(.white)
                            .frame(maxWidth: .infinity, alignment: .leading)
                        
                        TextField("", text: $lastName)
                            .multilineTextAlignment(.center)
                            .disableAutocorrection(true)
                            .frame(height: 35)
                            .font(.custom("Open Sans", size: 18))
                            .background(.white)
                            .clipShape(RoundedRectangle(cornerRadius: 50))
                            .overlay {
                                if lastName.isEmpty {
                                    Text("Enter Last Name")
                                        .font(.custom("Open Sans", size: 18))
                                        .foregroundStyle(.black.opacity(0.3))
                                        .allowsHitTesting(false)
                                }
                            }
                        
                        Text("Phone Number")
                            .font(.custom("Open Sans Bold", size: 18))
                            .foregroundStyle(.white)
                            .frame(maxWidth: .infinity, alignment: .leading)
                        
                        TextField("", text: $phoneNumber)
                            .keyboardType(.phonePad)
                            .frame(height: 35)
                            .multilineTextAlignment(.center)
                            .font(.custom("Open Sans", size: 18))
                            .background(.white)
                            .clipShape(RoundedRectangle(cornerRadius: 50))
                            .overlay {
                                if phoneNumber.isEmpty {
                                    Text("Enter Phone Number")
                                        .font(.custom("Open Sans", size: 18))
                                        .foregroundStyle(.black.opacity(0.3))
                                        .allowsHitTesting(false)
                                }
                            }
                        
                        Text("Address")
                            .font(.custom("Open Sans Bold", size: 18))
                            .foregroundStyle(.white)
                            .frame(maxWidth: .infinity, alignment: .leading)
                        
                        TextField("", text: $addressLine1)
                            .multilineTextAlignment(.center)
                            .font(.custom("Open Sans", size: 18))
                            .frame(height: 35)
                            .background(.white)
                            .clipShape(RoundedRectangle(cornerRadius: 50))
                            .overlay {
                                if addressLine1.isEmpty {
                                    Text("Enter Address")
                                        .font(.custom("Open Sans", size: 18))
                                        .foregroundStyle(.black.opacity(0.3))
                                        .allowsHitTesting(false)
                                }
                            }
                        
                        TextField("", text: $addressLine2)
                            .multilineTextAlignment(.center)
                            .frame(height: 35)
                            .font(.custom("Open Sans", size: 18))
                            .background(.white)
                            .clipShape(RoundedRectangle(cornerRadius: 50))
                            .overlay {
                                if addressLine2.isEmpty {
                                    Text("Enter Unit/Apt Number")
                                        .font(.custom("Open Sans", size: 18))
                                        .foregroundStyle(.black.opacity(0.3))
                                        .allowsHitTesting(false)
                                }
                            }
                        
                        Text("City")
                            .font(.custom("Open Sans Bold", size: 18))
                            .foregroundStyle(.white)
                            .frame(maxWidth: .infinity, alignment: .leading)
                        
                        TextField("", text: $city)
                            .multilineTextAlignment(.center)
                            .font(.custom("Open Sans", size: 18))
                            .frame(height: 35)
                            .background(.white)
                            .clipShape(RoundedRectangle(cornerRadius: 50))
                            .overlay {
                                if city.isEmpty {
                                    Text("Enter City")
                                        .font(.custom("Open Sans", size: 18))
                                        .foregroundStyle(.black.opacity(0.3))
                                        .allowsHitTesting(false)
                                }
                            }
                        Text("State")
                            .font(.custom("Open Sans Bold", size: 18))
                            .foregroundStyle(.white)
                            .frame(maxWidth: .infinity, alignment: .leading)
                        
                        TextField("", text: $state)
                            .multilineTextAlignment(.center)
                            .font(.custom("Open Sans", size: 18))
                            .frame(height: 35)
                            .background(.white)
                            .clipShape(RoundedRectangle(cornerRadius: 50))
                            .overlay {
                                if state.isEmpty {
                                    Text("Enter State")
                                        .font(.custom("Open Sans", size: 18))
                                        .foregroundStyle(.black.opacity(0.3))
                                        .allowsHitTesting(false)
                                }
                            }
                        
                        Text("Zipcode")
                            .font(.custom("Open Sans Bold", size: 18))
                            .foregroundStyle(.white)
                            .frame(maxWidth: .infinity, alignment: .leading)
                        
                        TextField("", text: $zipcode)
                            .multilineTextAlignment(.center)
                            .font(.custom("Open Sans", size: 18))
                            .frame(height: 35)
                            .background(.white)
                            .clipShape(RoundedRectangle(cornerRadius: 50))
                            .overlay {
                                if zipcode.isEmpty {
                                    Text("Enter Zipcode")
                                        .font(.custom("Open Sans", size: 18))
                                        .foregroundStyle(.black.opacity(0.3))
                                        .allowsHitTesting(false)
                                }
                            }
                        Button {
                            Task {
                                await saveChanges()
                            }
                        } label: {
                            Text(isSaving ? "Saving..." : "Save Changes")
                                .font(.custom("Open Sans Bold", size: 30))
                                .foregroundStyle(.white)
                                .frame(maxWidth: .infinity)
                                .frame(height: 60)
                                .background(Color("Secondary Color"))
                                .clipShape(RoundedRectangle(cornerRadius: 50))
                        }
                        .disabled(isSaving)
                        .opacity(isSaving ? 0.7 : 1)
                        .padding(.top, 10)
                        Spacer()
                    }.padding(.horizontal, 20)
                }
                        
                    
                
                
            }
        }
        .ignoresSafeArea(edges: .top)
        .alert("Save Changes", isPresented: $showAlert) {
            Button("OK") {
                if saveSucceeded {
                    navigation.goBack()
                }
            }
        } message: {
            Text(alertMessage)
        }
    }
    @MainActor
    private func saveChanges() async {
        guard isFormValid else {
            alertMessage = "Please complete all required fields."
            saveSucceeded = false
            showAlert = true
            return
        }

        isSaving = true
        defer {
            isSaving = false
        }

        let request = LoaneeRegistration(
            firstName: firstName.trimmingCharacters(in: .whitespacesAndNewlines),
            lastName: lastName.trimmingCharacters(in: .whitespacesAndNewlines),
            phoneNumber: phoneNumber.trimmingCharacters(in: .whitespacesAndNewlines),
            addressLine1: addressLine1.trimmingCharacters(in: .whitespacesAndNewlines),
            addressLine2: addressLine2.trimmingCharacters(in: .whitespacesAndNewlines),
            city: city.trimmingCharacters(in: .whitespacesAndNewlines),
            state: state.trimmingCharacters(in: .whitespacesAndNewlines).uppercased(),
            postalCode: zipcode.trimmingCharacters(in: .whitespacesAndNewlines)
        )

        do {
            try await APIService.shared.updateLoanee(
                id: participant.id,
                request: request
            )

            alertMessage = "Participant information updated successfully."
            saveSucceeded = true
            showAlert = true

        } catch {
            alertMessage = error.localizedDescription
            saveSucceeded = false
            showAlert = true
        }
    }
}
#Preview {
    ParticipantEditInfoView(participant: Participant.sampleData[0])
            .environment(AppNavigation())
}
