package com.worldoffers.validation;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for {@link DetailsValidationContext}.
 */
public class TestDetailsValidationContext {

    private static final String VALIDATION_ERROR = "This is an error.";
    private static final String OTHER_VALIDATION_ERROR = "This is another error.";
    private DetailsValidationContext context;
    private DetailsValidationContext otherContext;

    @Before
    public void setUp() {
        context = new DetailsValidationContext();
        otherContext = new DetailsValidationContext();
    }

    /**
     * Test a newly valid context returns that it is valid.
     */
    @Test
    public void testValidContext() {
        assertTrue("New context should be valid", context.isValid());
        assertTrue("Error list should be empty.", context.getErrors().isEmpty());
    }

    /**
     * Test adding an error to a context is added successfully and the context becomes invalid.
     */
    @Test
    public void testInvalidContext() {
        context.addError(VALIDATION_ERROR);

        assertFalse("Context should be invalid", context.isValid());
        assertEquals("Error should be contained in the list",
                VALIDATION_ERROR, context.getErrors());
    }

    /**
     * Test adding two errors to a context concatenates successfully and the context becomes invalid.
     */
    @Test
    public void testInvalidContextTwoErrors() {
        context.addError(VALIDATION_ERROR);
        context.addError(OTHER_VALIDATION_ERROR);

        assertFalse("Context should be invalid", context.isValid());
        assertEquals("Error should be contained in the list",
                VALIDATION_ERROR.concat(",").concat(OTHER_VALIDATION_ERROR), context.getErrors());
    }

    /**
     * Test consolidating 2 invalid {@link DetailsValidationContext} concatenates successfully.
     */
    @Test
    public void testInvalidContextTwoContexts() {
        context.addError(VALIDATION_ERROR);
        otherContext.addError(OTHER_VALIDATION_ERROR);

        assertFalse("Context should be invalid", context.isValid());
        assertEquals("Error should be contained in the list",
                VALIDATION_ERROR.concat(";").concat(OTHER_VALIDATION_ERROR), context.consolidateErrors(otherContext));
    }

    /**
     * Test consolidating one invalid and other valid {@link DetailsValidationContext} concatenates successfully.
     */
    @Test
    public void testConsolidatingTwoContextsOtherValid() {
        context.addError(VALIDATION_ERROR);

        assertFalse("Context should be invalid", context.isValid());
        assertEquals("Error should be contained in the list",
                VALIDATION_ERROR, context.consolidateErrors(otherContext));
    }

    /**
     * Test consolidating one valid  and other invalid {@link DetailsValidationContext} concatenates successfully.
     */
    @Test
    public void testConsolidatingTwoContextsOneValid() {
        otherContext.addError(VALIDATION_ERROR);

        assertFalse("Context should be invalid", otherContext.isValid());
        assertEquals("Error should be contained in the list",
                VALIDATION_ERROR, context.consolidateErrors(otherContext));
    }
}