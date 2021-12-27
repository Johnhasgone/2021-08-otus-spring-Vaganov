package ru.otus.spring12homework.mapper;

public interface Mapper<E, D> {
    D toDto(E entity);
    E toEntity(D dto);
}
