package com.example.musicdb.service;

import com.example.musicdb.model.*;
import com.example.musicdb.provider.UrlProvider;
import com.example.musicdb.repository.ArtistDtoRepository;
import com.example.musicdb.repository.TrackRepository;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ArtistService {

    private final GsonService gsonService;
    private final Gson gson;
    @Qualifier("artistRepo")
    private final ArtistDtoRepository artistDtoRepository;
    @Qualifier("trackRepo")
    private final TrackRepository trackRepository;
    private final UrlProvider urlProvider;

    public Artist selectArtistByName(String artistName) {
        String s = gsonService.deserializeJson(urlProvider.getArtistNameUrl() + artistName);
        ArtistsArray artistsArray = gson.fromJson(s, ArtistsArray.class);
        return artistsArray.getArtists()[0];
    }

    public List<Album> selectDiscographyByArtistName(String artistName) {
        String s = gsonService.deserializeJson(urlProvider.getArtistDiscographyUrl() + artistName);
        return gson.fromJson(s, AlbumList.class).getAlbum();
    }

    public ArtistDto saveArtist(String artistName, String login) {
        String s = gsonService.deserializeJson(urlProvider.getArtistNameUrl() + artistName);
        ArtistsArray artistsArray = gson.fromJson(s, ArtistsArray.class);
        Artist artist = artistsArray.getArtists()[0];
        ArtistDto artistDto = new ArtistDto(artist.getIdArtist(), artist.getStrArtist(), login);
        if (artistDtoRepository.isArtistAlreadySaved(artist.getIdArtist(), login) == 0) {
            artistDtoRepository.save(artistDto);
        }
        return artistDto;
    }

    public String deleteArtist(int artistId, String login) {
        if (artistDtoRepository.isArtistAlreadySaved(artistId, login) == 1) {
            ArtistDto artistDto = artistDtoRepository.getArtistDtoByIdArtist(artistId);
            artistDtoRepository.deleteFromDatabase(artistId, login);
            return String.format("Deleted %s from favourite", artistDto.getStrArtist());
        }
        return String.format("Artist %d did not exists in database", artistId);
    }


    public List<Album> selectFavouriteDiscography(String login) {
        List<String> allFavouriteArtist = artistDtoRepository.getAllFavouriteArtist(login);
        List<Album> list = new ArrayList<>();
        for (String artistName : allFavouriteArtist) {
            List<Album> albums = selectDiscographyByArtistName(artistName);
            albums.forEach(album -> album.setStrAlbum(album.getStrAlbum() + " by " + artistName));
            list.addAll(albums);
        }
        return list;
    }


    public Track selectTrack(String artistName, String trackName) {
        try {
            String s = gsonService.deserializeJson(urlProvider.getTrackUrl() + artistName + "&t=" + trackName);
            TrackList trackList = gson.fromJson(s, TrackList.class);
            return trackList.getTrack().get(0);
        } catch (NullPointerException e) {
            throw new RuntimeException("Artist or/and track did not exist");
        }
    }

    public Track saveTrack(String artistName, String trackName, String login) {
        Track track = selectTrack(artistName, trackName);
        Track dbTrack = new Track(login, track.getStrTrack(), track.getStrArtist(), track.getIdTrack());
        if (trackRepository.isTrackAlreadySaved(dbTrack.getStrTrack(), login) == 0) {
            trackRepository.save(dbTrack);
        }
        return track;
    }

    public List<Track> selectFavouriteTracks(String login) {
        List<Integer> favouriteIdTrack = trackRepository.getFavouriteIdTrack(login);

        List<Track> list = new ArrayList<>();
        for (int idTrack : favouriteIdTrack) {
            String s = gsonService.deserializeJson(urlProvider.getTrackIdUrl() + idTrack);
            TrackList trackList = gson.fromJson(s, TrackList.class);
            list.add(trackList.getTrack().get(0));
        }
        return list;
    }

    public String deleteTrack(int trackId, String login) {
        Track track = trackRepository.getTrackByIdTrackAndLogin(trackId, login);
        if (track == null) {
            throw new IllegalStateException("Data are incorrect");
        }
        trackRepository.deleteTrackFromDatabase(trackId, login);
        return String.format("Delete %s by %s from favourite tracks", track.getStrTrack(), track.getStrArtist());
    }


    public List<Artist> selectFavouriteArtist(String login) {
        List<String> allFavouriteArtist = artistDtoRepository.getAllFavouriteArtist(login);
        List<Artist> list = new ArrayList<>();
        for (String artistName : allFavouriteArtist) {
            String s = gsonService.deserializeJson(urlProvider.getArtistNameUrl() + artistName);
            Artist artist = gson.fromJson(s, ArtistsArray.class).getArtists()[0];
            list.add(artist);
        }
        return list;

    }

}
