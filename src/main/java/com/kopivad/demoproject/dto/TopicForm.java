package com.kopivad.demoproject.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class TopicForm {
    @NotNull
    @Size(min = 2, max = 25)
    private String title;
}
