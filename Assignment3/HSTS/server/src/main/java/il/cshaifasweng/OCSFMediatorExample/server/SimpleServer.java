package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.client.LogInEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.MsgToLogIn;
import il.cshaifasweng.OCSFMediatorExample.entities.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.User;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.util.List;

import org.hibernate.Session;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class SimpleServer extends AbstractServer {
	private Session session;

	public SimpleServer(int port, Session session) {
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
			} catch (IOException e) {
				e.printStackTrace();
			}
		} /*else if(message.getRequest().startsWith("#UpdateGrade=")){
			String[] numbers = request.split("=");
			String[] numbers2 = numbers[1].split(",");
			ConnectToDatabase.updateGrade(Integer.parseInt(numbers2[0]) , Integer.parseInt(numbers2[1]));
		}*/
		else if (message.getRequest().equals("#LogInAttempt")) {
			String userName = message.getUsername();
			String password = message.getPassword();
			message.setRequest("#LogInReply");
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Long> query = builder.createQuery(Long.class);
			Root<User> root = query.from(User.class);

			// Select count(*) from User where Username = userName
			query.select(builder.count(root)).where(builder.equal(root.get("username"), userName));

			TypedQuery<Long> typedQuery = session.createQuery(query);
			Long count = typedQuery.getSingleResult();

			if (count > 0) {
				// User with the given username exists
				System.out.println("Username exists");

				// Now, check if the password is identical
				CriteriaQuery<Long> passwordQuery = builder.createQuery(Long.class);
				Root<User> passwordRoot = passwordQuery.from(User.class);

				// Select count(*) from User where Username = userName and Password = password
				passwordQuery.select(builder.count(passwordRoot))
						.where(builder.and(
								builder.equal(passwordRoot.get("username"), userName),
								builder.equal(passwordRoot.get("password"), password)
						));

				TypedQuery<Long> passwordTypedQuery = session.createQuery(passwordQuery);
				Long passwordCount = passwordTypedQuery.getSingleResult();

				if (passwordCount > 0) {
					// Password is identical
					System.out.println("\nLogIn successfully\n");
					// Create User object and save the user
					CriteriaQuery<User> userQuery = builder.createQuery(User.class);
					Root<User> userRoot = userQuery.from(User.class);
					userQuery.select(userRoot)
							.where(builder.equal(userRoot.get("username"), userName));

					TypedQuery<User> userTypedQuery = session.createQuery(userQuery);
					User user = userTypedQuery.getSingleResult();

					// Now you have the User object, you can perform further actions
					System.out.println("Logged in user: " + user.getFirst() + " " + user.getLast() + "\n");
					message.setUser(user);
					message.setLogInFlag("Successfully");
					LogInEvent logInEvent = new LogInEvent(message);
					client.sendToClient(message);
				} else {
					// Password is not identical
					message.setLogInFlag("WrongPassword");
					System.out.println("WRONG PASSWORD");
					client.sendToClient(message);
				}
			}//here count<0 which means wrong_username
			else {
				message.setLogInFlag("WrongUsername");
				// User with the given username doesn't exist
				System.out.println("Username doesn't exist");
				client.sendToClient(message);
			}
		}
		else if (message.getRequest().equals("#AddQuestion")){
			ConnectToDatabase.AddQuestion(message.getQuestion());
		}
		else if (message.getRequest().equals("#UpdateQuestion")){
			ConnectToDatabase.updateQuestion(message.getQuestion());
		}

	}
}
