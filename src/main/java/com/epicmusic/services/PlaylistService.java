package com.epicmusic.services;

import com.epicmusic.dto.PlaylistDTO;
import com.epicmusic.entities.Playlist;
import com.epicmusic.exception.ResourceNotFoundException;
import com.epicmusic.repositories.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    public Playlist createPlaylist(PlaylistDTO playlistDTO) {
        Playlist playlist = new Playlist();
        playlist.setName(playlistDTO.getName());
        return playlistRepository.save(playlist);
    }

    public void deletePlaylist(Long id) {
        if (!playlistRepository.existsById(id)) {
            throw new ResourceNotFoundException("Playlist with id " + id + " not found");
        }
        playlistRepository.deleteById(id);
    }

    public boolean playlistExists(Long id) {
        return playlistRepository.existsById(id);
    }
}
