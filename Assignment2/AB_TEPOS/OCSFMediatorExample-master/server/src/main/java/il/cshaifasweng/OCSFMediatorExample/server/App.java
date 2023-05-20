package il.cshaifasweng.OCSFMediatorExample.server;
import il.cshaifasweng.OCSFMediatorExample.client.Course;
import il.cshaifasweng.OCSFMediatorExample.client.Student;

import java.io.IOException;
import java.util.List;
import org.hibernate.Session;
import static il.cshaifasweng.OCSFMediatorExample.server.ConnectToDatabase.EndConnection;

/**
 * Hello world!
 *
 */
public class App 
{
    private static SimpleServer server;

    public static void main( String[] args ) throws IOException
    {
        try{
            ConnectToDatabase.initializeDatabase();
            List<Student> studentList = ConnectToDatabase.getAllStudents();
            System.out.print("\n\nPRINTINGT\n\n\n" );
            for (Student student : studentList){
                System.out.print(student.getStudentName());
                List<Course> courses = student.getCourses();
                for (Course course : courses){
                    System.out.println(course.getCourse_name());
                }
            }
            server = new SimpleServer(3000);
            server.listen();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
