package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Grade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class GradEditor {
    private StudentDetails studentDetails;

    @FXML
    private TextField gradeText;

    @FXML
    private Button updategrade;

    @FXML
    private Label labelWrite;
    private Grade grade;
    @FXML
    public void UpdateGrade(ActionEvent actionEvent) {
        int newgrade = Integer.parseInt(gradeText.getText());
        grade.updateGrade(newgrade);
        studentDetails.updateGradeInTable(grade);
        try {
            SimpleClient.getClient().sendToServer("#UpdateGrade="+Integer.toString(this.grade.getId()) + "," + newgrade);
        } catch (IOException e) {
            showAlert("Error", "Failed to Update Grade" + e.getMessage());
			e.printStackTrace();
        }

    }


    public void SetGrade(Grade grade){this.grade = grade;}

    private void showAlert(String title, String message) {
        // Display an alert dialog to the user
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setStudentDetails(StudentDetails studentDetails){this.studentDetails = studentDetails;}
}
