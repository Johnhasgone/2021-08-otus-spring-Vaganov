package ru.otus.trackservice.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "track")
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long trackId;

    @Column
    private Long albumId;

    @Column
    private Long artistId;


    @ManyToMany
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(
            name = "track_genre",
            joinColumns = {@JoinColumn(name = "track_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")}
    )
    private List<Genre> genres;

}
