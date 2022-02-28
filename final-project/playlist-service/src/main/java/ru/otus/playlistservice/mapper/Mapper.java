package ru.otus.playlistservice.mapper;

public interface Mapper<E, D>{
    D toDto(E entity);

    E toEntity(D dto);
}
