package com.example.musicdb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.net.URL;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EqualsAndHashCode
public class Track {
    @SequenceGenerator(
            name = "track_sequence",
            sequenceName = "track_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "track_sequence"
    )
    @JsonIgnore
    private int trackId;
    private int idTrack;
    @JsonIgnore
    private String login;
    private String strTrack;
    @Column(insertable = false, updatable = false)
    @Transient
    private String strAlbum;
    private String strArtist;
    @Column(insertable = false, updatable = false)
    @Transient
    private URL strMusicVid;

    public Track(String login, String strTrack, String strArtist,int idTrack) {
        this.login = login;
        this.strTrack = strTrack;
        this.strArtist = strArtist;
        this.idTrack = idTrack;
    }

    @Override
    public String toString() {
        return "Track{" +
                "strTrack='" + strTrack + '\'' +
                ", strAlbum='" + strAlbum + '\'' +
                ", strArtist='" + strArtist + '\'' +
                ", strMusicVid=" + strMusicVid +
                '}';
    }
}
