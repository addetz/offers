package com.worldoffers.spark;

import com.google.gson.Gson;
import com.worldoffers.WorldOffersMessages;
import com.worldoffers.WorldOffersPaths;
import com.worldoffers.offers.Offer;
import com.worldoffers.offers.OfferServiceMapImpl;
import org.apache.commons.lang3.StringUtils;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Optional;

/**
 * {@link Route} implemented for fetching an existing offer.
 */
public class GetOfferRoute implements Route {

    @Override
    public Object handle(Request request, Response response) {
        response.type("application/json");
        String paramId = request.params(WorldOffersPaths.OFFER_PARAM_ID);

        if (!StringUtils.isNumeric(paramId)) {
            return new Gson().toJson(new OfferResponse(OfferResponseStatus.ERROR, WorldOffersMessages.OFFER_ID_NOT_NUMERIC));
        }

        Optional<Offer> offer = OfferServiceMapImpl.getInstance().getOffer(Long.parseLong(paramId));

        if(offer.isPresent()) {
            return new Gson().toJson(new OfferResponse(OfferResponseStatus.OK, new Gson().toJsonTree(offer.get())));
        }

        return new Gson().toJson(new OfferResponse(OfferResponseStatus.ERROR,
                String.format(WorldOffersMessages.INVALID_OFFER_ID, paramId)));
    }
}
