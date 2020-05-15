package com.worldoffers.offers;

/**
 * Enum for the status of an {@link Offer}
 *
 * @author addetz
 */
public enum OfferStatus {
    /**
     * Offer is active
     */
    ACTIVE,
    /**
     * Offer has been automatically expired by the system
     */
    EXPIRED,
    /**
     * Offer has been cancelled by the user
     */
    CANCELLED
}

