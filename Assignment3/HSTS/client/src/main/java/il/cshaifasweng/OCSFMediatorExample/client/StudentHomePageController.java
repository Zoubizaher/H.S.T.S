package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StudentHomePageController {
    private User user;

    public void showDetails(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentDetails.fxml"));
        try {
            AnchorPane newScene = loader.load();
            Stage currentStage = new Stage();
            Scene scene = new Scene(newScene);  // Set the loaded AnchorPane as the root of the scene
            currentStage.setTitle(user.getFullName() + " Details");
            currentStage.setScene(scene);
            StudentDetails controller = loader.getController();
            controller.setUser(user);
            controller.initializee();
            currentStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showStats(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentStats.fxml"));
        try {
            AnchorPane newScene = loader.load();
            Stage currentStage = App.getStage();
            Scene scene = new Scene(newScene);  // Set the loaded AnchorPane as the root of the scene
            currentStage.setTitle(user.getFullName() + " Stats");
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showGrades(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TakeExamStudent.fxml"));
        try {
            AnchorPane newScene = loader.load();
            Stage currentStage = App.getStage();
            Scene scene = new Scene(newScene);  // Set the loaded AnchorPane as the root of the scene
            currentStage.setTitle(user.getFullName() + " Take Exam");
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setUser(User user){this.user=user;}
}
