package com.example.musicdb.service.mail;

import com.example.musicdb.repository.AppUserRepository;
import com.example.musicdb.service.AppUserService;
import com.example.musicdb.service.ArtistService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class EmailService {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender javaMailSender;
    private final AppUserRepository appUserRepository;
    private final ArtistService artistService;


    @Async
    public void sendFavouriteTracks(String login) {
        String email = appUserRepository.selectEmailByLogin(login);
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setText(artistService.selectFavouriteTracks(login).toString(), true);
            mimeMessageHelper.setSubject("Your favorite tracks");
            mimeMessageHelper.setFrom("server@xyz.com");
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }

    @Async
    public void sendConfirmationToken(String to,
                                      String email) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setText(email, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("Confirm your email");
            mimeMessageHelper.setFrom("server@xyz.com");
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }

    }

}
