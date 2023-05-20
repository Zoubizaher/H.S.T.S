package il.cshaifasweng.OCSFMediatorExample.client;


import org.greenrobot.eventbus.Subscribe;
import java.util.List;

public class StudentListSubscriber {
    private List<Student> studentList;
    @Subscribe
    public void handleStudentList(List<Student> studentList) {
        // Handle the received student list
        this.studentList = studentList;
        System.out.println("\nLoading the StudentList");
        for (Student student : studentList) {
            System.out.println(student.getStudentName());
            List<Course> courses = student.getCourses();
            for (Course course : courses){
                System.out.println(course.getCourse_name());
            }
            // ... Handle other student properties
        }
    }


    public StudentListSubscriber(List<Student> studentList) {
        this.studentList = studentList;
    }
    public StudentListSubscriber() {
    }

    public List<Student> getStudentList(){return this.studentList;}
}

