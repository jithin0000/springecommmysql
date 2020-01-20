package com.jithin.ecommerce.validator;

import com.jithin.ecommerce.model.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        User user = (User) o;

        if (user.getPassword().length() <= 4) {
            errors.rejectValue("password","Length","password must at lease 4 character");
        }
    }
}
