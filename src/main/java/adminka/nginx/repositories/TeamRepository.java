package adminka.nginx.repositories;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import adminka.nginx.models.Team;

@Repository
public class TeamRepository {
    private HashMap<Integer, Team> teams = new HashMap<>();
    private int nextId = 1;

    public TeamRepository() {
    }

    public TeamRepository(HashMap<Integer, Team> teams) {
        this.teams = teams;
    }

    public HashMap<Integer, Team> getTeams() {
        return teams;
    }

    public void setTeams(HashMap<Integer, Team> teams) {
        this.teams = teams;
    }

    public void addTeam(Team team) {
        team.setId(nextId);
        teams.put(nextId, team);
        nextId++;
    }

    public void removeTeam(int id) {
        teams.remove(id);
    }

    public Team getTeam(int id) {
        return teams.get(id);
    }
}
