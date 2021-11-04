package ru.otus.spring08homework.mapper;

public interface Mapper<E, D> {
    D toDto(E entity);
    E toEntity(D dto);
}
