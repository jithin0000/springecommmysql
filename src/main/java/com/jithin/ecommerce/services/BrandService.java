package com.jithin.ecommerce.services;

import com.jithin.ecommerce.exception.BrandNotFoundException;
import com.jithin.ecommerce.model.Brand;
import com.jithin.ecommerce.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BrandService extends BaseService<BrandRepository, Brand> {
    protected BrandService(BrandRepository repository) {
        super(repository);
    }

    @Override
    public Brand update(Long id, Brand body) {

        Brand dbBrand = getRepository().findById(id).orElseThrow(() -> new BrandNotFoundException(id));

        dbBrand.setName(body.getName());
//        dbBrand.setProducts(body.getProducts());
        dbBrand.setUpdatedAt(new Date());

        return getRepository().save(dbBrand);
    }
}
