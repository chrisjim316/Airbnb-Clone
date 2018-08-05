package app;

import app.src.Navigation;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Driver class, launching the welcome screen initially at app launch
 * @author (Jim, Chow-Ching K1763805) (Rehman, Habib-Ur K1763960) (Mahmood, Mohsan K1763716) (Lee, Daniel K1764092)
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Navigation.goTo(primaryStage, "welcome", "Property Search");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
