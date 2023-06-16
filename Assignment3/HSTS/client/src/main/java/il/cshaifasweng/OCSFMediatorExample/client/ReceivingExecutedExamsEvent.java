package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.MsgBringExecutedExams;
import il.cshaifasweng.OCSFMediatorExample.entities.QuestionMsg;

public class ReceivingExecutedExamsEvent {
    private MsgBringExecutedExams message;

    public  MsgBringExecutedExams  getMessage() {
        return message;
    }

    public ReceivingExecutedExamsEvent(MsgBringExecutedExams message) {
        this.message = message;
    }
}
