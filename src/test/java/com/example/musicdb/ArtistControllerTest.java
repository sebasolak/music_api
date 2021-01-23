package com.example.musicdb;

import com.example.musicdb.controller.ArtistController;
import com.example.musicdb.model.Album;
import com.example.musicdb.model.Artist;
import com.example.musicdb.model.ArtistDto;
import com.example.musicdb.model.Track;
import com.example.musicdb.service.ArtistService;
import com.example.musicdb.service.SecurityContextHolderService;
import com.example.musicdb.service.mail.EmailService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

class ArtistControllerTest {

    //given
    //then
    //when
    private ArtistService artistService;
    private SecurityContextHolderService securityContextHolderService;
    private EmailService emailService;
    private ArtistController artistController;

    @BeforeEach
    void setUp() {
        artistService = Mockito.mock(ArtistService.class);
        securityContextHolderService = Mockito.mock(SecurityContextHolderService.class);
        emailService = Mockito.mock(EmailService.class);
        artistController = new ArtistController(artistService, securityContextHolderService, emailService);
    }

    @Test
    void shouldGetArtistByName() {
        Artist coldplay = new Artist(111239, "Coldplay", "Parlophone");

        BDDMockito.given(artistService.selectArtistByName("Coldplay"))
                .willReturn(coldplay);

        Artist artist = artistController.getArtistByName("Coldplay");

        Assertions.assertThat(artist).isEqualTo(coldplay);
    }

    @Test
    void shouldNotGetArtistByIncorrectNameName() {
        Artist artist = artistController.getArtistByName("some_incorrect_name");
        Assertions.assertThat(artist).isNull();
    }

    @Test
    void shouldGetDiscographyByArtistName() {
        //given
        List<Album> albums = Arrays.asList(
                new Album("Everyday Life", 2019),
                new Album("Live in Buenos Aires", 2018),
                new Album("Greatest Songs", 2018)
        );

        BDDMockito.given(artistService.selectDiscographyByArtistName("Coldplay"))
                .willReturn(albums);
        //when
        List<Album> coldplay = artistController.getDiscographyByArtistName("Coldplay");

        //then
        Assertions.assertThat(coldplay).isEqualTo(albums);
        Mockito.verify(artistService).selectDiscographyByArtistName("Coldplay");
    }


    @Test
    void shouldSaveArtistByName() {
        //given 111239, "example-login", "Coldplay"
        String login = "example-login";
        ArtistDto coldplay = new ArtistDto(111239, "Coldplay", login);
        BDDMockito.given(securityContextHolderService.getUserUsername())
                .willReturn(login);

        BDDMockito.given(artistService.saveArtist(coldplay.getStrArtist(), coldplay.getLogin()))
                .willReturn(coldplay);
        //then

        ArtistDto artistDto = artistController.saveArtist("Coldplay");

        //when
        Assertions.assertThat(artistDto).isEqualTo(coldplay);
        Mockito.verify(artistService).saveArtist("Coldplay", login);
    }

    @Test
    void shouldDeleteArtistById() {
        //given 111239, "example-login", "Coldplay"
        String login = "example-login";
        ArtistDto coldplay = new ArtistDto(111239, "Coldplay", login);
        BDDMockito.given(securityContextHolderService.getUserUsername())
                .willReturn(login);

        BDDMockito.given(artistService.deleteArtist(coldplay.getIdArtist(), coldplay.getLogin()))
                .willReturn(String.format("Artist %d did not exists in database", coldplay.getIdArtist()));

        //then
        String deleteArtist = artistController.deleteArtist(111239);

        //when
        Assertions.assertThat(deleteArtist)
                .isEqualTo(String.format("Artist %d did not exists in database", coldplay.getIdArtist()));
        Mockito.verify(artistService).deleteArtist(111239, login);
    }

    @Test
    void shouldGetFavouriteDiscography() {
        //given
        List<Album> favoriteArtistAlbums = Arrays.asList(
                new Album("Up In Smoke by Aerosmith", 2015),
                new Album("Live in Buenos Aires by Coldplay", 2018),
                new Album("Greatest Songs by Coldplay", 2018),
                new Album("From the Muddy Banks of the Wishkah by Nirvana", 1996)
        );
        String login = "example-login";
        BDDMockito.given(securityContextHolderService.getUserUsername())
                .willReturn(login);
        BDDMockito.given(artistService.selectFavouriteDiscography(login))
                .willReturn(favoriteArtistAlbums);

        //when
        List<Album> fetchData = artistController.getFavouriteDiscography();

        //then
        Assertions.assertThat(fetchData).isEqualTo(favoriteArtistAlbums);
        Mockito.verify(artistService).selectFavouriteDiscography(login);
    }

