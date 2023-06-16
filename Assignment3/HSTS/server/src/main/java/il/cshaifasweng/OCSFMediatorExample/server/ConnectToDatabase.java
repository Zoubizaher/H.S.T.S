package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ConnectToDatabase {
    private static Session session;

    private static List<Student> students;
    private static List<Teacher> teachers;

    private static List<User> users;
    private static SessionFactory getSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();
        // Add ALL of your entities here. You can also try adding a whole package.
        configuration.addAnnotatedClass(Grade.class);
        configuration.addAnnotatedClass(Student.class);
        configuration.addAnnotatedClass(Course.class);
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Teacher.class);
        configuration.addAnnotatedClass(Manager.class);
        configuration.addAnnotatedClass(Exam.class);
        configuration.addAnnotatedClass(Question.class);
        configuration.addAnnotatedClass(ExamSubmittion.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static List<Course> getAllCourses() throws Exception {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Course> query = builder.createQuery(Course.class);
        query.from(Course.class);
        List<Course> data = session.createQuery(query).getResultList();
        return data;
    }

    private static List<Grade> getAllGrades() throws Exception {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Grade> query = builder.createQuery(Grade.class);
        query.from(Grade.class);
        List<Grade> data = session.createQuery(query).getResultList();
        return data;
    }

    public static List<Student> getAllStudents(){
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Student> query = builder.createQuery(Student.class);
        query.from(Student.class);
        List<Student> data = session.createQuery(query).getResultList();
        return data;
    }

    public static List<User> getAllUsers(){
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        query.from(User.class);
        List<User> data = session.createQuery(query).getResultList();
        if (data.isEmpty()) {
            return null;
        }
        return data;
    }

    public static List<Teacher> getAllTeachers(){
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Teacher> query = builder.createQuery(Teacher.class);
        query.from(Teacher.class);
        List<Teacher> data = session.createQuery(query).getResultList();
        return data;
    }

    public static List<Question> getQuestionsByTeacher(Teacher teacher) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Question> questionQuery = builder.createQuery(Question.class);
        Root<Question> questionRoot = questionQuery.from(Question.class);

        // Join the Teacher entity to filter by teacher_id
        Join<Question, Teacher> teacherJoin = questionRoot.join("teacher");

        questionQuery.select(questionRoot)
                .where(builder.equal(teacherJoin.get("id"), teacher.getId()));

        TypedQuery<Question> questionTypedQuery = session.createQuery(questionQuery);
        return questionTypedQuery.getResultList();
    }
    public static List<Exam> getExamsByTeacher(Teacher teacher) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Exam> ExamQuery = builder.createQuery(Exam.class);
        Root<Exam> ExamRoot = ExamQuery.from(Exam.class);

        // Join the Teacher entity to filter by teacher_id
        Join<Exam, Teacher> teacherJoin = ExamRoot.join("teacher");

        ExamQuery.select(ExamRoot)
                .where(builder.equal(teacherJoin.get("id"), teacher.getId()));

        TypedQuery<Exam> ExamTypedQuery = session.createQuery(ExamQuery);
        return ExamTypedQuery.getResultList();
    }
    public static void save_all(Session session) throws Exception {
        for(Student student : getAllStudents()){
            session.save(student);
            session.flush();
        }
        for(Course course : getAllCourses()){
            session.save(course);
            session.flush();
        }
    }

    public static Student CreateStudent(String id, String first, String last, String username, String role, String mail, String password){
        Student student = new Student(id,first,last,username,role,mail,password);
        session.save(student);
        session.flush();
        return student;
    }

    public static Teacher CreateTeacher(String id, String first, String last, String username, String role, String mail, String password){
        Teacher teacher = new Teacher(id,first,last,username,role,mail,password);
        session.save(teacher);
        session.flush();
        return teacher;
    }

    public static Manager CreateManager(String id, String first, String last, String username, String role, String mail, String password){
        Manager manager = new Manager(id,first,last,username,role,mail,password);
        session.save(manager);
        session.flush();
        return manager;
    }

    public static Course CreateCourse(String id, String name, Teacher teacher){
        Course course = new Course(id, name, teacher);
        session.save(course);
        session.flush();
        return course;
    }

    public static void set_courseGrade(Student student, Course course, int grade){
        Grade grade1 = new Grade(grade, student, course);
        session.save(grade1);
        session.save(student);
        session.save(course);
        session.flush();
    }

    public static void Add_Course(Student student, Course course){
        if (student != null && course != null) {
            student.add_course(course);
        } else {
            System.out.print("STUDENT IS NULL!!\n");
        }
        session.save(student);
        session.save(course);
        session.flush();
    }
    public static void CreateData() throws Exception {
        System.out.print("Data Creation Start");
        System.out.print("\n");

        String role = "student";
        Student s1 = CreateStudent("32411", "Faisal", "Omari","faisalo321", role, "faisal@gmail.com", "1231");
        Student s2 = CreateStudent("32412", "Mohammad", "Arrabi","mohammadar", role, "mohammadar@gmail.com", "4213");
        Student s3 = CreateStudent("32413", "Abbas", "Ismail","abbasosh", role, "abbasosh@gmail.com", "5432");
        Student s4 = CreateStudent("32414", "Zaher", "Zoabi","zozo", role, "zozozoabi@gmail.com", "6423");
        Student s5 = CreateStudent("32415", "Murad", "Murad","murdam", role, "murdam@gmail.com", "4213");
        Student s6 = CreateStudent("32416", "David", "Jonas","david_jo", role, "david_jo@gmail.com", "6525");
        Student s7 = CreateStudent("32417", "Sam", "Brize","sambrize", role, "sambrize21@gmail.com", "9945");
        Student s8 = CreateStudent("32418", "Walter", "White","wallte", role, "walteroo@gmail.com", "5242");
        Student s9 = CreateStudent("32419", "Lionel", "Jordan","leojo", role, "leojoff@gmail.com", "5534");
        Student s10 = CreateStudent("32420", "Chris", "Barry","chrisboy", role, "chrisboy@gmail.com", "4213");

        role = "teacher";
        Teacher t1 = CreateTeacher("24210", "Mohammad", "Omari","mohamom", role, "23mohamom@gmail.com", "8667");
        Teacher t2 = CreateTeacher("24211", "Dan", "Lior","dany", role, "34dany2@gmail.com", "7755");
        Teacher t3 = CreateTeacher("24212", "George", "Williams","georgewill", role, "43georgewill2@gmail.com", "6543");

        Manager manager = CreateManager("11111", "Ahmad", "Al-Shafi'i","ahmadmanager", "manager", "managerahmad@gmail.com", "2775");

        Course c1 = CreateCourse("2313-21", "Algorithms", t1);
        Course c2 = CreateCourse("3214-41", "Software Engineering", t3);
        Course c3 = CreateCourse("3288-53", "Operating Systems", t1);
        Course c4 = CreateCourse("4324-16", "Image processing", t2);
        Course c5 = CreateCourse("3211-29", "Computer Graphics", t3);

        Add_Course(s1, c1);
        set_courseGrade(s1, c1, 90);// i think we need to put all grades = null
        Add_Course(s1, c2);
        set_courseGrade(s1, c2, 78);
        Add_Course(s1, c5);
        set_courseGrade(s1, c5, 58);
        Add_Course(s2, c3);
        set_courseGrade(s2, c3, 74);
        Add_Course(s2, c2);
        set_courseGrade(s2, c2, 85);
        Add_Course(s2, c4);
        set_courseGrade(s2, c4, 78);
        Add_Course(s2, c1);
        set_courseGrade(s2, c1, 87);
        Add_Course(s3, c1);
        set_courseGrade(s3, c1, 58);
        Add_Course(s3, c3);
        set_courseGrade(s3, c3, 89);
        Add_Course(s4, c4);
        set_courseGrade(s4, c4, 85);
        Add_Course(s4, c5);
        set_courseGrade(s4, c5, 78);
        Add_Course(s4, c1);
        set_courseGrade(s4, c1, 85);
        Add_Course(s4, c2);
        set_courseGrade(s4, c2, 78);
        Add_Course(s5, c4);
        set_courseGrade(s5, c4, 98);
        Add_Course(s5, c5);
        set_courseGrade(s5, c5, 65);
        Add_Course(s5, c2);
        set_courseGrade(s5, c2, 98);
        Add_Course(s5, c3);
        set_courseGrade(s5, c3, 87);
        Add_Course(s6, c1);
        set_courseGrade(s6, c1, 78);
        Add_Course(s6, c2);
        set_courseGrade(s6, c2, 53);
        Add_Course(s6, c4);
        set_courseGrade(s6, c4, 68);
        Add_Course(s6, c3);
        set_courseGrade(s6, c3, 89);
        Add_Course(s7, c4);
        set_courseGrade(s7, c4, 85);
        Add_Course(s7, c5);
        set_courseGrade(s7, c5, 89);
        Add_Course(s7, c1);
        set_courseGrade(s7, c1, 65);
        Add_Course(s7, c2);
        set_courseGrade(s7, c2, 77);
        Add_Course(s8, c4);
        set_courseGrade(s8, c4, 35);
        Add_Course(s8, c5);
        set_courseGrade(s8, c5, 86);
        Add_Course(s8, c3);
        set_courseGrade(s8, c3, 98);
        Add_Course(s8, c2);
        set_courseGrade(s8, c2, 87);
        Add_Course(s8, c4);
        set_courseGrade(s8, c4, 46);
        Add_Course(s9, c5);
        set_courseGrade(s9, c5, 85);
        Add_Course(s9, c3);
        set_courseGrade(s9, c3, 86);
        Add_Course(s10, c1);
        set_courseGrade(s10, c1, 79);
        Add_Course(s10, c2);
        set_courseGrade(s10, c2, 78);
        System.out.print("Data Creation Finish");
    }

    public static List<String> Students_names(Session session) throws Exception {
        List<Student> Students = getAllStudents();
        List<String> names = new ArrayList<>();
        for (Student student:Students) {
            names.add(student.getStudentName());
        }
        return names;
    }

    public static Map<String, Integer> getStudentGrades(Student student){
        Map<String, Integer> grades = student.getGradesMap();
        return grades;
    }

    public static void updateGrade(int Grade_ID, int new_grade) throws Exception {
        List<Grade> grades = getAllGrades();
        for(Grade grade : grades){
            if (grade.getId() == Grade_ID){
                grade.updateGrade(new_grade);
                session.beginTransaction();
                session.save(grade);
                session.flush();
                session.getTransaction().commit();
                break;
            }
        }
    }
    public static QuestionMsg AddQuestion(Question question, Teacher teacher) throws Exception {
        System.out.print("\nADDING QUESTION\n");
        session.beginTransaction();
        Question QuestionToADD = new Question(question.getQuestionText(),question.getAnswers(),
                question.getCorrectAnswer(),question.getTeacher());
        session.save(QuestionToADD);
        session.flush();
        teacher.getTeacherQuestionsList().add(QuestionToADD);
        session.getTransaction().commit();
        QuestionMsg msg = new QuestionMsg("#ReturningQuestion",QuestionToADD,teacher);
        return msg;
    }
    public static Exam addExam(Exam exam) throws Exception {
        System.out.print("\nADDING Exam\n");
        session.beginTransaction();
        Exam ExamToADD = new Exam(exam.getTeacher(), exam.getCourse(), exam.getQuestions(), exam.getTime(),
                exam.getQuestionPoints(), exam.getDescription_Teacher(), exam.getDescription_Student());
        session.save(ExamToADD);
        session.flush();
        session.getTransaction().commit();
        return ExamToADD;
    }
    public static ExamSubmittion AddExamSubmittin(ExamSubmittion exam) {
        System.out.print("\nADDING Exam\n");
        session.beginTransaction();
        ExamSubmittion ExamSubmittionToADD = new ExamSubmittion(exam.getStudent(), exam.getExam(), exam.getAnswers());
        session.save(ExamSubmittionToADD);
        session.flush();
        session.getTransaction().commit();
        return ExamSubmittionToADD;
    }

    public static Exam ShareExam ( Exam exam, String Password ){
        session.beginTransaction();
        // Load or get the entity you want to update
        Exam ExamToShare = session.load(Exam.class,exam.getId_num() );
        if (ExamToShare != null) {
            // Modify the desired property
            ExamToShare.setPassword(Password);
            ExamToShare.setShared(true);
            // Commit the transaction to persist the changes
            session.save(ExamToShare);
            session.flush();
            session.getTransaction().commit();

            // Changes successfully saved
            System.out.println("Exam shared: " + exam.getId_num());
        } else {
            // Exam not found
            System.out.println("Exam not found. ERROR sharing exam ");
        }

        return ExamToShare;
    }

    public static List<Student> getStudents() {
        return students;
    }

    public static List<User> getUsers() {
        return users;
    }

    public static Session initializeDatabase() throws IOException
    {
        try {
            SessionFactory sessionFactory = getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
            users = getAllUsers();
            if(users == null){
                CreateData();
            }
            users = getAllUsers();
            students = getAllStudents();
            teachers = getAllTeachers();
            session.getTransaction().commit();
            return session;
        } catch (Exception exception) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            System.err.println("An error occured, changes have been rolled back.");
            exception.printStackTrace();
        }
        return null;
    }
    public static void EndConnection(){
        session.getTransaction().commit(); // Save everything.
        session.close();
    }

  /*  public static void updateQuestion(Question question) {
        session.beginTransaction();
        session.save(question);
        session.flush();
        session.getTransaction().commit();
    }*///==> so also no need for this
}
