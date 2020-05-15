package com.worldoffers.validation;

import com.worldoffers.WorldOffersMessages;
import com.worldoffers.offers.GoodsDetails;
import com.worldoffers.offers.OfferDetails;
import com.worldoffers.offers.OfferStatus;
import org.joda.money.CurrencyUnit;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Test class for {@link DetailsValidationService}.
 *
 * @author addetz
 */
public class TestDetailsValidationService {
    private static final BigDecimal PRICE = BigDecimal.valueOf(100);
    private static final String CURRENCY_CODE = CurrencyUnit.GBP.getCurrencyCode();
    private static final String DESCRIPTION = "This is my new good";
    private static final LocalDateTime START_DATE = LocalDateTime.of(2018, 5, 10, 14, 10);
    private static final int OFFER_LENGTH_DAYS = 10;
    private static final int OFFER_LENGTH_HOURS = 15;

    private DetailsValidationService detailsValidationService;

    /**
     * Set up.
     */
    @Before
    public void setUp() {
        detailsValidationService = new DetailsValidationService();
    }

    /**
     * Tests validating a valid {@link OfferDetails}
     */
    @Test
    public void testValidOfferDetails() {
        OfferDetails offerDetails = new OfferDetails(START_DATE, OFFER_LENGTH_DAYS, OFFER_LENGTH_HOURS);
        DetailsValidationContext context = detailsValidationService.validate(offerDetails);

        assertTrue("Context should be valid.", context.isValid());
        assertTrue("No errors should be found on the context.", context.getErrors().isEmpty());
    }

    /**
     * Tests creating an {@link OfferDetails} with an empty start date.
     */
    @Test
    public void testNullOfferStartDate() {
        OfferDetails offerDetails = new OfferDetails(null, OFFER_LENGTH_DAYS, OFFER_LENGTH_HOURS);
        DetailsValidationContext context = detailsValidationService.validate(offerDetails);

        assertFalse("Context should be invalid.", context.isValid());
        assertEquals("Empty start date error should be contained in the list", WorldOffersMessages.EMPTY_STARTDATE,
                context.getErrors());
    }

    /**
     * Tests creating an {@link OfferDetails} with a negative day length.
     */
    @Test
    public void testNegativeOfferLengthDays() {
        OfferDetails offerDetails = new OfferDetails(START_DATE, -OFFER_LENGTH_DAYS, OFFER_LENGTH_HOURS);
        DetailsValidationContext context = detailsValidationService.validate(offerDetails);

        assertFalse("Context should be invalid.", context.isValid());
        assertEquals("Negative offer length days error should be contained in the list", WorldOffersMessages.NEGATIVE_OFFER_LENGTH_DAYS,
                context.getErrors());
    }

    /**
     * Tests creating an {@link OfferDetails} with a negative hours length.
     */
    @Test
    public void testNegativeOfferHoursDays() {
        OfferDetails offerDetails = new OfferDetails(START_DATE, OFFER_LENGTH_DAYS, -OFFER_LENGTH_HOURS);
        DetailsValidationContext context = detailsValidationService.validate(offerDetails);

        assertFalse("Context should be invalid.", context.isValid());
        assertEquals("Negative offer length hours error should be contained in the list",
                WorldOffersMessages.NEGATIVE_OFFER_LENGTH_HOURS, context.getErrors());
    }

    /**
     * Tests creating an {@link OfferDetails} with a negative hours length.
     */
    @Test
    public void testEmptyOfferStatus() {
        OfferDetails offerDetails = new OfferDetails(START_DATE, OFFER_LENGTH_DAYS, OFFER_LENGTH_HOURS);
        offerDetails.setStatus(null);
        DetailsValidationContext context = detailsValidationService.validate(offerDetails);

        assertFalse("Context should be invalid.", context.isValid());
        assertEquals("Negative offer length hours error should be contained in the list",
                WorldOffersMessages.EMPTY_OFFER_STATUS, context.getErrors());
    }

