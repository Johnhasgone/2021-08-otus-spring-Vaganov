package ru.otus.finalproject.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "playlist")
public class Playlist {
    @Id
    private Long id;

    @Column
    private String name;

    @ManyToMany
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(
            name = "playlist_track",
            joinColumns = {@JoinColumn(name = "playlist_id")},
            inverseJoinColumns = {@JoinColumn(name = "track_id")}
    )
    private List<Track> tracks;
}
