HSTS Project
The HSTS (High School Testing System) project is a software application that facilitates the management and administration of exams in a high school setting. It provides a platform for students to take exams online and for teachers to create, distribute, and evaluate exams.

Features
Student Portal: Students can log in to the system and view available exams, take exams, and receive immediate feedback on their performance.
Teacher Portal: Teachers can create new exams, manage exam questions, assign exams to specific students or groups, and view exam results.
Real-time Communication: The system uses the OCSF (Object Client-Server Framework) to enable real-time communication between clients and the server.
Event-driven Architecture: The application utilizes the EventBus library for event-based communication and decoupling of components.
MySQL Database: The system stores exam data, student information, and other relevant data in a MySQL database for persistent storage.
JavaFX GUI: The user interface is built using JavaFX, providing an intuitive and user-friendly experience.
Prerequisites
Java Development Kit (JDK) version 8 or above.
MySQL Server for database storage.
MySQL Connector/J library for Java database connectivity.
OCSF and EventBus libraries.
Installation
Clone the repository to your local machine using the following command:

bash
Copy code
git clone https://github.com/faisalomari/hsts.git
Set up the MySQL database by executing the SQL scripts provided in the database directory. Make sure to create the necessary database schema and tables.

Update the MySQL database connection configuration in the db.properties file located in the src/main/resources directory. Provide the appropriate values for the MySQL server host, port, database name, username, and password.

Import the project into your preferred Java IDE (e.g., Eclipse, IntelliJ IDEA).

Resolve any missing dependencies by adding the required JAR files to the project's classpath. Include the OCSF, EventBus, and MySQL Connector/J libraries.

Build and run the project.

Usage
Launch the application by running the server app firstly, and then the client (with option of multiple instances).

The application will start and display the login screen. Use the appropriate credentials (username and password) to log in as a student or teacher.

As a student, you can view available exams, select an exam, answer the questions, and submit your answers. You will receive immediate feedback on your performance.

As a teacher, you can create new exams, manage questions, assign exams to students or groups, and view exam results and statistics.

Explore the different features and functionalities of the application using the intuitive graphical user interface.

Contributing
Contributions to the HSTS project are welcome. If you would like to contribute, please follow these steps:

Fork the repository.

Create a new branch for your feature or bug fix.

Commit your changes and push them to your forked repository.

Submit a pull request, describing your changes and the rationale behind them.

Your contribution will be reviewed, and once approved, it will be merged into the main repository.



Contact
For any questions or inquiries, please contact:
1) Faisal Omari - faisalomari321@gmail.com
2) Zaher Zoabu - zoubizaher@gmail.com

Feel free to update and customize this README file according to your project's specific details and requirements.
