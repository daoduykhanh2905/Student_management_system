# Student Management System – Multi-Stage Development Plan

## Final Technology Direction

The project will evolve toward this architecture:

```text
Swing Desktop Application
        │
        │ REST / JSON
        ▼
Spring Boot Backend
        │
        │ JPA / Hibernate
        ▼
PostgreSQL
```

However, we should **not start here**.

The project should evolve in stages so that every stage produces a complete working application.

---

# Stage 0 — Project Design

## Goal

Define the system before writing too much code.

## Main actors

```text
Admin
Teacher
Student
```

## Main modules

```text
Authentication
Student Management
Teacher Management
Course Management
Class Management
Enrollment
Grades
Attendance
Reports
Dashboard
```

## Initial database entities

```text
User
Student
Teacher
Department
Course
Class
Enrollment
Grade
Attendance
Semester
```

## Deliverables

- Requirement specification
- Use-case diagram
- ER diagram
- Initial class diagram
- PostgreSQL database schema
- GUI wireframes

This stage is important for the final-year report because it demonstrates software engineering design before implementation.

---

# Stage 1 — Basic Swing Application Without Database

## Goal

Learn and establish the GUI architecture first.

Do not connect PostgreSQL yet.

Use in-memory data such as:

```java
List<Student> students = new ArrayList<>();
```

## Technology

```text
Java 21
Swing
Maven
```

## GUI screens

Create:

```text
LoginFrame
MainFrame
StudentPanel
StudentFormDialog
```

The main application might look like:

```text
┌─────────────────────────────────────────────────────────┐
│ Student Management System                              │
├───────────────┬─────────────────────────────────────────┤
│ Dashboard     │                                         │
│ Students      │                                         │
│ Teachers      │              CONTENT                    │
│ Courses       │                                         │
│ Classes       │                                         │
│ Grades        │                                         │
│ Attendance    │                                         │
│ Reports       │                                         │
│               │                                         │
│ Logout        │                                         │
└───────────────┴─────────────────────────────────────────┘
```

Use:

```java
JFrame
JPanel
JTable
JTextField
JComboBox
JButton
JDialog
JMenuBar
JTabbedPane
JScrollPane
```

For changing screens inside the main window, use:

```java
CardLayout
```

rather than opening many independent `JFrame` windows.

## Student CRUD

Implement:

- Add student
- Edit student
- Delete student
- View students
- Search students

Example:

```text
Students

Search: [______________]       [+ Add Student]

---------------------------------------------------------
ID       Name              Department          Status
---------------------------------------------------------
ST001    Nguyen Minh       Computer Science    Active
ST002    Tran Nam          Electronics         Active
---------------------------------------------------------

[Edit] [Delete] [View]
```

## Data

At this stage:

```text
Swing
 ↓
ArrayList<Student>
```

No database.

## Why this stage matters

It lets you solve:

- Swing layout
- JTable
- Event handling
- Form validation
- Application navigation

without debugging database code at the same time.

## Completion criteria

Stage 1 is complete when:

- Application starts
- Login screen works with a hard-coded account
- Main window works
- Student CRUD works
- Search works
- Data exists during application runtime

---

# Stage 2 — PostgreSQL + JDBC

Now replace the in-memory storage with a real database.

## Architecture

```text
Swing
  │
  ▼
DAO
  │
  ▼
JDBC
  │
  ▼
PostgreSQL
```

## Technology

```text
Java Swing
PostgreSQL
PostgreSQL JDBC Driver
Maven
```

## Example database

```sql
CREATE DATABASE student_management;
```

Initial table:

```sql
CREATE TABLE students (
    id BIGSERIAL PRIMARY KEY,
    student_code VARCHAR(20) UNIQUE NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    date_of_birth DATE,
    email VARCHAR(150),
    phone VARCHAR(30),
    address TEXT,
    status VARCHAR(30)
);
```

## Create DAO classes

Do not write SQL inside Swing code like this:

