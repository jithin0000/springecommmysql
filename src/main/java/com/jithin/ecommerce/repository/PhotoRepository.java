package com.jithin.ecommerce.repository;

import com.jithin.ecommerce.model.Photo;
import org.springframework.data.repository.CrudRepository;

public interface PhotoRepository extends CrudRepository<Photo, Long> {
}
