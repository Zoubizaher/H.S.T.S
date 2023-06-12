package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.MsgUpdateExam;

public class UpdateExamEvent {
    private MsgUpdateExam message;

    public MsgUpdateExam getMessage() {
        return message;
    }

    public UpdateExamEvent(MsgUpdateExam message) {
        this.message = message;
    }
}
