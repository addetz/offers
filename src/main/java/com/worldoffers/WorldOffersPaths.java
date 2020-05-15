package com.worldoffers;

/**
 * Paths and
 */
public interface WorldOffersPaths {
    int PORT_NUMBER = 8080;
    String BASE_URI = "/worldoffers";
    String OFFERS_URI = BASE_URI + "/offers";
    String CANCEL_URI = BASE_URI + "/cancel";
    String OFFER_PARAM_ID = ":id";
    long OFFER_EXPIRY_PERIOD = 10000L;
    long OFFER_EXPIRY_DELAY = 5000L;
}
