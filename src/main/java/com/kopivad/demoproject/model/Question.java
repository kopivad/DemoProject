package com.kopivad.demoproject.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "questions")
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    @ElementCollection(targetClass = Answer.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "questions_answer", joinColumns = @JoinColumn(name = "id_question"))
    private List<Answer> answers;

    @ManyToOne
    @JoinColumn(name = "id_test")
    private Test test;
}
