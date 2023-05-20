package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.client.Student;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class SimpleServer extends AbstractServer {
	private Session session;

	public SimpleServer(int port) {
		super(port);
		this.session = session;
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) throws IOException {
		String msgString = msg.toString();
		if (msgString.startsWith("#warning")) {
			Warning warning = new Warning("Warning from server!");
			try {
				client.sendToClient(warning);
				System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (msgString.startsWith("#GetStudents")) {
			System.out.print("\n");
			System.out.print("IN ESendingendingLSE");
			List<Student> studentList = ConnectToDatabase.getAllStudents();
			for (Student student : studentList){
				System.out.print("\n");
				System.out.print(student.getStudentName());
			}
			System.out.print("\n");
			System.out.print("OUT 32132ESendingendingLSE");
			try {
				client.sendToClient(studentList);
			}catch (IOException e){
				e.printStackTrace();
			}
			}
        else{
				System.out.print("IN ELSE");
			}

		}


}
