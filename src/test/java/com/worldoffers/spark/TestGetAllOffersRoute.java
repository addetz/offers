package com.worldoffers.spark;

import com.google.gson.Gson;
import com.worldoffers.offers.GoodsDetails;
import com.worldoffers.offers.OfferDetails;
import com.worldoffers.offers.OfferServiceMapImpl;
import org.joda.money.CurrencyUnit;
import org.junit.Test;
import spark.Request;
import spark.Response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Test class for {@link GetAllOffersRoute}.
 */
public class TestGetAllOffersRoute {

    private static final String OFFER_DESCRIPTION = "This is my new good.";
    private static final String CURRENCY_CODE = CurrencyUnit.GBP.getCurrencyCode();
    private static final BigDecimal PRICE = BigDecimal.valueOf(150);
    private static final LocalDateTime START_DATE = LocalDateTime.of(2018, 5, 10, 14, 10);
    private static final int OFFER_LENGTH_DAYS = 10;
    private static final int OFFER_LENGTH_HOURS = 15;

    private GoodsDetails goodsDetails;
    private OfferDetails offerDetails;
    private GetAllOffersRoute getAllOffersRoute;

    /**
     * Test fetching a list of offers.
     */
    @Test
    public void testGetAllOfferIds() {
        goodsDetails = new GoodsDetails(OFFER_DESCRIPTION, CURRENCY_CODE, PRICE);
        offerDetails = new OfferDetails(START_DATE, OFFER_LENGTH_DAYS, OFFER_LENGTH_HOURS);
        long firstOffer = OfferServiceMapImpl.getInstance().createOffer(goodsDetails, offerDetails);
        long secondOffer = OfferServiceMapImpl.getInstance().createOffer(goodsDetails, offerDetails);
        getAllOffersRoute = new GetAllOffersRoute();

        Request request = mock(Request.class);
        Response response = mock(Response.class);
        OfferResponse handleResponse = new Gson().fromJson(getAllOffersRoute.handle(request, response).toString(), OfferResponse.class);
        String allOfferIds = handleResponse.getData().getAsJsonArray().toString();

        assertEquals("Ok response should have been returned", OfferResponseStatus.OK, handleResponse.getStatus());
        assertTrue("Offer id should have been found.", allOfferIds.contains(String.valueOf(firstOffer)));
        assertTrue("Offer id should have been found.", allOfferIds.contains(String.valueOf(secondOffer)));
    }
}