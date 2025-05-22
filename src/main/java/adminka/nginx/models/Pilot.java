package adminka.nginx.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Pilot {
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private int cup;
    private Integer teamId;

    public Pilot(int id, String firstName, String lastName, int age, int cup, Integer teamId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.cup = cup;
        this.teamId = teamId;
    }

    public Pilot() {
    }

    @Id
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public int getCup() {
        return cup;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCup(int cup) {
        this.cup = cup;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }
}
