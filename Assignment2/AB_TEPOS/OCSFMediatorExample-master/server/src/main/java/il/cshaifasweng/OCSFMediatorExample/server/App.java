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
            server = new SimpleServer(3000);
            server.listen();
            System.out.print("Listening");
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
