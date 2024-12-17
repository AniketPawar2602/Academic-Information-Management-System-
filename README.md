# Academic Information Management System

The Student Record System is a Java-based desktop application that efficiently manages student records for educational institutions. Built using Java Swing for the graphical user interface and MySQL for database management, this system allows both administrators and Teachers to manage student information seamlessly.

## Features

### 1. Admin Login and Dashboard
Admins have access to a full range of management tools, including:
- **Add Teachers**: Admins can add teachers with their respective details, such as name, subject, department, username, and password.
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

### Screenshots
![Login Page](https://github.com/user-attachments/assets/84260e71-2711-43c5-99c1-e7da545dccc3)

![Admin Dashoboard](https://github.com/user-attachments/assets/41398d38-a9ff-41f1-a0ff-39ea56058e00)

![Teacher Dashoboard](https://github.com/user-attachments/assets/c97d2acb-f76b-4708-9953-4030b72e9159)

![add student](https://github.com/user-attachments/assets/ad4e0835-3b18-4238-8c47-edf62076f02f)

![after update student view](https://github.com/user-attachments/assets/c156fe41-d42f-4220-b240-2251ced51d12)

![seach student](https://github.com/user-attachments/assets/f621c21b-16e5-4018-a017-d61f9c660580)

![update student](https://github.com/user-attachments/assets/ddb5af50-5316-49e4-ae16-73002c23d9d1)

![view student](https://github.com/user-attachments/assets/58db29c3-2239-47b9-ac16-f33ab8455bf7)


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
