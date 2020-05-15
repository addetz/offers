package com.worldoffers.spark;

import com.google.gson.Gson;
import com.worldoffers.offers.OfferServiceMapImpl;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * {@link Route} implemented for creating a new offer.
 */
public class CreateOfferRoute implements Route {
    @Override
    public Object handle(Request request, Response response) {
        response.type("application/json");
        OfferWrapper newOfferWrapper = new Gson().fromJson(request.body(), OfferWrapper.class);
        try
        {
            long offerId = OfferServiceMapImpl.getInstance().createOffer(newOfferWrapper.getGoodsDetails(), newOfferWrapper.getOfferDetails());
            return new Gson().toJson(new OfferResponse(OfferResponseStatus.OK, new Gson().toJsonTree(String.valueOf(offerId))));
        } catch (IllegalArgumentException error) {
            return new Gson().toJson(new OfferResponse(OfferResponseStatus.ERROR, error.getMessage()));
        }
    }
}
