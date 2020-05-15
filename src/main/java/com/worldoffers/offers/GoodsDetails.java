package com.worldoffers.offers;

import java.math.BigDecimal;

/**
 * Helper class to encapsulate the goods details of an {@link Offer}
 *
 * @author addetz
 */
public class GoodsDetails {

    private final String description;
    private final String currencyCode;
    private final BigDecimal price;

    public GoodsDetails(String description, String currencyCode, BigDecimal goodsPrice) {
        this.description = description;
        this.currencyCode = currencyCode;
        this.price = goodsPrice;
    }

    /**
     * @return description of the goods
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return price of the goods
     */
    public BigDecimal getPrice() {
        return price;
    }


    /**
     * @return currency code of the details
     */
    public String getCurrencyCode() {
        return currencyCode;
    }


    /**
     * Copies the current instance.
     */
    public GoodsDetails copy() {
        return new GoodsDetails(description, currencyCode, price);
    }
}
