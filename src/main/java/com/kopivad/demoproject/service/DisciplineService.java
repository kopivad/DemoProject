package com.kopivad.demoproject.service;

import com.kopivad.demoproject.model.Discipline;
import org.springframework.stereotype.Service;

@Service
public interface DisciplineService {
    Iterable<Discipline> getAllDisciplines();

    void addNewDiscipline(Discipline discipline);
}
