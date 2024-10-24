//package com.epicmusic.services;
//
//import com.epicmusic.dto.TrackDTO;
//import com.epicmusic.entities.Track;
//import com.epicmusic.exception.ResourceNotFoundException;
//import com.epicmusic.repositories.TrackRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class TrackService {
//
//    @Autowired
//    private TrackRepository trackRepository;
//
//    public List<Track> getAllTracks() {
//        return trackRepository.findAll();
//    }
//
//    public Track createTrack(TrackDTO trackDTO) {
//        Track track = new Track();
//        track.setTitle(trackDTO.getTitle());
//        track.setArtist(trackDTO.getArtist());
//        track.setAlbum(trackDTO.getAlbum());
//        return trackRepository.save(track);
//    }
//
//    public void deleteTrack(Long id) {
//        if (!trackRepository.existsById(id)) {
//            throw new ResourceNotFoundException("Track with id " + id + " not found");
//        }
//        trackRepository.deleteById(id);
//    }
//
//    public boolean trackExists(Long id) {
//        return trackRepository.existsById(id);
//    }
//}