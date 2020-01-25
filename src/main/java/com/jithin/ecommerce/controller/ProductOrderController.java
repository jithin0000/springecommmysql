package com.jithin.ecommerce.controller;

import com.jithin.ecommerce.dto.DeleteResponseDto;
import com.jithin.ecommerce.dto.OrderRequestDto;
import com.jithin.ecommerce.exception.AddressNotFoundException;
import com.jithin.ecommerce.exception.ProductOrderNotFoundException;
import com.jithin.ecommerce.model.Address;
import com.jithin.ecommerce.model.ProductOrder;
import com.jithin.ecommerce.model.User;
import com.jithin.ecommerce.services.AddressService;
import com.jithin.ecommerce.services.BaseService;
import com.jithin.ecommerce.services.ProductOrderService;
import com.jithin.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

import static com.jithin.ecommerce.utils.constants.API_BASE;

@RestController
@RequestMapping(API_BASE + "/order")
public class ProductOrderController {
    @Autowired
    private ProductOrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;


    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }


    @PostMapping("/new")
    public ResponseEntity<ProductOrder> createProductOrder(@Valid @RequestBody OrderRequestDto order, Principal principal) {

        Address address = addressService.get(order.getAddress_id())
                .orElseThrow(() -> new AddressNotFoundException(order.getAddress_id()));



        ProductOrder productOrder = new ProductOrder();

        productOrder.setOrderNumber(order.getOrderNumber());
        productOrder.setOrderTotal(order.getOrderTotal());
        productOrder.setAddress(address);

            String username = principal.getName();
            User user = userService.findByUserName(username);
            if (user == null) {
                throw new UsernameNotFoundException("invalid user");
            }
            productOrder.setUser(user);

        return new ResponseEntity<>(orderService.create(productOrder), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductOrder> getProductOrder(@PathVariable Long id) {
        ProductOrder order = orderService.get(id).orElseThrow(() -> new ProductOrderNotFoundException(id));
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProductOrder(@PathVariable Long id) {
        DeleteResponseDto message = orderService.delete(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductOrder> updateProductOrder(@PathVariable Long id, @Valid @RequestBody ProductOrder body) {
        return new ResponseEntity<>(orderService.update(id, body), HttpStatus.OK);
    }

}
