package com.example.musicdb.repository;

import com.example.musicdb.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("trackRepo")
public interface TrackRepository extends JpaRepository<Track, Integer> {

    @Query(value = "SELECT COUNT(t) FROM Track t WHERE t.strTrack = ?1 and t.login = ?2")
    int isTrackAlreadySaved(String strTrack, String login);

    List<Track> getTrackByLogin(String login);

    @Query(value = "SELECT t.idTrack FROM Track t WHERE t.login = ?1")
    List<Integer> getFavouriteIdTrack(String login);

    Track getTrackByIdTrackAndLogin(int idTrack, String login);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Track t WHERE t.idTrack = ?1 and t.login = ?2")
    void deleteTrackFromDatabase(int idTrack, String login);
}
