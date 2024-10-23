package com.epicmusic.repositories;

import com.epicmusic.entities.Playlist;
import com.epicmusic.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    List<Playlist> findByUser(User user);
}
