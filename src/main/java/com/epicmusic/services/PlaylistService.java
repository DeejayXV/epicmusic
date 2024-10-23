package com.epicmusic.services;

import com.epicmusic.entities.Playlist;
import com.epicmusic.entities.User;
import com.epicmusic.repositories.PlaylistRepository;
import com.epicmusic.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private UserRepository userRepository;

    public Playlist createPlaylist(String username, String name, String description) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Playlist playlist = new Playlist();
        playlist.setUser(user);
        playlist.setName(name);
        playlist.setDescription(description);
        return playlistRepository.save(playlist);
    }

    public List<Playlist> getPlaylistsByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return playlistRepository.findByUser(user);
    }

    public Playlist updatePlaylist(Long playlistId, String name, String description) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        playlist.setName(name);
        playlist.setDescription(description);
        return playlistRepository.save(playlist);
    }

    public void deletePlaylist(Long playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        playlistRepository.delete(playlist);
    }
}
