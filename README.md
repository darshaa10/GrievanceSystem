# GrievanceSystem

## Smart Public Grievance Redressal System

This project is a Smart Public Grievance Redressal System developed using Java, JDBC, and MySQL. The system allows users to submit grievances, store them in a database, and manage grievance records efficiently.

## Project Module

**Member 3 – Database Connectivity and DAO Layer**

## Technologies Used

* Java
* JDBC
* MySQL
* VS Code
* Git
* GitHub

## Features Implemented

* MySQL Database Creation
* JDBC Database Connection
* User Table Management
* Grievance Table Management
* Insert User Data
* Insert Grievance Data
* Retrieve Records from Database
* DAO (Data Access Object) Implementation

## Project Structure

```
GrievanceSystem
│
├── src
│   ├── com/grievance/dao
│   ├── com/grievance/model
│   ├── TestDB.java
│   └── TestDAO.java
│
├── lib
│   └── mysql-connector-j.jar
│
├── database
│   └── grievance.sql
│
├── README.md
└── .gitignore
```

## How to Compile and Run

### Compile

```
javac -cp ".;lib/*" src/com/grievance/dao/*.java src/com/grievance/model/*.java src/TestDB.java src/TestDAO.java
```

### Run

```
java -cp ".;lib/*;src" TestDB
java -cp ".;lib/*;src" TestDAO
```

## Database

MySQL database is used to store user and grievance information. JDBC is used to connect Java application with MySQL database.

## Author

Member 3 – Database and DAO Implementation
