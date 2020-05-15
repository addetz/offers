package com.worldoffers.spark;

import com.google.gson.Gson;
import com.worldoffers.WorldOffersMessages;
import com.worldoffers.WorldOffersPaths;
import com.worldoffers.offers.GoodsDetails;
import com.worldoffers.offers.Offer;
import com.worldoffers.offers.OfferDetails;
import com.worldoffers.offers.OfferServiceMapImpl;
import org.joda.money.CurrencyUnit;
import org.junit.Before;
import org.junit.Test;
import spark.Request;
import spark.Response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link GetOfferRoute}.
 */
public class TestGetOfferRoute {

    private static final String OFFER_DESCRIPTION = "This is my new good.";
    private static final String CURRENCY_CODE = CurrencyUnit.GBP.getCurrencyCode();
    private static final BigDecimal PRICE = BigDecimal.valueOf(150);
    private static final LocalDateTime START_DATE = LocalDateTime.of(2018, 5, 10, 14, 10);
    private static final int OFFER_LENGTH_DAYS = 10;
    private static final int OFFER_LENGTH_HOURS = 15;

    private GoodsDetails goodsDetails;
    private OfferDetails offerDetails;
    private GetOfferRoute getOfferRoute;
    private long existingOfferId;

    @Before
    public void setUp() {
        goodsDetails = new GoodsDetails(OFFER_DESCRIPTION, CURRENCY_CODE, PRICE);
        offerDetails = new OfferDetails(START_DATE, OFFER_LENGTH_DAYS, OFFER_LENGTH_HOURS);
        existingOfferId = OfferServiceMapImpl.getInstance().createOffer(goodsDetails, offerDetails);
        getOfferRoute = new GetOfferRoute();
    }

    /**
     * Tests that the expected error is shown when a non-numeric offer id is passed.
     */
    @Test
    public void testAlphabeticOfferId() {
        Request request = mock(Request.class);
        Response response = mock(Response.class);
        when(request.params(WorldOffersPaths.OFFER_PARAM_ID)).thenReturn("abcde");
        OfferResponse handleResponse = new Gson().fromJson(getOfferRoute.handle(request, response).toString(), OfferResponse.class);

        assertEquals("Error response should have been returned", OfferResponseStatus.ERROR, handleResponse.getStatus());
        assertEquals("Non numeric offer id message should have been returned.",
                WorldOffersMessages.OFFER_ID_NOT_NUMERIC, handleResponse.getMessage());

    }

    /**
     * Tests that the expected error is shown when a an inexistent offer id is passed.
     */
    @Test
    public void testInvalidOfferId() {
        String inexistentId = "1234";
        Request request = mock(Request.class);
        Response response = mock(Response.class);
        when(request.params(WorldOffersPaths.OFFER_PARAM_ID)).thenReturn(inexistentId);
        OfferResponse handleResponse = new Gson().fromJson(getOfferRoute.handle(request, response).toString(), OfferResponse.class);

        assertEquals("Error response should have been returned", OfferResponseStatus.ERROR, handleResponse.getStatus());
        assertEquals("Inexistent offer id message should have been returned.",
                String.format(WorldOffersMessages.INVALID_OFFER_ID, inexistentId), handleResponse.getMessage());
    }

    /**
     * Tests that the {@link Offer} details are fetched correctly.
     */
    @Test
    public void testValidOfferId() {
        Request request = mock(Request.class);
        Response response = mock(Response.class);
        when(request.params(WorldOffersPaths.OFFER_PARAM_ID)).thenReturn(String.valueOf(existingOfferId));
        OfferResponse handleResponse = new Gson().fromJson(getOfferRoute.handle(request, response).toString(), OfferResponse.class);
        Optional<Offer> offer = OfferServiceMapImpl.getInstance().getOffer(existingOfferId);

        assertEquals("Ok response should have been returned", OfferResponseStatus.OK, handleResponse.getStatus());
        assertTrue("Offer should be present.", offer.isPresent());
        assertEquals("All offer details should have been returned.",
                new Gson().toJsonTree(offer.get()), handleResponse.getData());
    }

}