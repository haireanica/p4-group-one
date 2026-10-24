-- Copy of the Database team's MySQL schema (Database branch, outreachDB_backup.sql,
-- 2026-10-23), used for the in-memory H2 database in tests and local runs.
-- Against the team's MySQL database Flyway is off (see application-mysql.properties):
-- the Database team owns that schema, and this file must follow it.

CREATE TABLE center (
    centerId      INT NOT NULL PRIMARY KEY,
    name          VARCHAR(150),
    address_line1 VARCHAR(40) NOT NULL,
    address_line2 VARCHAR(30),
    city          VARCHAR(12) NOT NULL,
    state         CHAR(2) NOT NULL,
    postal_code   CHAR(5) NOT NULL
);

CREATE TABLE inventory (
    inventoryId INT NOT NULL PRIMARY KEY,
    centerId    INT,
    CONSTRAINT fk_inventory_center FOREIGN KEY (centerId) REFERENCES center (centerId)
);

CREATE TABLE asset (
    assetId     VARCHAR(100) NOT NULL PRIMARY KEY,
    inventoryId INT,
    name        VARCHAR(100),
    assetStatus VARCHAR(12) NOT NULL,
    device_type VARCHAR(100),
    CONSTRAINT fk_inventoryId FOREIGN KEY (inventoryId) REFERENCES inventory (inventoryId)
);

CREATE TABLE loanee (
    participantId INT NOT NULL PRIMARY KEY,
    firstName     VARCHAR(100),
    lastName      VARCHAR(100),
    phoneNumber   VARCHAR(30),
    address_line1 VARCHAR(150) NOT NULL,
    address_line2 VARCHAR(150),
    city          VARCHAR(100) NOT NULL,
    state         VARCHAR(2) NOT NULL,
    postal_code   VARCHAR(100) NOT NULL
);

CREATE TABLE loan (
    loanId        INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    assetId       VARCHAR(100),
    participantId INT,
    checkoutDate  DATE,
    dueDate       DATE,
    returnDate    DATE,
    loanStatus    VARCHAR(15) NOT NULL,
    reason        VARCHAR(80),
    CONSTRAINT fk_loan_asset FOREIGN KEY (assetId) REFERENCES asset (assetId),
    CONSTRAINT fk_participantId FOREIGN KEY (participantId) REFERENCES loanee (participantId)
);

CREATE TABLE maintenance (
    maintenanceId     INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    assetId           VARCHAR(100),
    issue             VARCHAR(40) NOT NULL,
    maintenanceStatus VARCHAR(155),
    repairNotes       VARCHAR(100) NOT NULL,
    openedDate        DATE,
    completedDate     DATE,
    CONSTRAINT fk_maintenance_asset FOREIGN KEY (assetId) REFERENCES asset (assetId)
);

CREATE TABLE credentials (
    userId   INT NOT NULL PRIMARY KEY,
    username VARCHAR(155) NOT NULL,
    password VARCHAR(155) NOT NULL
);

-- NOT YET IN THE TEAM DATABASE: login needs a role per account.
-- The same change for MySQL is in docs/mysql/add_roles_for_auth.sql.
CREATE TABLE role (
    id   INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
);

ALTER TABLE credentials ADD COLUMN roleId INT;
ALTER TABLE credentials ADD CONSTRAINT fk_credentials_role FOREIGN KEY (roleId) REFERENCES role (id);
ALTER TABLE credentials ADD CONSTRAINT uq_credentials_username UNIQUE (username);
