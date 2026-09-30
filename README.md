# 📚 Library Management System

A beginner-friendly **Java + JDBC + MySQL + HTML/CSS + JSP** Library Management System suitable for learning, college projects, and GitHub portfolios.

## Features

- User registration and login
- Session-based dashboard
- Book catalog
- Issue books
- 14-day due date calculation
- Payment recording
- Multiple payment methods
- MySQL database
- JDBC database connectivity
- Responsive UI

## Technologies

- Java
- Jakarta Servlet 6
- JSP
- JDBC
- MySQL
- HTML5
- CSS3
- Apache Tomcat 10.1+

## Project Structure

```text
Library-Management-System/
├── src/main/java/com/library/
│   ├── DBConnection.java
│   ├── LoginServlet.java
│   ├── RegisterServlet.java
│   ├── IssueBookServlet.java
│   ├── PaymentServlet.java
│   └── BookServlet.java
├── src/main/webapp/
│   ├── index.html
│   ├── login.html
│   ├── register.html
│   ├── dashboard.jsp
│   ├── books.jsp
│   ├── issueBook.jsp
│   ├── payment.jsp
│   ├── css/style.css
│   └── WEB-INF/web.xml
├── sql/library.sql
└── README.md
```

## Requirements

1. JDK 17 or later
2. Apache Tomcat 10.1+
3. MySQL 8+
4. MySQL Connector/J
5. Eclipse/IntelliJ IDEA/VS Code with Java support

## Database Setup

Open MySQL Workbench and run:

```sql
SOURCE path/to/Library-Management-System/sql/library.sql;
```

Or copy and execute `sql/library.sql`.

## Configure Database

Open:

`src/main/java/com/library/DBConnection.java`

Change these values according to your MySQL installation:

```java
private static final String URL = "jdbc:mysql://localhost:3306/library_db";
private static final String USER = "root";
private static final String PASSWORD = "root";
```

## JDBC Library

Add MySQL Connector/J to the project classpath.

For Maven, you can use:

```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>9.4.0</version>
</dependency>
```

## Run

1. Start MySQL.
2. Execute `sql/library.sql`.
3. Configure the database password.
4. Add MySQL Connector/J.
5. Deploy the application to Tomcat 10.1+.
6. Open the application URL, for example:

`http://localhost:8080/Library-Management-System/`

## Important Security Note

This is an educational project. Passwords are stored as plain text to keep the JDBC example simple. For a real application, use password hashing such as BCrypt/Argon2, environment variables for database credentials, CSRF protection, input validation, HTTPS, and a proper payment gateway.

The payment module in this project records a payment transaction in MySQL; it does **not** connect to a real bank/UPI/card payment gateway.

## Suggested GitHub Description

> A Java JDBC-based Library Management System using Jakarta Servlets, JSP, MySQL, HTML and CSS. Includes authentication, book catalog, book issuing and payment-record management.
