-- Columns the Database team added on 2026-10-23 (outreachDB_backup.sql).
-- V2 and V3 are never edited: Flyway checksums every migration it has run.

ALTER TABLE asset ADD COLUMN device_type VARCHAR(100);

-- The team calls it "reason"; V3 called it "loanReason".
ALTER TABLE loan ALTER COLUMN loanReason RENAME TO reason;

-- Participant contact details. The address columns are NOT NULL in the team
-- schema; DEFAULT '' lets the existing V3 demo loanees get a value.
ALTER TABLE loanee ADD COLUMN firstName     VARCHAR(100);
ALTER TABLE loanee ADD COLUMN lastName      VARCHAR(100);
ALTER TABLE loanee ADD COLUMN phoneNumber   VARCHAR(30);
ALTER TABLE loanee ADD COLUMN address_line1 VARCHAR(150) DEFAULT '' NOT NULL;
ALTER TABLE loanee ADD COLUMN address_line2 VARCHAR(150);
ALTER TABLE loanee ADD COLUMN city          VARCHAR(100) DEFAULT '' NOT NULL;
ALTER TABLE loanee ADD COLUMN state         VARCHAR(2)   DEFAULT '' NOT NULL;
ALTER TABLE loanee ADD COLUMN postal_code   VARCHAR(100) DEFAULT '' NOT NULL;
