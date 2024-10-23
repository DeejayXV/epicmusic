package com.epicmusic.controllers;

import com.epicmusic.entities.Playlist;
import com.epicmusic.services.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @PostMapping
    public ResponseEntity<?> createPlaylist(@RequestBody Playlist playlist, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utente non autenticato");
        }
        Playlist createdPlaylist = playlistService.createPlaylist(principal.getName(), playlist.getName(), playlist.getDescription());
        return ResponseEntity.ok(createdPlaylist);
    }

    @GetMapping
    public ResponseEntity<?> getPlaylists(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utente non autenticato");
        }
        List<Playlist> playlists = playlistService.getPlaylistsByUser(principal.getName());
        return ResponseEntity.ok(playlists);
    }

    @PutMapping("/{playlistId}")
    public ResponseEntity<Playlist> updatePlaylist(@PathVariable Long playlistId, @RequestBody Playlist playlist) {
        Playlist updatedPlaylist = playlistService.updatePlaylist(playlistId, playlist.getName(), playlist.getDescription());
        return ResponseEntity.ok(updatedPlaylist);
    }

    @DeleteMapping("/{playlistId}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long playlistId) {
        playlistService.deletePlaylist(playlistId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
