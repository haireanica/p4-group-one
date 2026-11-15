
import SwiftUI

struct ReturnSelectDevice: View {

    @Environment(AppNavigation.self) private var navigation

    let participant: Participant

    @State private var loans: [Loan] = []
    @State private var selectedLoanID: String? = nil
    @State private var isLoading = true
    @State private var isSubmitting = false
    @State private var loadError = ""
    @State private var returnError = ""
    @State private var returnNotes = ""


    @State private var selectedDeviceCondition = "Good"

    private let deviceCondition = [
        "Good",
        "Minor Damage",
        "Major Damage",
        "Needs Repair"
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

                    VStack {
                        Spacer()

                        Text("Return")
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

                    // Active loans
                    ScrollView {

                        LazyVStack(spacing: 12) {

                            if isLoading {

                                ProgressView("Loading active loans...")
                                    .tint(.white)
                                    .foregroundStyle(.white)

                            } else if !loadError.isEmpty {

                                Text(loadError)
                                    .foregroundStyle(.white)
                                    .multilineTextAlignment(.center)

                            } else if loans.isEmpty {

                                Text("This participant has no active loans.")
                                    .foregroundStyle(.white)
                                    .multilineTextAlignment(.center)

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

                                            Text("Loan #\(loan.id)")
                                                .font(.custom("Open Sans Bold", size: 18))
                                                .foregroundStyle(.black)

                                            Text(loan.centerLocation)
                                                .font(.custom("Open Sans Bold", size: 18))
                                                .foregroundStyle(.black)

                                            Text("Due: \(loan.loanDueDate)")
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
                    .frame(maxHeight: 250)
                    .background(Color.gray.opacity(0.5))
                    .clipShape(RoundedRectangle(cornerRadius: 25))
                    .padding(.top, 20)
                    .padding(.horizontal, 20)
                    .padding(.bottom, 15)

                    // Return error
                    if !returnError.isEmpty {
                        Text(returnError)
                            .foregroundStyle(.red)
                            .multilineTextAlignment(.center)
                            .padding(.horizontal, 20)
                    }

                    VStack {

                        HStack {

                            Text("Device Condition")
                                .multilineTextAlignment(.leading)
                                .font(.custom("Open Sans Bold", size: 20))
                                .foregroundStyle(.white)

                            Spacer()
                        }

                        Picker(
                            "Device Condition",
                            selection: $selectedDeviceCondition
                        ) {
                            ForEach(deviceCondition, id: \.self) { condition in
                                Text(condition)
                                    .tag(condition)
                            }
                        }
                        .pickerStyle(.menu)
                        .font(.custom("Open Sans Bold", size: 25))
                        .frame(maxWidth: .infinity)
                        .frame(height: 45)
                        .padding(.horizontal, 20)
                        .background(.white)
                        .foregroundStyle(.black)
                        .clipShape(RoundedRectangle(cornerRadius: 50))
                        
                        VStack(alignment: .leading, spacing: 8) {

                            Text("Return Notes")
                                .font(.custom("Open Sans Bold", size: 20))
                                .foregroundStyle(.white)

                            TextEditor(text: $returnNotes)
                                .font(.custom("Open Sans", size: 16))
                                .frame(height: 100)
                                .padding(8)
                                .scrollContentBackground(.hidden)
                                .background(.white)
                                .clipShape(RoundedRectangle(cornerRadius: 15))
                                .overlay(alignment: .topLeading) {
                                    if returnNotes.isEmpty {
                                        Text("Enter any notes about the device...")
                                            .font(.custom("Open Sans", size: 16))
                                            .foregroundStyle(.gray)
                                            .padding(.horizontal, 13)
                                            .padding(.vertical, 16)
                                            .allowsHitTesting(false)
                                    }
                                }
                        }
                        .padding(.top, 15)

                        Button {

                            Task {
                                await completeReturn()
                            }

                        } label: {

                            Text(isSubmitting ? "Processing..." : "Complete Return")
                                .font(.custom("Open Sans Bold", size: 30))
                                .foregroundStyle(.white)
                                .frame(maxWidth: .infinity)
                                .frame(height: 60)
                                .background(Color("Secondary Color"))
                                .clipShape(RoundedRectangle(cornerRadius: 50))
                        }
                        .disabled(
                            selectedLoanID == nil ||
                            isSubmitting ||
                            isLoading
                        )
                        .opacity(
                            selectedLoanID == nil ||
                            isSubmitting ||
                            isLoading ? 0.5 : 1
                        )
                        .padding(.top, 25)
                        .padding(.horizontal, 20)
                    }
                    .padding(.horizontal, 20)


                    Spacer()
                }
            }
        }
        .ignoresSafeArea(edges: .top)
        .task {
            await loadActiveLoans()
        }
    }

    // MARK: - Load Active Loans

    @MainActor
    private func loadActiveLoans() async {

        isLoading = true
        loadError = ""

        defer {
            isLoading = false
        }

        do {

            let allLoans = try await APIService.shared.getLoans()

            loans = allLoans
                .filter { loan in

                    let belongsToParticipant =
                        loan.participantId == participant.id

                    let hasNotBeenReturned = loan.returnDate?
                        .trimmingCharacters(in: .whitespacesAndNewlines)
                        .isEmpty ?? true

                    return belongsToParticipant &&
                        hasNotBeenReturned &&
                        loan.loanId != nil
                }
                .map { $0.toLoan() }

        } catch {

            loadError = error.localizedDescription
        }
    }


    @MainActor
    private func completeReturn() async {

        guard let selectedLoanID,
              let loanId = Int(selectedLoanID) else {
            return
        }

        isSubmitting = true
        returnError = ""

        defer {
            isSubmitting = false
        }

        let trimmedNotes = returnNotes.trimmingCharacters(
            in: .whitespacesAndNewlines
        )

        let request = ReturnRequest(
            condition: selectedDeviceCondition.lowercased(),
            notes: trimmedNotes.isEmpty ? nil : trimmedNotes
        )

        do {

            try await APIService.shared.returnLoan(
                loanId: loanId,
                request: request
            )

            navigation.goToDashboard()

        } catch {

            returnError = error.localizedDescription
        }
    }
}

// MARK: - Preview

#Preview {
    ReturnSelectDevice(
        participant: Participant.sampleData[0]
    )
    .environment(AppNavigation())
}
