package com.example.task_2_5_hibernate.service;

import java.util.List;

public interface EntityService<E, K> {
    List<E> getAll();

    E getById(K id);

    E update(E entity);

    E create(E entity);

    void createAll(List<E> entities);

    void delete(K id);
}
