--JavaFX project--
CustPoint - an application providing the ability to manage and create customers and appointments for your scheduling needs.

Author: Peter Sims
Application Version: 1.0
Date: 3/3/2023

IDE: IntelliJ IDEA Community Edition 2021.1.3
Java JDK/Version: Java SE 17.0.1 (jdk-17.0.1)
JavaFX Version: 17.0.2 (javafx-base:17.0.2)

To run the program:
  - Starter DB files have been provides in the DBStarterFiles directory.
  - Ensure you have the above versions of the Java SDK and JavaFX installed
  - Run the Main class found at 'src/main/java/sims/custpoint/Main.java' (if need be, specifically run the main() method in the class)
  - The following users are available for the application:
                #1 username: test  / password: test
                #2 username: admin / password: admin
  - Upon logging in, all functionality will be available. Customers and appointments are able to be deleted from the
    main menu, and clicking "Add" or "Modify" under each of their respective displays will take you to their own view.

Additional Reporting:
  - Besides being able to see what type of events happen for a given month and viewing a selected contact's schedule,
    the user is able to see how many customers reside in each district. If one or more lives in a first-level district,
    you will be able to view the country name, district name, and the count of customers residing there.

MySQL Driver used:
  - mysql-connector-java-8.0.26