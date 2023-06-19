package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.MsgExamSubmittion;
import il.cshaifasweng.OCSFMediatorExample.entities.MsgGetGrades;

public class GradesRecievedEvent {
    private MsgGetGrades message;
    public GradesRecievedEvent(MsgGetGrades message) {
        this.message = message;
    }

    public MsgGetGrades getMessage() {
        return message;
    }
}
