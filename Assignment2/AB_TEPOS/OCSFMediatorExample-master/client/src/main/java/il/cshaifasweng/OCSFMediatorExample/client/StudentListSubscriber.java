package il.cshaifasweng.OCSFMediatorExample.client;


import org.greenrobot.eventbus.Subscribe;
import java.util.List;

public class StudentListSubscriber {
    private List<Student> studentList;
    @Subscribe
    public void handleStudentList(List<Student> studentList) {
        // Handle the received student list
        this.studentList = studentList;
    }


    public StudentListSubscriber(List<Student> studentList) {
        this.studentList = studentList;
    }
    public StudentListSubscriber() {
    }

    public List<Student> getStudentList(){return this.studentList;}
}

