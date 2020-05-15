package com.worldoffers.validation;

import com.worldoffers.WorldOffersMessages;
import com.worldoffers.offers.GoodsDetails;
import com.worldoffers.offers.OfferDetails;
import org.joda.money.CurrencyUnit;
import org.joda.money.IllegalCurrencyException;

import java.math.BigDecimal;

/**
 * Common service for offer details objects which must be validated.
 */
public class DetailsValidationService {

    /**
     * @param goodsDetails object to validate
     * @return a {@link DetailsValidationContext} containing all errors of the object
     */
    public DetailsValidationContext validate(GoodsDetails goodsDetails) {
        DetailsValidationContext validationContext = new DetailsValidationContext();

        if(goodsDetails.getDescription() == null || goodsDetails.getDescription().length() == 0) {
            validationContext.addError(WorldOffersMessages.EMPTY_GOODS_DESCRIPTION);
        }

        try {
            CurrencyUnit.of(goodsDetails.getCurrencyCode());
        } catch (IllegalCurrencyException e) {
            validationContext.addError(String.format(WorldOffersMessages.ILLEGAL_CURRENCY,goodsDetails.getCurrencyCode()));
        }

        if (goodsDetails.getPrice() == null) {
            validationContext.addError(WorldOffersMessages.EMPTY_PRICE);
        } else if (goodsDetails.getPrice().compareTo(BigDecimal.ZERO) < 1) {
            validationContext.addError(WorldOffersMessages.NEGATIVE_PRICE);
        }

        return validationContext;

    }

    /**
     * @param offerDetails object to validate
     * @return a {@link DetailsValidationContext} containing all errors of the object
     */
    public DetailsValidationContext validate(OfferDetails offerDetails) {
        DetailsValidationContext validationContext = new DetailsValidationContext();

        if(offerDetails.getStartDate() == null) {
            validationContext.addError(WorldOffersMessages.EMPTY_STARTDATE);
        }

        if (offerDetails.getOfferLengthDays() < 0) {
            validationContext.addError(WorldOffersMessages.NEGATIVE_OFFER_LENGTH_DAYS);
        }

        if (offerDetails.getOfferLengthHours() < 0) {
            validationContext.addError(WorldOffersMessages.NEGATIVE_OFFER_LENGTH_HOURS);
        }

        if (offerDetails.getStatus() == null) {
            validationContext.addError(WorldOffersMessages.EMPTY_OFFER_STATUS);
        }

        return validationContext;
    }
}