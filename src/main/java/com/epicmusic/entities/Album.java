package com.epicmusic.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String artist;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private List<Track> tracks;

}