import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import student.BoardGame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import student.Planner;
import student.IPlanner;
import student.GameData;


/**
 * JUnit test for the Planner class.
 * 
 * Just a sample test to get you started, also using
 * setup to help out. 
 */
public class TestPlanner {
    static Set<BoardGame> games;

    @BeforeAll
    public static void setup() {
        games = new LinkedHashSet<>();
        games.add(new BoardGame("17 days", 6, 1, 8, 70, 70, 9.0, 600, 9.0, 2005));
        games.add(new BoardGame("Chess", 7, 2, 2, 10, 20, 10.0, 700, 10.0, 2006));
        games.add(new BoardGame("Go", 1, 2, 5, 30, 30, 8.0, 100, 7.5, 2000));
        games.add(new BoardGame("Go Fish", 2, 2, 10, 20, 120, 3.0, 200, 6.5, 2001));
        games.add(new BoardGame("golang", 4, 2, 7, 50, 55, 7.0, 400, 9.5, 2003));
        games.add(new BoardGame("GoRami", 3, 6, 6, 40, 42, 5.0, 300, 8.5, 2002));
        games.add(new BoardGame("Monopoly", 8, 6, 10, 20, 1000, 1.0, 800, 5.0, 2007));
        games.add(new BoardGame("Tucano", 5, 10, 20, 60, 90, 6.0, 500, 8.0, 2004));
    }

     @Test
    public void testFilter() {
        // test string value
        IPlanner planner1 = new Planner(games);
        List<BoardGame> filtered1 = planner1.filter("name == Go").toList();
        assertEquals(1, filtered1.size());
        assertEquals("Go", filtered1.get(0).getName());

         // test empty filter
         IPlanner planner2 = new Planner(games);
         List<BoardGame> filtered2 = planner2.filter("\"\"").toList();
         assertEquals(8, filtered2.size());
         assertEquals("17 days", filtered2.get(0).getName());

         // test multiple filters
         IPlanner planner3 = new Planner(games);
         List<BoardGame> filtered3 = planner3.filter("name ~= Go, minPlayers <= 2").toList();
         assertEquals(3, filtered3.size());
         assertEquals("Go", filtered3.get(0).getName());

         // invalid column
         IPlanner planner4 = new Planner(games);
         List<BoardGame> filtered4 = planner4.filter("name ~= Go, minUsers < 2").toList();
         assertEquals(4, filtered4.size());
         assertEquals("Go", filtered3.get(0).getName());

         // invalid value
         IPlanner planner5 = new Planner(games);
         List<BoardGame> filtered5 = planner5.filter("name != Go, minPlaytime >= go").toList();
         assertEquals(7, filtered5.size());
         System.out.println(filtered5);

         // test double value
         IPlanner planner6 = new Planner(games);
         List<BoardGame> filtered6 = planner6.filter("rating > 9").toList();
         assertEquals(2, filtered6.size());
         //System.out.println(filtered6);
         assertEquals("Chess", filtered6.get(0).getName());

         // test int value
         IPlanner planner7 = new Planner(games);
         List<BoardGame> filtered7 = planner7.filter("year < 2003").toList();
         assertEquals(3, filtered7.size());
         assertEquals("Go", filtered7.get(0).getName());
    }

    @Test
    public void testFilterWithSortOn() {
        // test string value
        IPlanner planner1 = new Planner(games);
        List<BoardGame> filtered1 = planner1.filter("name ~= Go", GameData.NAME).toList();
        assertEquals(4, filtered1.size());
        assertEquals("Go", filtered1.get(0).getName());
        System.out.println(filtered1);

        // test empty filter
        IPlanner planner2 = new Planner(games);
        List<BoardGame> filtered2 = planner2.filter("\"\"", GameData.RANK).toList();
        assertEquals(8, filtered2.size());
        assertEquals("Go", filtered2.get(0).getName());

        // test multiple filters
        IPlanner planner3 = new Planner(games);
        List<BoardGame> filtered3 = planner3.filter("name ~= Go, minPlayers <= 2", GameData.MAX_PLAYERS).toList();
        assertEquals(3, filtered3.size());
        assertEquals("Go", filtered3.get(0).getName());

        // invalid column
        IPlanner planner4 = new Planner(games);
        List<BoardGame> filtered4 = planner4.filter("name ~= Go, minUsers < 2", GameData.NAME).toList();
        assertEquals(4, filtered4.size());


        // invalid value
        IPlanner planner5 = new Planner(games);
        List<BoardGame> filtered5 = planner5.filter("name != Go, minPlaytime >= go", GameData.YEAR).toList();
        assertEquals(7, filtered5.size());
        assertEquals("Go Fish", filtered5.get(0).getName());

        // test double value
        IPlanner planner6 = new Planner(games);
        List<BoardGame> filtered6 = planner6.filter("rating > 9", GameData.DIFFICULTY).toList();
        assertEquals(2, filtered6.size());
        assertEquals("golang", filtered6.get(0).getName());

        // test int value
        IPlanner planner7 = new Planner(games);
        List<BoardGame> filtered7 = planner7.filter("year < 2003", GameData.YEAR).toList();
        assertEquals(3, filtered7.size());
        assertEquals("Go", filtered7.get(0).getName());
    }

    @Test
    public void testFilterWithSortOnAndOrder() {
        // test string value
        IPlanner planner1 = new Planner(games);
        List<BoardGame> filtered1 = planner1.filter("name ~= Go", GameData.NAME, true).toList();
        assertEquals(4, filtered1.size());
        assertEquals("Go", filtered1.get(0).getName());
        System.out.println(filtered1);

        // test empty filter
        IPlanner planner2 = new Planner(games);
        List<BoardGame> filtered2 = planner2.filter("\"\"", GameData.RANK, false).toList();
        assertEquals(8, filtered2.size());
        assertEquals("Monopoly", filtered2.get(0).getName());

        // test multiple filters
        IPlanner planner3 = new Planner(games);
        List<BoardGame> filtered3 = planner3.filter("name ~= Go, minPlayers <= 2", GameData.MAX_PLAYERS, true).toList();
        assertEquals(3, filtered3.size());
        assertEquals("Go", filtered3.get(0).getName());

        // invalid column
        IPlanner planner4 = new Planner(games);
        List<BoardGame> filtered4 = planner4.filter("name ~= Go, minUsers < 2", GameData.NAME, true).toList();
        assertEquals(4, filtered4.size());

        // invalid value
        IPlanner planner5 = new Planner(games);
        List<BoardGame> filtered5 = planner5.filter("name != Go, minPlaytime >= go", GameData.YEAR, true).toList();
        assertEquals(7, filtered5.size());
        assertEquals("Go Fish", filtered5.get(0).getName());

        // test double value
        IPlanner planner6 = new Planner(games);
        List<BoardGame> filtered6 = planner6.filter("rating > 9", GameData.DIFFICULTY, false).toList();
        assertEquals(2, filtered6.size());
        assertEquals("Chess", filtered6.get(0).getName());

        // test int value
        IPlanner planner7 = new Planner(games);
        List<BoardGame> filtered7 = planner7.filter("year < 2003", GameData.YEAR, false).toList();
        assertEquals(3, filtered7.size());
        assertEquals("GoRami", filtered7.get(0).getName());
    }

    @Test
    public void testReset() {
        IPlanner planner = new Planner(games);
        List<BoardGame> filtered = planner.filter("name == Go").toList();
        assertEquals(1, filtered.size());
        planner.reset();
        List<BoardGame> filtered2 = planner.filter("\"\"").toList();
        assertEquals(8, filtered2.size());
    }
}
