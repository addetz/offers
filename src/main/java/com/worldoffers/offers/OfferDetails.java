package com.worldoffers.offers;

import com.worldoffers.validation.DetailsValidationContext;

import java.time.LocalDateTime;

/**
 * Helper class to encapsulate the offer details of a {@link Offer}.
 *
 * @author addetz
 */
public class OfferDetails {

    private final LocalDateTime startDate;
    private final int offerLengthDays;
    private final int offerLengthHours;

    //Only non final field as offer status needs to be changed.
    private OfferStatus status;

    public OfferDetails(LocalDateTime startDate, int offerLengthDays,
                        int offerLengthHours) {
        this.startDate = startDate;
        this.offerLengthDays = offerLengthDays;
        this.offerLengthHours = offerLengthHours;
        this.status = OfferStatus.ACTIVE;
    }

    /**
     * @return start date and time of the offer
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * @return number of days the offer will be active
     */
    public int getOfferLengthDays() {
        return offerLengthDays;
    }

    /**
     * @return number of hours the offer will be active
     */
    public int getOfferLengthHours() {
        return offerLengthHours;
    }

    /**
     * @return {@link OfferStatus} of the offer
     */
    public OfferStatus getStatus() {
        return status;
    }

    /**
     * Update offer status.
     */
    public void setStatus(OfferStatus status) {
        this.status = status;
    }


    /**
     * Copies the current instance
     */
    public OfferDetails copy() {
        return new OfferDetails(startDate, offerLengthDays, offerLengthHours);
    }
}

