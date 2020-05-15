package com.worldoffers.offers;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link Offer}.
 */
public class TestOffer {

    /**
     * Test for offer cancelling.
     */
    @Test
    public void testUpdateOfferStatis() {
        long offerId = 12345L;
        GoodsDetails goodsDetails = mock(GoodsDetails.class);
        GoodsDetails goodsDetailsCopy = mock(GoodsDetails.class);
        OfferDetails offerDetails = mock(OfferDetails.class);
        OfferDetails offerDetailsCopy = mock(OfferDetails.class);
        when(goodsDetails.copy()).thenReturn(goodsDetailsCopy);
        when(offerDetails.copy()).thenReturn(offerDetailsCopy);

        Offer offer = new Offer(offerId, goodsDetails, offerDetails);
        Offer cancelledOffer = offer.updateOfferStatus(OfferStatus.CANCELLED);

        assertEquals("Goods details copy should be referenced", goodsDetailsCopy, cancelledOffer.getGoodsDetail());
        assertEquals("Offer details copy should be referenced", offerDetailsCopy, cancelledOffer.getOfferDetail());
        verify(offerDetailsCopy).setStatus(OfferStatus.CANCELLED);
    }

}