//
//  ParticipantSearchFormView.swift
//  LendITiOS
//
//  Created by Steven Petocz on 10/14/26.
//
import SwiftUI

struct ParticipantSearchFormView: View {
    var body: some View {
        ZStack {
            Color("Primary Color")
                .ignoresSafeArea()
            
            VStack(spacing: 0) {
                
                // Top colored section
                ZStack {
                    UnevenRoundedRectangle(
                        topLeadingRadius: 0,
                        bottomLeadingRadius: 0,
                        bottomTrailingRadius: 100,
                        topTrailingRadius: 0
                    )
                    .fill(Color(Color.white))
                    .frame(maxWidth: .infinity)
                    .frame(height: 260)
                    .position(x: 200, y: 30)
                    
                    UnevenRoundedRectangle(
                        topLeadingRadius: 0,
                        bottomLeadingRadius: 0,
                        bottomTrailingRadius: 100,
                        topTrailingRadius: 0
                    )
                    .fill(Color("Secondary Color"))
                    .frame(maxWidth: .infinity)
                    .frame(height: 250)
                    .position(x: 200, y: 30)
                    
                    Text("Participant Search Form")
                        .foregroundStyle(.white)
                        .position(x: 200, y: 60)
                        .font(.custom("Open Sans Bold", size: 50))
                        }
                
                //put remainder here
                
                    }
                    
                }
            }
}


#Preview {
    ParticipantSearchFormView()
}
