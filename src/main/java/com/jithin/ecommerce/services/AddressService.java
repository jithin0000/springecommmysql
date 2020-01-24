package com.jithin.ecommerce.services;

import com.jithin.ecommerce.model.Address;
import com.jithin.ecommerce.repository.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService extends BaseService<AddressRepository, Address> {
    protected AddressService(AddressRepository repository) {
        super(repository);
    }

    @Override
    public Address update(Long id, Address body) {
        return null;
    }
}
