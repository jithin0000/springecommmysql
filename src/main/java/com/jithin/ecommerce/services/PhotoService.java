package com.jithin.ecommerce.services;

import com.jithin.ecommerce.model.Photo;
import com.jithin.ecommerce.repository.PhotoRepository;
import org.springframework.stereotype.Service;

@Service
public class PhotoService extends BaseService<PhotoRepository, Photo> {
    protected PhotoService(PhotoRepository repository) {
        super(repository);
    }

    @Override
    public Photo update(Long id, Photo body) {
        return null;
    }
}
