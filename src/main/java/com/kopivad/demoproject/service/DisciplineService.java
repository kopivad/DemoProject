package com.kopivad.demoproject.service;

import com.kopivad.demoproject.model.Discipline;

public interface DisciplineService {
    Iterable<Discipline> getAllDisciplines();

    void addNewDiscipline(Discipline discipline);
}
