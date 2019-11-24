package com.kopivad.testingsystem.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "quiz_results")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuizResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


}
