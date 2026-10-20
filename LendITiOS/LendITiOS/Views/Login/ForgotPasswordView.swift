//
//  ForgotPasswordView.swift
//  LendITiOS
//
//  Created by Steven Petocz on 10/20/26.
//

import SwiftUI

struct ForgotPasswordView: View {
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
                        Text("Forgot Password")
                            .foregroundStyle(.white)
                            .font(.custom("Open Sans Bold", size: 55))
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
    ForgotPasswordView()
}