```java
public void actionPerformed(ActionEvent e) {
    Connection conn = ...
    PreparedStatement ps = ...
}
```

Instead create:

```text
StudentDAO
TeacherDAO
CourseDAO
```

Example:

```java
public interface StudentDAO {

    List<Student> findAll();

    Student findById(long id);

    Student findByCode(String studentCode);

    void save(Student student);

    void update(Student student);

    void delete(long id);
}
```

Then:

```java
public class StudentDAOImpl implements StudentDAO {

    private final Connection connection;

    ...
}
```

## Connection utility

Create:

```text
DatabaseConnection.java
```

For example:

```java
public class DatabaseConnection {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/student_management";

    private static final String USER = "postgres";

    private static final String PASSWORD = "password";

    public static Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }
}
```

Later we should improve this.

## Completion criteria

Stage 2 is complete when:

```text
✓ Add student → PostgreSQL INSERT
✓ Edit student → PostgreSQL UPDATE
✓ Delete student → PostgreSQL DELETE
✓ Search student → PostgreSQL SELECT
✓ Restart program → data remains
```

Now the application is already a working database management application.

---

# Stage 3 — Proper Layered Architecture

Stage 2 works, but the architecture should now be cleaned up.

Move to:

```text
Swing UI
   │
   ▼
Controller
   │
   ▼
Service
   │
   ▼
DAO
   │
   ▼
PostgreSQL
```

Recommended package structure:

```text
student-management/
│
├── pom.xml
│
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/example/studentmanagement/
    │   │
    │   │       ├── Main.java
    │   │       │
    │   │       ├── model/
    │   │       │   ├── Student.java
    │   │       │   ├── Teacher.java
    │   │       │   ├── Course.java
    │   │       │   ├── Enrollment.java
    │   │       │   └── Grade.java
    │   │       │
    │   │       ├── view/
    │   │       │   ├── LoginFrame.java
    │   │       │   ├── MainFrame.java
    │   │       │   ├── StudentPanel.java
    │   │       │   └── StudentDialog.java
    │   │       │
    │   │       ├── controller/
    │   │       │   ├── StudentController.java
    │   │       │   └── LoginController.java
    │   │       │
    │   │       ├── service/
    │   │       │   ├── StudentService.java
    │   │       │   └── AuthenticationService.java
    │   │       │
    │   │       ├── dao/
    │   │       │   ├── StudentDAO.java
    │   │       │   └── StudentDAOImpl.java
    │   │       │
    │   │       └── util/
    │   │           └── DatabaseConnection.java
```

## Responsibility

### View

Only GUI.

```text
StudentPanel
StudentDialog
```

Should not contain SQL.

### Controller

Connect GUI and services.

For example:

```java
studentController.addStudent(student);
```

### Service

Contains business logic.

Example:

```java
public void addStudent(Student student) {

    validateStudent(student);

    if (studentDAO.findByCode(student.getStudentCode()) != null) {
        throw new StudentAlreadyExistsException();
    }

    studentDAO.save(student);
}
```

### DAO

Responsible only for database operations.

This separation becomes extremely important before Spring Boot is introduced.

---

# Stage 4 — Complete Core Management Modules

Now extend the project beyond Student CRUD.

## 4.1 Department Management

```text
Department
----------
id
departmentCode
departmentName
description
```

Functions:

```text
Add
Edit
Delete
Search
```

## 4.2 Teacher Management

```text
Teacher
-------
id
teacherCode
firstName
lastName
email
phone
department
status
```

Functions:

```text
Add teacher
Edit teacher
Delete teacher
Search teacher
Assign department
```

## 4.3 Course Management

```text
Course
------
id
courseCode
courseName
credits
department
description
```

Functions:

```text
Create
Edit
Delete
Search
```

## Completion criteria

At the end of Stage 4:

```text
Student Management     ✓
Teacher Management     ✓
Department Management  ✓
Course Management      ✓
```

