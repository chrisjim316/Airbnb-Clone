package app;

import app.models.Point;

import java.util.HashMap;

/**
 * This class Config sets the positions of each region marker to match the regions in the London map
 * @author (Jim, Chow-Ching K1763805) (Rehman, Habib-Ur K1763960) (Mahmood, Mohsan K1763716) (Lee, Daniel K1764092)
 */
public class Config {
    // Declare the size of the actual window
    public static int WINDOW_WIDTH = 960;
    public static int WINDOW_HEIGHT = 600;
    public static HashMap<String, Point> MARKER_POINTS = new HashMap();
    static {
        MARKER_POINTS.put("Hillingdon", new Point(172, 195));
        MARKER_POINTS.put("Tower Hamlets", new Point(495, 208));
        MARKER_POINTS.put("Greenwich", new Point(555, 274));
        MARKER_POINTS.put("Newham", new Point(550, 160));
        MARKER_POINTS.put("Barking and Dagenham", new Point(630, 160));
        MARKER_POINTS.put("Havering", new Point(710, 130));
        MARKER_POINTS.put("Southwark", new Point(453, 263));
        MARKER_POINTS.put("Croydon", new Point(450, 350));
        MARKER_POINTS.put("Bromley", new Point(555, 420));
        MARKER_POINTS.put("Lewisham", new Point(500, 260));
        MARKER_POINTS.put("Redbridge", new Point(580, 100));
        MARKER_POINTS.put("Hackney", new Point(455, 135));
        MARKER_POINTS.put("Waltham Forest", new Point(495, 80));
        MARKER_POINTS.put("Bexley", new Point(630, 250));
        MARKER_POINTS.put("Wandsworth", new Point(390, 265));
        MARKER_POINTS.put("Lambeth", new Point(420, 295));
        MARKER_POINTS.put("Merton", new Point(385, 320));
        MARKER_POINTS.put("Sutton", new Point(390, 360));
        MARKER_POINTS.put("Kingston upon Thames", new Point(285, 380));
        MARKER_POINTS.put("Richmond upon Thames", new Point(285, 267));
        MARKER_POINTS.put("Ealing", new Point(250, 170));
        MARKER_POINTS.put("Hounslow", new Point(210, 240));
        MARKER_POINTS.put("Harrow", new Point(240, 90));
        MARKER_POINTS.put("Brent", new Point(295, 125));
        MARKER_POINTS.put("Barnet", new Point(350, 55));
        MARKER_POINTS.put("Haringey", new Point(448, 95));
        MARKER_POINTS.put("Enfield", new Point(440, 22));
        MARKER_POINTS.put("Islington", new Point(425, 138));
        MARKER_POINTS.put("City of London", new Point(449, 192));
        MARKER_POINTS.put("Camden", new Point(390, 140));
        MARKER_POINTS.put("Westminster", new Point(410, 197));
        MARKER_POINTS.put("Hammersmith and Fulham", new Point(334, 185));
        MARKER_POINTS.put("Kensington and Chelsea", new Point(360, 200));
    }
}
