package com.worldoffers.spark;

/**
 * Enum to encapsulate the status of the {@link OfferResponse}.
 */
public enum OfferResponseStatus {
    OK("Success"),
    ERROR("Error");

    final private String status;

    OfferResponseStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
