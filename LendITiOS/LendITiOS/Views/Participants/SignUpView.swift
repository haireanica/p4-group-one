import SwiftUI

struct SignUpView: View {
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
                        Text("Sign Up")
                            .foregroundStyle(.white)
                            .font(.custom("Open Sans Bold", size: 70))
                        Spacer()
                    }
                }
                .frame(height: 180)

                // Main content
                
                Spacer()
                
            }
        }
        .ignoresSafeArea(edges: .top)
    }
}

#Preview {
    SignUpView()
}
