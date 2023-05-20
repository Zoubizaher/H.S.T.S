package il.cshaifasweng.OCSFMediatorExample.client;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import il.cshaifasweng.OCSFMediatorExample.client.Course;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
        courseIdColumn.setCellValueFactory(new PropertyValueFactory<>("")); // Empty value factory

        TableColumn<Grade, String> courseNameColumn = new TableColumn<>("Course Name");
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("")); // Empty value factory

        TableColumn<Grade, Integer> gradeColumn = new TableColumn<>("Grade");
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("")); // Empty value factory

        // Set cell value factories manually
        courseIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        courseNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCourse_name()));
        gradeColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getGrade()).asObject());

        studentTable.getColumns().addAll(courseIdColumn, courseNameColumn, gradeColumn);
    }


    private void populateTable() {
        System.out.print("Populating");
        List<Course> courses = student.getCourses();
        List<Grade> grades = student.getGrades();
        for(Course c : courses){
            System.out.print(c.getCourse_name());
        }
        System.out.print("\nPRINTING GRADES\n");
        for (Grade grade : grades){
            System.out.print(grade.getGrade());
            studentTable.getItems().add(grade);
        }
    }

    public void setStudent(Student student) {
        System.out.println("Adding student to class");
        this.student = student;
        this.studentNameLabel.setText(student.getStudentName());
        initializeTable();
        populateTable();
    }
}
