package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Entity
//@Table(name = "Teachers")
public class Teacher extends User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_num;

    @OneToMany(mappedBy = "teacher",cascade = CascadeType.ALL, fetch = FetchType.EAGER )

    private List<Course> courses = new ArrayList<>();

    public Teacher(String id, String first, String last, String username, String role, String mail, String password){
        super(id, first, last, username, role, mail,password);
    }

    public Teacher() {
        super();
    }

    public void AddCourse(Course course){this.courses.add(course);}
    public void SetCourses(List<Course> courses){this.courses=courses;}
}
