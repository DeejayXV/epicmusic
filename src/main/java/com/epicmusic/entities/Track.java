package com.epicmusic.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private int duration;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;
}