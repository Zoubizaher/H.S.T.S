package il.cshaifasweng.OCSFMediatorExample.entities;
import java.io.Serializable;

public class MsgToLogIn implements Serializable {
    private static final long serialVersionUID = 1L;
    String request;
    String password;
    String username;

    public MsgToLogIn(String request, String password, String username) {

        this.request = request;
        this.password = password;
        this.username = username;
    }

    public MsgToLogIn(String request) {
        this.request = request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRequest() {
        return request;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
