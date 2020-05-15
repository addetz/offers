package com.worldoffers.offers;

import com.worldoffers.WorldOffersMessages;
import com.worldoffers.validation.DetailsValidationContext;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Test class for {@link OfferDetails}.
 */
public class TestOfferDetails {

    private static final LocalDateTime START_DATE = LocalDateTime.of(2018, 5, 10, 14, 10);
    private static final int OFFER_LENGTH_DAYS = 10;
    private static final int OFFER_LENGTH_HOURS = 15;

    /**
     * Tests creating, getting and validating a valid {@link OfferDetails}
     */
    @Test
    public void testValidDetails() {
        OfferDetails offerDetails = new OfferDetails(START_DATE, OFFER_LENGTH_DAYS, OFFER_LENGTH_HOURS);
        assertEquals("Startdate is incorrect.", START_DATE, offerDetails.getStartDate());
        assertEquals("Offer length days is incorrect.", OFFER_LENGTH_DAYS, offerDetails.getOfferLengthDays());
        assertEquals("Offer length hours is incorrect.", OFFER_LENGTH_HOURS, offerDetails.getOfferLengthHours());
        assertEquals("Offer status is incorrect.", OfferStatus.ACTIVE, offerDetails.getStatus());
    }

    /**
     * Tests copy {@link OfferDetails}
     */
    @Test
    public void testValidDetailsCopy() {
        OfferDetails offerDetails = new OfferDetails(START_DATE, OFFER_LENGTH_DAYS, OFFER_LENGTH_HOURS).copy();
        assertEquals("Startdate is incorrect.", START_DATE, offerDetails.getStartDate());
        assertEquals("Offer length days is incorrect.", OFFER_LENGTH_DAYS, offerDetails.getOfferLengthDays());
        assertEquals("Offer length hours is incorrect.", OFFER_LENGTH_HOURS, offerDetails.getOfferLengthHours());
        assertEquals("Offer status is incorrect.", OfferStatus.ACTIVE, offerDetails.getStatus());
    }
}