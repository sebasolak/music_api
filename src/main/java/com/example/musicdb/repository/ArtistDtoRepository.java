package com.example.musicdb.repository;

import com.example.musicdb.model.Artist;
import com.example.musicdb.model.ArtistDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("artistRepo")
public interface ArtistDtoRepository extends JpaRepository<ArtistDto, Integer> {

    @Query(value = "SELECT COUNT(a) FROM ArtistDto a WHERE a.idArtist = ?1 and a.login = ?2")
    int isArtistAlreadySaved(int idArtist, String login);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ArtistDto a WHERE a.idArtist = ?1 and a.login = ?2")
    void deleteFromDatabase(int idArtist, String login);

    ArtistDto getArtistDtoByIdArtist(int idArtist);

    @Query(value = "SELECT a.strArtist FROM ArtistDto a WHERE a.login = ?1")
    List<String> getAllFavouriteArtist(String login);



}
