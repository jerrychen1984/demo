package com.demo.example.utils;

import com.demo.example.data.service.exception.InvalidParamsException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.Set;

@Component
public class ObjectValidator implements InitializingBean {

    private Validator validator;

    @Override
    public void afterPropertiesSet() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public <T> void check(T object) throws InvalidParamsException {

        Set<ConstraintViolation<T>> constraintViolations =
                validator.validate(object);

        if (!constraintViolations.isEmpty()) {
            Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
            throw new InvalidParamsException(iterator.next().getMessage());
        }
    }

}
