package com.worldoffers.offers;

import org.joda.money.CurrencyUnit;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for {@link OfferServiceMapImpl}
 */
public class TestOfferServiceMapImpl {

    private static final String OFFER_DESCRIPTION = "This is my new good.";
    private static final String CURRENCY_CODE = CurrencyUnit.GBP.getCurrencyCode();
    private static final BigDecimal PRICE = BigDecimal.valueOf(150);
    private static final LocalDateTime START_DATE = LocalDateTime.of(2018, 5, 10, 14, 10);
    private static final int OFFER_LENGTH_DAYS = 10;
    private static final int OFFER_LENGTH_HOURS = 15;

    private OfferServiceMapImpl offerServiceMapImpl;
    private GoodsDetails goodsDetails;
    private OfferDetails offerDetails;

    @Before
    public void setUp() {
        goodsDetails = new GoodsDetails(OFFER_DESCRIPTION, CURRENCY_CODE, PRICE);
        offerDetails = new OfferDetails(START_DATE, OFFER_LENGTH_DAYS, OFFER_LENGTH_HOURS);
        offerServiceMapImpl = OfferServiceMapImpl.getInstance();
        offerServiceMapImpl.clearOffers();
    }

    /**
     * Tests fetching and empty offers list does not throw any errors.
     */
    @Test
    public void testGetEmptyOffersList() {
        assertEquals("Offers list should be empty.", 0, offerServiceMapImpl.getOffers().size());
    }


    /**
     * Test creating a new {@link Offer}.
     */
    @Test
    public void testCreateValidOffer() {
        offerServiceMapImpl.createOffer(goodsDetails, offerDetails);
        assertEquals("One offer should have been created.", 1, offerServiceMapImpl.getOffers().size());
    }

    /**
     * Test creating a new invalid {@link Offer} throws an error.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateInvalidOfferDetails() {
        OfferDetails invalidOffer = new OfferDetails(null, OFFER_LENGTH_DAYS, OFFER_LENGTH_HOURS);
        offerServiceMapImpl.createOffer(goodsDetails, invalidOffer);
    }

    /**
     * Test creating a new invalid {@link Offer} throws an error.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateInvalidGoodsDetails() {
        GoodsDetails invalidGoodsDetails = new GoodsDetails(null, CURRENCY_CODE, PRICE);
        offerServiceMapImpl.createOffer(invalidGoodsDetails, offerDetails);
    }

    /**
     * Test fetching an existing offer.
     */
    @Test
    public void testGetExistingOffer() {
        BigDecimal newPrice =  PRICE.add(PRICE);
        LocalDateTime newStartDate = START_DATE.plusMonths(1);
        GoodsDetails newGoodsDetails = new GoodsDetails(OFFER_DESCRIPTION, CURRENCY_CODE, newPrice);
        OfferDetails newOfferDetails = new OfferDetails(newStartDate, OFFER_LENGTH_DAYS, OFFER_LENGTH_HOURS);
        offerServiceMapImpl.createOffer(goodsDetails, offerDetails);
        long newOfferId = offerServiceMapImpl.createOffer(newGoodsDetails, newOfferDetails);
        Optional<Offer> fetchedOffer = offerServiceMapImpl.getOffer(newOfferId);

        assertTrue("Fetched offer should have been found", fetchedOffer.isPresent());
        assertEquals("Incorrect price for fetched offer", newPrice, fetchedOffer.get().getGoodsDetail().getPrice());
        assertEquals("Incorrect startDate for fetched offer", newStartDate, fetchedOffer.get().getOfferDetail().getStartDate());

    }

    /**
     * Tests that fetching an inexistent id will return empty and not fail.
     */
    @Test
    public void testGetNonExistentOffer() {
        assertEquals("Empty optional should have been found", Optional.empty(), offerServiceMapImpl.getOffer(123L));
    }

    /**
     * Tests that canceling an inexistent id will throw an error.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCancelNonExistentOffer() {
        offerServiceMapImpl.cancelOffer(123L);
    }

    /**
     * Tests that canceling an inexistent id will throw an error.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExpireNonExistentOffer() {
        offerServiceMapImpl.expireOffer(123L);
    }

    /**
     * Tests canceling a valid {@link Offer}.
     */
    @Test
    public void testCancelOffer() {
        long offerId = offerServiceMapImpl.createOffer(goodsDetails, offerDetails);
        offerServiceMapImpl.cancelOffer(offerId);

        Optional<Offer> cancelledOffer = offerServiceMapImpl.getOffer(offerId);
        assertTrue("Fetched offer should have been found", cancelledOffer.isPresent());
        assertEquals("Offer should be cancelled", OfferStatus.CANCELLED, cancelledOffer.get().getOfferDetail().getStatus());
        assertEquals("Start date should be unchanged", START_DATE, cancelledOffer.get().getOfferDetail().getStartDate());
        assertEquals("Offer length should be unchanged", OFFER_LENGTH_DAYS, cancelledOffer.get().getOfferDetail().getOfferLengthDays());
        assertEquals("Offer length should be unchanged", OFFER_LENGTH_HOURS, cancelledOffer.get().getOfferDetail().getOfferLengthHours());
        assertEquals("Goods description should be unchanged", OFFER_DESCRIPTION, cancelledOffer.get().getGoodsDetail().getDescription());
        assertEquals("Goods currency should be unchanged", CURRENCY_CODE, cancelledOffer.get().getGoodsDetail().getCurrencyCode());
        assertEquals("Goods price should be unchanged", PRICE, cancelledOffer.get().getGoodsDetail().getPrice());

    }

    /**
     * Tests canceling an expired {@link Offer} throws an error.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCancelOfferAlreadyExpired() {
        long offerId = offerServiceMapImpl.createOffer(goodsDetails, offerDetails);
        offerServiceMapImpl.expireOffer(offerId);
        offerServiceMapImpl.cancelOffer(offerId);
    }

    /**
     * Tests expiring a valid {@link Offer}.
     */
    @Test
    public void testExpireOffer() {
        long offerId = offerServiceMapImpl.createOffer(goodsDetails, offerDetails);
        offerServiceMapImpl.expireOffer(offerId);

        Optional<Offer> expiredOffer = offerServiceMapImpl.getOffer(offerId);
        assertTrue("Fetched offer should have been found", expiredOffer.isPresent());
        assertEquals("Offer should be cancelled", OfferStatus.EXPIRED, expiredOffer.get().getOfferDetail().getStatus());
        assertEquals("Start date should be unchanged", START_DATE, expiredOffer.get().getOfferDetail().getStartDate());
        assertEquals("Offer length should be unchanged", OFFER_LENGTH_DAYS, expiredOffer.get().getOfferDetail().getOfferLengthDays());
        assertEquals("Offer length should be unchanged", OFFER_LENGTH_HOURS, expiredOffer.get().getOfferDetail().getOfferLengthHours());
        assertEquals("Goods description should be unchanged", OFFER_DESCRIPTION, expiredOffer.get().getGoodsDetail().getDescription());
        assertEquals("Goods currency should be unchanged", CURRENCY_CODE, expiredOffer.get().getGoodsDetail().getCurrencyCode());
        assertEquals("Goods price should be unchanged", PRICE, expiredOffer.get().getGoodsDetail().getPrice());

    }
}
