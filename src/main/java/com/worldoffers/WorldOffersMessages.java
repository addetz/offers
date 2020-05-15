package com.worldoffers;

/**
 * List of error messages that the API can return.
 */
public interface WorldOffersMessages {
    String EMPTY_GOODS_DESCRIPTION = "Goods description cannot be empty.";
    String ILLEGAL_CURRENCY = "Provided currency %s is invalid.";
    String EMPTY_PRICE = "Offer price cannot be empty.";
    String NEGATIVE_PRICE = "Offer price must greater than 0.";
    String EMPTY_STARTDATE = "Offer start date cannot be empty.";
    String NEGATIVE_OFFER_LENGTH_DAYS = "Number of days for the offer length must be 0 or positive.";
    String NEGATIVE_OFFER_LENGTH_HOURS = "Number of hour for the offer length must be 0 or positive.";
    String EMPTY_OFFER_STATUS = "Offer status cannot be empty.";
    String INVALID_OFFER_ID = "No offer exists with provided id %s.";
    String OFFER_ID_NOT_NUMERIC = "Offer id must be numeric.";
    String WELCOME = "Welcome to WorldOffers!";
    String OFFER_ALREADY_EXPIRED = "Offer with id %s is already expired and can no longer be updated.";
}
