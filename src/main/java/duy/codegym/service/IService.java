package duy.codegym.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IService<T> {
    Iterable<T> findAll();
    Optional<T> findById(Long id);
    void save(T t);

    void remove(T t);
    Page<T> findAllByNameContaining(String name, Pageable pageable);
    Page<T> findAll(Pageable pageable);
    List<T> findByCategory(Long id);
}
