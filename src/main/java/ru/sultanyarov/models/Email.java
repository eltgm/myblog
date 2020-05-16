package ru.sultanyarov.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Email {
    private String name;
    private String senderMail;
    private String senderUrl;
    private String home;
    private String subject;
    private String message;
}
