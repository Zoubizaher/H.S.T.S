package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CreateExamController implements Initializable{
    private Teacher teacher;


    @FXML
    private TableView<Question> questionTable;
    @FXML
    private TextField time_minutes;
    @FXML
    private ChoiceBox<Course> courseChoiceBox;
    private List<Question> questions = new ArrayList<>();
    public void updateLIST() {
        questionTable.refresh();
    }

    private List<Question> selectedQuestions = new ArrayList<>();
    /*@FXML
    void initialize(){
        EventBus.getDefault().register(this);
        //System.out.print("\n CHECK REF: "+ teacher.getFullName());

    }*/
    public void initializee() {
        List<Course> teacherCourses = teacher.getCourses();
        ObservableList<Course> courseList = FXCollections.observableArrayList(teacherCourses);
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
        TextFormatter<Integer> formatter = new TextFormatter<>(new IntegerStringConverter(), 0,
                c -> c.getControlNewText().matches("\\d*") ? c : null);
        time_minutes.setTextFormatter(formatter);
        ObservableList<Question> questionsForTeacher = FXCollections.observableArrayList();

        List<Question> questionList = teacher.getTeacherQuestionsList();
        if (!questionList.isEmpty()) {
            questionsForTeacher.addAll(questionList);
            questions.addAll(questionList);
        }

        TableColumn<Question, Integer> questionNumCol = new TableColumn<>("Question_num");
        TableColumn<Question, String> questionCol = new TableColumn<>("Question");
        TableColumn<Question, String> aCol = new TableColumn<>("A");
        TableColumn<Question, String> bCol = new TableColumn<>("B");
        TableColumn<Question, String> cCol = new TableColumn<>("C");
        TableColumn<Question, String> dCol = new TableColumn<>("D");
        TableColumn<Question, String> answerCol = new TableColumn<>("Answer");

        questionNumCol.setCellValueFactory(new PropertyValueFactory<>("IdNum"));
        questionCol.setCellValueFactory(new PropertyValueFactory<>("questionText"));
        aCol.setCellValueFactory(new PropertyValueFactory<>("answerA"));
        bCol.setCellValueFactory(new PropertyValueFactory<>("answerB"));
        cCol.setCellValueFactory(new PropertyValueFactory<>("answerC"));
        dCol.setCellValueFactory(new PropertyValueFactory<>("answerD"));
        answerCol.setCellValueFactory(new PropertyValueFactory<>("correctAnswer"));

        TableColumn<Question, Boolean> selectCol = new TableColumn<>("Select");
        selectCol.setCellValueFactory(new PropertyValueFactory<>("selected"));
        selectCol.setCellFactory(column -> new TableCell<Question, Boolean>() {
            private final Button selectButton = new Button();

            {
                selectButton.setOnAction(event -> {
                    Question question = getTableRow().getItem();
                    boolean selected = !question.isSelected();
                    if (selected) {
                        selectedQuestions.add(question);
                    } else {
                        selectedQuestions.remove(question);
                    }
                    question.setSelected(selected);
                    updateButtonState(selected);
                });
            }

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    Question question = getTableRow().getItem();
                    if (question != null) {
                        updateButtonState(question.isSelected());
                        setGraphic(selectButton);
                    } else {
                        setGraphic(null);
                    }
                }
            }

            private void updateButtonState(boolean selected) {
                if (selected) {
                    selectButton.setText("Deselect");
                } else {
                    selectButton.setText("Select");
                }
            }
        });

        TableColumn<Question, Integer> pointsColumn = new TableColumn<>("Points");
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        pointsColumn.setCellFactory(column -> {
            return new TableCell<Question, Integer>() {
                private TextField textField = new TextField();

                {
                    textField.textProperty().addListener((observable, oldValue, newValue) -> {
                        if (!newValue.matches("\\d*")) {
                            textField.setText(newValue.replaceAll("[^\\d]", ""));
                        }
                    });
                    textField.setOnAction(event -> {
                        int points = Integer.parseInt(textField.getText());
                        // Handle the points input as desired, e.g., store it in the Question object
                        Question question = getTableRow().getItem();
                        question.setPoints(points);
                    });

                    textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                        if (!isNowFocused) {
                            int points = Integer.parseInt(textField.getText());
                            // Handle the points input as desired, e.g., store it in the Question object
                            Question question = getTableRow().getItem();
                            question.setPoints(points);
                        }
                    });
                }

                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        setGraphic(textField);
                        if (isEditing()) {
                            textField.setText(getString());
                        } else {
                            textField.setText(String.valueOf(item));
                        }
                    }
                }

                private String getString() {
                    return getItem() == null ? "" : getItem().toString();
                }
            };
        });


        questionTable.getColumns().addAll(selectCol, pointsColumn);
        questionTable.getColumns().addAll(
                questionNumCol, questionCol, aCol, bCol, cCol, dCol, answerCol
        );

        questionTable.setItems(questionsForTeacher);
    }



    @FXML
    public void AddExam(ActionEvent actionEvent) throws IOException {
        Map<Question, Integer> question_grade = new HashMap<>();
        int sum=0;
        int points;
        boolean flag = true;
        for(Question question : selectedQuestions){
            System.out.print("Selected Questions: ");
            if(question.isSelected()){
                System.out.print(question.getQuestionText() + ",");
                points = question.getPoints();
                if(points == 0){
                    flag = false;
                }else{
                    sum += question.getPoints();
                }
            }
            System.out.print("\n");
        }
        if(flag){
            if(sum!=100){
                System.out.print("POINTS SUM IS NOT 100, ERROR!\n");
                flag = false;
            }else{
                System.out.print("Points are good!\n");
            }
        }else{
            System.out.print("Selected Questions with points of 0!\n");
        }
        if(flag){
            int time = Integer.parseInt(time_minutes.getText());
            if(time > 0){
                System.out.print("Time of exam = " + time + "\n");
                Course selectedCourse = courseChoiceBox.getValue();
                if (selectedCourse != null) {
                    // Handle the selected course as needed
                    System.out.println("Selected Course: " + selectedCourse.getCourse_name());
                    //Create the exam and send it to the server.
                    Exam exam = new Exam(teacher,selectedCourse,questions, time);
                    MsgExamCreation msg = new MsgExamCreation("#NewExam", exam);
                    SimpleClient.getClient().sendToServer(msg);
                } else {
                    System.out.println("No course selected.");
                }
            }else{
                System.out.print("Time is not set correctly");
            }
        }
    }
    @Subscribe
    public void onReceivingExam(CreateExamEvent message){
        System.out.print("\nGot the exam, the exam id = " + message.getMessage().getExam().getId_num());
    }
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EventBus.getDefault().register(this);
    }
}
