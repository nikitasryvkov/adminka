package adminka.nginx.repositories;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import adminka.nginx.models.Pilot;

@Repository
public class PilotRepository {
    private HashMap<Integer, Pilot> pilots = new HashMap<>();
    private int nextId = 1;

    public PilotRepository() {
    }

    public PilotRepository(HashMap<Integer, Pilot> pilots) {
        this.pilots = pilots;
    }

    public HashMap<Integer, Pilot> getPilots() {
        return pilots;
    }

    public void setPilots(HashMap<Integer, Pilot> pilots) {
        this.pilots = pilots;
    }

    public void addPilot(Pilot pilot) {
        pilot.setId(nextId);
        pilots.put(nextId, pilot);
        nextId++;
    }

    public void removePilot(int id) {
        pilots.remove(id);
    }

    public Pilot getPilot(int id) {
        return pilots.get(id);
    }
}
