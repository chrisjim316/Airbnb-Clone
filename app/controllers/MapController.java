package app.controllers;

import app.Config;
import app.models.AirbnbListing;
import app.models.Point;
import app.src.Navigation;
import app.src.Statistics;
import app.src.UIUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * @author (Jim, Chow-Ching K1763805) (Rehman, Habib-Ur K1763960) (Mahmood, Mohsan K1763716) (Lee, Daniel K1764092)
 */
public class MapController implements Initializable {

    @FXML
    private Pane map;

    @FXML
    private VBox listingsContainer;

    @FXML
    private GridPane root;

    private VBox listings;

    private Text neighbourhood;

    private ComboBox sort;

    // Only 4 stats to be shown at any point in time
    private Text statsText1;
    private Text statsText2;
    private Text statsText3;
    private Text statsText4;

    // This GridPane stores 4 Text elements above ( statsText 1-4 )
    private GridPane statsPane;

    // Below are the filters used for the search results
    private static final String LOWEST_PRICE = "Lowest Price";
    private static final String HIGHEST_PRICE = "Highest Price";
    private static final String HOST_NAME = "Host name";

    // Declare max and min bounds, the more properties in the region the bigger the marker
    private final int MAX_MARKER_HEIGHT = 40;
    private final int MIN_MARKER_HEIGHT = 30;

    private HashMap<String, ArrayList<AirbnbListing>> mapData;

    private ArrayList<AirbnbListing> propertyListings;

    private HashMap<String, AirbnbListing> bookedListings = new HashMap<>();

    // Create Statistics object to calculate the statistics and store them in local variables inside the class to be extracted using public get methods
    // This stats will be utilized once the app is launched
    private Statistics stats;

