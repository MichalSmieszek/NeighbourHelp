package project.Model;

import javax.persistence.*;
@Entity
@Table(name="User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String name;
    private String email;
    private char sex;
    private String password;

    public char getSex() {

        return sex;
    }
    public void setSex(char sex) {

        this.sex = sex;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getEmail()
    {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }
    public int getId(){

        return id;
    }
    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }
    public void setId(int id) {
        this.id = id;
    }
}