package com.worldoffers.spark;

import com.google.gson.Gson;
import com.worldoffers.WorldOffersMessages;
import com.worldoffers.WorldOffersPaths;
import com.worldoffers.offers.OfferServiceMapImpl;
import org.apache.commons.lang3.StringUtils;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * {@link Route} implemented for cancelling existing offers.
 */
public class CancelOfferRoute implements Route {
    @Override
    public Object handle(Request request, Response response) {
        response.type("application/json");
        String paramId = request.params(WorldOffersPaths.OFFER_PARAM_ID);

        if (!StringUtils.isNumeric(paramId)) {
            return new Gson().toJson(new OfferResponse(OfferResponseStatus.ERROR, WorldOffersMessages.OFFER_ID_NOT_NUMERIC));
        }

        try {
            OfferServiceMapImpl.getInstance().cancelOffer(Long.parseLong(paramId));
            return new Gson().toJson(new OfferResponse(OfferResponseStatus.OK));
        } catch (IllegalArgumentException error) {
            return new Gson().toJson(new OfferResponse(OfferResponseStatus.ERROR, error.getMessage()));
        }
    }
}