At this point you have a good intermediate demonstration version.

---

# Stage 5 — Academic Structure

This stage makes the project much more realistic.

## Semester

```text
Semester
--------
id
name
academicYear
startDate
endDate
```

Example:

```text
Semester 1
2026-2027
```

## Class / Course Offering

Do not consider `Course` and `Class` the same entity.

Example:

```text
Course
CS301 - Database Systems
```

may produce:

```text
CS301-A
CS301-B
CS301-C
```

A class could contain:

```text
Class
-----
id
classCode
courseId
teacherId
semesterId
room
maxStudents
schedule
```

## Enrollment

```text
Enrollment
----------
id
studentId
classId
enrollmentDate
status
```

Relationships:

```text
Student
   │
   │
   ▼
Enrollment
   │
   │
   ▼
Class
   │
   ├── Course
   └── Teacher
```

This stage demonstrates many-to-many database relationships.

---

# Stage 6 — Grade Management

Now add real academic business logic.

## Grade entity

```text
Grade
-----
id
enrollmentId
assignmentScore
midtermScore
finalScore
totalScore
letterGrade
```

Example calculation:

```text
Assignment = 20%
Midterm    = 30%
Final      = 50%
```

Therefore:

```text
Total =
Assignment × 0.2
+ Midterm × 0.3
+ Final × 0.5
```

Then:

```text
>= 8.5       A
>= 7.0       B
>= 5.5       C
>= 4.0       D
<  4.0       F
```

Put this calculation in:

```text
GradeService
```

not inside Swing.

Example:

```java
public double calculateFinalScore(
        double assignment,
        double midterm,
        double finalExam) {

    return assignment * 0.2
         + midterm * 0.3
         + finalExam * 0.5;
}
```

---

# Stage 7 — Attendance Management

Database:

```text
Attendance
----------
id
enrollmentId
date
status
```

Status:

```text
PRESENT
ABSENT
LATE
EXCUSED
```

Teacher GUI:

```text
Course: Database Systems
Class: CS301-A
Date: 15-08-2026

-----------------------------------
Student              Status
-----------------------------------
Nguyen Minh          Present
Tran Nam             Absent
Le Anh               Late
-----------------------------------

                 [Save Attendance]
```

The application can calculate:

```text
Attendance percentage
```

and generate warnings when:

```text
Attendance < 80%
```

---

# Stage 8 — Authentication and Authorization

Now replace the hard-coded login.

Create:

```text
users
```

Example:

```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(30) NOT NULL,
    enabled BOOLEAN DEFAULT TRUE
);
```

Roles:

```text
ADMIN
TEACHER
STUDENT
```

Permissions:

```text
ADMIN
 ├── Student CRUD
 ├── Teacher CRUD
 ├── Course CRUD
 ├── Class management
 └── Reports

TEACHER
 ├── View classes
 ├── View students
 ├── Update grades
 └── Attendance

STUDENT
 ├── View profile
 ├── View courses
 ├── View grades
 └── View attendance
```

Use BCrypt password hashing.

Never store raw passwords in the database.

---

# Stage 9 — Dashboard and Reporting

At this point the application is functionally complete.

Now improve its presentation.

## Dashboard

Show:

```text
Total Students
Total Teachers
Total Courses
Active Classes
Average GPA
Attendance Rate
```

Swing can display charts using:

```text
JFreeChart
```

## Reports

Generate:

```text
Student transcript
Student list
Class list
Grade report
Attendance report
GPA ranking
```

Potential export formats:

```text
PDF
Excel
CSV
```

Useful libraries:

```text
JasperReports → PDF/report generation
Apache POI    → Excel
```

---

# Stage 10 — Testing

Before introducing Spring Boot, test the service layer.

Use:

```text
JUnit 5
Mockito
```

You should especially test:

```text
Grade calculation
Student validation
Duplicate student codes
Enrollment validation
Authentication
Permission rules
Attendance percentage
```

---

# Stage 11 — Stable Desktop Version