    // Show local stats by default
    private boolean isLocalStats = true;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        // Initialize everything
    }

    public void loadMap(final HashMap<String, ArrayList<AirbnbListing>> mapData) {
        this.mapData = mapData;
        // Calculate the stats
        this.stats = new Statistics(mapData);

        // Load the London map from external sources and set the size
        ImageView mapImage = new ImageView(getClass().getResource("../views/images/LondonMap.png").toExternalForm());
        mapImage.setPreserveRatio(true);
        mapImage.setFitHeight(500);
        mapImage.setX(138.5);
        // Add to fxml
        map.getChildren().add(mapImage);

        // Find the max number of properties in any region
        int maxPropertyNum = 0;
        for (ArrayList<AirbnbListing> properties : mapData.values()) {
            if (properties.size() > maxPropertyNum) maxPropertyNum = properties.size();
        }
        // Start loading data onto map
        for (String key : mapData.keySet()) {
            if (Config.MARKER_POINTS.containsKey(key)) {
                Point point = Config.MARKER_POINTS.get(key);
                ImageView propertyMarker = new ImageView(getClass().getResource("../views/images/home.png").toExternalForm());
                propertyMarker.setPreserveRatio(true);
                // Calculate the potential marker height relative to the MAX_MARKER_HEIGHT
                double potentialMarkerHeight = MAX_MARKER_HEIGHT * ((double) mapData.get(key).size() / maxPropertyNum);
                // If potentialMarkerHeight is less than the minimum marker height, set it to the minimum
                double markerHeight = (potentialMarkerHeight >= MIN_MARKER_HEIGHT) ? potentialMarkerHeight : MIN_MARKER_HEIGHT;
                propertyMarker.setFitHeight(markerHeight);
                propertyMarker.setX(point.getX());
                propertyMarker.setY(point.getY());
                propertyMarker.setOnMouseClicked((MouseEvent e) -> {
                    loadNeighbourhoodListings(key);
                });
                // Add marker to the map once the size and style is finalized
                map.getChildren().add(propertyMarker);
            }
        }
    }

    private void loadNeighbourhoodListings(String name) {
        // Update text
        if (neighbourhood != null) {
            // Calls setText method if neighbourhood Text element already exists
            neighbourhood.setText(name);
        } else {
            // Initially, when the neighbourhood fxml Text element doesn't exist, a next Text class has to be created
            neighbourhood = new Text(name);
            neighbourhood.getStyleClass().add("title");
            listingsContainer.getChildren().add(neighbourhood); // Add neighbourhood element to fxml/screen
        }

        // Update statistics based on region and show global stats initially
        if (statsPane == null) {
            // Initially, statsPane will be null so four new Text elements have to be created
            statsText1 = new Text();
            statsText2 = new Text();
            statsText3 = new Text();
            statsText4 = new Text();
            // Load arrow image to scroll through the 4 local stats and 4 global stats, respectively
            ImageView nextIcon = new ImageView(getClass().getResource("../views/images/forward.png").toExternalForm());
            nextIcon.setPreserveRatio(true);
            nextIcon.setFitHeight(14);
            nextIcon.setOnMouseClicked((MouseEvent e) -> {
                // Once the user click the arrow, isLocalStats will be set to the opposite state for the user to click through local stats and global stats
                isLocalStats = !isLocalStats;
                // @param name here means the neighbourhood name to get region-specific stats
                updateStats(isLocalStats, name);
            });
            statsPane = new GridPane();
            statsPane.add(statsText1, 0, 0);
            statsPane.add(statsText2, 1, 0);
            statsPane.add(statsText3, 2, 0);
            statsPane.add(statsText4, 3, 0);
            statsPane.add(nextIcon, 4, 0);
            statsPane.getStyleClass().add("statistics");
            // Spread out the statistics equally to fill the GridPane
            GridPane.setHgrow(statsText1, Priority.ALWAYS);
            GridPane.setHgrow(statsText2, Priority.ALWAYS);
            GridPane.setHgrow(statsText3, Priority.ALWAYS);
            GridPane.setHgrow(statsText4, Priority.ALWAYS);
            GridPane.setHgrow(nextIcon, Priority.ALWAYS);
            // Finally add statsPane to fxml/screen
            listingsContainer.getChildren().add(statsPane);
        }

        // Search filter processing
        if (sort == null) {
            GridPane gridPane = new GridPane();
            ObservableList<String> sorts =
                    FXCollections.observableArrayList(
                            LOWEST_PRICE,
                            HIGHEST_PRICE,
                            HOST_NAME
                    );
            sort = new ComboBox(sorts);
            sort.setValue(LOWEST_PRICE);
            // Add value change listener
            sort.valueProperty().addListener((observable, oldValue, newValue) -> {
                String newValueStr = String.valueOf(newValue);
                String oldValueStr = String.valueOf(oldValue);
                if (!newValueStr.equals(oldValueStr)) {
                    // Sort
                    sortPropertyListings(newValueStr);
                    // Update UI
                    loadPropertyListings();
                }
            });
            gridPane.add(sort, 0, 0);
            GridPane.setHgrow(sort, Priority.ALWAYS);
            GridPane.setValignment(sort, VPos.TOP);
            GridPane.setHalignment(sort, HPos.LEFT);

            // Add bookButtons to the fxml/screen
            listingsContainer.getChildren().add(gridPane);

            // Book buttons next to each listing for user to add to the 'handleCheckoutAction basket' with the total price calculated based on the price/night and minimum nights and finally handleCheckoutAction
            GridPane bookBar = new GridPane();
            Button bookButton = new Button("BOOK");
            bookButton.getStyleClass().add("accented");
            bookButton.setOnMouseClicked((MouseEvent e) -> {
                if (!bookedListings.isEmpty()) {
                    // There are listings to book
                    // Go to the booking panel
                    Navigation.goTo((Stage) map.getScene().getWindow(), "booking", "Booking", loader -> {
                        BookingController bookingController = loader.getController();
                        bookingController.initializeBookedListings(bookedListings);
                    });
                } else {
                    // No listings to book
                    System.out.println("Nothing to book");
                }
            });
            bookBar.add(bookButton, 1, 0);
            GridPane.setValignment(bookButton, VPos.CENTER);
            GridPane.setHalignment(bookButton, HPos.RIGHT);
            GridPane.setHgrow(bookButton, Priority.ALWAYS);
            bookBar.setPadding(new Insets(14, 44, 14, 44));
            root.add(bookBar, 0, 4);
        }
        // Get the listings for the neighbourhood
        propertyListings = mapData.get(name);

        sortPropertyListings(LOWEST_PRICE);
        loadPropertyListings();
        // Update local stats every time a region marker is clicked to refresh local stats
        updateStats(isLocalStats, name);
    }

    private void updateStats(boolean isLocalStats, String key) {
        if (isLocalStats) {
            // If user wants local stats, then show local stats
            statsText1.setText(stats.getAveragePricesMessage(key));
            statsText2.setText(stats.getAverageMinimumNightsMessage(key));
            statsText3.setText(stats.getAverageAvailabilityMessage(key));
            statsText4.setText(stats.getHighestHostMessage(key));
        } else {
            // if isLocalStats is false, then get global stats
            statsText1.setText(stats.getAverageNumberOfReviewsMessage());
            statsText2.setText(stats.getPriciestRegionMessage());
            statsText3.setText(stats.getNumberOfPropertiesMessage());
            statsText4.setText(stats.getNumberOfEntireRoomAptsMessage());
        }
    }

    /**
     * Creates VBox listings under listingsContainer to separate it from statistics and neighbourhood title
     * Then, this method proceeds to create a GridPane object for each listing in propertyListings, and finally
     * appending to listingsContainer for display
     */
    private void loadPropertyListings() {
        if (listings == null) {
            // Element already does not exist, so create and add it
            listings = new VBox();
            listings.setPadding(new Insets(25, 0, 25, 0));
            listings.spacingProperty().setValue(8);
            listingsContainer.getChildren().add(listings);
        } else {
            // Element already exists, so clear it
            listings.getChildren().clear();
        }

        /**
         * This loops through all the search results, and create block style elements for each listing and append details as appropriate
         */
        for (AirbnbListing listing : propertyListings) {
            // Calls createPropertyListingItem to create a GridPane object from the AirbnbListing listing object
            GridPane gridPane = UIUtils.createPropertyListingItem(listing);
            Button bookButton = new Button("ADD");
            bookButton.setOnMouseClicked((MouseEvent e) -> {
                updateBooking(bookButton, listing);
            });
            gridPane.add(bookButton, 1, 2, 2, 1);
            GridPane.setHalignment(bookButton, HPos.CENTER);
            listings.getChildren().add(gridPane);
        }
    }

    /**
     * This method updates the user 'handleCheckoutAction basket'
     * Updates the arrayList of bookings everytime the user clicks the bookButton beside each listing (either remove or add)
     * @param bookButton
     * @param listing
     */
    public void updateBooking(Button bookButton, AirbnbListing listing) {
        // Check if bookedListing contains this AirbnbListing already or not
        if (bookedListings.containsKey(listing.getId())) {
            bookedListings.remove(listing.getId());
            bookButton.getStyleClass().remove("selected");
            bookButton.setText("ADD");
        } else {
            bookedListings.put(listing.getId(), listing);
            bookButton.getStyleClass().add("selected");
            bookButton.setText("ADDED");
        }
    }

    // Here we implement the sort filter on three criteria - lowest price, highest price, and host name
    private void sortPropertyListings(String sortBy) {
        switch (sortBy) {
            case LOWEST_PRICE:
                propertyListings.sort(Comparator.comparing(AirbnbListing::getPrice));
                break;
            case HIGHEST_PRICE:
                propertyListings.sort(Comparator.comparing(AirbnbListing::getPrice).reversed());
                break;
            case HOST_NAME:
                propertyListings.sort(Comparator.comparing(AirbnbListing::getHost_name));
                break;
            default:
                // void
                System.out.println(sortBy + " unknown sort");
        }
    }

    // Used to goBack to the previous scene/screen
    public void goBack(final ActionEvent actionEvent) {
        Navigation.goBack((Stage) map.getScene().getWindow());
    }
}
