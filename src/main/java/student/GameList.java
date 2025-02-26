package student;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class GameList implements IGameList {

    /** hold all the games. */
    private Set<String> setOfGames;

    /**
     * Constructor for the GameList.
     */
    public GameList() {
        setOfGames = new LinkedHashSet<>();
    }

    /**
     * Get a list of the names of games from the game list.
     * @return a list of the names of games.
     */
    @Override
    public List<String> getGameNames() {
       return List.copyOf(setOfGames); // unchanged
    }

    /**
     * Removes all games in the list (clears it out completely).
     */
    @Override
    public void clear() {
        setOfGames.clear();
    }

    /**
     * Counts the number of games in the list.
     * @return the number of games in the list.
     */
    @Override
    public int count() {
        return setOfGames.size();
    }

    /**
     * Saves the list of games to a file.
     * @param filename the name of the file to save the list to.
     */
    @Override
    public void saveGame(String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            for (String game : setOfGames) {
                writer.write(game);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Adds a game or games to the list.
     * @param str the string to parse and add games to the list.
     * @param filtered the filtered list to use as a basis for adding.
     * @throws IllegalArgumentException if the string is not valid.
     */
    @Override
    public void addToList(String str, Stream<BoardGame> filtered) throws IllegalArgumentException {

        List<BoardGame> gameList = filtered.toList();
        int size = gameList.size();


        if (ADD_ALL.equals(str)) {
            addAllToList(gameList);
            return;
        }

        if (str.contains("-")) {
            String[] range = str.split("-");
            if (range.length != 2) {
                throw new IllegalArgumentException("Invalid range: " + str);
            }
            try {
                int start = Integer.parseInt(range[0]);
                int end = Integer.parseInt(range[1]);
                if (start < 1 || end > size || start > end) {
                    throw new IllegalArgumentException("Invalid range: " + str);
                }
                addRangeToList(gameList, start - 1, end - 1);
                return;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Range contains non-numeric values: " + str);
            }
        }

        try {
            int number = Integer.parseInt(str);
            if (number < 1 || number > size) {
                throw new IllegalArgumentException("Invalid game number: " + str);
            }
            addSingleGameToList(gameList, number - 1);
            return;
        } catch (NumberFormatException e) {
            // Optional<T> is a wrapper class that helps to handle the case where a value may or may not be present.
            Optional<BoardGame> game = gameList.stream().filter(boardGame -> boardGame.getName().equals(str.trim())).findFirst();
            // isPresent() checks whether a value exists inside the Optional.
            if (game.isPresent()) {
                addGameBasedOnName(str);
                return;
            } else {
                throw new IllegalArgumentException("Invalid game name: " + str);
            }
        }
    }

    /**
     * Add a single game to the game list based on the name of game.
     * @param gameName the name of game added to the game list.
     */
    private void addGameBasedOnName(String gameName) {
        setOfGames.add(gameName);
    }

    /**
     * Add a single game to the game list according to the index number.
     * @param gameList the game list to use as a basis for adding.
     * @param number the number of the game added to the game list.
     */
    private void addSingleGameToList(List<BoardGame> gameList, int number) {
        BoardGame game = gameList.get(number);
        setOfGames.add(game.getName());
    }

    /**
     * Add games to the game list whose indices are between start and end.
     * @param gameList the game list to use as a basis for adding.
     * @param start the start number of the games added to the game list.
     * @param end the end number of the games added to the game list.
     */
    private void addRangeToList(List<BoardGame> gameList, int start, int end) {
        for (int i = start; i <= end; i++) {
            BoardGame game = gameList.get(i);
            setOfGames.add(game.getName());
        }
    }

    /**
     * Add all games in the filtered list to the game list.
     * @param gameList the game list to use as a basis for adding.
     */
    private void addAllToList(List<BoardGame> gameList) {
        for (BoardGame game : gameList) {
            setOfGames.add(game.getName());
        }
    }

    /**
     * Removes a game or games from the list.
     * @param str the string to parse and remove games from the list.
     * @throws IllegalArgumentException if the string is not valid.
     */
    @Override
    public void removeFromList(String str) throws IllegalArgumentException {
        int size = setOfGames.size();
        if (ADD_ALL.equals(str)) {
            clear();
            return;
        }
        if (str.contains("-")) {
            String[] range = str.split("-");
            if (range.length != 2) {
                throw new IllegalArgumentException("Invalid range: " + str);
            }
            try {
                int start = Integer.parseInt(range[0]);
                int end = Integer.parseInt(range[1]);
                if (start < 1 || end > size || start > end) {
                    throw new IllegalArgumentException("Invalid range: " + str);
                }
                removeRangeFromList(start - 1, end - 1);
                return;
            } catch (NumberFormatException e){
                throw new IllegalArgumentException("Range contains non-numeric values: " + str);
            }
        }
        try {
            int number = Integer.parseInt(str);
            if (number < 1 || number > size) {
                throw new IllegalArgumentException("Invalid game number: " + str);
            }
            removeSingleGameFromList(number - 1);
            return;
        } catch (NumberFormatException e) {
            if (setOfGames.contains(str.trim())) {
                removeGameBasedOnGame(str);
                return;
            } else {
                throw new IllegalArgumentException("Invalid game name: " + str);
            }
        }
    }

    /**
     * Remove a single game from the game list based on the name of game.
     * @param gameName the name of game removed from the game list.
     */
    private void removeGameBasedOnGame(String gameName) {
        setOfGames.remove(gameName);
    }

    /**
     * Remove a single game from the game list according to the index number.
     * @param number the index of the game removed from the game list.
     */
    private void removeSingleGameFromList(int number) {
        List<String> gameList = new ArrayList<>(setOfGames);
        String gameName = "";
        for (int i = gameList.size() - 1; i >= 0; i--) {
            if (i == number) {
                gameName = gameList.get(i);
            }
        }
        setOfGames.remove(gameName);
    }

    /**
     * Removes games from the game list whose indices are between start and end.
     * @param start the start index of the games removed from the game list.
     * @param end the end index of the games removed from the game list.
     */
    private void removeRangeFromList(int start, int end) {
        List<String> gameList = new ArrayList<>(setOfGames);
        Set<String> removedGames = new HashSet<>();
        for (int i = gameList.size() - 1; i >= 0; i--) {
            if (i >= start && i <= end) {
               removedGames.add(gameList.get(i));
            }
        }
        for (String game : removedGames) {
            setOfGames.remove(game);
        }
    }
}
