package com.worldoffers.offers;

import org.joda.money.CurrencyUnit;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Test class for {@link OfferExpiryService}.
 */
public class TestOfferExpiryService {

    private static final int OFFER_LENGTH_DAYS = 10;
    private static final int OFFER_LENGTH_HOURS = 8;

    /**
     * Test that offer expiry works as expected.
     */
    @Test
    public void testExpireOffers() {
        OfferService offerService = OfferServiceMapImpl.getInstance();
        OfferExpiryService expiryService = new OfferExpiryService();

        LocalDateTime currentTime = LocalDateTime.now();
        GoodsDetails goodsDetails = new GoodsDetails("Description", CurrencyUnit.GBP.getCurrencyCode(), BigDecimal.valueOf(1000));
        OfferDetails activeOfferDaysDetails = new OfferDetails(currentTime.minusDays(OFFER_LENGTH_DAYS/2),
                OFFER_LENGTH_DAYS, OFFER_LENGTH_HOURS);
        OfferDetails activeOfferHoursDetails = new OfferDetails(currentTime.minusHours(OFFER_LENGTH_HOURS/2),
                0, OFFER_LENGTH_HOURS);
        OfferDetails expiredOfferDetails = new OfferDetails(currentTime.minusDays(OFFER_LENGTH_DAYS * 2),
                OFFER_LENGTH_DAYS, OFFER_LENGTH_HOURS);
        OfferDetails cancelledOfferDetails = new OfferDetails(currentTime.minusHours(OFFER_LENGTH_HOURS + 2),
                OFFER_LENGTH_DAYS, OFFER_LENGTH_HOURS);
        long activeOfferDaysId = offerService.createOffer(goodsDetails, activeOfferDaysDetails);
        long activeOfferHoursId = offerService.createOffer(goodsDetails, activeOfferHoursDetails);
        long expiredOfferId = offerService.createOffer(goodsDetails, expiredOfferDetails);
        long cancelledOfferId = offerService.createOffer(goodsDetails, cancelledOfferDetails);
        offerService.cancelOffer(cancelledOfferId);

        expiryService.expireOffers();

        assertTrue("Offer should be present.", offerService.getOffer(activeOfferDaysId).isPresent());
        assertEquals("Offer should have remained active.", OfferStatus.ACTIVE,
                offerService.getOffer(activeOfferDaysId).get().getOfferDetail().getStatus());
        assertTrue("Offer should be present.", offerService.getOffer(activeOfferHoursId).isPresent());
        assertEquals("Offer should have remained active.", OfferStatus.ACTIVE,
                offerService.getOffer(activeOfferHoursId).get().getOfferDetail().getStatus());
        assertTrue("Offer should be present.", offerService.getOffer(expiredOfferId).isPresent());
        assertEquals("Offer should have been expired.", OfferStatus.EXPIRED,
                offerService.getOffer(expiredOfferId).get().getOfferDetail().getStatus());
        assertTrue("Offer should be present.", offerService.getOffer(cancelledOfferId).isPresent());
        assertEquals("Offer should have remained cancelled.", OfferStatus.CANCELLED,
                offerService.getOffer(cancelledOfferId).get().getOfferDetail().getStatus());

    }

}