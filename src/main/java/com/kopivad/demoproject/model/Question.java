package com.kopivad.demoproject.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "questions")
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String firstAnswer;

    @NotNull
    private String secondAnswer;

    @NotNull
    private String thirdAnswer;

    @NotNull
    private String fourthAnswer;

    @NotNull
    private String rightAnswer;

    @ManyToOne
    @JoinColumn(name = "id_test")
    private Test test;
}
