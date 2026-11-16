# Orlando Lend IT – iOS Application

## Requirements

- Java 21 JDK
- MySQL Server
- MySQL Workbench (recommended)
- Xcode
- macOS

## How to Run

### Step 1: Set Up the Database

1. Open MySQL Workbench and connect to your local MySQL Server instance.
2. Navigate to **Server → Data Import**.
3. Select **Import from Self-Contained File**.
4. Browse to and select `backend/OrlandoLendITdump.sql`.
5. Under **Default Target Schema**, select `outreachdb`. If the schema does not exist, create it first.
6. Click **Start Import** and wait for the import to complete.
7. Verify that the `outreachdb` database and its tables appear in the Schemas panel.

### Step 2: Configure and Run the Backend

1. Open `backend/src/main/resources/application.properties`.
2. Update the following database connection settings to match your local MySQL configuration:
   - `spring.datasource.url`
   - `spring.datasource.username`
   - `spring.datasource.password`
3. Open a terminal.
4. Navigate into the `backend` folder.
5. Start the Spring Boot application:
   `mvn spring-boot:run`

### Step 3: Run the iOS Application
1. Open the iOS project in Xcode.
2. Allow Xcode to resolve any required dependencies.
3. Select an available iPhone simulator from the device selection menu.
4. Verify that the application is configured to connect to the local backend server using localhost.
5. Select the application scheme and click Run (▶) to build and launch the application in the simulator.

### Step 4: Log In
- Username: admin
- Password: AdminPassword
