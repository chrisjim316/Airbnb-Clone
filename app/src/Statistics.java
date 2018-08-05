package app.src;

import app.models.AirbnbListing;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Iterate through searchResults to calculate generate useful statistics for the user
 * @author (Jim, Chow-Ching K1763805) (Rehman, Habib-Ur K1763960) (Mahmood, Mohsan K1763716) (Lee, Daniel K1764092)
 */
public class Statistics {
    private int averageNumberOfReviews;
    private int numberOfProperties;
    private int numberOfEntireRoomApts;
    private String priciestRegion;
    // Highest price takes the highest price as most expensive region
    private int highestPrice;
    // Stores the average property price of every neighbourhood
    private HashMap<String, Integer> averagePrices = new HashMap<>();
    // Stores the average number of nights in every neighbourhood
    private HashMap<String, Integer> averageMinimumNights = new HashMap<>();
    // Stores the average number of nights in every neighbourhood
    private HashMap<String, String> highestHosts = new HashMap<>();
    // Stores the average number of available nights per year in every neighbourhood
    private HashMap<String, Integer> averageAvailability = new HashMap<>();

    public Statistics(final HashMap<String, ArrayList<AirbnbListing>> searchResults) {
        for (HashMap.Entry<String, ArrayList<AirbnbListing>> entry : searchResults.entrySet()) {
            // Get each AirbnbListing hashmap from the search results
            ArrayList<AirbnbListing> value = entry.getValue(); 

            // Calculate total number of properties available based on search results
            numberOfProperties += value.size();

            // Declare local variables
            int priceSum = 0;
            int minimumNightSum = 0;
            int highestHostProps = 0;
            int availabilitySum = 0;
            String highestHost = "";

            // Iterate through what we have and perform operations on each house/listing
            for (AirbnbListing house : value) {
                if(house.getCalculatedHostListingsCount() > highestHostProps) {
                    // If currentHostListingsCount is higher than the highestHostProps then update highestHost to current host and highestHostProps to currentHostListingsCount
                    highestHost = house.getHost_name();
                    highestHostProps = house.getCalculatedHostListingsCount();
                }

                // Add number of reviews for each house in the search results
                averageNumberOfReviews += house.getNumberOfReviews();

                // Counter variable for the prices for the listings in the entire region
                priceSum += house.getPrice();
                // Counter variable for minimum nights
                minimumNightSum += house.getMinimumNights();
                // Counter variable to record and sum up available nights per year for each listing in the results
                availabilitySum += house.getAvailability365();

                if (house.getPrice() > highestPrice) {
                    // Compare it with existing highest price
                    // If the current house price is higher than existing highest price then update highest price to current price
                    highestPrice = house.getPrice();
                    // Update priciestRegion to the region the current house is in
                    priciestRegion = house.getNeighbourhood();
                }

                // If house room type is entire room/apt, add one to numberOfEntireRoomApts
                if (house.getRoom_type().equals("Entire home/apt")) {
                    numberOfEntireRoomApts++;
                }
            }

            averageNumberOfReviews /= searchResults.size();
            // Deduce the up-rounded average (minimum) price per property for the neighbourhood in the HashMap
            averagePrices.put(entry.getKey(), (int) Math.ceil(priceSum / value.size()));

            // Deduce the average nights in the neighbourhood in the HashMap
            averageMinimumNights.put(entry.getKey(), (int) Math.ceil(minimumNightSum / value.size()));

            // Deduce the host with the greatest number of properties
            highestHosts.put(entry.getKey(), highestHost);

            // Deduce the average number of availabilities.
            averageAvailability.put(entry.getKey(), (int) Math.ceil(availabilitySum / value.size()));
        }
    }


    // Global stats below

    public String getAverageNumberOfReviewsMessage() { return averageNumberOfReviews + " number of reviews on average"; }

    public String getNumberOfPropertiesMessage() { return numberOfProperties +  " total available properties"; }

    public String getNumberOfEntireRoomAptsMessage() {
        return numberOfEntireRoomApts + " total entire homes/apartments";
    }

    public String getPriciestRegionMessage() { return priciestRegion + " is the most expensive region"; }

    // Local stats below

    public String getAveragePricesMessage(String key) {
        return averagePrices.get(key) + " pounds/night on average";
    }

    public String getAverageMinimumNightsMessage(String key) { return averageMinimumNights.get(key) + " minimum nights on average"; }

    public String getAverageAvailabilityMessage(String key) { return averageAvailability.get(key) + " days per year available on average "; }

    public String getHighestHostMessage(String key) { return highestHosts.get(key) + " has the most listings"; }

    public int getAverageNumberOfReviews() {
        return averageNumberOfReviews;
    }

    public int getNumberOfProperties() {
        return numberOfProperties;
    }

    public int getNumberOfEntireRoomApts() {
        return numberOfEntireRoomApts;
    }

    public String getPriciestRegion() {
        return priciestRegion;
    }

    public int getHighestPrice() {
        return highestPrice;
    }

    public int getAveragePrices(String key) {
        return averagePrices.get(key);
    }

    public int getAverageMinimumNights(String key) {
        return averageMinimumNights.get(key);
    }

    public String getHighestHosts(String key) {
        return highestHosts.get(key);
    }

    public int getAverageAvailability(String key) {
        return averageAvailability.get(key);
    }
}
