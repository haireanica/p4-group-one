

import SwiftUI

struct ParticipantSearchResultsView: View {
    @Environment(AppNavigation.self) private var navigation
    
    let lastName: String
    let phoneNumber: String
    
    @State private var participants: [Participant] = []
    @State private var selectedParticipantID: Int? = nil
    @State private var isLoading = false
    @State private var loadError: String?
    
    private var filteredParticipants: [Participant] {
        let searchLastName = lastName.trimmingCharacters(in: .whitespacesAndNewlines)
        let searchPhone = phoneNumber.filter { $0.isNumber }
        
        
        return participants.filter { participant in
            let matchesLastName = !searchLastName.isEmpty &&
            participant.lastName.localizedCaseInsensitiveContains(searchLastName)
            
            let participantPhone = participant.phoneNumber.filter { $0.isNumber }
            
            let matchesPhone = !searchPhone.isEmpty &&
            participantPhone.contains(searchPhone)
            
            return matchesLastName || matchesPhone
        }
    }
    private var selectedParticipant: Participant? {
        participants.first { $0.id == selectedParticipantID }
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
                VStack(spacing: 0){
                    HStack{
                        Text("Select Participant")
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
                                ProgressView("Loading participants...")
                                    .foregroundStyle(.white)
                                    .padding()
                            } else if let loadError {
                                Text(loadError)
                                    .foregroundStyle(.white)
                                    .padding()
                            } else if filteredParticipants.isEmpty {
                                Text("No participants found.")
                                    .font(.custom("Open Sans Bold", size: 18))
                                    .foregroundStyle(.white)
                                    .padding()
                            }
                            ForEach(filteredParticipants) { participant in
                                let isSelected = selectedParticipantID == participant.id
                                
                                Button {
                                    selectedParticipantID = participant.id
                                } label: {
                                    VStack(alignment: .center, spacing: 5) {
                                        Text("\(participant.firstName) \(participant.lastName)")
                                            .font(.custom("Open Sans Bold", size: 18))
                                            .foregroundStyle(.black)
                                        
                                        Text(participant.phoneNumber)
                                            .font(.custom("Open Sans Bold", size: 18))
                                            .foregroundStyle(.black)
                                        
                                        Text([participant.addressLine1, participant.addressLine2]
                                            .filter { !$0.isEmpty }
                                            .joined(separator: " "))
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
                    .frame(maxHeight: 400)
                    .background(Color.gray.opacity(0.5))
                    .clipShape(RoundedRectangle(cornerRadius: 25))
                    .padding(.top, 20)
                    .padding(.horizontal, 20)
                    
                    Button {
                        if let participant = selectedParticipant {
                            navigation.navigate(to: .participantEditInfo(participant: participant))}
                    } label: {
                        Text("Edit Info")
                            .font(.custom("Open Sans Bold", size: 30))
                            .foregroundStyle(.white)
                            .frame(maxWidth: .infinity)
                            .frame(height: 60)
                            .background(Color("Secondary Color"))
                            .clipShape(RoundedRectangle(cornerRadius: 50))
                    }.padding(.top, 15)
                        .padding(.horizontal, 20)
                        .disabled(selectedParticipant == nil)
                    
                    Button {
                        if let participant = selectedParticipant {
                            navigation.navigate(to: .participantViewLoans(participant: participant))
                        }
                    } label: {
                        Text("View Loans")
                            .font(.custom("Open Sans Bold", size: 30))
                            .foregroundStyle(.white)
                            .frame(maxWidth: .infinity)
                            .frame(height: 60)
                            .background(Color("Secondary Color"))
                            .clipShape(RoundedRectangle(cornerRadius: 50))
                    }.padding(.top, 15)
                        .padding(.horizontal, 20)
                        .disabled(selectedParticipant == nil)
                    
                    Spacer()
                    
                    Spacer()
                    
                }
                
            }
        }
        .ignoresSafeArea(edges: .top)
        .task {
            await loadParticipants()
        }
    }
    // MARK: - Load Participants
    
    @MainActor
    private func loadParticipants() async {
        isLoading = true
        loadError = nil
        
        defer {
            isLoading = false
        }
        
        do {
            let loanees = try await APIService.shared.getLoanees()
            participants = loanees.map { $0.toParticipant() }
        } catch {
            loadError = error.localizedDescription
        }
    }
}



#Preview {
    ParticipantSearchResultsView(lastName: "", phoneNumber: "")
        .environment(AppNavigation())
        
}
