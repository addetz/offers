package com.worldoffers.validation;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A wrapper class which holds all the validation errors of a details object.
 */
public class DetailsValidationContext {

    private final List<String> validationErrors = new ArrayList<>();

    /**
     * @param error to add to the errors list
     */
    public void addError(String error) {
        validationErrors.add(error);
    }

    /**
     * @return the entire list of errors
     */
    public String getErrors() {
        return validationErrors.stream().collect(Collectors.joining( "," ));
    }

    /**
     * @return whether the context has errors or not
     */
    public boolean isValid() {
        return validationErrors.isEmpty();
    }

    /**
     * Helper method to consolidate between 2 {@link DetailsValidationContext} objects.
     */
    public String consolidateErrors(DetailsValidationContext otherValidationContext) {
        //If either context is valid then don't concatenate
        if (otherValidationContext.isValid()) {
            return this.getErrors();
        }

        if (this.isValid()) {
            return otherValidationContext.getErrors();
        }

        return this.getErrors() + ";" + otherValidationContext.getErrors();
    }
}

