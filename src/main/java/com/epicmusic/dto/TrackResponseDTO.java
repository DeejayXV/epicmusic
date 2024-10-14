package com.epicmusic.dto;

public class TrackResponseDTO {
    private Long id;
    private String title;
    private String artist;
    private String album;

    public TrackResponseDTO(Long id, String title, String artist, String album) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
    }

}