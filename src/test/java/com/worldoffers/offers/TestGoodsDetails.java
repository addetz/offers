package com.worldoffers.offers;

import org.joda.money.CurrencyUnit;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * Test class for {@link GoodsDetails}.
 */
public class TestGoodsDetails {

    private static final BigDecimal PRICE = BigDecimal.valueOf(100);
    private static final String CURRENCY_CODE = CurrencyUnit.GBP.getCurrencyCode();
    private static final String DESCRIPTION = "This is my new good";

    /**
     * Tests creating, getting and validating a valid {@link GoodsDetails}
     */
    @Test
    public void testValidDetails() {
        GoodsDetails goodsDetails = new GoodsDetails(DESCRIPTION, CURRENCY_CODE, PRICE);
        assertEquals("Price is incorrect.", PRICE, goodsDetails.getPrice());
        assertEquals("Currency code is incorrect.", CURRENCY_CODE, goodsDetails.getCurrencyCode());
        assertEquals("Description is incorrect.", DESCRIPTION, goodsDetails.getDescription());
    }

    /**
     * Tests copy {@link GoodsDetails}
     */
    @Test
    public void testValidDetailsCopy() {
        GoodsDetails goodsDetails = new GoodsDetails(DESCRIPTION, CURRENCY_CODE, PRICE).copy();
        assertEquals("Price is incorrect.", PRICE, goodsDetails.getPrice());
        assertEquals("Currency code is incorrect.", CURRENCY_CODE, goodsDetails.getCurrencyCode());
        assertEquals("Description is incorrect.", DESCRIPTION, goodsDetails.getDescription());
    }
}