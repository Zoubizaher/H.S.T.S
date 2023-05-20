package il.cshaifasweng.OCSFMediatorExample.client;

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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> students= new ArrayList<>();
    public Course(String id, String name) {
        super();
        this.id = id;
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
}
