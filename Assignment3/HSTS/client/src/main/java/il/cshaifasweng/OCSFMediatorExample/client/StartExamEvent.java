package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ShareExamMsg;
import il.cshaifasweng.OCSFMediatorExample.entities.TakeExamMsg;

public class StartExamEvent {
    private TakeExamMsg takeExamMsg;
    public StartExamEvent(TakeExamMsg takeExamMsg){
        this.takeExamMsg = takeExamMsg;
    }
    public TakeExamMsg getTakeExamMsg() {
        return takeExamMsg;
    }

    public void setTakeExamMsg(TakeExamMsg takeExamMsg) {
        this.takeExamMsg = takeExamMsg;
    }
}
