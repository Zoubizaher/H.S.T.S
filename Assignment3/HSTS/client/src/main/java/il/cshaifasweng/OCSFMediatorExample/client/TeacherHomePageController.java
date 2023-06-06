package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.User;


import java.net.URL;
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


    public void setUser(User user){this.user=user;}



    /*the standard fxml initialize made a bug for me - i couldnt send the user
    * */
    void initializee() {
        //EventBus.getDefault().register(this);

        welcomeLabel.setText("Welcome "+ user.getFullName());
}


public void AddQuestion (ActionEvent actionEvent ){ // i chose to load another stage
    Platform.runLater(() -> {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddQuestion.fxml"));
        try {
            AnchorPane newScene = loader.load();
            Stage currentStage = new Stage();
            Scene scene = new Scene(newScene);  // Set the loaded AnchorPane as the root of the scene
            currentStage.setTitle("Add Question SharedPlatform");
            currentStage.setScene(scene);
            AddQuestionController controller = loader.getController();
            //controller.setUser(user);
           // controller.initializee();
            currentStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } });
    }


}






















