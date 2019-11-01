package com.kopivad.demoproject.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tests")
@Data
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_test")
    private Long id;

    @Column(name = "test_title")
    private String title;

    @Column(name = "questions_count")
    private int countOfQuestion;

    @ManyToOne
    @JoinColumn(name = "id_topic", nullable = false)
    private Topic topic;

    @ElementCollection(targetClass = Topic.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "test_questions", joinColumns = @JoinColumn(name = "id_test"))
    private Set<Question> questions;
}
