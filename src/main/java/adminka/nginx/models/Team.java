package adminka.nginx.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Team {
    private int id;
    private String title;
    private String country;
    private int constructorCup;

    public Team(int id, String title, String country, int constructorCup) {
        this.id = id;
        this.title = title;
        this.country = country;
        this.constructorCup = constructorCup;
    }

    public Team() {
    }

    @Id
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCountry() {
        return country;
    }

    public int getConstructorCup() {
        return constructorCup;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setConstructorCup(int constructorCup) {
        this.constructorCup = constructorCup;
    }
}
