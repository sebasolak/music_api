package com.example.musicdb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.net.URL;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class Artist {



    @SequenceGenerator(
            name = "artist_sequence",
            sequenceName = "artist_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "artist_sequence"
    )
    private int idArtist;
    private String strArtist;
    @Transient
    private String strLabel;
    @Transient
    private int intBornYear;
    @Transient
    private String strStyle;
    @Transient
    private String strGenre;
    @Transient
    private String strMood;
    @Transient
    private String strWebsite;
    @Transient
    private String strBiographyEN;
    @Transient
    private String strBiographyPL;
    @Transient
    private String strCountry;
    @Transient
    private URL strArtistThumb;


    public Artist(int idArtist, String strArtist, String strLabel) {
        this.idArtist = idArtist;
        this.strArtist = strArtist;
        this.strLabel = strLabel;
    }


}
