package il.cshaifasweng.OCSFMediatorExample.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Courses")
public class Course implements Serializable {
    @Id
    @Column(name = "Course_ID")
    private String id;

    @Column(name = "Course_Name")
    private String course_name;

  @ManyToOne
  @JoinColumn(name = "teacher_id")
  private Teacher teacher;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "Username")
    )
    private List<Student> students= new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(
            name = "question_course",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "Question_Num")
    )
    private List<Question> questions = new ArrayList<>();


    public Course(String id, String name, Teacher teacher) {
        super();
        this.id = id;
        this.teacher = teacher;
        if (teacher != null) {
            teacher.AddCourse(this);
        }
        this.course_name = name;
    }
    public Course() {
        super();
        this.id = "Unknown";
        this.course_name = "Unknown";
    }
    public void add_student(Student student){
        students.add(student);
    }

    public String getCourse_name(){
        return this.course_name;
    }

    public void addQuestion(Question question){this.questions.add(question);}
    public List<Question> getQuestions(){return this.questions;}
    public String getId(){return this.id;}

    public List<Student> getStudents() {return this.students;}
}
