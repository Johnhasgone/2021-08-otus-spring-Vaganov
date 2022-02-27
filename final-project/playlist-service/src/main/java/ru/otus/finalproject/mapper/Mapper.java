package ru.otus.finalproject.mapper;

public interface Mapper<E, D>{
    D toDto(E entity);

    E toEntity(D dto);
}
