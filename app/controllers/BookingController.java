package app.controllers;

import app.models.AirbnbListing;
import app.src.Navigation;
import app.src.UIUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author (Jim, Chow-Ching K1763805) (Rehman, Habib-Ur K1763960) (Mahmood, Mohsan K1763716) (Lee, Daniel K1764092)
 */
public class BookingController implements Initializable {

    @FXML
    private Text PriceText;

    @FXML
    private Text NightsText;

    @FXML
    private Text NightsInputErrorText;

    @FXML
    private VBox listingsContainer;

    private int totalPrice;
    private int totalNights;

    // To be used to remove the spaces in the user input on the number of nights they wish to stay
    private static final String spacesRegex = "\\s+";

    // Stores the bookingListings like a 'handleCheckoutAction basket'
    private ArrayList<AirbnbListing> bookedListings;

    // The customer may not stay for more than 999 days at any AirbnbListing
    private final int MAX_NIGHT_NUMBER_LENGTH = 3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void initializeBookedListings(HashMap<String, AirbnbListing> bookedListings) {
        // Set the values for the bookedListings, results is received from user selection from the previous panel
        this.bookedListings = new ArrayList<>(bookedListings.values());
        showBookedListings();
        updatePriceAndNights();
    }

    /**
     * Show AirbnbListings that were selected from the previous panel
     */
    public void showBookedListings() {
        // Empty listingsContainer's previous results first
        listingsContainer.getChildren().clear();

        // This loops through all the search results, and create block style elements for each listing and append details as appropriate
        for (AirbnbListing listing : bookedListings) {
            // Calls createPropertyListingItem to create a GridPane object from the AirbnbListing listing object
            GridPane gridPane = UIUtils.createPropertyListingItem(listing);

            Text nightsText = new Text(" nights");
            gridPane.add(nightsText, 2, 2);
            GridPane.setValignment(nightsText, VPos.CENTER);
            GridPane.setHalignment(nightsText, HPos.RIGHT);

            TextField textField = new TextField(String.valueOf(listing.getMinimumNights()));
            textField.setAlignment(Pos.CENTER);
            textField.setMaxWidth(40);
            // Calculate the initial total nights and price
            totalNights += listing.getMinimumNights();
            totalPrice += listing.getMinimumNights() * listing.getPrice();
            AtomicInteger validOldValue = new AtomicInteger(listing.getMinimumNights());
            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                // Replace all spaces to prevent NumberFormatException
                newValue = newValue.replaceAll(spacesRegex, "");
                if (newValue.length() > MAX_NIGHT_NUMBER_LENGTH) {
                    showInputErrorMessage();
                    return;
                }
                int newNights;
                try {
                    newNights = Integer.parseInt(newValue);
                    NightsInputErrorText.setText("");
                } catch (NumberFormatException nfe) {
                    // Reset state
                    totalPrice -= validOldValue.get() * listing.getPrice();
                    totalNights -= validOldValue.get();
                    validOldValue.set(0);
                    updatePriceAndNights();
                    showInputErrorMessage();
                    return;
                }

                // Update nights and price values only if user enters an integer higher than the required minimum nights for this specific house listing
                if (newNights >= listing.getMinimumNights()) {
                    // Remove the old values and update with the new values
                    totalNights -= validOldValue.get();
                    totalNights += newNights;
                    totalPrice -= validOldValue.get() * listing.getPrice();
                    totalPrice += newNights * listing.getPrice();
                    validOldValue.set(newNights);
                    // Call update setText method
                    updatePriceAndNights();
                } else {
                    showInputErrorMessage();
                }
            });
            gridPane.add(textField, 1, 2);
            GridPane.setValignment(textField, VPos.CENTER);
            GridPane.setHalignment(textField, HPos.RIGHT);
            GridPane.setHgrow(textField, Priority.ALWAYS);

            listingsContainer.getChildren().add(gridPane);
        }
    }

    /**
     * As this method is called after GridPane pricePane and two text elements PriceText and NightsText are created,
     * there is no need to test for null or empty values, instead here we simply utilized setText to override
     * previous price and nights stats
     */
    public void updatePriceAndNights() {
        // Update the two Text elements - PriceText and NightsText
        PriceText.setText("Total Price: £" + totalPrice);
        NightsText.setText("Total Nights: " + totalNights);
    }

    /**
     * This method takes the user back to the region-specific house listings, where the user is able to add or remove bookings
     * Previous selections are saved with the help of stack structure
     *
     * @param actionEvent
     */
    public void goBack(ActionEvent actionEvent) {
        Navigation.goBack((Stage) listingsContainer.getScene().getWindow());
    }

    /**
     * Show error message when the customer enters an incorrect input beyond the range or an input that is unable to be identified
     */
    private void showInputErrorMessage() {
        NightsInputErrorText.setText("Enter an integer that is within 3 digits and that is greater than the minimum amount of nights");
        NightsInputErrorText.setVisible(true);
    }

    /**
     * Utilized when the customer click the checkout button and output meaningful messages at appropriate times
     *
     * @param actionEvent
     */
    public void handleCheckoutAction(ActionEvent actionEvent) {
        if (totalNights == 0) {
            // If customer didn't enter any number of nights they wish to book or there are errors in all their inputs, then show error message
            showInputErrorMessage();
        } else {
            // Go to the checkout panel
            Navigation.goTo((Stage) listingsContainer.getScene().getWindow(), "checkout", "Checkout", loader -> {
                CheckoutController checkoutController = loader.getController();
                String paymentSuccessfulText = "Successfully paid £" + totalPrice + " for " + totalNights + " night(s).\nEnjoy your stay! :)";
                checkoutController.showSuccessfulPaymentText(paymentSuccessfulText);
            });
        }
    }
}
