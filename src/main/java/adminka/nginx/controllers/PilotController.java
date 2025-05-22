package adminka.nginx.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import adminka.nginx.models.Pilot;
import adminka.nginx.repositories.PilotRepository;
import adminka.nginx.repositories.TeamRepository;

@Controller
@RequestMapping("/pilots")
public class PilotController {
    private final PilotRepository pilotRepository;
    private final TeamRepository teamRepository;

    public PilotController(PilotRepository pilotRepository, TeamRepository teamRepository) {
        this.pilotRepository = pilotRepository;
        this.teamRepository = teamRepository;
    }

    @GetMapping
    public String listPilots(Model model) {
        List<Map<String, Object>> pilotData = new ArrayList<>();
        for (Pilot pilot : pilotRepository.getPilots().values()) {
            Map<String, Object> data = new HashMap<>();
            data.put("pilot", pilot);
            data.put("teamName", teamRepository.getTeam(pilot.getTeamId()) != null
                    ? teamRepository.getTeam(pilot.getTeamId()).getTitle()
                    : "No Team");
            pilotData.add(data);
        }
        model.addAttribute("pilotData", pilotData);
        return "pilots/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("pilot", new Pilot());
        model.addAttribute("teams", teamRepository.getTeams().values()); // Важно!
        return "pilots/add";
    }

    @PostMapping("/add")
    public String addPilot(@ModelAttribute Pilot pilot) {
        if (pilot.getTeamId() == null) {
            pilot.setTeamId(0); // Или другое значение по умолчанию
        }
        pilotRepository.addPilot(pilot);
        return "redirect:/pilots";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Pilot pilot = pilotRepository.getPilot(id);
        if (pilot == null)
            return "redirect:/pilots";
        model.addAttribute("pilot", pilot);
        model.addAttribute("teams", teamRepository.getTeams().values());
        return "pilots/edit";
    }

    @PostMapping("/edit/{id}")
    public String updatePilot(@PathVariable int id, @ModelAttribute Pilot pilot) {
        pilot.setId(id);
        pilotRepository.addPilot(pilot);
        return "redirect:/pilots";
    }

    @GetMapping("/delete/{id}")
    public String deletePilot(@PathVariable int id) {
        pilotRepository.removePilot(id);
        return "redirect:/pilots";
    }
}