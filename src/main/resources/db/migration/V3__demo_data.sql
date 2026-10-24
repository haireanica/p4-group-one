-- Demo data for the H2 database (tests and local runs). Not used against MySQL.
-- Dates are relative to CURRENT_DATE so "overdue" stays realistic whenever
-- the app is started. Loan reasons use the LoanReason values.

INSERT INTO center (centerId, name, address_line1, address_line2, city, state, postal_code) VALUES
    (1, 'Downtown',     '100 W Central Blvd', NULL,        'Orlando',   'FL', '32801'),
    (2, 'East Orlando', '7400 E Colonial Dr', 'Suite 120', 'Orlando',   'FL', '32807'),
    (3, 'Kissimmee',    '210 N Main St',      NULL,        'Kissimmee', 'FL', '34741');

INSERT INTO inventory (inventoryId, centerId) VALUES (1, 1), (2, 2), (3, 3);

INSERT INTO asset (assetId, inventoryId, name, assetStatus) VALUES
    ('1001', 1, 'Chromebook-001', 'ON_LOAN'),
    ('1002', 1, 'Chromebook-002', 'ON_LOAN'),
    ('1003', 1, 'Chromebook-003', 'AVAILABLE'),
    ('1004', 1, 'Laptop-HP-004',  'ON_LOAN'),
    ('1005', 1, 'Laptop-HP-005',  'MAINTENANCE'),
    ('1006', 1, 'Hotspot-006',    'ON_LOAN'),
    ('1007', 1, 'Hotspot-007',    'AVAILABLE'),
    ('1008', 1, 'Tablet-008',     'ON_LOAN'),
    ('2001', 2, 'Chromebook-101', 'ON_LOAN'),
    ('2002', 2, 'Chromebook-102', 'AVAILABLE'),
    ('2003', 2, 'Laptop-HP-103',  'ON_LOAN'),
    ('2004', 2, 'Laptop-HP-104',  'AVAILABLE'),
    ('2005', 2, 'Hotspot-105',    'ON_LOAN'),
    ('2006', 2, 'Tablet-106',     'MAINTENANCE'),
    ('3001', 3, 'Chromebook-201', 'ON_LOAN'),
    ('3002', 3, 'Chromebook-202', 'AVAILABLE'),
    ('3003', 3, 'Laptop-HP-203',  'AVAILABLE'),
    ('3004', 3, 'Hotspot-204',    'ON_LOAN'),
    ('3005', 3, 'Tablet-205',     'AVAILABLE');

-- Contact details are left empty, like the team's own sample rows.
INSERT INTO loanee (participantId, address_line1, city, state, postal_code) VALUES
    (5001, '', '', '', ''), (5002, '', '', '', ''), (5003, '', '', '', ''), (5004, '', '', '', ''), (5005, '', '', '', ''), (5006, '', '', '', ''), (5007, '', '', '', ''), (5008, '', '', '', ''), (5009, '', '', '', ''), (5010, '', '', '', ''),
    (5011, '', '', '', ''), (5012, '', '', '', ''), (5013, '', '', '', ''), (5014, '', '', '', ''), (5015, '', '', '', ''), (5016, '', '', '', ''), (5017, '', '', '', ''), (5018, '', '', '', ''), (5019, '', '', '', ''), (5020, '', '', '', '');

