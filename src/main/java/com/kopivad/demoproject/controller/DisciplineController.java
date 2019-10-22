package com.kopivad.demoproject.controller;

import com.kopivad.demoproject.form.DisciplineForm;
import com.kopivad.demoproject.model.Discipline;
import com.kopivad.demoproject.model.User;
import com.kopivad.demoproject.service.DisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/discipline")
public class DisciplineController {
    public final DisciplineService disciplineService;

    public DisciplineController(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @GetMapping("/all")
    public String allDisciplines(Model model) { // It returns list of all disciplines
        Iterable<Discipline> allDisciplines = disciplineService.getAllDisciplines();
        model.addAttribute("allDisciplines", allDisciplines);
        return "allDiscipline";
    }

    @GetMapping("/add")
    public String addDisciplinePage(Model model, DisciplineForm disciplineForm) { // It returns add discipline page
        model.addAttribute("disciplineFrom", disciplineForm);
        return "addDiscipline";
    }

    @PostMapping("/add")
    public String addDiscipline(@Valid DisciplineForm form, BindingResult bindingResult, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "addDiscipline";
        }
        User currentUser = (User) authentication.getPrincipal();
        Discipline discipline = new Discipline();
        discipline.setTitle(form.getTitle());
        discipline.setAuthor(currentUser);

        disciplineService.addNewDiscipline(discipline);
        return "redirect:/discipline/all";
    }
}
