package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.client.LogInEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class SimpleServer extends AbstractServer {
	private Session session;

	private List<User> OnlineUsers = new ArrayList<>();

	public SimpleServer(int port, Session session) {
		super(port);
		this.session = session;
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) throws Exception {
		if (msg instanceof MsgToLogIn) {
			MsgToLogIn message = (MsgToLogIn) msg;
			if (message.getRequest().startsWith("#GetStudents")) {
				List<Student> studentList = ConnectToDatabase.getStudents();
				try {
					client.sendToClient(studentList);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if (message.getRequest().equals("#LogInAttempt")) {
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
						if(OnlineUsers.contains(user)){
							System.out.print("User is online, can't login twice!");
							message.setLogInFlag("UserOnline");
							client.sendToClient(message);
						}else{
							OnlineUsers.add(user);
							if(user.getRole().equals("teacher")){ // we add this to update  question list
								String id = user.getId();
								Teacher teacher = (Teacher) user;
								List<Question> questions = ConnectToDatabase.getQuestionsByTeacher(teacher);
								if(questions.isEmpty()){
									System.out.print("\nEMPTY QUESTION\n");
								}
								teacher.setTeacherQuestionsList(questions);// we add this to update exam list
								List<Exam> exams = ConnectToDatabase.getExamsByTeacher(teacher);
								if(exams.isEmpty()){
									System.out.print("\nEMPTY EXAMS\n");
								}
								teacher.setTeacherExamsList(exams);
								message.setTeacher(teacher);
							}
							// Now you have the User object, you can perform further actions
							System.out.println("Logged in user: " + user.getFirst() + " " + user.getLast() + "\n");
							message.setUser(user);
							message.setLogInFlag("Successfully");
							LogInEvent logInEvent = new LogInEvent(message);
							client.sendToClient(message);
						}
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
		} else if (msg instanceof QuestionMsg) {
			QuestionMsg message2 = (QuestionMsg) msg;
			if (message2.getRequest().equals("#AddQuestion")){
				System.out.print(message2.getQuestion().getIdNum());
				QuestionMsg msg1 = ConnectToDatabase.AddQuestion(message2.getQuestion(),
						message2.getTeacherWhoCreate());
				client.sendToClient(msg1);
			} //todo
			/*else if (message2.getRequest().equals("#UpdateQuestion")){
				ConnectToDatabase.updateQuestion(message2.getQuestion());
			}*/ //==> no need for this because edit question is just like add question
		} else if (msg instanceof MsgExamCreation) {
			MsgExamCreation message = (MsgExamCreation) msg;
			if(message.getRequest().equals("#NewExam")){
				Exam exam = ConnectToDatabase.addExam(message.getExam());
				MsgExamCreation msg1 = new MsgExamCreation("#ExamCreationDone", exam);
				client.sendToClient(msg1);
			}
		}else if(msg instanceof MsgUpdateExam){
			MsgUpdateExam message = (MsgUpdateExam) msg;
			Exam exam = ConnectToDatabase.addExam(message.getExam());
			MsgUpdateExam msg1 = new MsgUpdateExam("#ExamUpdatingDone", exam);
			client.sendToClient(msg1);
		} else if(msg instanceof ShareExamMsg){
			ShareExamMsg message =(ShareExamMsg) msg;
			Exam exam=ConnectToDatabase.ShareExam(message.getExamToShare(), message.getPasswordToSet());
			client.sendToClient(new ShareExamMsg("#ExamSharingDone",exam));
		} else if(msg instanceof MsgToLogOut){
			MsgToLogOut message =(MsgToLogOut) msg;
			System.out.print("\nLogging out: " + message.getUser());
			for(User user: OnlineUsers){
				if (user.getId().equals(message.getUser().getId())){
					OnlineUsers.remove(user);
					break;
				}
			}
		}else if(msg instanceof TakeExamMsg){
			TakeExamMsg message =(TakeExamMsg) msg;
			System.out.print("\nTry to execute exam!, password = " + message.getPasswordToSet() +
					"  student= " +  message.getStudent().getFullName() );

			String idNum = message.getExamIdNum();  // The id_num value of the Exam you want to load
			String password = message.getPasswordToSet();  // The password value of the Exam you want to load

			session.beginTransaction();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Exam> criteriaQuery = builder.createQuery(Exam.class);
			Root<Exam> root = criteriaQuery.from(Exam.class);

			criteriaQuery.select(root)
					.where(builder.equal(root.get("id_num"), idNum),
							builder.equal(root.get("password"), password));

			Query<Exam> query = session.createQuery(criteriaQuery);

			Exam exam = query.uniqueResult();
			if (exam != null) {
				// The exam with the provided id_num and password is found
				// You can work with the exam object here
				System.out.print("  --  from server - exam found. id: "+exam.getId_num());
				Student student = message.getStudent();
				CriteriaBuilder builder1 = session.getCriteriaBuilder();
				CriteriaQuery<Long> criteriaQuery1 = builder1.createQuery(Long.class);
				Root<ExamSubmittion> root1 = criteriaQuery1.from(ExamSubmittion.class);

				criteriaQuery1.select(builder1.count(root1));
				criteriaQuery1.where(
						builder1.equal(root1.get("exam"), exam),
						builder1.equal(root1.get("student"), student)
				);

				Long count = session.createQuery(criteriaQuery1).getSingleResult();

				if (count > 0) {
					System.out.println("Yes, there is a saved ExamSubmission with the given exam and student.");
					System.out.print("\nStudent already took the exam!\n");
					client.sendToClient(new TakeExamMsg("#StudentAlreadyTookExam", exam));
				} else {
					System.out.println("No, there is no saved ExamSubmission with the given exam and student.");
					System.out.print("\nStudent didn't take the exam!\n");
					client.sendToClient(new TakeExamMsg("#ExamReturnedSuccessfully", exam));
				}
			} else {
				client.sendToClient(new TakeExamMsg("#ExamReturnedUnsuccessfully", null));
				// No exam with the provided id_num and password is found
				System.out.print(" exam not found  ");
			}
			session.getTransaction().commit();
		}else if(msg instanceof MsgExamSubmittion){
			MsgExamSubmittion message =(MsgExamSubmittion) msg;
			if(message.getRequest().equals("#ExamSubmitted")){
				ExamSubmittion exam = ConnectToDatabase.AddExamSubmittin(message.getExam());
				System.out.print("Exam saved");
				client.sendToClient(new MsgExamSubmittion("#ExamSubmittedSuccessfully", null));
			}
		}
	}
}
