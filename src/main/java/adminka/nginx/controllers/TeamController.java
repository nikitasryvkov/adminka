package adminka.nginx.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import adminka.nginx.models.Team;
import adminka.nginx.repositories.TeamRepository;

@Controller
@RequestMapping("/teams")
public class TeamController {
    private TeamRepository teamRepository;

    @Autowired
    public TeamController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @GetMapping
    public String listTeams(Model model) {
        Map<Integer, Team> teams = teamRepository.getTeams();
        model.addAttribute("teams", teams.values());
        return "teams/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("team", new Team());
        return "teams/add";
    }

    @PostMapping("/add")
    public String addTeam(@ModelAttribute Team team) {
        teamRepository.addTeam(team);
        return "redirect:/teams";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Team team = teamRepository.getTeam(id);
        if (team == null)
            return "redirect:/teams";
        model.addAttribute("team", team);
        return "teams/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateTeam(@PathVariable int id, @ModelAttribute Team team) {
        team.setId(id);
        teamRepository.addTeam(team);
        return "redirect:/teams";
    }

    @GetMapping("/delete/{id}")
    public String deleteTeam(@PathVariable int id) {
        teamRepository.removeTeam(id);
        return "redirect:/teams";
    }
}