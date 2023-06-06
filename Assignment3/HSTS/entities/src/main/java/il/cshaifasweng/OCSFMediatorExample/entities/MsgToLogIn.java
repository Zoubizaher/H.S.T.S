package il.cshaifasweng.OCSFMediatorExample.entities;
import java.io.Serializable;

public class MsgToLogIn implements Serializable {
    private static final long serialVersionUID = 1L;
    String request;
    String password;
    String username;

    String LogInFlag;
    private User user = null;

    private Question question;

    private int Question_num;

    public MsgToLogIn(String request, String password, String username) {
        this.request = request;
        this.password = password;
        this.username = username;
    }

    public MsgToLogIn(String request) {
        this.request = request;
    }

    public MsgToLogIn(String hashtag, Question question) {
        this.request = hashtag;
        this.question = question;
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

    public void setUser(User user){
        this.user = user;
    }
    public void setLogInFlag(String bool){
        this.LogInFlag = bool;
    }
    public String getLogInFlag(){return this.LogInFlag;}
    public User getUser(){return this.user;}
    public String getRequest() {
        return request;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public Question getQuestion(){return this.question;}

    public void setQuestion_num(int question_num) {
        Question_num = question_num;
    }
    public int getQuestion_num(){
        return this.Question_num;
    }
}
