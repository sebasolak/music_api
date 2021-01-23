package com.example.musicdb.controller;

import com.example.musicdb.model.Album;
import com.example.musicdb.model.Artist;
import com.example.musicdb.model.ArtistDto;
import com.example.musicdb.model.Track;
import com.example.musicdb.service.ArtistService;
import com.example.musicdb.service.SecurityContextHolderService;
import com.example.musicdb.service.mail.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
public class ArtistController {

    private final ArtistService artistService;
    private final SecurityContextHolderService securityContextHolderService;
    private final EmailService emailService;

    @GetMapping(path = "name/{artistName}")
    public Artist getArtistByName(@PathVariable("artistName") String artistName) {
        return artistService.selectArtistByName(artistName);
    }

    @GetMapping(path = "albums/{artistName}")
    public List<Album> getDiscographyByArtistName(@PathVariable("artistName") String artistName) {
        return artistService.selectDiscographyByArtistName(artistName);
    }

    @PostMapping(path = "name/{artistName}")
    public ArtistDto saveArtist(@PathVariable("artistName") String artistName) {
        String username = securityContextHolderService.getUserUsername();
        return artistService.saveArtist(artistName, username);
    }

    @DeleteMapping(path = "delete/{artistId}")
    public String deleteArtist(@PathVariable("artistId") int artistId) {
        String username = securityContextHolderService.getUserUsername();
        return artistService.deleteArtist(artistId, username);
    }

    @GetMapping(path = "albums")
    public List<Album> getFavouriteDiscography() {
        String username = securityContextHolderService.getUserUsername();
        return artistService.selectFavouriteDiscography(username);
    }

    @GetMapping(path = "track/{artistName}/{trackName}")
    public Track getTrack(@PathVariable("artistName") String artistName,
                          @PathVariable("trackName") String trackName) {
        return artistService.selectTrack(artistName, trackName);
    }

    @PostMapping(path = "track/{artistName}/{trackName}")
    public Track saveTrack(@PathVariable("artistName") String artistName,
                          @PathVariable("trackName") String trackName) {
        String username = securityContextHolderService.getUserUsername();
        return artistService.saveTrack(artistName, trackName,username);
    }

    @GetMapping(path = "tracks")
    public List<Track> getFavouriteTracks() {
        String username = securityContextHolderService.getUserUsername();
        return artistService.selectFavouriteTracks(username);
    }

    @DeleteMapping(path = "delete/track/{trackId}")
    public String deleteTrack(@PathVariable("trackId") int trackId) {
        String username = securityContextHolderService.getUserUsername();
        return artistService.deleteTrack(trackId, username);
    }

    @GetMapping(path = "send")
    public String sendFavouriteTracks() {
        String username = securityContextHolderService.getUserUsername();
        emailService.sendFavouriteTracks(username);
        return String.format("%s, your favourite tracks was send to you", username);
    }

    @GetMapping(path = "artists")
    public List<Artist> getFavouriteArtists() {
        String username = securityContextHolderService.getUserUsername();
        return artistService.selectFavouriteArtist(username);
    }
}
