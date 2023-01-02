package com.github.szsalyi.globalblue.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class ValuesAllowedValidator implements ConstraintValidator<ValuesAllowed, BigDecimal> {

    @Value("${vat.values}")
    private String vatValues;

    private List<BigDecimal> expectedValues;

    private String returnMessage;

    @Override
    public void initialize(final ValuesAllowed requiredIfChecked) {
        expectedValues = Arrays.stream(vatValues.split(","))
                .map(BigDecimal::new)
                .toList();

        returnMessage = requiredIfChecked.message().concat(expectedValues.toString());
    }

    @Override
    public boolean isValid(final BigDecimal testValue, final ConstraintValidatorContext context) {
        boolean valid = expectedValues.contains(testValue);

        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(returnMessage)
                    .addConstraintViolation();
        }

        return valid;
    }
}