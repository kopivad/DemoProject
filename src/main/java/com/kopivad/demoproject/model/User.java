package com.kopivad.demoproject.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_password")
    private String password;

    @Enumerated(EnumType.STRING) // Type which be in db
    @Column(name = "user_role")
    private Role role;

//    @OneToMany(mappedBy = "teacher")
//    private List<Discipline> disciplines;

}
