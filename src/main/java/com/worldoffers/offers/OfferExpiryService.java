package com.worldoffers.offers;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for expiring {@link Offer}.
 */
public class OfferExpiryService {

    /**
     * Processes all offers and expires them if their offer period has expired.
     */
    public void expireOffers() {
        OfferService offerService = OfferServiceMapImpl.getInstance();
        List<Long> offerIdList = offerService.getOffers();
        offerIdList.stream()
                .map(offerService::getOffer)
                .filter(offer -> isOfferExpired(offer.get()))
                .forEach(offer -> offerService.expireOffer(offer.get().getOfferId()));
    }

    /**
     * Helper method to find active offers that need to be expired
     * @param offer to process
     * @return true if the offer should be expired
     */
    private boolean isOfferExpired(Offer offer) {
        OfferDetails offerDetail = offer.getOfferDetail();
        LocalDateTime offerExpiryTime = offerDetail.getStartDate()
                .plusDays(offerDetail.getOfferLengthDays())
                .plusHours(offerDetail.getOfferLengthHours());

        return offerDetail.getStatus() == OfferStatus.ACTIVE && offerExpiryTime.isBefore(LocalDateTime.now());
    }
}
