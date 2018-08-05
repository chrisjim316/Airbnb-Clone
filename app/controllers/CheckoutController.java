package app.controllers;

import app.src.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author (Jim, Chow-Ching K1763805) (Rehman, Habib-Ur K1763960) (Mahmood, Mohsan K1763716) (Lee, Daniel K1764092)
 */
public class CheckoutController implements Initializable {
    @FXML
    private Text paymentSuccessfulText;

    @FXML
    private Hyperlink backButton;

    public CheckoutController() {
        super();
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        // initialize everything
    }

    public void showSuccessfulPaymentText(String paymentStr) {
        paymentSuccessfulText.setText(paymentStr);
    }

    /**
     * This method takes the user back to the region-specific house listings, where the user is able to add or remove bookings
     * Previous selections are saved with the help of stack structure
     *
     * @param actionEvent
     */
    public void goBack(ActionEvent actionEvent) {
        Navigation.goBack((Stage) backButton.getScene().getWindow());
    }
}
