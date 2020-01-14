package com.jithin.ecommerce.services;

import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public abstract class BaseService<T extends CrudRepository<E, Long>, E> {

    private final T repository;

    protected BaseService(T repository) {
        this.repository = repository;
    }

    public T getRepository() {
        return repository;
    }

    public Iterable<E> getAll(){
        return repository.findAll();
    }

    public Optional<E> get(Long id){
        return repository.findById(id);
    }

    public E create(E body){
        return repository.save(body);
    }

    public String delete(Long id){
        repository.deleteById(id);
        return "deleted";
    }

    public abstract E update(Long id, E body);


}
