package com.example.task_2_5_hibernate.dao;

import java.util.List;

public interface EntityDao<E, K> {
    int BATCH_SIZE = 100;
    List<E> findAll();

    E findById(K id);

    E update(E entity);

    void delete(K id);

    void create(E entity);

    void createAll(List<E> entities);
}
