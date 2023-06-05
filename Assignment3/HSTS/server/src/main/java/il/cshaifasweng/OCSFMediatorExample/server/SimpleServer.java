package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.MsgToLogIn;
import il.cshaifasweng.OCSFMediatorExample.entities.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.User;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.util.List;

import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class SimpleServer extends AbstractServer {
	private Session session;

	public SimpleServer(int port) {
		super(port);
		this.session = session;
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) throws Exception {
		//String request = msg.toString();
		MsgToLogIn message = (MsgToLogIn) msg;
		//String request = message.getRequest();
		/*
		the first if , else if , i dont know where we use it, for login look at the 3rd else if
		 */
		if (message.getRequest().startsWith("#GetStudents")) {
			List<Student> studentList = ConnectToDatabase.getStudents();
			try {
				client.sendToClient(studentList);
			}catch (IOException e){
				e.printStackTrace();
			}
		} /*else if(message.getRequest().startsWith("#UpdateGrade=")){
			String[] numbers = request.split("=");
			String[] numbers2 = numbers[1].split(",");
			ConnectToDatabase.updateGrade(Integer.parseInt(numbers2[0]) , Integer.parseInt(numbers2[1]));
		}*/

        else if( message.getRequest().equals("#LogInAttempt")){  // login attempt
			       System.out.print("hey user  \n");
				String userName= message.getUsername();
				String password= message.getPassword();
			System.out.print("user: "+userName+" pass: "+password);
		/*	CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<User> query = builder.createQuery(User.class);
			Root<User> root= query.from(User.class);
			// this give me user where id_num=userName
			Predicate userCondition = (Predicate) query.where(builder.equal(root.get("id_num"), userName));
			// this give me user where Password = password
			Predicate passwordCondition = (Predicate) query.where(builder.equal(root.get("Password"), password));
			// this two sentence give me the both condition
			Predicate combinedCondition = builder.and(userCondition,passwordCondition);
			query.where(combinedCondition);
			// now in the date should be OR one user or nothing
			List<User> data = session.createQuery(query).getResultList();
			if ( data.isEmpty())// there is no such user
			{   System.out.print("NO FUCKING USER ");                      }

			else {
				System.out.print("hey user  ");
			}*/

			}

		}


}
