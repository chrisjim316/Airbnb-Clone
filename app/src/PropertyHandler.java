package app.src;

import app.models.AirbnbListing;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class takes care of getting the search results based on user input on the welcome screen and store the search results
 * in a Hashmap and return it accordingly for processing such as generating statistics
 * @author (Jim, Chow-Ching K1763805) (Rehman, Habib-Ur K1763960) (Mahmood, Mohsan K1763716) (Lee, Daniel K1764092)
 */
public class PropertyHandler {
    private final ArrayList<AirbnbListing> listings;

    /**
     * Constructor for PropertyHandler
     * Calls AirbnbDataLoader and reads the airbnb csv file that is stored in the root folder
     */
    public PropertyHandler() {
        AirbnbDataLoader csvReader = new AirbnbDataLoader();
        this.listings = csvReader.load();
    }

    /**
     * This method gathers matching data from csv data
     * @param startingPrice
     * @param endingPrice
     * @return Hashmap of airbnblisting search results, String being the region name, and arraylist of airbnbListing objects binding to each region
     */
    public HashMap<String, ArrayList<AirbnbListing>> search(int startingPrice, int endingPrice) {
        HashMap<String, ArrayList<AirbnbListing>> markers = new HashMap<>(); // regionName : arrayList of listings

        for (AirbnbListing house : listings) {
            // First check if the house listing is within price range user input
            if (house.getPrice() >= startingPrice && house.getPrice() <= endingPrice) {
                // Get the region and put it as key value, check if value exist in hashmap already

                String region = house.getNeighbourhood();

                if (markers.containsKey(region)) {
                    // If exist, then call value(arraylist) and append new house listing
                    ArrayList<AirbnbListing> tempList1 = markers.get(region);
                    tempList1.add(house);
                    markers.replace(region, tempList1);
                } else {
                    // If region does not exist, create new key value pair and new arraylist of airbnblistings
                    ArrayList<AirbnbListing> tempList2 = new ArrayList<AirbnbListing>();
                    tempList2.add(house);
                    markers.put(region, tempList2);
                }
            }
        }
        return markers;
    }

}