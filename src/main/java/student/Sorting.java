package student;

import java.util.stream.Stream;

public final class Sorting {

    /**
     * Constructor for Sorting.
     */
    private Sorting() {

    }

    /**
     * Sorts the stream of board games based on the specified column and order.
     * @param filterGames the stream of board games to be sorted.
     * @param sortOn the column to sort the results on.
     * @param ascending whether the sort is in ascending order.
     * @return a sorted stream of board games based on the specified column and order.
     */
    public static Stream<BoardGame> sortOn(Stream<BoardGame> filterGames,
                                           GameData sortOn, boolean ascending) {
        switch (sortOn) {
            case NAME:
                if (ascending) {
                    return filterGames.sorted((g1, g2) -> g1.getName().compareToIgnoreCase(g2.getName()));
                } else {
                    return filterGames.sorted((g1, g2) -> g2.getName().compareToIgnoreCase(g1.getName()));
                }
            case RATING:
                if (ascending) {
                    return filterGames.sorted((g1, g2) -> Double.compare(g1.getRating(), g2.getRating()));
                } else {
                    return filterGames.sorted((g1, g2) -> Double.compare(g2.getRating(), g1.getRating()));
                }
            case DIFFICULTY:
                if (ascending) {
                    return filterGames.sorted((g1, g2) -> Double.compare(g1.getDifficulty(), g2.getDifficulty()));
                } else {
                    return filterGames.sorted((g1, g2) -> Double.compare(g2.getDifficulty(), g1.getDifficulty()));
                }
            case RANK:
                if (ascending) {
                    return filterGames.sorted((g1, g2) -> g1.getRank() - g2.getRank());
                } else {
                    return filterGames.sorted((g1, g2) -> g2.getRank() - g1.getRank());
                }
            case MIN_PLAYERS:
                if (ascending) {
                    return filterGames.sorted((g1, g2) -> g1.getMinPlayers() - g2.getMinPlayers());
                } else {
                    return filterGames.sorted((g1, g2) -> g2.getMinPlayers() - g1.getMinPlayers());
                }
            case MAX_PLAYERS:
                if (ascending) {
                    return filterGames.sorted((g1, g2) -> g1.getMaxPlayers() - g2.getMaxPlayers());
                } else {
                    return filterGames.sorted((g1, g2) -> g2.getMaxPlayers() - g1.getMaxPlayers());
                }
            case MIN_TIME:
                if (ascending) {
                    return filterGames.sorted((g1, g2) -> g1.getMinPlayTime() - g2.getMinPlayTime());
                } else {
                    return filterGames.sorted((g1, g2) -> g2.getMinPlayTime() - g1.getMinPlayTime());
                }
            case MAX_TIME:
                if (ascending) {
                    return filterGames.sorted((g1, g2) -> g1.getMaxPlayTime() - g2.getMaxPlayTime());
                } else {
                    return filterGames.sorted((g1, g2) -> g2.getMaxPlayTime() - g1.getMaxPlayTime());
                }
            case YEAR:
                if (ascending) {
                    return filterGames.sorted((g1, g2) -> g1.getYearPublished() - g2.getYearPublished());
                } else {
                    return filterGames.sorted((g1, g2) -> g2.getYearPublished() - g1.getYearPublished());
                }
            default:
                return filterGames;
        }
    }
}
