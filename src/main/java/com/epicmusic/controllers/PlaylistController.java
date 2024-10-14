package com.epicmusic.controllers;

import com.epicmusic.dto.PlaylistDTO;
import com.epicmusic.dto.PlaylistResponseDTO;
import com.epicmusic.entities.Playlist;
import com.epicmusic.services.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @GetMapping
    public List<PlaylistResponseDTO> getAllPlaylists() {
        return playlistService.getAllPlaylists().stream()
                .map(playlist -> new PlaylistResponseDTO(playlist.getId(), playlist.getName(), playlist.getUser().getUsername()))
                .collect(Collectors.toList());
    }

    @PostMapping
    public PlaylistResponseDTO createPlaylist(@RequestBody PlaylistDTO playlistDTO) {
        Playlist playlist = playlistService.createPlaylist(playlistDTO);
        return new PlaylistResponseDTO(playlist.getId(), playlist.getName(), playlist.getUser().getUsername());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long id) {
        if (playlistService.playlistExists(id)) {
            playlistService.deletePlaylist(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}