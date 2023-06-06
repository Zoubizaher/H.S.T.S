package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.MsgToLogIn;
import il.cshaifasweng.OCSFMediatorExample.entities.QuestionMsg;

public class ReceivingQuestionEvent {
    private QuestionMsg message;

    public  QuestionMsg  getMessage() {
        return message;
    }

    public ReceivingQuestionEvent(QuestionMsg message) {
        this.message = message;
    }
}
