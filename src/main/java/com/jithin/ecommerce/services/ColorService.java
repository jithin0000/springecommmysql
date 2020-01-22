package com.jithin.ecommerce.services;

import com.jithin.ecommerce.model.Category;
import com.jithin.ecommerce.model.Color;
import com.jithin.ecommerce.repository.ColorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ColorService extends BaseService<ColorRepository, Color> {
    protected ColorService(ColorRepository repository) {
        super(repository);
    }

    @Override
    public Color update(Long id, Color body) {
        return null;
    }

    public Page<Color> paginatedColorList(int page, int size, String sort, String search){

        Page<Color> colors = null;
        if (!StringUtils.isEmpty(search))
        {
            colors = getRepository().findByNameContainingIgnoreCase(search, PageRequest.of(page, size, Sort.by(sort)));
        }else {

            colors = getRepository().findAll(PageRequest.of(page, size, Sort.by(sort)));
        }

        return colors;

    }
}
