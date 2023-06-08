package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Questions")
public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_num;

    @Column(name = "Question_text")
    private String questionText;

    @Column(name = "OptionA")
    private String answerA;
    @Column(name = "OptionB")
    private String answerB;
    @Column(name = "OptionC")
    private String answerC;
    @Column(name = "OptionD")
    private String answerD;
    @Column(name = "Answer")
    private String correctAnswer;

  /*  @ManyToMany(mappedBy = "questions", fetch = FetchType.EAGER)
    private List<Course> courses = new ArrayList<>();*/



    @ElementCollection
    @OrderColumn(name = "answer_order")
    private List<String> answers;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    public Question(String questionText, List<String> answers, String correctAnswer,Teacher teacher) {
        this.questionText = questionText;
        this.answers = answers;
        this.answerA = answers.get(0);
        this.answerB = answers.get(1);
        this.answerC = answers.get(2);
        this.answerD = answers.get(3);
        this.correctAnswer = correctAnswer;
        this.teacher=teacher;
    }

    public Question() {

    }

  //  public void setCourses(List<Course> courses){this.courses = courses;}
    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getAnswerProperty(int index) {
        return answers.get(index);
    }

   /* public int getQuestionNum() {
        return id_num;
    }*/

    public String getAnswerA() {
        return answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public Teacher getTeacher() {
        return teacher;
    }

   /* public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }*/


    public String getAnswerD() {
        return answerD;
    }

    public int getIdNum(){return this.id_num;}

    public void setAnswerA(String answer){this.answerA = answer;}
    public void setAnswerB(String answer){this.answerB = answer;}
    public void setAnswerC(String answer){this.answerC = answer;}
    public void setAnswerD(String answer){this.answerD = answer;}
 //   public List<Course> getCourses(){return this.courses;}
   // public void AddCourse(Course course){this.courses.add(course);}
}

