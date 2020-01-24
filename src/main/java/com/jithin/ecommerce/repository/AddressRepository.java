package com.jithin.ecommerce.repository;

import com.jithin.ecommerce.model.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
