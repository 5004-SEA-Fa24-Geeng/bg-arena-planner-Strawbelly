package student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

class GameListTest {

    public Set<BoardGame> games;
    public IGameList gameList;

    @TempDir
    Path tempDir;

    private String testFile;

    @BeforeEach
    void setUp() {
        // create a set of games for testing
        games = new LinkedHashSet<>();
        games.add(new BoardGame("17 days", 6, 1, 8, 70, 70, 9.0, 600, 9.0, 2005));
        games.add(new BoardGame("Chess", 7, 2, 2, 10, 20, 10.0, 700, 10.0, 2006));
        games.add(new BoardGame("Go", 1, 2, 5, 30, 30, 8.0, 100, 7.5, 2000));
        games.add(new BoardGame("Go Fish", 2, 2, 10, 20, 120, 3.0, 200, 6.5, 2001));
        games.add(new BoardGame("golang", 4, 2, 7, 50, 55, 7.0, 400, 9.5, 2003));
        games.add(new BoardGame("GoRami", 3, 6, 6, 40, 42, 5.0, 300, 8.5, 2002));
        games.add(new BoardGame("Monopoly", 8, 6, 10, 20, 1000, 1.0, 800, 5.0, 2007));
        games.add(new BoardGame("Tucano", 5, 10, 20, 60, 90, 6.0, 500, 8.0, 2004));

        // create a game list for testing
        gameList = new GameList();
        gameList.addToList("Go", games.stream());
        gameList.addToList("Go Fish", games.stream());
        gameList.addToList("golang", games.stream());
        gameList.addToList("GoRami", games.stream());

        // get the name of the test file
        testFile = tempDir.resolve("test_games.txt").toString();
    }


    @Test
    void getGameNames() {
        List<String> gameNames = gameList.getGameNames();
        assertEquals(4, gameNames.size());
        assertFalse(gameNames.contains("Chess"));
        assertTrue(gameNames.contains("golang"));
        assertTrue(gameNames.contains("Go"));
    }

    @Test
    void clear() {
        assertEquals(4, gameList.count());
        gameList.clear();
        assertEquals(0, gameList.count());
    }

    @Test
    void count() {
        List<String> gameNames = gameList.getGameNames();
        assertEquals(4, gameNames.size());
    }

    @Test
    void saveGame() {
        gameList.saveGame(testFile);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(testFile));
            assertEquals("Go", reader.readLine());
            assertEquals("Go Fish", reader.readLine());
            assertEquals("golang", reader.readLine());
            assertEquals("GoRami", reader.readLine());

            assertNull(reader.readLine());
            reader.close();
        } catch (IOException e) {
            fail("Failed to read the file: " + e.getMessage());
        }
    }

    @Test
    void testAddSingleGameToList() {
        IGameList list1 = new GameList();
        list1.addToList("1", games.stream());
        assertEquals(1, list1.count());
        assertEquals("17 days", list1.getGameNames().get(0));
        System.out.println(list1.getGameNames());

        IGameList list2 = new GameList();
        assertThrows(IllegalArgumentException.class, () -> {
            list2.addToList("0", games.stream());
        });

        IGameList list3 = new GameList();
        assertThrows(IllegalArgumentException.class, () -> {
            list3.addToList("9", games.stream());
        });
    }

    @Test
    void testAddRangeToList() {
        // the correct range of number of games is between 1 and 8
        IGameList list1 = new GameList();
        list1.addToList("1-3", games.stream());
        assertEquals(3, list1.count());
        assertEquals("17 days", list1.getGameNames().get(0));
        System.out.println(list1.getGameNames());

        IGameList list2 = new GameList();
        list2.addToList("1-1", games.stream());
        assertEquals(1, list2.count());
        assertEquals("17 days", list2.getGameNames().get(0));
        System.out.println(list1.getGameNames());

        IGameList list3 = new GameList();
        assertThrows(IllegalArgumentException.class, () -> {
            list3.addToList("3-1", games.stream());
        });

        IGameList list4 = new GameList();
        assertThrows(IllegalArgumentException.class, () -> {
            list4.addToList("0-8", games.stream());
        });

        IGameList list5 = new GameList();
        assertThrows(IllegalArgumentException.class, () -> {
            list5.addToList("3-9", games.stream());
        });

        IGameList list6 = new GameList();
        assertThrows(IllegalArgumentException.class, () -> {
            list6.addToList("3-", games.stream());
        });

        IGameList list7 = new GameList();
        assertThrows(IllegalArgumentException.class, () -> {
            list7.addToList("a-b", games.stream());
        });
    }

    @Test
    void testAddGameBasedOnName() {
        IGameList list1 = new GameList();
        assertThrows(IllegalArgumentException.class, () -> {
            list1.addToList("GoGoTiger", games.stream());
        });

        IGameList list2 = new GameList();
        list2.addToList("Go Fish", games.stream());
        list2.addToList("Tucano", games.stream());
        assertEquals(2, list2.count());
        assertEquals("Go Fish", list2.getGameNames().get(0));

    }

    @Test
    void testAddAllToList() {
        IGameList list = new GameList();
        list.addToList("all", games.stream());
        assertEquals(8, list.count());
        assertEquals("17 days", list.getGameNames().get(0));
        //System.out.println(list.getGameNames());
    }

    @Test
    void testRemoveSingleGameFromList() {
        gameList.removeFromList("1");
        assertEquals(3, gameList.count());
        assertEquals("Go Fish", gameList.getGameNames().get(0));


        assertThrows(IllegalArgumentException.class, () -> {
            gameList.removeFromList("0");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            gameList.removeFromList("9");
        });
    }

    @Test
    void testRemoveRangeFromList() {
        // the correct range of number of games is between 1 and 4
        assertThrows(IllegalArgumentException.class, () -> {
            gameList.removeFromList("5-1");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            gameList.removeFromList("1-5");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            gameList.removeFromList("0-2");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            gameList.removeFromList("1-");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            gameList.removeFromList("a-bcd");
        });

        // create a new game list for testing
        gameList = new GameList();
        gameList.addToList("Go", games.stream());
        gameList.addToList("go fish", games.stream());
        gameList.addToList("golang", games.stream());
        gameList.addToList("GoRami", games.stream());
        gameList.removeFromList("1-1");
        assertEquals(3, gameList.count());
        assertFalse(gameList.getGameNames().contains("Go"));
        assertEquals("Go Fish", gameList.getGameNames().get(0));

        // create a new game list for testing
        gameList = new GameList();
        gameList.addToList("Go", games.stream());
        gameList.addToList("Go Fish", games.stream());
        gameList.addToList("golang", games.stream());
        gameList.addToList("GoRami", games.stream());
        gameList.removeFromList("1-4");
        assertEquals(0, gameList.count());
    }

    @Test
    void testRemoveGameBasedOnGame() {
        gameList.removeFromList("go fish");
        assertEquals(3, gameList.count());

        assertThrows(IllegalArgumentException.class, () -> {
            gameList.removeFromList("goTiger");
        });
    }

    @Test
    void testRemoveAll() {
        gameList.removeFromList("all");
        assertEquals(0, gameList.count());
    }
}
