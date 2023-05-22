package il.cshaifasweng.OCSFMediatorExample.client;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import il.cshaifasweng.OCSFMediatorExample.entities.Grade;
import il.cshaifasweng.OCSFMediatorExample.entities.Student;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class StudentDetails implements Initializable {

    private Student student;

    @FXML
    private Label studentNameLabel;

    @FXML
    private TableView<Grade> studentTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialization code
    }

    private void initializeTable() {
        // Set up the table columns
        TableColumn<Grade, Integer> courseIdColumn = new TableColumn<>("Grade ID");
        courseIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());

        TableColumn<Grade, String> courseNameColumn = new TableColumn<>("Course Name");
        courseNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCourse_name()));

        TableColumn<Grade, Integer> gradeColumn = new TableColumn<>("Grade");
        gradeColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getGrade()).asObject());

        TableColumn<Grade, Void> showColumn = new TableColumn<>("Edit");
        showColumn.setCellFactory(param -> new TableCell<>() {
            private final Button showButton = new Button("Edit");

            {
                showButton.setOnAction(event -> {
                    Grade selectedGrade = getTableRow().getItem();
                    if (selectedGrade != null) {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("GradEditor.fxml"));
                            Parent root = loader.load();
                            GradEditor nextController = loader.getController();
                            nextController.SetGrade(selectedGrade);
                            nextController.setStudentDetails(StudentDetails.this);
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.setTitle(selectedGrade.getCourse_name() + " Grade Editor");
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(showButton);
                }
            }
        });
        studentTable.getColumns().add(showColumn);
        studentTable.getColumns().addAll(courseIdColumn, courseNameColumn, gradeColumn);
    }

    private void populateTable() {
        List<Grade> grades = student.getGrades();

        studentTable.getItems().addAll(grades);
    }

    public void setStudent(Student student) {
        this.student = student;
        this.studentNameLabel.setText(student.getStudentName());
        initializeTable();
        populateTable();
    }
    public void updateGradeInTable(Grade updatedGrade) {
        // Find the grade in the table and update its values
        for (Grade grade : studentTable.getItems()) {
            if (grade.getId() == updatedGrade.getId()) {
                grade = updatedGrade;
                studentTable.refresh();
                break;
            }
        }
    }

}
