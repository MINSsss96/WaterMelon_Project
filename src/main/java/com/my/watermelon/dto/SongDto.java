package com.my.watermelon.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SongDto {
    private Long id;
    private String title;
    private String artist;
    private String genreName;
    private String lyrics;
    private String album;
    private String spotifyId;
}
