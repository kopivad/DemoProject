package com.kopivad.demoproject.form;

import com.kopivad.demoproject.model.User;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class DisciplineForm {
    @NotNull
    @Size(min = 2, max = 25)
    private String title;
}