    /**
     * Tests creating, getting and validating a valid {@link GoodsDetails}
     */
    @Test
    public void testValidGoodsDetails() {
        GoodsDetails goodsDetails = new GoodsDetails(DESCRIPTION, CURRENCY_CODE, PRICE);
        DetailsValidationContext context = detailsValidationService.validate(goodsDetails);

        assertTrue("Context should be valid.", context.isValid());
        assertTrue("No errors should be found on the context.", context.getErrors().isEmpty());
    }


    /**
     * Tests validating a {@link GoodsDetails} with empty description.
     */
    @Test
    public void testEmptyGoodsDetailsDescription() {
        GoodsDetails goodsDetails = new GoodsDetails("", CURRENCY_CODE, PRICE);
        DetailsValidationContext context = detailsValidationService.validate(goodsDetails);

        assertFalse("Context should be invalid.", context.isValid());
        assertEquals("Empty description error should be contained in the list",
                WorldOffersMessages.EMPTY_GOODS_DESCRIPTION, context.getErrors());

    }

    /**
     * Tests validating a {@link GoodsDetails} with null description.
     */
    @Test
    public void testNullGoodsDescription() {
        GoodsDetails goodsDetails = new GoodsDetails(null, CURRENCY_CODE, PRICE);
        DetailsValidationContext context = detailsValidationService.validate(goodsDetails);

        assertFalse("Context should be invalid.", context.isValid());
        assertEquals("Empty description error should be contained in the list",
                WorldOffersMessages.EMPTY_GOODS_DESCRIPTION, context.getErrors());

    }

    /**
     * Tests validating a {@link GoodsDetails} with null price.
     */
    @Test
    public void testNullGoodsPrice() {
        GoodsDetails goodsDetails = new GoodsDetails(DESCRIPTION, CURRENCY_CODE, null);
        DetailsValidationContext context = detailsValidationService.validate(goodsDetails);

        assertFalse("Context should be invalid.", context.isValid());
        assertEquals("Empty price error should be contained in the list",
                WorldOffersMessages.EMPTY_PRICE, context.getErrors());
    }

    /**
     * Tests validating a {@link GoodsDetails} with zero price.
     */
    @Test
    public void testZeroGoodsPrice() {
        GoodsDetails goodsDetails = new GoodsDetails(DESCRIPTION, CURRENCY_CODE, BigDecimal.ZERO);
        DetailsValidationContext context = detailsValidationService.validate(goodsDetails);

        assertFalse("Context should be invalid.", context.isValid());
        assertEquals("Negative price error should be contained in the list",
                WorldOffersMessages.NEGATIVE_PRICE, context.getErrors());
    }

    /**
     * Tests validating a {@link GoodsDetails} with negative price.
     */
    @Test
    public void testNegativeGoodsPrice() {
        GoodsDetails goodsDetails = new GoodsDetails(DESCRIPTION, CURRENCY_CODE, BigDecimal.valueOf(-100));
        DetailsValidationContext context = detailsValidationService.validate(goodsDetails);

        assertFalse("Context should be invalid.", context.isValid());
        assertEquals("Negative price error should be contained in the list",
                WorldOffersMessages.NEGATIVE_PRICE, context.getErrors());
    }

    /**
     * Tests validating a {@link GoodsDetails} with invalid currency code.
     */
    @Test
    public void testInvalidGoodsCurrencyCode() {
        String invalidCurrency = "invalid";
        GoodsDetails goodsDetails = new GoodsDetails(DESCRIPTION, invalidCurrency, PRICE);
        DetailsValidationContext context = detailsValidationService.validate(goodsDetails);

        assertFalse("Context should be invalid.", context.isValid());
        assertEquals("Invalid currency error should be contained in the list",
                String.format(WorldOffersMessages.ILLEGAL_CURRENCY, invalidCurrency), context.getErrors());

    }

}