# Student Record System

The **Student Record System** is a Java-based desktop application that provides an efficient way to manage student records for educational institutions. Built using Java Swing for the graphical user interface and MySQL for database management, this system allows both **Admins** and **Teachers** to manage student information seamlessly.

## Features

### 1. Admin Login and Dashboard
Admins have access to a full range of management tools, including:
- **Add Teachers**: Admins can add teachers with their respective details like name, subject, department, username, and password.
- **Manage Teachers**: Admins can manage teacher credentials and information from the dashboard.

### 2. Teacher Login and Dashboard
Teachers have a dedicated interface where they can:
- **Add Students**: Teachers can input student data such as ID, name, age, roll number, enrollment number, course, year, and section.
- **View Students**: A table view allows teachers to see all student records.
- **Search for Students**: Teachers can search for individual students using their ID.
- **Update Student Information**: Teachers can modify student records when necessary.

### 3. CRUD Operations
- **Create**: Admins can add teacher records, and teachers can add student records.
- **Read**: Teachers can view all student records or search for individual students.
- **Update**: Teachers can update existing student details.
- **Delete**: Admins (or teachers with permissions) can delete records.

### 4. Login System
The login page provides a dropdown for selecting user roles (Admin/Teacher), and ensures secure login with username and password validation.

### 5. Database Integration
The system connects to a MySQL database to store and manage student and teacher records:
- **Admin Table**: Stores admin credentials.
- **Teacher Table**: Stores teacher details including login credentials.
- **Student Table**: Stores all student-related data, enabling CRUD operations.

## Technologies Used
- **Java (Swing)**: For creating the desktop GUI.
- **MySQL**: For managing all persistent data (students, teachers, and admins).
- **JDBC**: For database connectivity.

## How to Run
1. Clone the repository.
2. Set up MySQL and create a database named `student_records`.
3. Use the SQL script provided to create tables (`admin`, `teachers`, and `students`).
4. Update the database credentials in the `connectDatabase()` method.
5. Run the project from your IDE (e.g., Eclipse) and use the GUI for managing student records.