package com.techyourchance.unittestingfundamentals.exercise1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class NegativeNumberValidatorTest {
    NegativeNumberValidator negativeNumberValidator;

    @Before
    public void setUp(){
        negativeNumberValidator = new NegativeNumberValidator();
    }

    @Test
    public void testNegativeNumberValidatorWithPositiveNumber(){
        boolean validationResult = negativeNumberValidator.isNegative(1);
        Assert.assertThat(validationResult ,is(false));
    }

    @Test
    public void testNegativeNumberValidatorWithZero(){
        boolean validationResult = negativeNumberValidator.isNegative(0);
        Assert.assertThat(validationResult ,is(false));
    }

    @Test
    public void testNegativeNumberValidatorWithNegativeNumber(){
        boolean validationResult = negativeNumberValidator.isNegative(-1);
        Assert.assertThat(validationResult ,is(true));
    }
}