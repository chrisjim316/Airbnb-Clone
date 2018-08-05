package app.src;

import app.models.AirbnbListing;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

/**
 * UIUtils reuses code found both in loadPropertyListing method in MapController.java and showBookedListing method in BookingController.java
 * into one modularised method createPropertyListingsUI
 * @author (Jim, Chow-Ching K1763805) (Rehman, Habib-Ur K1763960) (Mahmood, Mohsan K1763716) (Lee, Daniel K1764092)
 */
public class UIUtils {
    /**
     * Empty Constructor
     */
    public UIUtils() {
    }

    /**
     * This method is responsible for creating a GridPane object that wraps a AirbnbListing object and its details to display to the user
     * @param listing
     * @return a GridPane object
     */
    public static GridPane createPropertyListingItem(AirbnbListing listing) {
        GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add("listing");
        gridPane.setPadding(new Insets(18, 18, 18, 18));
        Text propertyName = new Text(listing.getName());
        propertyName.getStyleClass().add("h2");
        gridPane.add(propertyName, 0, 0);
        GridPane.setHalignment(propertyName, HPos.LEFT);
        GridPane.setHgrow(propertyName, Priority.ALWAYS);
        Text propertyBedrooms = new Text(listing.getMinimumNights() + " Minimum Nights");
        gridPane.add(propertyBedrooms, 0, 1);
        GridPane.setHalignment(propertyBedrooms, HPos.LEFT);
        GridPane.setHgrow(propertyBedrooms, Priority.ALWAYS);
        Text propertyPrice = new Text("£" + listing.getPrice() + " per night");
        propertyPrice.getStyleClass().add("h2");
        gridPane.add(propertyPrice, 1, 0, 2, 1);
        GridPane.setHalignment(propertyPrice, HPos.RIGHT);
        GridPane.setValignment(propertyPrice, VPos.CENTER);

        String propertyDetailsText = listing.getNumberOfReviews() + " Reviews · By " + listing.getHost_name();
        Text propertyDetails = new Text(propertyDetailsText);
        gridPane.add(propertyDetails, 0, 2);
        GridPane.setHalignment(propertyDetails, HPos.LEFT);
        GridPane.setHgrow(propertyDetails, Priority.ALWAYS);
        return gridPane;
    }
}
