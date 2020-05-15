package com.worldoffers.spark;

import com.worldoffers.offers.GoodsDetails;
import com.worldoffers.offers.OfferDetails;

/**
 * Wrapper class for details for offer creation.
 */
public class OfferWrapper {

    private final GoodsDetails goodsDetails;
    private final OfferDetails offerDetails;

    public OfferWrapper(GoodsDetails goodsDetails, OfferDetails offerDetails) {
        this.goodsDetails = goodsDetails;
        this.offerDetails = offerDetails;
    }

    public GoodsDetails getGoodsDetails() {
        return goodsDetails;
    }

    public OfferDetails getOfferDetails() {
        return offerDetails;
    }
}
