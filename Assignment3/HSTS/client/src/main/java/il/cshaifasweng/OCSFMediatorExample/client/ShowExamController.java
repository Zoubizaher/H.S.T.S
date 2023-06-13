package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.Teacher;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ShowExamController implements Initializable{
    private Teacher teacher;
    private Exam exam;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField TeacherDescription;

    @FXML
    private TableView<Question> QuestionsTable;
    public void updateLIST() {
        QuestionsTable.refresh();
    }

    @Subscribe
    public void onReceivingExam(CreateExamEvent message){

    }
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
        List<Question> ExamQuestions = exam.getQuestions();
        QuestionsTable.setItems(FXCollections.observableArrayList(ExamQuestions));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<Question, Integer> questionNumCol = new TableColumn<>("Question_num");
        TableColumn<Question, String> questionCol = new TableColumn<>("Question");
        TableColumn<Question, String> aCol = new TableColumn<>("A");//manual set-> the header label is set to "A",
        TableColumn<Question, String> bCol = new TableColumn<>("B");
        TableColumn<Question, String> cCol = new TableColumn<>("C");
        TableColumn<Question, String> dCol = new TableColumn<>("D");
        TableColumn<Question, String> answerCol = new TableColumn<>("Answer");
        TableColumn<Question, Integer> pointsCol = new TableColumn<>("Points");


        // Define property value factories for each column
        questionNumCol.setCellValueFactory(new PropertyValueFactory<>("IdNum"));
        questionCol.setCellValueFactory(new PropertyValueFactory<>("questionText"));
        aCol.setCellValueFactory(new PropertyValueFactory<>("answerA"));
        bCol.setCellValueFactory(new PropertyValueFactory<>("answerB"));
        cCol.setCellValueFactory(new PropertyValueFactory<>("answerC"));
        dCol.setCellValueFactory(new PropertyValueFactory<>("answerD"));
        answerCol.setCellValueFactory(new PropertyValueFactory<>("correctAnswer"));
        pointsCol.setCellValueFactory(cellData -> {
            Question question = cellData.getValue();
            Map<Question, Integer> questionPointsMap = exam.getQuestionPoints();
            Integer points = questionPointsMap.get(question);
            TeacherDescription.setText(exam.getDescription_Teacher());
            return new SimpleObjectProperty<>(points);
        });

        QuestionsTable.getColumns().addAll(
                questionNumCol, questionCol, aCol, bCol, cCol, dCol, answerCol, pointsCol
        );
        EventBus.getDefault().register(this);
    }



    public void AutoExam(ActionEvent actionEvent) {

        // todo tomorrow start from here


    }
}
