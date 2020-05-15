package com.worldoffers.spark;

import com.google.gson.Gson;
import com.worldoffers.WorldOffersMessages;
import com.worldoffers.offers.GoodsDetails;
import com.worldoffers.offers.OfferDetails;
import org.joda.money.CurrencyUnit;
import org.junit.Before;
import org.junit.Test;
import spark.Request;
import spark.Response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link CreateOfferRoute}.
 */
public class TestCreateOfferRoute {

    private static final String OFFER_DESCRIPTION = "This is my new good.";
    private static final String CURRENCY_CODE = CurrencyUnit.GBP.getCurrencyCode();
    private static final BigDecimal PRICE = BigDecimal.valueOf(150);
    private static final LocalDateTime START_DATE = LocalDateTime.of(2018, 5, 10, 14, 10);
    private static final int OFFER_LENGTH_DAYS = 10;
    private static final int OFFER_LENGTH_HOURS = 15;

    private GoodsDetails goodsDetails;
    private OfferDetails offerDetails;
    private CreateOfferRoute createOfferRoute;

    @Before
    public void setUp() {
        goodsDetails = new GoodsDetails(OFFER_DESCRIPTION, CURRENCY_CODE, PRICE);
        offerDetails = new OfferDetails(START_DATE, OFFER_LENGTH_DAYS, OFFER_LENGTH_HOURS);
        createOfferRoute = new CreateOfferRoute();
    }

    /**
     * Tests that creating a valid offer will create the offer and return the offer id.
     */
    @Test
    public void testCreateValidOffer() {
        OfferWrapper wrapper = new OfferWrapper(goodsDetails, offerDetails);
        Request request = mock(Request.class);
        Response response = mock(Response.class);
        when(request.body()).thenReturn(new Gson().toJson(wrapper));
        OfferResponse handleResponse = new Gson().fromJson(createOfferRoute.handle(request, response).toString(), OfferResponse.class);

        assertEquals("Okay response should have been returned", OfferResponseStatus.OK, handleResponse.getStatus());
        assertEquals("8 digit offer id should have been returned", 8, handleResponse.getData().getAsString().length());
    }

    /**
     * Tests that creating an invalid offer will return error status
     * and appropriate error when {@link GoodsDetails} is invalid.
     */
    @Test
    public void testCreateInvalidGoodsOffer() {
        GoodsDetails invalidGoods = new GoodsDetails("", CURRENCY_CODE, PRICE);
        OfferWrapper wrapper = new OfferWrapper(invalidGoods, offerDetails);
        Request request = mock(Request.class);
        Response response = mock(Response.class);
        when(request.body()).thenReturn(new Gson().toJson(wrapper));
        OfferResponse handleResponse = new Gson().fromJson(createOfferRoute.handle(request, response).toString(), OfferResponse.class);

        assertEquals("Okay response should have been returned", OfferResponseStatus.ERROR, handleResponse.getStatus());
        assertEquals("Empty goods error message should have been returned.",
                WorldOffersMessages.EMPTY_GOODS_DESCRIPTION, handleResponse.getMessage());
    }

    /**
     * Tests that creating an invalid offer will return error status
     * and appropriate error when {@link OfferDetails} is invalid.
     */
    @Test
    public void testCreateInvalidOfferDetails() {
        OfferDetails invalidOffer = new OfferDetails(null, OFFER_LENGTH_DAYS, OFFER_LENGTH_HOURS);
        OfferWrapper wrapper = new OfferWrapper(goodsDetails, invalidOffer);
        Request request = mock(Request.class);
        Response response = mock(Response.class);
        when(request.body()).thenReturn(new Gson().toJson(wrapper));
        OfferResponse handleResponse = new Gson().fromJson(createOfferRoute.handle(request, response).toString(), OfferResponse.class);

        assertEquals("Okay response should have been returned", OfferResponseStatus.ERROR, handleResponse.getStatus());
        assertEquals("Empty goods error message should have been returned.",
                WorldOffersMessages.EMPTY_STARTDATE, handleResponse.getMessage());
    }

}