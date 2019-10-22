package com.kopivad.demoproject.service;

import com.kopivad.demoproject.dao.DisciplineRepository;
import com.kopivad.demoproject.model.Discipline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisciplineServiceImpl implements DisciplineService {
    private final DisciplineRepository disciplineRepository;

    public DisciplineServiceImpl(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    @Override
    public Iterable<Discipline> getAllDisciplines() {
        return disciplineRepository.findAll();
    }

    @Override
    public void addNewDiscipline(Discipline discipline) {
        disciplineRepository.save(discipline);
    }
}
