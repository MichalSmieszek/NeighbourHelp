package project.Model;

import javax.persistence.*;

@Entity
@Table(name="UserToHouse")
public class UserToHouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    int user;
    int house;

    public int getUser() {
        return user;
    }

    public void setUser(int user) {

        this.user = user;
    }

    public int getHouse() {

        return house;
    }

    public void setHouse(int house) {

        this.house = house;
    }
    public int getId(){

        return id;
    }
}
