WorldOffers
==========
This a standalone REST API providing simple functionality creating and querying offers.

It has functionality to:
- create a new offer
- retrieve the list of all offers
- fetch the details of a specific offer
- cancel an active offer 
- automatically expire offers

Technologies
============
- This is a Java8 project which uses the Spark Java framework for creating the REST API and handling JSON requests. 
- Spark starts an embedded Jetty server.
- The app is built with Gradle.

Endpoints
=========
The application will run at http://localhost:8080/worldoffers.
It exposes the following endpoints:

/offers
---------
- Fetch all the offers available on the app.
- Method : GET 
- Return : a list of all offer ids

/offers/{:id}
-------------------------
- Fetch the offer corresponding to the given id. 
- Method : GET
- Return :  a JSON object with the offer details of the offer corresponding to the given id. 
 
 /offers/{:id}
-------------------------
- Create a new offer with the specified offer details in the request body. See below for offer format.
- Method : POST
- Return :  a JSON object with the offer details of the offer corresponding to the given id.  

/cancel/{:id}
-------------------------
- Cancel the account corresponding to the given id
- Method : PUT
- Return : status of the operation


Offer format
============
The expected JSON format of offers is as seen below in the example below: 
```
{ 
    "goodsDetails":
    {
        "description":"This is my new good.",
        "currencyCode":"GBP",
        "price":150
    },
    "offerDetails":
    {
        "startDate":
        {
            "date":{"year":2018,"month":5,"day":10},
            "time":{"hour":14,"minute":10,"second":0,"nano":0}
        },
        "offerLengthDays":10,
        "offerLengthHours":15,
        "status":"ACTIVE"
    }
}
```

Implementation details
======================
- The offers are saved in memory, but other implementations can be extended if needed. 
- Once input, offers cannot be edited. The user is expected to cancel the offer wished to be edited 
and simply create another one. 
- Only live offers can be cancelled. 

How to build the project
======================

1. No containers or servers are required for running the project.
2. Run ```./gradlew build``` from the project directory. 
3. Run the WorldOffers app ```./gradlew run```
