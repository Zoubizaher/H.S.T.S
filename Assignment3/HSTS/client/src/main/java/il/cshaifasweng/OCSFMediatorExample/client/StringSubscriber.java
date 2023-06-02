package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Student;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class StringSubscriber {
    private String receivedMSG;
    @Subscribe
    public void handleMSG(String msg) {
        // Handle the received student list
        this.receivedMSG = msg;
    }
    public StringSubscriber() {
        this.receivedMSG = "null";
    }
    public String getReceivedMSG(){return this.receivedMSG;}
}
