# Orlando Lend IT – Web Application

## Requirements

- Java 21 JDK
- MySQL Server
- MySQL Workbench (recommended)
- Node.js and npm

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
5. Start the Spring Boot application using Maven:

   `mvn spring-boot:run`

*(Keep this terminal window running while using the web application so the backend server stays active.)*

### Step 3: Run the Frontend Webapp

1. Open a second, separate terminal window (leave the backend running in the first one).
2. Navigate into the `webapp` folder.
3. Install the required project dependencies (only required the first time you set it up):

   `npm install`

4. Start the local development server:

   `npm run dev`

5. Open the local URL provided in your terminal (usually http://localhost:5173) in your web browser to start using the application.

### Step 4: Log In

- Username: admin
- Password: AdminPassword
