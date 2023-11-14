package com.boots.service;

import com.boots.entity.PasswordResetToken;
import com.boots.entity.User;
import com.boots.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class PasswordResetTokenService {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private EmailService emailService;

    public PasswordResetToken createPasswordResetToken(User user) {
        String resetToken = generateResetToken();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(resetToken);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryDate(calculateExpiryDate());
        return passwordResetTokenRepository.save(passwordResetToken);
    }

    public void sendPasswordResetEmail(PasswordResetToken passwordResetToken) {
        String resetLink = generateResetLink(passwordResetToken.getToken());
        String emailContent = generateEmailContent(resetLink);
        emailService.sendEmail(passwordResetToken.getUser().getUsername(), "Password Reset", emailContent);
    }

    private String generateResetToken() {
        return UUID.randomUUID().toString();
    }

    private Date calculateExpiryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, PasswordResetToken.EXPIRATION);
        return calendar.getTime();
    }

    private String generateResetLink(String token) {
        return "http://localhost:8080/resetPassword?token=" + token;
    }

    private String generateEmailContent(String resetLink) {
        return "<p>Здравствуйте,</p>"
                + "<p>Вы получили это письмо, потому что запросили сброс пароля для вашей учетной записи.</p>"
                + "<p>Пожалуйста, перейдите по ссылке ниже, чтобы сбросить пароль:</p>"
                + "<p><a href=\"" + resetLink + "\">Сбросить пароль</a></p>"
                + "<p>Если вы не запрашивали сброс пароля, пожалуйста, проигнорируйте это письмо.</p>";
    }
}