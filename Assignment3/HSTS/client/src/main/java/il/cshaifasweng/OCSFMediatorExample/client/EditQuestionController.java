package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

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
        question.setQuestionText(textQ.getText());
        question.setAnswerA(optionA.getText());
        question.setAnswerB(optionB.getText());
        question.setAnswerC(optionC.getText());
        question.setAnswerD(optionD.getText());
        question.setCorrectAnswer(optionCorrect.getText());
        QuestionMsg msg = new QuestionMsg("#UpdateQuestion", question);
        SimpleClient.getClient().sendToServer(msg);
        addQuestionController.updateLIST();
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
