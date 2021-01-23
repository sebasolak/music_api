package com.example.musicdb.repository;

import com.example.musicdb.model.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository("appUserRepo")
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
    Optional<AppUser> findByLogin(String login);

    @Query(value = "SELECT a.email from AppUser a WHERE a.login = ?1")
    String selectEmailByLogin(String login);

    @Transactional
    @Modifying
    @Query("UPDATE AppUser a SET a.enabled = TRUE WHERE a.login = ?1")
    int enableAppUser(String login);
}
