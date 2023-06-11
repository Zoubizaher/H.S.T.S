package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditQuestionController {
    AddQuestionController addQuestionController;
    private Question question;
    @FXML
    private TextField textQ;

    @FXML
    private TextField optionA;

    @FXML
    private TextField optionB;

    @FXML
    private TextField optionC;

    @FXML
    private TextField optionD;

    @FXML
    private TextField optionCorrect;
    public void UpdateQuestion(ActionEvent actionEvent) throws IOException {
        String QuestionTxt;
        List<String> answers = new ArrayList<>();
        String correctAnswer;
        QuestionTxt= textQ.getText();
        answers.add(optionA.getText());
        answers.add(optionB.getText());
        answers.add(optionC.getText());
        answers.add(optionD.getText());
        correctAnswer=optionCorrect.getText();
        Question questionToEdit = new Question (QuestionTxt,answers,correctAnswer,addQuestionController.getTeacher());
        QuestionMsg msg = new QuestionMsg("#AddQuestion", questionToEdit,addQuestionController.getTeacher());
        SimpleClient.getClient().sendToServer(msg);
        addQuestionController.updateLIST();
        // now wee need to close the scene
        Node sourceNode = (Node) actionEvent.getSource();
        Stage currentStage = (Stage) sourceNode.getScene().getWindow();
        currentStage.close();

    }

    public void setQuestion(Question question){this.question = question;}
    public void initializee(){
        textQ.setText(question.getQuestionText());
        optionA.setText(question.getAnswerA());
        optionB.setText(question.getAnswerB());
        optionC.setText(question.getAnswerC());
        optionD.setText(question.getAnswerD());
        optionCorrect.setText(question.getCorrectAnswer());
    }

    public void setPreviousLoader(AddQuestionController addQuestionController){this.addQuestionController =addQuestionController;}
}
