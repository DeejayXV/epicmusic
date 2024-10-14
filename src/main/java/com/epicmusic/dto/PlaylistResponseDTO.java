package com.epicmusic.dto;

public class PlaylistResponseDTO {
    private Long id;
    private String name;
    private String username;

    public PlaylistResponseDTO(Long id, String name, String username) {
        this.id = id;
        this.name = name;
        this.username = username;
    }
}
