package app.controllers;

import app.src.Navigation;
import app.src.PropertyHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author (Jim, Chow-Ching K1763805) (Rehman, Habib-Ur K1763960) (Mahmood, Mohsan K1763716) (Lee, Daniel K1764092)
 */
public class WelcomeController implements Initializable {
    @FXML
    private ComboBox from;

    @FXML
    private ComboBox to;

    @FXML
    private Text error;

    private final PropertyHandler propertyHandler;

    public WelcomeController() {
        super();
        this.propertyHandler = new PropertyHandler();
    }


    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        // initialize everything
    }

    /**
     * This method validates whether user input - fromPrice and toPrice are correct, fromPrice must be smaller that toPrice
     *
     * @param x
     * @param y
     * @return false if otherwise
     */
    private boolean validate(Integer x, Integer y) {
        return x < y;
    }

    /**
     * This method handles user action events
     *
     * @param event
     */
    @FXML
    private void handleSearchAction(ActionEvent event) {
        // Hide error message
        error.setVisible(false);
        Integer fromPrice = Integer.parseInt(from.getValue().toString());
        Integer toPrice = Integer.parseInt(to.getValue().toString());
        if (validate(fromPrice, toPrice)) {
            // Price is valid
            // Go to map panel
            Navigation.goTo((Stage) from.getScene().getWindow(), "map", "Property Search Results", loader -> {
                MapController mapController = loader.getController();
                mapController.loadMap(propertyHandler.search(fromPrice, toPrice));
            });
        } else {
            // Price is invalid
            // Show error message
            error.setVisible(true);
        }
    }
}
