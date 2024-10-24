//package com.epicmusic.controllers;
//
//import com.epicmusic.dto.TrackDTO;
//import com.epicmusic.dto.TrackResponseDTO;
//import com.epicmusic.entities.Track;
//import com.epicmusic.services.TrackService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/tracks")
//public class TrackController {
//
//    @Autowired
//    private TrackService trackService;
//
//    @GetMapping
//    public List<TrackResponseDTO> getAllTracks() {
//        return trackService.getAllTracks().stream()
//                .map(track -> new TrackResponseDTO(track.getId(), track.getTitle(), track.getArtist(), track.getAlbum()))
//                .collect(Collectors.toList());
//    }
//
//    @PostMapping
//    public TrackResponseDTO createTrack(@RequestBody TrackDTO trackDTO) {
//        Track track = trackService.createTrack(trackDTO);
//        return new TrackResponseDTO(track.getId(), track.getTitle(), track.getArtist(), track.getAlbum());
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteTrack(@PathVariable Long id) {
//        if (trackService.trackExists(id)) {
//            trackService.deleteTrack(id);
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//}