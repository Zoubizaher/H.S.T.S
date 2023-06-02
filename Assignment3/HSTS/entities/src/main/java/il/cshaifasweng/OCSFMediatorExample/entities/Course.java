package il.cshaifasweng.OCSFMediatorExample.entities;

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

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "Teacher_Course",
//            joinColumns = @JoinColumn(name = "course_id"),
//            inverseJoinColumns = @JoinColumn(name = "id_num")
//    )
    @Transient
    private Teacher teacher;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "FirstName")
    )
    private List<Student> students= new ArrayList<>();
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
}
