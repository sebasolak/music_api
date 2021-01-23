package com.example.musicdb.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class ArtistDto {

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
    private int dbId;
    private int idArtist;
    private String strArtist;
    private String login;

    public ArtistDto(int idArtist, String strArtist, String login) {
        this.idArtist = idArtist;
        this.strArtist = strArtist;
        this.login = login;
    }
}
