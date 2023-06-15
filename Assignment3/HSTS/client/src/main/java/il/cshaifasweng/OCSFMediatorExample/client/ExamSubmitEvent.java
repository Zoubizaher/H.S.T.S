package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.MsgExamCreation;
import il.cshaifasweng.OCSFMediatorExample.entities.MsgExamSubmittion;

public class ExamSubmitEvent {
    private MsgExamSubmittion message;
    public ExamSubmitEvent(MsgExamSubmittion message) {
        this.message = message;
    }

    public MsgExamSubmittion getMessage() {
        return message;
    }
}
