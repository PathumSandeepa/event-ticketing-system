# Real-Time Event Ticketing System

This repository contains a **Real-Time Event Ticketing System** built using Angular, Spring Boot, Core Java (JDK 17), and MySQL. The project includes a frontend Angular application, a backend Spring Boot service, and a CLI application for command-line interactions. The CLI application can operate as a standalone tool or as part of the Spring Boot application.

---

## Project Structure

```
event-ticketing-system/
|
├── front-end/       # Frontend (Angular)
├── ticketing-system/ # Backend (Spring Boot) / Command-Line Interface (Core Java)
```

---

## Technologies Used

### Frontend:

- **Angular** with TypeScript, HTML, CSS
- **Tailwind CSS**

### Backend:

- **Spring Boot** (version 3.3.5)
- **Core Java** (JDK 17)

### Database:

- **MySQL**

---

## Prerequisites

Ensure you have the following installed on your system:

### Frontend:

- Node.js: v20.18.0
- npm: v10.9.2
- Angular CLI: v19.0.4

### Backend:

- Java Development Kit (JDK): 17
- Maven: Latest version
- MySQL Database: Ensure a running MySQL instance.

---

## How to Run the Project

### Step 1: Clone the Repository

```bash
git clone https://github.com/PathumSandeepa/event-ticketing-system.git
cd event-ticketing-system
```

### Step 2: Run the Angular Frontend

1. Navigate to the `front-end/` directory:

   ```bash
   cd front-end
   ```

2. Install dependencies:

   ```bash
   npm install
   ```

3. Start the development server:

   ```bash
   npm start
   ```

4. Open your browser and navigate to `http://localhost:4200` to access the application.

---

### Step 3: Set Up the MySQL Database

1. Create a MySQL database named `ticketing_system`.
2. Update the database credentials in the Spring Boot application (`application.yml`):
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/ticketing_system
   spring.datasource.username=<your-username>
   spring.datasource.password=<your-password>
   ```

---

### Step 4: Run the Spring Boot Backend

1. Navigate to the `ticketing-system/` directory:

   ```bash
   cd ../ticketing-system
   ```

2. Build the project using Maven:

   ```bash
   mvn clean install
   ```

3. Run the Spring Boot application:

   ```bash
   mvn spring-boot:run
   ```

The backend server will start at `http://localhost:8080`.

---

### Step 5: Run the CLI Application (Optional)

1. Navigate to the CLI directory:

   ```bash
   cd src/main/java/com/backend/ticketing_system/cli
   ```

2. Compile and run the CLI application using JDK 17:

   ```bash
   javac *.java
   java CLI
   ```

Note: When running the Spring Boot application, the CLI works as part of the backend.

---

## Key Features

- **Frontend:**

  - Angular-based UI for ticketing operations.
  - Tailwind CSS for responsive design.

- **Backend:**

  - REST API and web socket using Spring Boot.
  - Command-Line Interface (CLI) for standalone ticket management.

- **Database:**

  - MySQL integration for configaration storage.

---

## License

This project does not use any specific license.

---

## Contributions

Contributions are welcome! Feel free to fork the repository and submit a pull request.

---

## Author

**Pathum Sandeepa**

