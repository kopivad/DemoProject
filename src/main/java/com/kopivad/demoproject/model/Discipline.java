package com.kopivad.demoproject.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "disciplines")
@Data
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "discipline_id")
    private Long id;

    @Column(name = "discipline_title")
    private String title;

//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User teacher;
}
