
import SwiftUI

struct NewLoanSearchParticipant: View {
    @Environment(AppNavigation.self) private var navigation
    @State private var lastName = ""
    @State private var phoneNumber = ""
    @State private var participants: [Participant] = []
    @State private var isSearching = false
    @State private var showSearchError = false
    @State private var searchError = ""
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
                        Text("New Loan")
                            .foregroundStyle(.white)
                            .font(.custom("Open Sans Bold", size: 70))
                        Spacer()
                    }
                }
                .frame(height: 180)

                // Main content
                VStack(spacing: 0){
                    HStack{
                        Text("Search Participant")
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
                        .padding(.horizontal, 10)
                        .padding(.bottom, 15)
                    
                    VStack{
                        Text("Last Name")
                            .font(.custom("Open Sans Bold", size: 20))
                            .foregroundStyle(.white)
                            .frame(maxWidth: .infinity, alignment: .leading)
                        
                        TextField("", text: $lastName)
                            .multilineTextAlignment(.center)
                            .disableAutocorrection(true)
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
                            Task {
                                    await searchParticipants()
                                }
                            
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
        .alert("Search Error", isPresented: $showSearchError) {
            Button("OK", role: .cancel) { }
        } message: {
            Text(searchError)
        }
            }
    @MainActor
    private func searchParticipants() async {
        isSearching = true
        defer { isSearching = false }

        do {
            let allParticipants = try await APIService.shared.getLoanees()

            let searchLastName = lastName
                .trimmingCharacters(in: .whitespacesAndNewlines)

            let searchPhone = phoneNumber.filter { $0.isNumber }

            participants = allParticipants
                .map { $0.toParticipant() }
                .filter { participant in
                    let matchesLastName = !searchLastName.isEmpty &&
                        participant.lastName.localizedCaseInsensitiveContains(searchLastName)

                    let participantPhone = participant.phoneNumber.filter { $0.isNumber }

                    let matchesPhone = !searchPhone.isEmpty &&
                        participantPhone.contains(searchPhone)

                    return matchesLastName || matchesPhone
                }

            navigation.navigate(
                to: .newLoanSelectParticipant(participants: participants)
            )
        } catch {
            searchError = error.localizedDescription
            showSearchError = true
        }
    }
}


#Preview {
    NewLoanSearchParticipant()
        .environment(AppNavigation())
}
