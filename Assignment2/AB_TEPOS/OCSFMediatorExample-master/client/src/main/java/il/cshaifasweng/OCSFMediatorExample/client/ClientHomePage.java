package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

public class ClientHomePage {
    private EventBus eventBus;
    private StudentListSubscriber subscriber;

    @FXML
    public void showStList(ActionEvent actionEvent) {
        try {
            // Register the StudentListSubscriber as a subscriber
            StudentListSubscriber subscriber = new StudentListSubscriber();
            EventBus.getDefault().register(subscriber);
            SimpleClient.getClient().sendToServer("#GetStudents");


            // Wait for the subscriber to receive the student list
            Thread.sleep(500); // Add a delay to ensure the subscriber receives the list (adjust the duration as needed)

            // Get the student list from the subscriber
            List<Student> studentList = subscriber.getStudentList();
            EventBus.getDefault().unregister(subscriber);
            LoadFXML("StudentsListTable.fxml", studentList);

        } catch (IOException e) {
            showAlert("Error", "Failed to Get Data!" + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }




    void LoadFXML(String path, List<Student> studentList) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            AnchorPane newScene = loader.load();
            StudentsListTable controller = loader.getController();
            controller.setStudentList(studentList);

            Stage currentStage = App.getStage();
            Scene scene = new Scene(newScene);
            currentStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showAlert(String title, String message) {
        // Display an alert dialog to the user
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
