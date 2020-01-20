package com.jithin.ecommerce.controller;

import com.jithin.ecommerce.dto.AddUserToCart;
import com.jithin.ecommerce.dto.CartResponse;
import com.jithin.ecommerce.dto.DeleteResponseDto;
import com.jithin.ecommerce.exception.CartNotFoundException;
import com.jithin.ecommerce.exception.DepartmentNotFoundException;
import com.jithin.ecommerce.exception.InvalidLoginResponse;
import com.jithin.ecommerce.exception.ProductNotFoundException;
import com.jithin.ecommerce.model.Cart;
import com.jithin.ecommerce.model.Department;
import com.jithin.ecommerce.model.Product;
import com.jithin.ecommerce.model.User;
import com.jithin.ecommerce.services.CartService;
import com.jithin.ecommerce.services.DepartmentService;
import com.jithin.ecommerce.services.ProductService;
import com.jithin.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

import static com.jithin.ecommerce.utils.constants.API_BASE;

@RestController
@RequestMapping(API_BASE + "/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(cartService.getAll());
    }

    @GetMapping("/add/{cardId}/product/{productId}")
    public ResponseEntity<?> addToCart(@PathVariable Long cardId, @PathVariable Long productId) {
        Cart cart = cartService.get(cardId).orElseThrow(() -> new CartNotFoundException(cardId));
        Product product = productService.get(productId).orElseThrow(() -> new ProductNotFoundException(productId));

        CartResponse response = new CartResponse();

        if (cart.getProducts().isEmpty()) {
            cart.addToCart(product);
        } else {


            if (cart.getProducts().contains(product)) {
                cart.removeFromCart(product);
            } else {
                cart.addToCart(product);
            }
        }


        int total = 0;

        for (Product pdt : cart.getProducts()) {
            total += pdt.getPrice();
        }

        cart.setTotal(total);
        Cart updated = cartService.create(cart);


        return ResponseEntity.ok(updated);

    }

    @PostMapping("/new")
    public ResponseEntity<Cart> createCart(@Valid @RequestBody Cart cart) {
        return new ResponseEntity<>(cartService.create(cart), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCart(@PathVariable Long id) {
        Cart cart = cartService.get(id).orElseThrow(() -> new CartNotFoundException(id));
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable Long id) {
        DeleteResponseDto message = cartService.delete(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Cart> updateCart(@PathVariable Long id, @Valid @RequestBody Cart body) {
        return new ResponseEntity<>(cartService.update(id, body), HttpStatus.OK);
    }

    @PutMapping("/{id}/user")
    public ResponseEntity<?> addUserToCart(@PathVariable Long id, @Valid @RequestBody AddUserToCart userToCart) {
        User user = userService.getById(userToCart.getUserId());
        if (user == null) {
            return new ResponseEntity<>(new InvalidLoginResponse()  , HttpStatus.UNAUTHORIZED);
        }
        Cart cart = cartService.get(id).orElseThrow(() -> new CartNotFoundException(id));

        cart.setUser(user);
        return new ResponseEntity<>(cartService.create(cart), HttpStatus.OK);
    }
}
