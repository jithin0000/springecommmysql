package com.jithin.ecommerce.controller;

import com.jithin.ecommerce.dto.DeleteResponseDto;
import com.jithin.ecommerce.exception.AddressNotFoundException;
import com.jithin.ecommerce.model.Address;
import com.jithin.ecommerce.model.User;
import com.jithin.ecommerce.services.AddressService;
import com.jithin.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.security.Principal;

import static com.jithin.ecommerce.utils.constants.API_BASE;

@RestController
@RequestMapping(API_BASE + "/address")
public class AddressController {
    @Autowired
    private AddressService addressService;
    @Autowired
    private UserService userService;


    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(addressService.getAll());
    }


    @PostMapping("/new")
    public ResponseEntity<Address> createAddress(@Valid @RequestBody Address address, Principal principal) {

        if (principal != null) {
            String username = principal.getName();
            User user = userService.findByUserName(username);
            if (user == null) {
                throw new UsernameNotFoundException("invalid user");
            }
            address.setUser(user);
        }

        return new ResponseEntity<>(addressService.create(address), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddress(@PathVariable Long id) {
        Address address = addressService.get(id).orElseThrow(() -> new AddressNotFoundException(id));
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long id) {
        DeleteResponseDto message = addressService.delete(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @Valid @RequestBody Address body) {
        return new ResponseEntity<>(addressService.update(id, body), HttpStatus.OK);
    }

}
