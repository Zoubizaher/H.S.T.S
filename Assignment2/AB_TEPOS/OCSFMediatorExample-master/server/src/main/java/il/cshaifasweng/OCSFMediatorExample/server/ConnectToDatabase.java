package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.Grade;
import il.cshaifasweng.OCSFMediatorExample.entities.Student;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConnectToDatabase {
    private static Session session;

    private static List<Student> students;
    private static SessionFactory getSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();
        // Add ALL of your entities here. You can also try adding a whole package.
        configuration.addAnnotatedClass(Grade.class);
        configuration.addAnnotatedClass(Student.class);
        configuration.addAnnotatedClass(Course.class);
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

    public static Student CreateStudent(String id, String first, String last){
        Student student = new Student(id,first,last);
        session.save(student);
        session.flush();
        return student;
    }
    public static Course CreateCourse(String id, String name){
        Course course = new Course(id, name);
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
        student.add_course(course);
        session.save(student);
        session.save(course);
        session.flush();
    }
    public static void CreateData() throws Exception {
        System.out.print("Data Creation Start");
        System.out.print("\n");
        Student s1 = CreateStudent("1", "Faisal", "Omari");
        Student s2 = CreateStudent("2", "Mohammad", "Arrabi");
        Student s3 = CreateStudent("3", "Abbas", "Ismail");
        Student s4 = CreateStudent("4", "Zaher", "Zoabi");
        Student s5 = CreateStudent("5", "Murad", "Murad");
        Student s6 = CreateStudent("6", "David", "Jonas");
        Student s7 = CreateStudent("7", "Sam", "Brize");
        Student s8 = CreateStudent("8", "Walter", "White");
        Student s9 = CreateStudent("9", "Lionel", "Jordan");
        Student s10 = CreateStudent("10", "Chris", "Barry");
        Course c1 = CreateCourse("2313-21", "Algorithms");
        Course c2 = CreateCourse("3214-41", "Software Engineering");
        Course c3 = CreateCourse("3288-53", "Operating Systems");
        Course c4 = CreateCourse("4324-16", "Image processing");
        Course c5 = CreateCourse("3211-29", "Computer Graphics");
        Add_Course(s1, c1);
        set_courseGrade(s1, c1, 90);
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

    public static List<Student> getStudents() {
        return students;
    }

    public static Session initializeDatabase() throws IOException
    {
        try {
            SessionFactory sessionFactory = getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
           // CreateData();
            students = getAllStudents();
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
}
