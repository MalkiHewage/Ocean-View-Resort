# Ocean View Resort - Hotel Reservation System

A Java Swing-based hotel reservation management system with MySQL database backend for Ocean View Resort in Galle, Sri Lanka.

## Features

- **User Authentication**: Secure login system with SHA-256 password hashing
- **Add Reservations**: Create new bookings with guest details and room selection
- **View Reservations**: Search and display reservation details by reservation number
- **Bill Generation**: Calculate and print itemized bills for guests

## Room Types & Pricing (per night)

| Room Type | Price (LKR) |
|-----------|-------------|
| Single    | 5,000       |
| Double    | 8,000       |
| Family    | 12,000      |
| Suite     | 20,000      |

## Prerequisites

- Java 8 or higher
- MySQL Server 5.7 or higher
- MySQL Connector/J (JDBC driver)

## Database Setup

1. Start your MySQL server

2. Create the database and tables by running the schema script:
   ```bash
   mysql -u root -p < sql/schema.sql
   ```

   Or manually in MySQL:
   ```sql
   CREATE DATABASE IF NOT EXISTS ocean_view_resort;
   USE ocean_view_resort;

   CREATE TABLE IF NOT EXISTS users (
       id INT PRIMARY KEY AUTO_INCREMENT,
       username VARCHAR(50) UNIQUE NOT NULL,
       password VARCHAR(255) NOT NULL
   );

   CREATE TABLE IF NOT EXISTS reservations (
       reservation_number VARCHAR(20) PRIMARY KEY,
       guest_name VARCHAR(100) NOT NULL,
       address VARCHAR(255) NOT NULL,
       contact_number VARCHAR(20) NOT NULL,
       room_type ENUM('SINGLE', 'DOUBLE', 'FAMILY', 'SUITE') NOT NULL,
       check_in_date DATE NOT NULL,
       check_out_date DATE NOT NULL,
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
   );
   ```

3. Configure database connection in `src/dao/DatabaseConnection.java`:
   ```java
   private static final String HOST = "localhost";
   private static final String PORT = "3306";
   private static final String DATABASE = "ocean_view_resort";
   private static final String USERNAME = "root";
   private static final String PASSWORD = "";  // Set your MySQL password
   ```

## Compilation

1. Download the MySQL Connector/J JAR file and place it in a `lib` folder

2. Compile the project:
   ```bash
   # Windows
   javac -cp "lib/mysql-connector-j-8.0.33.jar" -d out src/*.java src/model/*.java src/dao/*.java src/controller/*.java src/view/*.java

   # Linux/Mac
   javac -cp "lib/mysql-connector-j-8.0.33.jar" -d out src/*.java src/model/*.java src/dao/*.java src/controller/*.java src/view/*.java
   ```

## Running the Application

```bash
# Windows
java -cp "out;lib/mysql-connector-j-9.6.0.jar" Main

# Linux/Mac
java -cp "out:lib/mysql-connector-j-8.0.33.jar" Main
```

## Default Login Credentials

- **Username**: admin
- **Password**: admin123

The default admin user is created automatically on first run.

## Project Structure

```
eresortjava/
├── src/
│   ├── model/
│   │   ├── User.java              # User entity
│   │   ├── Reservation.java       # Reservation entity
│   │   └── RoomType.java          # Room type enum with pricing
│   ├── dao/
│   │   ├── DatabaseConnection.java # MySQL connection singleton
│   │   ├── UserDAO.java           # User data access
│   │   └── ReservationDAO.java    # Reservation CRUD operations
│   ├── view/
│   │   ├── LoginFrame.java        # Login window
│   │   ├── MainFrame.java         # Main dashboard
│   │   ├── AddReservationPanel.java    # New reservation form
│   │   ├── DisplayReservationPanel.java # View reservation details
│   │   └── BillPanel.java         # Bill calculation and printing
│   ├── controller/
│   │   ├── AuthController.java    # Authentication logic
│   │   └── ReservationController.java # Reservation business logic
│   └── Main.java                  # Application entry point
├── sql/
│   └── schema.sql                 # Database schema
└── README.md
```

## Architecture

The application follows the MVC (Model-View-Controller) pattern with DAO (Data Access Object) for database operations:

- **Model**: Data entities (User, Reservation, RoomType)
- **View**: Swing UI components (LoginFrame, MainFrame, panels)
- **Controller**: Business logic (AuthController, ReservationController)
- **DAO**: Database operations (UserDAO, ReservationDAO)

## Usage

1. **Login**: Enter your credentials on the login screen
2. **Add Reservation**: Fill in guest details, select room type, and choose dates
3. **Display Reservation**: Search by reservation number to view details
4. **Calculate Bill**: Generate and print bills for checkout

## Reservation Number Format

Reservations are assigned numbers in the format: `RES-YYYYMMDD-XXX`
- Example: `RES-20250128-001`
