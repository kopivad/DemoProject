package com.kopivad.testingsystem.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "user_answers")
@Data
public class UserQuestionResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String sessionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Answer answer;
}