    @Test
    void shouldGetTrack() {
        //given
        Track track = new Track("example-login", "Paradise", "Coldplay", 123456);
        BDDMockito.given(artistService.selectTrack("Coldplay", "Paradise"))
                .willReturn(track);
        //when
        Track fetchData = artistController.getTrack("Coldplay", "Paradise");

        //then
        Assertions.assertThat(fetchData).isEqualTo(track);
        Mockito.verify(artistService).selectTrack("Coldplay", "Paradise");
    }

    @Test
    void shouldThrowExceptionWhenArtistOrAndTrackIsIncorrect() {
        //given
        Mockito.when(artistService.selectTrack("abc", "def"))
                .thenThrow(new RuntimeException("Artist or/and track did not exist"));
        //then
        Assertions.assertThatThrownBy(() -> artistController.getTrack("abc", "def"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Artist or/and track did not exist");
    }

    @Test
    void shouldSaveTrack() {
        //given
        String login = "example-login";

        Track track = new Track(login, "Creep", "Radiohead", 123456);
        BDDMockito.given(securityContextHolderService.getUserUsername())
                .willReturn(login);
        BDDMockito.given(artistService.saveTrack("Radiohead", "Creep", login))
                .willReturn(track);

        //when
        ArgumentCaptor<Track> captor = ArgumentCaptor.forClass(Track.class);
        Track fetchData = artistController.saveTrack("Radiohead", "Creep");

        //then
        Assertions.assertThat(fetchData).isEqualTo(track);
        Mockito.verify(artistService).saveTrack("Radiohead", "Creep", login);
    }

    @Test
    void shouldGetFavouriteTracks() {
        //given
        String login = "example-login";
        List<Track> favouriteTracks = Arrays.asList(
                new Track(login, "Creep", "Radiohead", 123456),
                new Track(login, "Coldplay", "Paradise", 327896),
                new Track(login, "Nirvana", "Lithium", 327896)
        );
        BDDMockito.given(securityContextHolderService.getUserUsername())
                .willReturn(login);
        BDDMockito.given(artistService.selectFavouriteTracks(login))
                .willReturn(favouriteTracks);
        //when

        List<Track> fetchData = artistController.getFavouriteTracks();
        //then
        Assertions.assertThat(fetchData).isEqualTo(favouriteTracks);
        Mockito.verify(artistService).selectFavouriteTracks(login);
    }

    @Test
    void shouldDeleteTrackByIdTrack() {
        //given 111239, "example-login", "Coldplay"
        String login = "example-login";
        Track track = new Track(login, "Paradise", "Coldplay", 32724193);

        BDDMockito.given(securityContextHolderService.getUserUsername())
                .willReturn(login);

        BDDMockito.given(artistService.deleteTrack(track.getIdTrack(), login))
                .willReturn(String.format("Delete %s by %s from favourite tracks", track.getStrTrack(), track.getStrArtist()));
        //then
        String deleteTrackResponse = artistController.deleteTrack(32724193);

        //when
        Assertions.assertThat(deleteTrackResponse)
                .isEqualTo(String.format("Delete %s by %s from favourite tracks", track.getStrTrack(), track.getStrArtist()));
        Mockito.verify(artistService).deleteTrack(32724193, login);
    }

    @Test
    void shouldThrowExceptionWhenIdTrackIsIncorrect() {
        //given
        String login = "example-login";
        BDDMockito.given(securityContextHolderService.getUserUsername())
                .willReturn(login);
        Mockito.when(artistService.deleteTrack(32724193, login))
                .thenThrow(new RuntimeException("Data are incorrect"));
        //then
        Assertions.assertThatThrownBy(() -> artistController.deleteTrack(32724193))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Data are incorrect");
    }

    @Test
    void shouldSendFavouriteTracks() {
        //given
        String login = "example-login";
        BDDMockito.given(securityContextHolderService.getUserUsername())
                .willReturn(login);
        //when
        String fetchData = artistController.sendFavouriteTracks();

        //then
        Assertions.assertThat(fetchData)
                .isEqualTo(String.format("%s, your favourite tracks was send to you", login));
    }

    @Test
    void shouldGetFavouriteArtists() {
        //given
        String login = "example-login";
        List<Artist> artists = Arrays.asList(
                new Artist(111239, "Coldplay", "Parlophone"),
                new Artist(112943, "The Cranberries", "BMG"),
                new Artist(111268, "Aerosmith", "Columbia")
        );

        BDDMockito.given(securityContextHolderService.getUserUsername())
                .willReturn(login);
        BDDMockito.given(artistService.selectFavouriteArtist(login))
                .willReturn(artists);

        //when
        List<Artist> fetchData = artistController.getFavouriteArtists();

        //then
        Assertions.assertThat(fetchData).isEqualTo(artists);
        Mockito.verify(artistService).selectFavouriteArtist(login);

    }


}