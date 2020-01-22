package com.jithin.ecommerce.services;

import com.jithin.ecommerce.model.Color;
import com.jithin.ecommerce.repository.ColorRepository;
import org.springframework.stereotype.Service;

@Service
public class ColorService extends BaseService<ColorRepository, Color> {
    protected ColorService(ColorRepository repository) {
        super(repository);
    }

    @Override
    public Color update(Long id, Color body) {
        return null;
    }
}
