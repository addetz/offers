package com.worldoffers.spark;

import com.google.gson.JsonElement;

/**
 * Class encapsulating the response of the the Spark Java routes.
 *
 * @author addetz
 */
public class OfferResponse {
    private OfferResponseStatus status;
    private String message;
    private JsonElement data;

    /**
     * Constructor for Options checks
     */
    public OfferResponse(OfferResponseStatus status) {
        this.status = status;
    }

    /**
     * Constructor for the error case.
     */
    public OfferResponse(OfferResponseStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * Constructor for the success case.
     */
    public OfferResponse(OfferResponseStatus status, JsonElement data) {
        this.status = status;
        this.data = data;
    }

    public OfferResponseStatus getStatus() {
        return status;
    }

    public void setStatus(OfferResponseStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }
}
