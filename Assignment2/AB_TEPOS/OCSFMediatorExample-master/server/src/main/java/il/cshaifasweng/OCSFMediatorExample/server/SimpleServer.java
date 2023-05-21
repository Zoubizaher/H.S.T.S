package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.client.Student;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) throws Exception {
		String msgString = msg.toString();
		if (msgString.startsWith("#warning")) {
			Warning warning = new Warning("Warning from server!");
			try {
				client.sendToClient(warning);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (msgString.startsWith("#GetStudents")) {
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
