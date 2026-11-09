## How to Run

### Step 1: Run the Backend (Make sure you have Java 21 JDK)
1. Run the backend/outreachdb_dump.sql.
2. Edit the backend/src/main/resources/application.properties file, replacing the following with your system's database: datasource url, username and password.
3. Open your terminal.
4. Navigate into the backend folder via terminal
5. Start the Spring Boot application using the Maven wrapper:
`mvn spring-boot:run`
*(Keep this terminal window running while using the app so the server stays active).*

### Step 2: Run the Frontend Webapp
The webapp provides the visual dashboard in your browser. To start it:
1. Open a second, separate terminal window (leave the backend running in the first one).
2. Navigate into the webapp folder in the terminal
3. Install the required project dependencies (only required the very first time you set it up):
`npm install`
4. Start the local development server:
`npm run dev`
5. Open the local URL provided in your terminal (usually http://localhost:5173) in your web browser to start using the application.
6. Login right now is username: admin password: AdminPassword
