package com.kopivad.demoproject.model;

import lombok.Data;

import javax.persistence.*;

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
}
