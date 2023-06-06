package il.cshaifasweng.OCSFMediatorExample.entities;
import java.io.Serializable;
import java.util.List;

public class QuestionMsg implements Serializable{
    private static final long serialVersionUID = 1L;
    String request;
    private Question question;

    private List<Course> courses_of_question;

    private int Question_num;

    public QuestionMsg(String hashtag, Question question, List<Course> courses) {
        this.request = hashtag;
        this.question = question;
        this.courses_of_question = courses;
    }
    public QuestionMsg(String hashtag, Question question) {
        this.request = hashtag;
        this.question = question;
    }
    public List<Course> getCourses_of_question(){return this.courses_of_question;}
    public void setQuestion_num(int question_num) {
        Question_num = question_num;
    }
    public int getQuestion_num(){
        return this.Question_num;
    }

    public Question getQuestion(){return this.question;}

    public Object getRequest() {return this.request;}
}
