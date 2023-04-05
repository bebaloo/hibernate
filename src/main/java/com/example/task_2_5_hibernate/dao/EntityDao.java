package com.example.task_2_5_hibernate.dao;

import java.util.List;
import java.util.Optional;

public interface EntityDao<E, K> {
    int BATCH_SIZE = 100;
    List<E> findAll();

    Optional<E> findById(K id);

    E update(E entity);

    void delete(K id);

    void create(E entity);

    void createAll(List<E> entities);
}