This is an important milestone.

At this point:

```text
Swing
  │
  ▼
Controller
  │
  ▼
Service
  │
  ▼
DAO
  │
  ▼
JDBC
  │
  ▼
PostgreSQL
```

The application should be completely usable.

Call this:

```text
Version 1.0
Desktop Edition
```

This means that even if something goes wrong during Spring Boot integration, you still have a complete working final-year project.

---

# Stage 12 — Introduce Spring Boot

Now start Version 2.

Instead of Swing directly accessing PostgreSQL:

```text
Swing
  ↓
DAO
  ↓
PostgreSQL
```

change it to:

```text
Swing
  │
  │ HTTP
  ▼
Spring Boot
  │
  ▼
PostgreSQL
```

New architecture:

```text
┌──────────────────────────┐
│ Swing Client             │
│                          │
│ JFrame                   │
│ JPanel                   │
│ JTable                   │
└────────────┬─────────────┘
             │
             │ HTTP + JSON
             ▼
┌──────────────────────────┐
│ Spring Boot Server       │
│                          │
│ REST Controller          │
│ Service                  │
│ Repository               │
└────────────┬─────────────┘
             │
             │ JPA
             ▼
┌──────────────────────────┐
│ PostgreSQL               │
└──────────────────────────┘
```

---

# Stage 13 — Create Spring Boot Backend

Create a separate project:

```text
student-management-server/
```

Recommended dependencies:

```text
Spring Boot
Spring Web
Spring Data JPA
PostgreSQL Driver
Validation
Spring Security
```

Project layout:

```text
student-management-server/
│
├── controller/
├── service/
├── repository/
├── entity/
├── dto/
├── security/
├── exception/
└── config/
```

Example:

```text
StudentController
StudentService
StudentRepository
StudentEntity
StudentDTO
```

---

# Stage 14 — Replace DAO With Spring Data JPA

Before:

```java
public List<Student> findAll() {

    String sql = "SELECT * FROM students";

    ...
}
```

After:

```java
public interface StudentRepository
        extends JpaRepository<Student, Long> {

}
```

Your database layer becomes:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
JPA / Hibernate
    ↓
PostgreSQL
```

---

# Stage 15 — Create REST APIs

Example student endpoints:

```text
GET    /api/students
GET    /api/students/{id}
POST   /api/students
PUT    /api/students/{id}
DELETE /api/students/{id}
```

Search:

```text
GET /api/students?keyword=Minh
```

Courses:

```text
GET    /api/courses
POST   /api/courses
PUT    /api/courses/{id}
DELETE /api/courses/{id}
```

Enrollment:

```text
POST /api/enrollments
GET  /api/students/{id}/enrollments
```

Grades:

```text
GET /api/students/{id}/grades
PUT /api/grades/{id}
```

Attendance:

```text
POST /api/attendance
GET  /api/students/{id}/attendance
```

---

# Stage 16 — Connect Swing to REST API

Now remove database access from the Swing program.

Old:

```java
studentDAO.findAll();
```

New:

```java
studentApiClient.getStudents();
```

Create:

```text
api/
├── StudentApiClient.java
├── CourseApiClient.java
├── TeacherApiClient.java
└── AuthenticationApiClient.java
```

The communication becomes:

```text
Swing
 ↓
StudentApiClient
 ↓
HTTP
 ↓
Spring Boot
 ↓
PostgreSQL
```

For JSON serialization use:

```text
Jackson
```

You can use Java's built-in:

```java
java.net.http.HttpClient
```

so another HTTP library is not necessarily required.

---

# Stage 17 — Spring Security

Move authentication to the server.

For example:

```text
Swing Login
    │
    ▼
POST /api/auth/login
    │
    ▼
Spring Security
    │
    ▼
