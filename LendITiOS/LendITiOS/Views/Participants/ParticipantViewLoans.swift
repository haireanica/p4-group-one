

import SwiftUI

struct ParticipantViewLoans: View {
    @Environment(AppNavigation.self) private var navigation
    
    let participant: Participant

    @State private var loans: [Loan] = []
    @State private var isLoading = false
    @State private var loadError: String?
    @State private var selectedLoanID: String? = nil
    
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
                        Text("Participant Loans")
                            .foregroundStyle(.white)
                            .font(.custom("Open Sans Bold", size: 55))
                        Spacer()
                    }
                }
                .frame(height: 180)

                // Main content
                VStack(spacing: 0){
                    HStack{
                        Text("Select Loan")
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
                                ProgressView("Loading loans...")
                                    .foregroundStyle(.white)

                            } else if let loadError {
                                Text(loadError)
                                    .foregroundStyle(.white)

                            } else if loans.isEmpty {
                                Text("No loans found for this participant.")
                                    .foregroundStyle(.white)

                            } else {
                                ForEach(loans) { loan in
                                    let isSelected = selectedLoanID == loan.id
                                    
                                    Button {
                                        selectedLoanID = loan.id
                                    } label: {
                                        VStack(alignment: .center, spacing: 5) {
                                            Text(loan.deviceType)
                                                .font(.custom("Open Sans Bold", size: 18))
                                                .foregroundStyle(.black)
                                            Text(loan.centerLocation)
                                                .font(.custom("Open Sans Bold", size: 18))
                                                .foregroundStyle(.black)
                                            Text("Loaned: " + loan.loanStartDate)
                                                .font(.custom("Open Sans Bold", size: 18))
                                                .foregroundStyle(.black)
                                            Text("Due: " + loan.loanDueDate)
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
                    
                    
                    Spacer()
        
                    Spacer()

                }
                
            }
        }
        .ignoresSafeArea(edges: .top)
        .task {
            await loadLoans()
        }
            }
    @MainActor
    private func loadLoans() async {
        isLoading = true
        loadError = nil

        defer {
            isLoading = false
        }

        do {
            let loanDtos = try await APIService.shared.getLoans()

            loans = loanDtos
                .filter { $0.participantId == participant.id }
                .map { $0.toLoan() }

        } catch {
            loadError = error.localizedDescription
        }
    }
}


#Preview {
    ParticipantViewLoans(participant: Participant.sampleData[0])
            .environment(AppNavigation())
}
