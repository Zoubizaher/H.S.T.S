package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.User;


import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class TeacherHomePageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addQuestionButton;

    @FXML
    private Label welcomeLabel;

    private User user =new User();

    private Teacher teacher;
    public void setUser(User user){this.user=user;}

    public void setTeacher(Teacher teacher){this.teacher=teacher;}

    /*the standard fxml initialize made a bug for me - i couldnt send the user
    * */
    void initializee() {
        EventBus.getDefault().register(this);
        welcomeLabel.setText("Welcome "+ user.getFullName());
    }
        // both three functions below ensure for us that teacher is updated when we still online
    @Subscribe
    public void onReceivingQuestionEvent(ReceivingQuestionEvent message){
        setUser(message.getMessage().getTeacherWhoCreate());
    }
    @Subscribe
    public void onReceivingExam(CreateExamEvent message){
        setTeacher(message.getMessage().getExam().getTeacher());
    }
    @Subscribe
    public void onReceivingExamUpdate(UpdateExamEvent message){
        setTeacher(message.getMessage().getExam().getTeacher());
    }

    @Subscribe
    public void onExamErrorEvent(ExamErrorMsgEvent event) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            Platform.runLater(() -> { // there is a possible that event can sent by another thread, here we ensure it sent by javafx thrad
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        String.format("Message: \nData: %s\nTimestamp: %s\n",
                                event.getErrorMsg(),
                                event.getTimeStamp().format(dtf))
                );
                alert.setTitle("Alert!");
                alert.setHeaderText("Message:");
                alert.show();
            }); }



    public void AddQuestion (ActionEvent actionEvent ){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddQuestion.fxml"));
            AnchorPane newScene = loader.load();
            Scene scene = new Scene(newScene);
            AddQuestionController controller = loader.getController();
            for(Question question : teacher.getTeacherQuestionsList()){
                System.out.print(question.getQuestionText());
            }
            controller.setTeacher(teacher);
            controller.initializee();
            Stage currentStage = new Stage();
            currentStage.setTitle("Add Question SharedPlatform");
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void create_exam(ActionEvent actionEvent) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateExam.fxml"));
            AnchorPane newScene = loader.load();
            Scene scene = new Scene(newScene);
            CreateExamController controller = loader.getController();
            controller.setTeacher(teacher);
            controller.initializee();
            Stage currentStage = new Stage();
            currentStage.setTitle("Create a new exam");
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void Show_Exams(ActionEvent actionEvent) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowExams.fxml"));
            AnchorPane newScene = loader.load();
            Scene scene = new Scene(newScene);
            ShowExamsController controller = loader.getController();
            controller.setTeacher(teacher);
            Stage currentStage = new Stage();
            currentStage.setTitle("Created Exams");
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}






















