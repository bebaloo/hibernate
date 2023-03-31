package com.example.task_2_5_hibernate.dao;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EntityDao<E, K> {
    int BATCH_SIZE = 100;
    List<E> findAll();

    Optional<E> findById(K id);

    E update(E entity);

    boolean delete(K id);

    E create(E entity);

    @Transactional
    int[][] createAll(List<E> entities);
}
