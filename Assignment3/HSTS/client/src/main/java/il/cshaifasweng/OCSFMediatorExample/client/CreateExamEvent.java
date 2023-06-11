package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.MsgExamCreation;

public class CreateExamEvent {
    private MsgExamCreation message;

    public MsgExamCreation getMessage() {
        return message;
    }

    public CreateExamEvent(MsgExamCreation message) {
        this.message = message;
    }
}
