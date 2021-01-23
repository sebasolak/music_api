package com.example.musicdb.provider;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Getter
public class UrlProvider {

    @Value("${artist.name.url}")
    private String artistNameUrl;

    @Value("${artist.id.url}")
    private String artistIdUrl;

    @Value("${artist.name.discography.url}")
    private String artistDiscographyUrl;

    @Value("${track.name.artist.url}")
    private String trackUrl;

    @Value("${track.id.url}")
    private String trackIdUrl;

    @Value("${token.confirmation.url}")
    private String tokenConfirmationUrl;
}
