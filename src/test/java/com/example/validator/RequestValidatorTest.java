package com.example.validator;

import com.example.service.data.RequestValidator;
import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.*;

public class RequestValidatorTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    public void testValidatorWithNull() {
        boolean validate = RequestValidator.validate(null);
        assertTrue(!validate);

    }

    public void testValidatorWithInvalidObject() {
        String object = "";
        boolean validate = RequestValidator.validate(object);
        assertTrue(!validate);

    }
}