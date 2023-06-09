package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.Teacher;
import il.cshaifasweng.OCSFMediatorExample.entities.User;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
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

    @Subscribe
    public void onReceivingQuestionEvent(ReceivingQuestionEvent message){
        setUser(message.getMessage().getTeacherWhoCreate());
    }


    public void AddQuestion (ActionEvent actionEvent ){ // i chose to load another stage
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddQuestion.fxml"));
            AnchorPane newScene = loader.load();
          //  Stage currentStage = new Stage();
            Scene scene = new Scene(newScene);  // Set the loaded AnchorPane as the root of the scene
            AddQuestionController controller = loader.getController();
//            Teacher teacher = (Teacher) user;
            for(Question question : teacher.getTeacherQuestionsList()){
                System.out.print(question.getQuestionText());
            }
          /*  List<Question> questionList=teacher.getTeacherQuestionsList();
                for(Question question : questionList){
                    if(questionList.isEmpty())
                    { System.out.print("\nSystem check Q.list is empty : "); }
                      else
                    {System.out.print("\nSystem check for Q.list: " + question.getQuestionText() + "\n");}
                }*/

            controller.setTeacher(teacher);
            controller.initializee();
            Stage currentStage = new Stage();
            currentStage.setTitle("Add Question SharedPlatform");
            currentStage.setScene(scene);//*************************
            currentStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}






















