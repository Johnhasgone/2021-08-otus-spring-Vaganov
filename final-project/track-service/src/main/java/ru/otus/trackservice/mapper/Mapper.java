package ru.otus.trackservice.mapper;

public interface Mapper<E, D>{
    D toDto(E entity);

    E toEntity(D dto);
}
