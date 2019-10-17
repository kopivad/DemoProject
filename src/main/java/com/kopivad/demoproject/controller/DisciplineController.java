package com.kopivad.demoproject.controller;

import com.kopivad.demoproject.model.Discipline;
import com.kopivad.demoproject.model.User;
import com.kopivad.demoproject.service.DisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/discipline")
public class DisciplineController {
    @Autowired
    public DisciplineService disciplineService;

    @GetMapping("/all")
    public String allDisciplines(Model model) { // It returns list of all disciplines
        Iterable<Discipline> allDisciplines = disciplineService.getAllDisciplines();
        model.addAttribute("allDisciplines", allDisciplines);
        return "allDiscipline";
    }

    @GetMapping("/add")
    public String addDisciplinePage(Authentication authentication) { // It returns add discipline page
        System.out.println(authentication.getName());
        return "addDiscipline";
    }

    @PostMapping("/add")
    public String addDiscipline(Authentication authentication, @ModelAttribute Discipline discipline) {
        disciplineService.addNewDiscipline(discipline);
        return "redirect:/discipline/all";
    }
}
