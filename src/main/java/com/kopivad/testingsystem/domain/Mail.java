package com.kopivad.testingsystem.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Mail {
    private String receiver;
    private String subject;
    private String text;

    public Mail(@JsonProperty("receiver") String receiver,
                @JsonProperty("subject") String subject,
                @JsonProperty("text") String text) {
        this.receiver = receiver;
        this.subject = subject;
        this.text = text;
    }
}
