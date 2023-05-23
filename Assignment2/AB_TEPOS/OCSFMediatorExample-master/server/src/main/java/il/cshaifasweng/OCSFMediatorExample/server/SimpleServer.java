package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.Student;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.util.List;

import org.hibernate.Session;

public class SimpleServer extends AbstractServer {
	private Session session;

	public SimpleServer(int port) {
		super(port);
		this.session = session;
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) throws Exception {
		String msgString = msg.toString();
		if (msgString.startsWith("#GetStudents")) {
			List<Student> studentList = ConnectToDatabase.getStudents();
			try {
				client.sendToClient(studentList);
			}catch (IOException e){
				e.printStackTrace();
			}
		} else if(msgString.startsWith("#UpdateGrade=")){
			String[] numbers = msgString.split("=");
			String[] numbers2 = numbers[1].split(",");
			ConnectToDatabase.updateGrade(Integer.parseInt(numbers2[0]) , Integer.parseInt(numbers2[1]));
		}

        else{
				System.out.print("IN ELSE");
			}

		}


}
