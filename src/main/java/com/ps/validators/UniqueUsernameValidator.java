package com.ps.validators;


import com.ps.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by samchu on 2017/2/9.
 */
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {

    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        // If the repository is null then return null
        if (accountRepository == null) {
            return true;
        }
        // Check if the username is unique
        return accountRepository.findByUsername(username) == null;
    }
}