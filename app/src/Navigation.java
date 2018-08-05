package app.src;

import app.Config;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

/**
 * The Navigation class provides the panel navigation functionality.
 * @author (Jim, Chow-Ching K1763805) (Rehman, Habib-Ur K1763960) (Mahmood, Mohsan K1763716) (Lee, Daniel K1764092)
 */
public class Navigation {
    /**
     * The callback interface is used to specify code to run during the view load
     */
    public interface loadCallback {
        void run(FXMLLoader loader);
    }

    /**
     * The Panel class is used to store all the state of a Panel
     */
    public static class Panel {
        private Scene scene;
        private String title;

        public Panel(Scene scene, String stageTitle) {
            this.scene = scene;
            this.title = stageTitle;
        }
    }

    // Stores the panel history (incl. state)
    private Stack<Panel> history = new Stack<>();
    private Panel previousPanel;
    private static Navigation ourInstance = new Navigation();

    // Constructor private to prevent direct instantiation
    private Navigation() {

    }

    public static Navigation getInstance() {
        return ourInstance;
    }


    /**
     * Navigates to a specified panel and saves its state
     * Overloaded shortcut to be used when no callback needed.
     *
     * @param stage
     * @param view
     * @param title
     */
    public static void goTo(Stage stage, String view, String title) {
        Navigation.goTo(stage, view, title, null);
    }

    /**
     * Navigates to a specified panel and saves its state
     * Sets up the scene with the style.css files and specified fxml view
     * Executes the callback if specified passing it the view loader
     *
     * @param stage
     * @param view
     * @param title
     * @param cb
     */
    public static void goTo(Stage stage, String view, String title, Navigation.loadCallback cb) {
        try {
            FXMLLoader loader = new FXMLLoader(Navigation.class.getResource("../views/" + view + ".fxml"));
            Parent root = loader.load();
            if (cb != null) {
                // Run callback if specified
                cb.run(loader);
            }
            Scene scene = new Scene(root, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
            scene.getStylesheets().add(Navigation.class.getResource("../views/styles/styles.css").toString());
            // Add the previous panel to the history
            ourInstance.history.push(ourInstance.previousPanel);
            // Set current panel to the previous panel
            ourInstance.previousPanel = new Panel(scene, title);
            Navigation.updateStage(stage, scene, title);
        } catch (IOException e) {
            // Prints error message if exception is caught
            e.printStackTrace();
        }
    }

    /**
     * Navigate back to the previous panel and restores its state
     *
     * @param stage
     */
    public static void goBack(Stage stage) {
        // Load the previous pane
        // Most recent one in history becomes the previous panel
        ourInstance.previousPanel = ourInstance.history.pop();
        Navigation.updateStage(stage, ourInstance.previousPanel.scene, ourInstance.previousPanel.title);
    }

    /**
     * Updates the specified stage with the a new scene and title
     *
     * @param stage
     * @param scene
     * @param title
     */
    public static void updateStage(Stage stage, Scene scene, String title) {
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
