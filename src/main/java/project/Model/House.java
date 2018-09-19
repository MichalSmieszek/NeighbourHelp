package project.Model;
import javax.persistence.*;

@Entity
@Table(name="House")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {

        return id;
    }

}
