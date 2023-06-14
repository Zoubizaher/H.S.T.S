package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.TakeExamMsg;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StudentTakeExamController implements Initializable {
    private Student student;
    private Exam exam;

    @FXML
    private TextField DigitsText;

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public void setStudent(Student student) {
        this.student = student;
        List<Course> studentCourses = student.getCourses();
        ObservableList<Course> courseList = FXCollections.observableArrayList(studentCourses);
        courseChoiceBox.setItems(courseList);
        courseChoiceBox.setConverter(new StringConverter<Course>() {
            @Override
            public String toString(Course course) {
                if (course == null) {
                    return "";
                }
                return course.getCourse_name();
            }

            @Override
            public Course fromString(String string) {
                // Not used in this case
                return null;
            }
        });
    }

    public Exam getExam() {
        return exam;
    }

    public Student getStudent() {
        return student;
    }
    @FXML
    private ChoiceBox<Course> courseChoiceBox;
    public void TakeExam(ActionEvent actionEvent) {
        String password = DigitsText.getText();
        if(password.length()!=4){
            Platform.runLater(() -> { // there is a possible that event can sent by another thread, here we ensure it sent by javafx thrad
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        String.format("Message: \nData: %s",
                                "Password length error!"));
                alert.setTitle("Alert!");
                alert.setHeaderText("Message:");
                alert.show();
            });
        }else{
            Course selectedCourse = courseChoiceBox.getValue();
            Platform.runLater(() -> { // there is a possible that event can sent by another thread, here we ensure it sent by javafx thrad
                TakeExamMsg msg = new TakeExamMsg("TakeExamRequest", password, selectedCourse, student);
                try {
                    SimpleClient.getClient().sendToServer(msg);
                } catch (IOException e) {
                    System.out.print("cannot send msg to server from StudentTakeExamController");
                    throw new RuntimeException(e);
                }
            });
        }
    }
    @Subscribe
    public void onShareExamEvent(ShareExamEvent shareExamEvent){

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EventBus.getDefault().register(this);
    }
}
