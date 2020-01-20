package com.jithin.ecommerce.services;

import com.jithin.ecommerce.exception.CartNotFoundException;
import com.jithin.ecommerce.model.Cart;
import com.jithin.ecommerce.repository.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService extends BaseService<CartRepository, Cart> {
    protected CartService(CartRepository repository) {
        super(repository);
    }

    @Override
    public Cart update(Long id, Cart body) {

        Cart dbCart = getRepository().findById(id).orElseThrow(() -> new CartNotFoundException(id));

        dbCart.setProducts(body.getProducts());

        return getRepository().save(dbCart);
    }
}
