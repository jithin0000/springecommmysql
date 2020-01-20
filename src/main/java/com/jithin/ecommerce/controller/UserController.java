package com.jithin.ecommerce.controller;

import com.jithin.ecommerce.dto.LoginRequest;
import com.jithin.ecommerce.dto.TokenResponse;
import com.jithin.ecommerce.model.User;
import com.jithin.ecommerce.security.JwtTokenProvider;
import com.jithin.ecommerce.services.UserService;
import com.jithin.ecommerce.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.jithin.ecommerce.utils.constants.API_BASE;

@RestController
@RequestMapping(API_BASE + "/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult bindingResult) {

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage().toString());
        }


        return new ResponseEntity(userService.RegisterUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtTokenProvider.generatingToken(authentication);
        return ResponseEntity.ok(new TokenResponse(true, jwtToken));
    }
}
