import app.src.PropertyHandler;
import app.src.Statistics;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This is a unit test class to check if the methods give out the right and expected result.
 * Returns error if the expected result is wrong.
 * We are testing calculations in Statistics class.
 * @author (Jim, Chow-Ching K1763805) (Rehman, Habib-Ur K1763960) (Mahmood, Mohsan K1763716) (Lee, Daniel K1764092)
 */
public class Tests {
    private static PropertyHandler propertyHandler = new PropertyHandler();
    // Stores the statistics of data range from 0 to 150.
    private static Statistics zeroTo150Stats;
    // Stores the statistics of data range from 300 to 500.
    private static Statistics _300To500Stats;

    //The following strings are created in order to avoid writing the same sentences over and over again.
    private static final String TOWER_HAMLETS = "Tower Hamlets";
    private static final String CROYDON = "Croydon";

    private static final String ZERO_TO_150_STATS = " zero to 150 stats";
    private static final String _300_TO_500_STATS = " 300 to 500 stats";

    // This method initialises the statistics of properties priced ranged from 0 to 150 and from 300 to 500.
    @BeforeClass
    public static void init() {
        zeroTo150Stats = new Statistics(propertyHandler.search(0, 150));
        _300To500Stats = new Statistics(propertyHandler.search(300, 500));
    }

    // Checks the global statistics price range from 0 to 150.
    @Test
    public void zeroTo150Stats() {
        assertEquals("failure - " + ZERO_TO_150_STATS + " average number of reviews does not match", 27, zeroTo150Stats.getAverageNumberOfReviews());
        assertEquals("failure - " + ZERO_TO_150_STATS + " highest price does not match", "Enfield", zeroTo150Stats.getPriciestRegion());
        assertEquals("failure - " + ZERO_TO_150_STATS + " number of entire room apartments does not match", 20057, zeroTo150Stats.getNumberOfEntireRoomApts());
        assertEquals("failure - " + ZERO_TO_150_STATS + " total number of properties does not match", 46486, zeroTo150Stats.getNumberOfProperties());
    }

    // Checks the global statistics of price range from 300 to 500.
    @Test
    public void _300To500Stats() {
        assertEquals("failure - " + _300_TO_500_STATS + " average number of reviews does not match", 3, _300To500Stats.getAverageNumberOfReviews());
        assertEquals("failure - " + _300_TO_500_STATS + " highest price does not match", "Westminster", _300To500Stats.getPriciestRegion());
        assertEquals("failure - " + _300_TO_500_STATS + " number of entire room apartments does not match", 1201, _300To500Stats.getNumberOfEntireRoomApts());
        assertEquals("failure - " + _300_TO_500_STATS + " total number of properties does not match", 1256, _300To500Stats.getNumberOfProperties());
    }

    // Checks specific statistics of Tower Hamlets price range from 0 to 150.
    @Test
    public void towerHamletsZeroTo150Stats() {
        assertEquals("failure - " + TOWER_HAMLETS + ZERO_TO_150_STATS + " day availability does not match", 148, zeroTo150Stats.getAverageAvailability(TOWER_HAMLETS));
        assertEquals("failure - " + TOWER_HAMLETS + ZERO_TO_150_STATS + " average price does not match", 64, zeroTo150Stats.getAveragePrices(TOWER_HAMLETS));
        assertEquals("failure - " + TOWER_HAMLETS + ZERO_TO_150_STATS + " average minimum nights does not match", 3, zeroTo150Stats.getAverageMinimumNights(TOWER_HAMLETS));
        assertEquals("failure - " + TOWER_HAMLETS + ZERO_TO_150_STATS + " highest host does not match", "Tom", zeroTo150Stats.getHighestHosts(TOWER_HAMLETS));
    }

    // Checks specific statistics of Croydon price range from 0 to 150.
    @Test
    public void croydonZeroTo150Stats() {
        assertEquals("failure - " + CROYDON + ZERO_TO_150_STATS + " day availability does not match", 211, zeroTo150Stats.getAverageAvailability(CROYDON));
        assertEquals("failure - " + CROYDON + ZERO_TO_150_STATS + " average price does not match", 48, zeroTo150Stats.getAveragePrices(CROYDON));
        assertEquals("failure - " + CROYDON + ZERO_TO_150_STATS + " average minimum nights does not match", 2, zeroTo150Stats.getAverageMinimumNights(CROYDON));
        assertEquals("failure - " + CROYDON + ZERO_TO_150_STATS + " highest host does not match", "Francis And Mark", zeroTo150Stats.getHighestHosts(CROYDON));
    }

    // Checks the specific statistics of Tower Hamlets price range from 300 to 500.
    @Test
    public void towerHamlets300To500Stats() {
        assertEquals("failure - " + TOWER_HAMLETS + _300_TO_500_STATS + " day availability does not match", 209, _300To500Stats.getAverageAvailability(TOWER_HAMLETS));
        assertEquals("failure - " + TOWER_HAMLETS + _300_TO_500_STATS + " average price does not match", 367, _300To500Stats.getAveragePrices(TOWER_HAMLETS));
        assertEquals("failure - " + TOWER_HAMLETS + _300_TO_500_STATS + " average minimum nights does not match", 3, _300To500Stats.getAverageMinimumNights(TOWER_HAMLETS));
        assertEquals("failure - " + TOWER_HAMLETS + _300_TO_500_STATS + " highest host does not match", "Tom", _300To500Stats.getHighestHosts(TOWER_HAMLETS));
    }

    // Checks the specific statistics of Croydon price range from 300 to 500.
    @Test
    public void croydon300To500Stats() {
        assertEquals("failure - " + CROYDON + _300_TO_500_STATS + " day availability does not match", 233, _300To500Stats.getAverageAvailability(CROYDON));
        assertEquals("failure - " + CROYDON + _300_TO_500_STATS + " average price does not match", 338, _300To500Stats.getAveragePrices(CROYDON));
        assertEquals("failure - " + CROYDON + _300_TO_500_STATS + " average minimum nights does not match", 3, _300To500Stats.getAverageMinimumNights(CROYDON));
        assertEquals("failure - " + CROYDON + _300_TO_500_STATS + " highest host does not match", "Deniza", _300To500Stats.getHighestHosts(CROYDON));
    }
}
