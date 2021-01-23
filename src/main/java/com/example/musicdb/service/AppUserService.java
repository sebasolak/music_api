package com.example.musicdb.service;

import com.example.musicdb.model.appuser.AppUser;
import com.example.musicdb.model.appuser.ConfirmationToken;
import com.example.musicdb.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "User with login %s not found";
    @Qualifier("appUserRepo")
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return appUserRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, login)));
    }

    public String signUpUser(AppUser appUser) {
        boolean userExists = appUserRepository.findByLogin(appUser.getLogin())
                .isPresent();

        if (userExists) {
            throw new IllegalStateException("login already taken");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

    public int enableAppUser(String login) {
        return appUserRepository.enableAppUser(login);
    }
}
