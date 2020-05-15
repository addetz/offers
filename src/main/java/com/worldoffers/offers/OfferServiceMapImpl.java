package com.worldoffers.offers;

import com.worldoffers.WorldOffersMessages;
import com.worldoffers.validation.DetailsValidationContext;
import com.worldoffers.validation.DetailsValidationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Map implementation of {@link OfferService}
 *
 * @author addetz
 */
public class OfferServiceMapImpl implements OfferService {

    private static final long MINIMUM_OFFER_NUMBER = 10000000;
    private static final long MAXIMUM_OFFER_NUMBER = 99999999;

    //  Need to protect against bombarding create offer calls, while staying fast on read calls
    private final Map<Long, Offer> offersList= new ConcurrentHashMap<>();
    private final DetailsValidationService detailsValidationService = new DetailsValidationService();

    private static OfferServiceMapImpl instance = null;

    private OfferServiceMapImpl() {
        // Exists only to defeat instantiation.
    }

    public static OfferServiceMapImpl getInstance() {
        if(instance == null) {
            instance = new OfferServiceMapImpl();
        }
        return instance;
    }

    /**
     * @return the full list of offers
     */
    @Override
    public List<Long> getOffers() {
        return new ArrayList<>(offersList.keySet());
    }

    /**
     * Bulk remove all offers.
     */
    public void clearOffers() {
        offersList.clear();
    }

    /**
     * @param offerId to fetch on
     * @return corresponding optional {@link Offer}
     */
    @Override
    public Optional<Offer> getOffer(long offerId) {
        return offersList.containsKey(offerId) ? Optional.of(offersList.get(offerId)) : Optional.empty();
    }

    /**
     * Generates an offerId and creates a new offer.
     * @return the offerId of the newly created {@link Offer}
     * @throws IllegalArgumentException for invalid parameters
     */
    @Override
    public long createOffer(GoodsDetails goodsDetails, OfferDetails offerDetails) {
        //Firstly validate the constructed goods and offer details
        DetailsValidationContext goodsDetailsValidationContext = detailsValidationService.validate(goodsDetails);
        DetailsValidationContext offerDetailsValidationContext = detailsValidationService.validate(offerDetails);

        if(!goodsDetailsValidationContext.isValid() || !offerDetailsValidationContext.isValid()) {
            throw new IllegalArgumentException(goodsDetailsValidationContext.consolidateErrors(offerDetailsValidationContext));
        }

        //If all is valid, generate id and insert
        Offer newOffer = new Offer(generateOfferId(), goodsDetails, offerDetails);
        offersList.putIfAbsent(newOffer.getOfferId(), newOffer);
        return newOffer.getOfferId();
    }



    /**
     * Cancels an existing offer.
     * @param offerId id of the offer to cancel
     * @throws IllegalArgumentException if the id provided does not exist
     */
    @Override
    public void cancelOffer(long offerId) {
        updateOfferStatus(offerId, OfferStatus.CANCELLED);
    }

    /**
     * Helper method to safely update the {@link OfferStatus} of an {@link Offer}.
     * @param offerId id of the offer to expire
     * @param newStatus the new status of the offer
     * @throws IllegalArgumentException if the id provided does not exist
     */
    private void updateOfferStatus(long offerId, OfferStatus newStatus) {
        if(!offersList.containsKey(offerId)) {
            throw new IllegalArgumentException(String.format(WorldOffersMessages.INVALID_OFFER_ID, String.valueOf(offerId)));
        }

        if(offersList.get(offerId).getOfferDetail().getStatus() == OfferStatus.EXPIRED) {
            throw new IllegalArgumentException(String.format(WorldOffersMessages.OFFER_ALREADY_EXPIRED, String.valueOf(offerId)));
        }
        offersList.computeIfPresent(offerId, (key, value) -> value.updateOfferStatus(newStatus));
    }

    /**
     * @return a randomly generated 8 digit offer id
     */
    private long generateOfferId() {
        return ThreadLocalRandom.current().nextLong(MINIMUM_OFFER_NUMBER, MAXIMUM_OFFER_NUMBER);
    }

    /**
     * Expires an existing offer.
     * @param offerId id of the offer to expire
     * @throws IllegalArgumentException if the id provided does not exist
     */
    @Override
    public void expireOffer(long offerId) {
        updateOfferStatus(offerId,OfferStatus.EXPIRED);

    }
}

