# PDMS-DB
[![Ask DeepWiki](https://devin.ai/assets/askdeepwiki.png)](https://deepwiki.com/7mza6/PDMS-DB)

## Overview

PDMS-DB is a desktop-based Package Delivery Management System (PDMS) developed in Java Swing. This application provides a comprehensive solution for managing all aspects of package delivery, including customer information, order processing, staff management, and logistics. It features a role-based access system for Admins, Staff, and Delivery personnel, each with a tailored dashboard and functionalities.

## Features

*   **User Authentication & Roles**: Secure login system with password hashing for three distinct user roles: Admin, Staff, and Delivery Staff.
*   **Order Management**: A complete CRUD interface for creating, viewing, updating, and deleting orders. Includes order status tracking (Pending, In Progress, Delivered).
*   **Customer Management**: Maintain a comprehensive database of customer details and their order history.
*   **Package Management**: Manage package information, including weight, content description, fragility, and recipient details.
*   **Logistics & Operations**: Efficiently manage delivery routes, vehicles, and company branches.
*   **Staff Assignment**: Admins can assign orders to available delivery staff based on matching routes.
*   **Delivery Dashboard**: A dedicated interface for delivery staff to view their assigned orders and update delivery statuses in real-time.
*   **Modern UI**: A clean and responsive user interface built using the FlatLaf look and feel library.

## Technology Stack

*   **Language**: Java
*   **UI Framework**: Java Swing
*   **UI Theme**: [FlatLaf](https://www.formdev.com/flatlaf/)
*   **Layout**: MigLayout
*   **Database**: MySQL
*   **Connectivity**: JDBC
*   **Libraries**:
    *   Gson for JSON processing
    *   SwingX for enhanced Swing components
    *   Raven-DateTime for date pickers
    *   Raven-Toast for notifications

## Getting Started

Follow these instructions to get a copy of the project up and running on your local machine.

### Prerequisites

*   JDK 8 or higher
*   MySQL Server
*   An IDE that supports Apache Ant projects (e.g., Apache NetBeans)

### Database Setup

1.  Start your MySQL server.
2.  Create a new database named `PDMS`:
    ```sql
    CREATE DATABASE PDMS;
    ```
3.  Import the complete database schema using the MySQL Workbench file located at `DB/PDMS.mwb`. Alternatively, you can build the schema from the individual CRUD classes. A basic `CUSTOMER` table schema is also provided in `DB/CUSTOMER.sql`.
4.  Ensure your database server is running on `localhost:3300` with the username `root` and password `root`.
5.  If your database credentials differ, update them in the `src/PDMS/application/Application.java` file:
    ```java
    public static String url = "jdbc:mysql://localhost:3300/PDMS";
    public static String username = "root";  
    public static String password = "root";
    ```

### Project Setup and Execution

1.  Clone the repository to your local machine:
    ```sh
    git clone https://github.com/7mza6/pdms-db.git
    ```
2.  Open the project folder in Apache NetBeans. The IDE will automatically recognize it as a Java with Ant project.
3.  The required libraries are included in the `/library` directory and linked in the project configuration.
4.  Run the main application file: `src/PDMS/application/Application.java`.

## Usage

*   Launch the application to open the login screen.
*   Log in with credentials corresponding to one of the three roles: **Admin**, **Staff**, or **Delivery Staff**.
*   The application will display a dashboard with a side menu tailored to the user's role.
    *   **Admin**: Has full access to all management modules, including Users, Staff, Delivery Staff, Branches, Routes, and Vehicles.
    *   **Staff**: Can manage Orders, Customers, and Packages.
    *   **Delivery Staff**: Has a simplified dashboard to view assigned orders and update their delivery status.
