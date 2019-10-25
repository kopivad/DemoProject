package com.kopivad.demoproject.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "topics")
@Data
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_topic")
    private Long id;

    @Column(name = "topic_title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User author;

    @ElementCollection(targetClass = Topic.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "topic_tests", joinColumns = @JoinColumn(name = "id_topic"))
    private Set<Test> tests;
}
