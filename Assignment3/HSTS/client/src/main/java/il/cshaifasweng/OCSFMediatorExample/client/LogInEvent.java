package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.MsgToLogIn;
import org.greenrobot.eventbus.Subscribe;
public class LogInEvent {

    private MsgToLogIn message;

    public  MsgToLogIn  getMessage() {
        return message;
    }

    public LogInEvent(MsgToLogIn message) {
        this.message = message;
    }
}