JWT Token
```

Server response:

```json
{
    "token": "...",
    "username": "teacher01",
    "role": "TEACHER"
}
```

Then each Swing request sends:

```text
Authorization: Bearer TOKEN
```

Now users cannot bypass permissions simply by modifying the Swing application.

---

# Stage 18 — Multi-User System

With Spring Boot:

```text
PC 1 Swing ───┐
              │
PC 2 Swing ───┼──► Spring Boot ───► PostgreSQL
              │
PC 3 Swing ───┘
```

Only Spring Boot communicates directly with PostgreSQL.

This is much more realistic for an actual university system.

---

# Stage 19 — Advanced Features

Only add these after the core application is stable.

Possible additions:

## Audit Logs

```text
2026-08-15 10:30
admin01 created Student ST1034

2026-08-15 11:20
teacher03 changed grade
7.5 → 8.0
```

## Academic warnings

Automatically identify students with:

```text
GPA < 2.0
```

or:

```text
Attendance < 80%
```

## Student performance charts

Display GPA over semesters.

## Import/export

Support:

```text
CSV import
Excel import
Excel export
PDF transcript
```

## Database backup

Admin could trigger or document PostgreSQL backup procedures.

---

# Recommended Overall Milestones

## Milestone 1 — GUI Prototype

```text
Swing
ArrayList
```

Features:

```text
Login
Student CRUD
Search
```

## Milestone 2 — Database Application

```text
Swing
JDBC
PostgreSQL
```

Features:

```text
Persistent Student CRUD
Teacher CRUD
Course CRUD
```

## Milestone 3 — Full Desktop System

```text
Swing
Service Layer
DAO
JDBC
PostgreSQL
```

Features:

```text
Authentication
Students
Teachers
Departments
Courses
Classes
Enrollment
Grades
Attendance
```

## Milestone 4 — Professional Desktop Application

Add:

```text
Reports
Charts
PDF
Excel
Validation
Logging
Unit testing
```

This can be:

```text
Student Management System v1.0
```

## Milestone 5 — Backend Migration

Add:

```text
Spring Boot
Spring Data JPA
REST API
```

Architecture becomes:

```text
Swing → REST → Spring Boot → PostgreSQL
```

## Milestone 6 — Secure Multi-User System

Add:

```text
Spring Security
JWT
Roles
Authorization
Audit log
```

Call this:

```text
Student Management System v2.0
```

---

# Recommended Library Set

## Stage 1

```text
Java 21
Swing
Maven
```

## Stage 2–4

Add:

```text
PostgreSQL JDBC Driver
SLF4J
Logback
```

Architecture:

```text
Swing → JDBC → PostgreSQL
```

## Stage 5–10

Add:

```text
JUnit 5
Mockito
BCrypt
JFreeChart
Apache POI
JasperReports
```

## Stage 12+

Backend:

```text
Spring Boot
Spring Web
Spring Data JPA
Hibernate
Spring Validation
Spring Security
PostgreSQL Driver
```

Swing client:

```text
Swing
Java HttpClient
Jackson
```

---

# Recommended Development Strategy

The most important point is:

**Do not start with Spring Boot.**

Start with:

```text
Stage 1
Swing + ArrayList
```

Then:

```text
Stage 2
Swing + JDBC + PostgreSQL
```

Then:

```text
Stage 3
Swing + Controller + Service + DAO + PostgreSQL
```

Once that architecture is clean, change only the lower communication layer.

Before:

```text
Swing
  ↓
Controller
  ↓
Service
  ↓
DAO
  ↓
PostgreSQL
```

After:

```text
Swing
  ↓
Controller
  ↓
ApiClient
  ↓ HTTP
Spring Boot
  ↓
Service
  ↓
Repository
  ↓
PostgreSQL
```

This gives the project a strong final-presentation story:

> Version 1 was designed as a standalone desktop application using Java Swing, JDBC, and PostgreSQL. After validating the functional requirements and business logic, the system was migrated to a client-server architecture using Spring Boot REST APIs. This enabled centralized authentication, better security, multiple concurrent clients, and separation between the presentation and data layers.
