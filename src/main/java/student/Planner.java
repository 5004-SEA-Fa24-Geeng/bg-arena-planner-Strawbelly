package student;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Planner implements IPlanner {

    /** hold the original board games. */
    private final Set<BoardGame> originalGames;

    /** hold the current filtered board games. */
    private Stream<BoardGame> currentGames;

    /**
     * Constructor for Planner.
     * @param games the board games.
     */
    public Planner(Set<BoardGame> games) {
        this.originalGames = games;
        this.currentGames = games.stream();
    }

    /**
     * Filters board games based on the string filter.
     * The result stream is sorted by the name of the board game in ascending order.
     * @param filter the filter to apply to the board games.
     * @return a stream of board games that match the filter.
     */
    @Override
    public Stream<BoardGame> filter(String filter) {

        if (filter.isEmpty()) {
            return currentGames = sortOn(currentGames, GameData.NAME, true);
        }

        if (!filter.contains(",")) {
            return currentGames = sortOn(filterSingle(filter, currentGames), GameData.NAME, true);
        }

        String[] filters = filter.split(",");
        for (String condition : filters) {
            Stream<BoardGame> tempStream = filterSingle(condition, currentGames);
            if (tempStream != null) {
                currentGames = tempStream;
            } else {
                break;
            }
        }
        return sortOn(currentGames, GameData.NAME, true);
    }

    /**
     * Filters board games based on the string filter,
     * and sorts the stream of board games based on the specific column.
     * @param filter the filter to apply to the board games.
     * @param sortOn the column to sort the results on.
     * @return a stream of board games that match the filter.
     */
    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn) {
        if (filter.isEmpty()) {
            return currentGames = sortOn(currentGames, sortOn, true);
        }

        if (!filter.contains(",")) {
            Stream<BoardGame> boardGameStream = filterSingle(filter, currentGames);
            return currentGames = sortOn(boardGameStream, sortOn, true);
        }

        String[] filters = filter.split(",");
        for (String condition : filters) {
            Stream<BoardGame> tempStream = filterSingle(condition, currentGames);
            if (tempStream != null) {
                currentGames = tempStream;
            } else {
                break;
            }
        }
        return sortOn(currentGames, sortOn, true);
    }

    /**
     * Filters board games based on the string filter,
     * and sorts the stream of board games based on the specific column in the specific order.
     * @param filter The filter to apply to the board games.
     * @param sortOn The column to sort the results on.
     * @param ascending Whether to sort the results in ascending order or descending order.
     * @return a stream of board games that match the filter.
     */
    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn, boolean ascending) {
        if (filter.isEmpty()) {
            return currentGames = sortOn(currentGames, sortOn, ascending);
        }

        if (!filter.contains(",")) {
            // the filter string only has one filter
            Stream<BoardGame> boardGameStream = filterSingle(filter, currentGames);
            return currentGames = sortOn(boardGameStream, sortOn, ascending);
        }

        // the filter string has multiple filters
        String[] filters = filter.split(",");
        for (String condition : filters) {
            Stream<BoardGame> tempStream = filterSingle(condition, currentGames);
            if (tempStream != null) {
                currentGames = tempStream;
            } else {
                break;
            }
        }
        return sortOn(currentGames, sortOn, ascending);
    }

    /**
     * Filters games when the filter string contains only one filter condition.
     * @param filter the filter string used for filtering.
     * @param filterGames the stream of board games to be filtered.
     * @return a stream of board games that match the filter condition.
     */
    private Stream<BoardGame> filterSingle(String filter, Stream<BoardGame> filterGames) {
        Stream<BoardGame> filteredGames = null;
        Operations operator = Operations.getOperatorFromStr(filter);
        if (operator == null) {
            return filteredGames;
        }

        String[] parts = filter.split(operator.getOperator());
        if (parts.length != 2) {
            return filteredGames;
        }

        GameData column;
        try {
            column = GameData.fromString(parts[0].trim());
        } catch (IllegalArgumentException e) {
            return filteredGames;
        }

        String value;
        try {
            value = parts[1].trim();
            if (column != GameData.NAME) {
                Integer.parseInt(value);
            }
        } catch (IllegalArgumentException e) {
            return filteredGames;
        }
        return filterGames.filter(game -> Filters.filter(game, column, operator, value));
    }

    /**
     * Sorts the stream of board games based on the specified column and order.
     * @param filterGames the stream of board games to be sorted.
     * @param sortOn the column to sort the results on.
     * @param ascending whether the sort is in ascending order.
     * @return a sorted stream of board games based on the specified column and order.
     */
    private static Stream<BoardGame> sortOn(Stream<BoardGame> filterGames, GameData sortOn, boolean ascending) {
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

    @Override
    public void reset() {
        this.currentGames = this.originalGames.stream();
    }
}
