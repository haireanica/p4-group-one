import SwiftUI

struct SignUpView: View {
    @State private var firstName = ""
    @State private var lastName = ""
    @State private var phoneNumber = ""
    @State private var addressLine1 = ""
    @State private var addressLine2 = ""
    @State private var city = ""
    @State private var zipcode = ""
    
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
                VStack(spacing: 0){
                    HStack{
                        Text("New Participant")
                            .font(.custom("Open Sans Bold", size: 20))
                            .foregroundStyle(.white)
                        Spacer()
                        Button {
                            //need to get the main menu button working
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
                        .padding(.bottom, 10)
                    VStack{
                        Text("First Name")
                            .font(.custom("Open Sans Bold", size: 18))
                            .foregroundStyle(.white)
                            .frame(maxWidth: .infinity, alignment: .leading)
                        
                        TextField("", text: $firstName)
                            .multilineTextAlignment(.center)
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
                            
                        } label: {
                            Text("Sign Up")
                                .font(.custom("Open Sans Bold", size: 30))
                                .foregroundStyle(.white)
                                .frame(maxWidth: .infinity)
                                .frame(height: 60)
                                .background(Color("Secondary Color"))
                                .clipShape(RoundedRectangle(cornerRadius: 50))
                        }.padding(.top, 10)
                        Spacer()
                    }.padding(.horizontal, 20)
                    
                        
                    
                }
                
            }
        }
        .ignoresSafeArea(edges: .top)
    }
}

#Preview {
    SignUpView()
}
