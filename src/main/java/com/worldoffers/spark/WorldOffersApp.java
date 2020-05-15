package com.worldoffers.spark;

import com.worldoffers.WorldOffersMessages;
import com.worldoffers.WorldOffersPaths;
import com.worldoffers.offers.OfferExpiryService;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

import static spark.Spark.*;

/**
 * Main class of the World Offers application.
 */
public class WorldOffersApp {

    private static final OfferExpiryService expiryService = new OfferExpiryService();

    public static void main(String[] args) {
        startExpiryServiceTimerTask();

        port(WorldOffersPaths.PORT_NUMBER);
        //Welcome screen
        get(WorldOffersPaths.BASE_URI, (req, res)-> WorldOffersMessages.WELCOME);
        //Get all offers
        get(WorldOffersPaths.OFFERS_URI, new GetAllOffersRoute());
        //Create a new offer
        post(WorldOffersPaths.OFFERS_URI, new CreateOfferRoute());
        //Fetch an existing offer
        get(WorldOffersPaths.OFFERS_URI + "/" + WorldOffersPaths.OFFER_PARAM_ID, new GetOfferRoute());
        //Cancel an offer
        put(WorldOffersPaths.CANCEL_URI + "/" + WorldOffersPaths.OFFER_PARAM_ID, new CancelOfferRoute());
    }

    /**
     * Method which starts and runs {@link OfferExpiryService} as a periodic timer task.
     */
    private static void startExpiryServiceTimerTask() {
        Timer timer = new Timer("Timer");
        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                expiryService.expireOffers();
                System.out.println("Offer expiry processed at " + LocalDateTime.now());
            }
        };

        timer.scheduleAtFixedRate(repeatedTask, WorldOffersPaths.OFFER_EXPIRY_DELAY, WorldOffersPaths.OFFER_EXPIRY_PERIOD);
    }
}