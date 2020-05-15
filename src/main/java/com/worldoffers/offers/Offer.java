package com.worldoffers.offers;

/**
 * Immutable offer representation.
 *
 * @author addetz
 */
public class Offer {

    private final Long offerId;
    private final GoodsDetails goodsDetail;
    private final OfferDetails offerDetail;

    public Offer(Long offerId, GoodsDetails goodsDetail, OfferDetails offerDetail) {
        this.offerId = offerId;
        this.goodsDetail = goodsDetail;
        this.offerDetail = offerDetail;
    }

    /**
     * @return id of the offer
     */
    public Long getOfferId() {
        return offerId;
    }

    /**
     * @return {@link GoodsDetails} of the offer
     */
    public GoodsDetails getGoodsDetail() {
        return goodsDetail;
    }

    /**
     * @return {@link OfferDetails} of the offer
     */
    public OfferDetails getOfferDetail() {
        return offerDetail;
    }

    /**
     * @return a cancelled copy of this instance.
     */
    public Offer updateOfferStatus(OfferStatus newStatus) {
        GoodsDetails copiedGoodsDetails = goodsDetail.copy();
        OfferDetails copiedOfferDetails = offerDetail.copy();
        copiedOfferDetails.setStatus(newStatus);
        return new Offer(offerId, copiedGoodsDetails,copiedOfferDetails);
    }
}