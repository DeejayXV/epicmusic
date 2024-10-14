package com.epicmusic.controllers;

import com.epicmusic.entities.Track;
import com.epicmusic.services.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tracks")
public class TrackController {

    @Autowired
    private TrackService trackService;

    @GetMapping
    public List<Track> getAllTracks() {
        return trackService.getAllTracks();
    }

    @PostMapping
    public Track createTrack(@RequestBody Track track) {
        return trackService.createTrack(track);
    }

    @DeleteMapping("/{id}")
    public void deleteTrack(@PathVariable Long id) {
        trackService.deleteTrack(id);
    }
}
