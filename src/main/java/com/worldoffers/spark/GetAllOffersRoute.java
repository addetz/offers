package com.worldoffers.spark;

import com.google.gson.Gson;
import com.worldoffers.offers.OfferServiceMapImpl;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * {@link Route} implemented for fetching all existing offers.
 */
public class GetAllOffersRoute implements Route {
    @Override
    public Object handle(Request request, Response response) {
        return new Gson().toJson(
                new OfferResponse(OfferResponseStatus.OK,
                        new Gson().toJsonTree(OfferServiceMapImpl.getInstance().getOffers())));
    }
}
