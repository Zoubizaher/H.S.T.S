package il.cshaifasweng.OCSFMediatorExample.client;

import org.greenrobot.eventbus.EventBus;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.Warning;

import java.util.List;

public class SimpleClient extends AbstractClient {

	private List<Student> studentList;
	private static SimpleClient client = null;

	private SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		System.out.print("Receiving a message");
		if (msg.getClass().equals(Warning.class)) {
			EventBus.getDefault().post(new WarningEvent((Warning) msg));
		}
		if (msg instanceof List<?>) {
			System.out.print("Receiving the list");
			studentList = (List<Student>) msg;
			EventBus.getDefault().post(studentList);
		} else {
			System.out.print("Receiving the number");
			System.out.print(msg);
		}
	}


	
	public static SimpleClient getClient() {
		if (client == null) {
			client = new SimpleClient("localhost", 3000);
		}
		return client;
	}

	public List<Student> getStudentList(){return this.studentList;}

}
