package ru.sultanyarov.service;

import ru.sultanyarov.models.Email;

public interface EmailService {
    void sendEmail(Email email);
}
