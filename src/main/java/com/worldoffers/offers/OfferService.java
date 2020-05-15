package com.worldoffers.offers;

import java.util.List;
import java.util.Optional;

/**
 * Service for getting, adding and cancelling offers.
 *
 * @author addetz
 */
public interface OfferService {

    /**
     * @return the full list of offers
     */
    List<Long> getOffers();

    /**
     * @param offerId to fetch on
     * @return corresponding optional {@link Offer}
     */
    Optional<Offer> getOffer(long offerId);

    /**
     * Generates an offerId and creates a new offer.
     * @return the offerId of the newly created {@link Offer}
     * @throws IllegalArgumentException for invalid parameters
     */
    long createOffer(GoodsDetails goodsDetails, OfferDetails offerDetails);

    /**
     * Cancels an existing offer.
     * @param offerId id of the offer to cancel
     * @throws IllegalArgumentException if the id provided does not exist
     */
    void cancelOffer(long offerId);

    /**
     * Expires an existing offer.
     * @param offerId id of the offer to expire
     * @throws IllegalArgumentException if the id provided does not exist
     */
    void expireOffer(long offerId);
}
