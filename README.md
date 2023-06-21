<h1>HSTS Project</h1>

<p>The HSTS (High School Testing System) project is a software application that facilitates the management and administration of exams in a high school setting. It provides a platform for students to take exams online and for teachers to create, distribute, and evaluate exams.</p>

<h2>Features</h2>

<ul>
  <li><strong>Student Portal:</strong> Students can log in to the system and view available exams, take exams, and receive immediate feedback on their performance.</li>
  <li><strong>Teacher Portal:</strong> Teachers can create new exams, manage exam questions, assign exams to specific students or groups, and view exam results.</li>
  <li><strong>Real-time Communication:</strong> The system uses the OCSF (Object Client-Server Framework) to enable real-time communication between clients and the server.</li>
  <li><strong>Event-driven Architecture:</strong> The application utilizes the EventBus library for event-based communication and decoupling of components.</li>
  <li><strong>MySQL Database:</strong> The system stores exam data, student information, and other relevant data in a MySQL database for persistent storage.</li>
  <li><strong>JavaFX GUI:</strong> The user interface is built using JavaFX, providing an intuitive and user-friendly experience.</li>
</ul>

<h2>Prerequisites</h2>

<p>Before getting started, make sure you have the following prerequisites:</p>

<ul>
  <li>Java Development Kit (JDK) version 8 or above.</li>
  <li>MySQL Server for database storage.</li>
  <li>MySQL Connector/J library for Java database connectivity.</li>
  <li>OCSF and EventBus libraries.</li>
</ul>

<h2>Installation</h2>

<p>Follow these steps to install and set up the project:</p>

<ol>
  <li>Clone the repository to your local machine using the following command:</li>
</ol>

<pre><code>git clone https://github.com/faisalomari/hsts.git</code></pre>

<ol start="2">
  <li>Set up the MySQL database by executing the SQL scripts provided in the database directory. Make sure to create the necessary database schema and tables.</li>
  <li>Update the MySQL database connection configuration in the <code>hibernate.properties</code> file located in the <code>src/main/resources</code> directory. Provide the appropriate values for the MySQL server host, port, database name, username, and password.</li>
  <li>Import the project into your preferred Java IDE (e.g., Eclipse, IntelliJ IDEA).</li>
  <li>Resolve any missing dependencies by adding the required JAR files to the project's classpath. Include the OCSF, EventBus, and MySQL Connector/J libraries.</li>
  <li>Build and run the project.</li>
</ol>

<h2>Usage</h2>

<p>Follow these steps to use the application:</p>

<ol>
  <li>Launch the application by running the server app firstly, and then the client (with the option of multiple instances).</li>
  <li>The application will start and display the login screen. Use the appropriate credentials (username and password) to log in as a student or teacher.</li>
  <li>As a student, you can view available exams, select an exam, answer the questions, and submit your answers. You will receive immediate feedback on your performance.</li>
  <li>As a teacher, you can create new exams, manage questions, assign exams to students or groups, and view exam results and statistics.</li>
  <li>Explore the different features and functionalities of the application using the intuitive graphical user interface.</li>
</ol>

<h2>Contributing</h2>

<p>Contributions to the HSTS project are welcome. If you would like to contribute, please follow these steps:</p>

<ol>
  <li>Fork the repository.</li>
  <li>Create a new branch for your feature or bug fix.</li>
  <li>Commit your changes and push them to your forked repository.</li>
  <li>Submit a pull request, describing your changes and the rationale behind them.</li>
</ol>

<p>Your contribution will be reviewed, and once approved, it will be merged into the main repository.</p>

<h2>Contact</h2>

<p>For any questions or inquiries, please contact:</p>

<ol>
  <li>Faisal Omari - faisalomari321@gmail.com</li>
  <li>Zaher Zoabu - zoubizaher@gmail.com</li>
</ol>
<ol>
  <h2>Login interface:</h2>
  ![LoginInterface](https://github.com/faisalomari/HSTS/assets/75030682/5b15a00e-9ce7-4fcc-8051-2f9eb20de375)
  <h2>Student interface:</h2>
  ![Student](https://github.com/faisalomari/HSTS/assets/75030682/b22782c8-c635-489a-ad3b-4c28697eeb6b)
  <h2>Teacher interface:</h2>
  ![Teacher](https://github.com/faisalomari/HSTS/assets/75030682/4842655f-8d1d-4baf-bc91-03fad98309a2)
</ol>
