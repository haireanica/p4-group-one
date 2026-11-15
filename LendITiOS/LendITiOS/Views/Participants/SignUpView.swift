import SwiftUI

struct SignUpView: View {
    @Environment(AppNavigation.self) private var navigation
    @State private var firstName = ""
    @State private var lastName = ""
    @State private var phoneNumber = ""
    @State private var addressLine1 = ""
    @State private var addressLine2 = ""
    @State private var city = "Orlando"
    @State private var state = "FL"
    @State private var zipcode = ""
    
    @State private var isSubmitting = false
    @State private var showSignUpError = false
    @State private var signUpError = ""
    
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
                        Text("Sign Up")
                            .foregroundStyle(.white)
                            .font(.custom("Open Sans Bold", size: 70))
                        Spacer()
                    }
                }
                .frame(height: 140)

                // Main content
                VStack(spacing: 0){
                    HStack{
                        Text("New Participant")
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
                        .padding(.bottom, 5)
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
                                    await registerParticipant()
                                }
                        } label: {
                            Text("Sign Up")
                                .font(.custom("Open Sans Bold", size: 30))
                                .foregroundStyle(.white)
                                .frame(maxWidth: .infinity)
                                .frame(height: 50)
                                .background(Color("Secondary Color"))
                                .clipShape(RoundedRectangle(cornerRadius: 50))
                        }.padding(.top, 8)
                        Spacer()
                    }.padding(.horizontal, 20)
                    
                        
                    
                }
                
            }
        }
        .ignoresSafeArea(edges: .top)
        .alert("Sign Up Failed", isPresented: $showSignUpError) {
            Button("OK", role: .cancel) {}
        } message: {
            Text(signUpError)
        }
    }
    

    @MainActor
    private func registerParticipant() async {

        guard !firstName.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty,
              !lastName.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty,
              !phoneNumber.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty,
              !addressLine1.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty,
              !city.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty,
              !state.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty,
              !zipcode.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty else {

            signUpError = "Please complete all required fields."
            showSignUpError = true
            return
        }

        isSubmitting = true

        defer {
            isSubmitting = false
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
            let _ = try await APIService.shared.registerLoanee(request: request)

            navigation.goToDashboard()

        } catch {
            signUpError = error.localizedDescription
            showSignUpError = true
        }
    }
}

#Preview {
    SignUpView()
        .environment(AppNavigation())
}
