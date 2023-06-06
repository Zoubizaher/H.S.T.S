package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
public class AddQuestionController{
    private Teacher teacher;
    @FXML
    private TextField QuestionText;

    @FXML
    private TextField A;

    @FXML
    private TextField B;

    @FXML
    private TextField C;

    @FXML
    private TextField D;

    @FXML
    private TextField AnswerChar;

    @FXML
    private VBox coursesVBox = new VBox();

    @FXML
    private TableView<Question> questionTable;

    private TableColumn<Question, Void> editCol;
    private List<Question> questions = new ArrayList<>();

    public void updateLIST() {
        questionTable.refresh();
    }
    @FXML
    void initialize(){
        EventBus.getDefault().register(this);
    }
    public void initializee() {
        System.out.print("INITIALIZING");
        TableColumn<Question, Integer> questionNumCol = new TableColumn<>("Question_num");
        TableColumn<Question, String> questionCol = new TableColumn<>("Question");
        TableColumn<Question, String> aCol = new TableColumn<>("A");
        TableColumn<Question, String> bCol = new TableColumn<>("B");
        TableColumn<Question, String> cCol = new TableColumn<>("C");
        TableColumn<Question, String> dCol = new TableColumn<>("D");
        TableColumn<Question, String> answerCol = new TableColumn<>("Answer");

        // Define property value factories for each column
        questionNumCol.setCellValueFactory(new PropertyValueFactory<>("IdNum"));
        questionCol.setCellValueFactory(new PropertyValueFactory<>("questionText"));
        aCol.setCellValueFactory(new PropertyValueFactory<>("answerA"));
        bCol.setCellValueFactory(new PropertyValueFactory<>("answerB"));
        cCol.setCellValueFactory(new PropertyValueFactory<>("answerC"));
        dCol.setCellValueFactory(new PropertyValueFactory<>("answerD"));
        answerCol.setCellValueFactory(new PropertyValueFactory<>("correctAnswer"));

        // Make the option columns editable as text fields
        aCol.setCellFactory(TextFieldTableCell.forTableColumn());
        bCol.setCellFactory(TextFieldTableCell.forTableColumn());
        cCol.setCellFactory(TextFieldTableCell.forTableColumn());
        dCol.setCellFactory(TextFieldTableCell.forTableColumn());

        // Add the columns to the table
        questionTable.getColumns().addAll(
                questionNumCol, questionCol, aCol, bCol, cCol, dCol, answerCol
        );
        for (Course course : teacher.getCourses()) {
            CheckBox checkBox = new CheckBox(course.getCourse_name());
            coursesVBox.getChildren().add(checkBox);
        }
        for (Course course : teacher.getCourses()){
            System.out.print("Course: " + course.getCourse_name());
            for(Question question : course.getQuestions()){
                System.out.print("\nQuestion: " + question.getQuestionText());
                for(Question question1 : questions){
                    if(question1.getQuestionText().equals(question.getQuestionText())){
                    }else{
                        questions.add(question);
                        questionTable.getItems().add(question);
                    }
                }
            }
        }
        // Create the edit column
        editCol = new TableColumn<>("Edit");

        // Set the cell factory to create custom cells with edit buttons
        editCol.setCellFactory(createEditButtonCellFactory());

        // Add the edit column to the table
        questionTable.getColumns().add(editCol);
    }
    private Callback<TableColumn<Question, Void>, TableCell<Question, Void>> createEditButtonCellFactory() {
        return new Callback<>() {
            @Override
            public TableCell<Question, Void> call(TableColumn<Question, Void> param) {
                return new TableCell<>() {
                    private final Button editButton = new Button("Edit");

                    {
                        // Handle button action
                        editButton.setOnAction((ActionEvent event) -> {
                            Question question = getTableRow().getItem();
                            // Handle the edit action for the clicked question
                            handleEditQuestion(question);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(editButton);
                        }
                    }
                };
            }
        };
    }
    private void handleEditQuestion(Question question) {
        Platform.runLater(() -> {
            System.out.print("Question ID0: " + question.getIdNum());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditQuestion.fxml"));
            try {
                AnchorPane newScene = loader.load();
                Stage currentStage = new Stage();
                Scene scene = new Scene(newScene);  // Set the loaded AnchorPane as the root of the scene
                currentStage.setTitle("Edit Question");
                currentStage.setScene(scene);
                EditQuestionController controller = loader.getController();
                controller.setQuestion(question);
                controller.setPreviousLoader(this);
                controller.initializee();
                currentStage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public void AddQuestion(ActionEvent actionEvent) throws IOException {
        ObservableList<CheckBox> selectedCheckboxes = FXCollections.observableArrayList();
        String Questiontxt;
        List<Course> choosenCourses = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        String correctAnswer;
        for (Node node : coursesVBox.getChildren()) {
            if (node instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) node;
                if (checkBox.isSelected()) {
                    selectedCheckboxes.add(checkBox);
                }
            }
        }
        answers.add(A.getText());
        answers.add(B.getText());
        answers.add(C.getText());
        answers.add(D.getText());
        Questiontxt = QuestionText.getText();
        correctAnswer = AnswerChar.getText();
        Question question = new Question(Questiontxt, answers, correctAnswer);
        for (CheckBox checkBox : selectedCheckboxes) {
            String courseName = checkBox.getText();
            for(Course course : teacher.getCourses()){
                if(course.getCourse_name().equals(courseName)){
                    choosenCourses.add(course);
                    course.addQuestion(question);
                    question.AddCourse(course);
                }
            }
        }
        QuestionMsg msg1 = new QuestionMsg("#AddQuestion", question, choosenCourses);
        SimpleClient.getClient().sendToServer(msg1);
    }
    @Subscribe
    public void onReceivingQuestionEvent(ReceivingQuestionEvent message){
        System.out.print("message returning question");
        A.setText("");
        B.setText("");
        C.setText("");
        D.setText("");
        QuestionText.setText("");
        AnswerChar.setText("");
        Question q = message.getMessage().getQuestion();
        System.out.print(q.getQuestionText());
        questionTable.getItems().add(q);
        questionTable.refresh();
    }
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
