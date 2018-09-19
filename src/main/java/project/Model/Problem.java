package project.Model;
import javax.persistence.*;

@Entity
@Table(name="Problem")
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int house;
    private int applicant;
    private String description;
    private int danger;

    public int getHouse() {

        return house;
    }

    public void setHouse(int house) {
        this.house = house;
    }

    public int getApplicant() {

        return applicant;
    }

    public void setApplicant(int user) {

        this.applicant = user;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public int getDanger()
    {

        return danger;
    }

    public void setDanger(int danger) {

        this.danger = danger;
    }


    public int getId() {

        return id;
    }
}