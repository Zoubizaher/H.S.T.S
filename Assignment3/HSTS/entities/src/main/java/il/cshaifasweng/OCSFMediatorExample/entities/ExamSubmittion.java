package il.cshaifasweng.OCSFMediatorExample.entities;

import il.cshaifasweng.OCSFMediatorExample.entities.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.Student;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;
@Entity
@Table(name = "ExamsSubmit")
public class ExamSubmittion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_num;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "exam_submission_answers", joinColumns = @JoinColumn(name = "exam_submission_id"))
    @MapKeyJoinColumn(name = "question_id")
    @Column(name = "answer")
    private Map<Question, String> answers;
    public ExamSubmittion(Student student, Exam exam, Map<Question, String> answers){
        this.student = student;
        this.exam = exam;
        this.answers = answers;
    }
    public ExamSubmittion(){
    }
    public Student getStudent() {
        return student;
    }

    public Exam getExam() {
        return exam;
    }

    public Map<Question, String> getAnswers() {
        return answers;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public void setAnswers(Map<Question, String> answers) {
        this.answers = answers;
    }
}