package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.MsgToLogIn;
import il.cshaifasweng.OCSFMediatorExample.entities.Student;
import org.greenrobot.eventbus.EventBus;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;

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
		MsgToLogIn message = (MsgToLogIn) msg;
		if (msg instanceof List<?>) {
			studentList = (List<Student>) msg;
			EventBus.getDefault().post(studentList);
		} else if (message.getRequest().equals("#LogInReply")) {
			EventBus.getDefault().post(new LogInEvent(message));
		} else {
			System.out.print("UNKOWN MESSAGE TYPE");
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
