

import Foundation

struct ReturnRequest: Codable {
    let condition: String
    let notes: String?
    
    init(condition: String, notes: String? = nil) {
        self.condition = condition
        self.notes = notes
    }
}
