package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "Manager")
public class Manager extends User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_num;
    public Manager(String id, String first, String last, String username, String role, String mail, String password){
        super(id, first, last, username, role, mail, password);
    }
}