-- loanId is left out on purpose: it is an IDENTITY column, and inserting
-- explicit ids would leave the sequence behind and break later checkouts.
INSERT INTO loan (assetId, participantId, checkoutDate, dueDate, returnDate, loanStatus, reason) VALUES
    -- currently active (some past due -> shown as overdue)
    ('1001', 5001, DATEADD('DAY', -5,  CURRENT_DATE), DATEADD('DAY', 9,  CURRENT_DATE), NULL, 'ACTIVE', 'EMPLOYMENT'),
    ('1002', 5002, DATEADD('DAY', -20, CURRENT_DATE), DATEADD('DAY', -6, CURRENT_DATE), NULL, 'ACTIVE', 'EDUCATION'),
    ('1004', 5003, DATEADD('DAY', -3,  CURRENT_DATE), DATEADD('DAY', 11, CURRENT_DATE), NULL, 'ACTIVE', 'TELEHEALTH'),
    ('1006', 5004, DATEADD('DAY', -10, CURRENT_DATE), DATEADD('DAY', 4,  CURRENT_DATE), NULL, 'ACTIVE', 'BENEFITS'),
    ('1008', 5005, DATEADD('DAY', -1,  CURRENT_DATE), DATEADD('DAY', 13, CURRENT_DATE), NULL, 'ACTIVE', 'EDUCATION'),
    ('2001', 5006, DATEADD('DAY', -17, CURRENT_DATE), DATEADD('DAY', -3, CURRENT_DATE), NULL, 'ACTIVE', 'EMPLOYMENT'),
    ('2003', 5007, DATEADD('DAY', -8,  CURRENT_DATE), DATEADD('DAY', 6,  CURRENT_DATE), NULL, 'ACTIVE', 'EMPLOYMENT'),
    ('2005', 5008, DATEADD('DAY', -2,  CURRENT_DATE), DATEADD('DAY', 12, CURRENT_DATE), NULL, 'ACTIVE', 'EDUCATION'),
    ('3001', 5009, DATEADD('DAY', -12, CURRENT_DATE), DATEADD('DAY', 2,  CURRENT_DATE), NULL, 'ACTIVE', 'EDUCATION'),
    ('3004', 5010, DATEADD('DAY', -25, CURRENT_DATE), DATEADD('DAY', -11,CURRENT_DATE), NULL, 'ACTIVE', 'OTHER'),
    -- returned history (feeds the weekly trend chart)
    ('1003', 5011, DATEADD('DAY', -52, CURRENT_DATE), DATEADD('DAY', -38, CURRENT_DATE), DATEADD('DAY', -40, CURRENT_DATE), 'RETURNED', 'EMPLOYMENT'),
    ('1007', 5012, DATEADD('DAY', -48, CURRENT_DATE), DATEADD('DAY', -34, CURRENT_DATE), DATEADD('DAY', -33, CURRENT_DATE), 'RETURNED', 'EDUCATION'),
    ('2002', 5013, DATEADD('DAY', -44, CURRENT_DATE), DATEADD('DAY', -30, CURRENT_DATE), DATEADD('DAY', -31, CURRENT_DATE), 'RETURNED', 'EDUCATION'),
    ('2004', 5014, DATEADD('DAY', -40, CURRENT_DATE), DATEADD('DAY', -26, CURRENT_DATE), DATEADD('DAY', -27, CURRENT_DATE), 'RETURNED', 'EMPLOYMENT'),
    ('3002', 5015, DATEADD('DAY', -37, CURRENT_DATE), DATEADD('DAY', -23, CURRENT_DATE), DATEADD('DAY', -20, CURRENT_DATE), 'RETURNED', 'TELEHEALTH'),
    ('3003', 5016, DATEADD('DAY', -33, CURRENT_DATE), DATEADD('DAY', -19, CURRENT_DATE), DATEADD('DAY', -19, CURRENT_DATE), 'RETURNED', 'EMPLOYMENT'),
    ('1003', 5017, DATEADD('DAY', -30, CURRENT_DATE), DATEADD('DAY', -16, CURRENT_DATE), DATEADD('DAY', -17, CURRENT_DATE), 'RETURNED', 'BENEFITS'),
    ('1007', 5018, DATEADD('DAY', -26, CURRENT_DATE), DATEADD('DAY', -12, CURRENT_DATE), DATEADD('DAY', -12, CURRENT_DATE), 'RETURNED', 'EDUCATION'),
    ('2002', 5019, DATEADD('DAY', -22, CURRENT_DATE), DATEADD('DAY', -8,  CURRENT_DATE), DATEADD('DAY', -9,  CURRENT_DATE), 'RETURNED', 'EMPLOYMENT'),
    ('3005', 5020, DATEADD('DAY', -19, CURRENT_DATE), DATEADD('DAY', -5,  CURRENT_DATE), DATEADD('DAY', -6,  CURRENT_DATE), 'RETURNED', 'EDUCATION'),
    ('2004', 5011, DATEADD('DAY', -15, CURRENT_DATE), DATEADD('DAY', -1,  CURRENT_DATE), DATEADD('DAY', -2,  CURRENT_DATE), 'RETURNED', 'EMPLOYMENT'),
    ('3002', 5013, DATEADD('DAY', -9,  CURRENT_DATE), DATEADD('DAY', 5,   CURRENT_DATE), DATEADD('DAY', -1,  CURRENT_DATE), 'RETURNED', 'EDUCATION');

INSERT INTO maintenance (maintenanceId, assetId, issue, maintenanceStatus, repairNotes, openedDate, completedDate) VALUES
    (1, '1005', 'Cracked screen',        'OPEN',      'Replacement panel ordered',          DATEADD('DAY', -4, CURRENT_DATE), NULL),
    (2, '2006', 'Will not charge',       'OPEN',      'Testing with a new charger',         DATEADD('DAY', -2, CURRENT_DATE), NULL),
    (3, '1003', 'Sticky keyboard',       'COMPLETED', 'Cleaned keyboard, returned to pool', DATEADD('DAY', -45, CURRENT_DATE), DATEADD('DAY', -42, CURRENT_DATE));
