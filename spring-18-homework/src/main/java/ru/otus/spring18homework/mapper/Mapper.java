package ru.otus.spring18homework.mapper;

public interface Mapper<E, D> {
    D toDto(E entity);
    E toEntity(D dto);
}
