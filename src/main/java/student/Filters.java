package student;

public final class Filters {

    /**
     * Constructor for Filters.
     */
    private Filters() {}

    /**
     * Filters a board game based on the column, operation, and value.
     * @param game the board game to be filtered.
     * @param column the attribute of the game.
     * @param op the operation to be performed.
     * @param value the value to compare against.
     * @return if the game meets the filter condition, return true, otherwise, return false.
     */
    public static boolean filter(BoardGame game, GameData column,
                                 Operations op, String value) {
        switch (column) {
            case NAME:
                // filter the name
                return filterString(game.getName(), op, value);
            case MIN_PLAYERS:
                // filter the minimum players
                return filterNumber(game.getMinPlayers(), op, value);
            case MAX_PLAYERS:
                // filter the maximum players
                return filterNumber(game.getMaxPlayers(), op, value);
            case MAX_TIME:
                // filter the maximum play time
                return filterNumber(game.getMaxPlayTime(), op, value);
            case MIN_TIME:
                // filter the minimum play time
                return filterNumber(game.getMinPlayTime(), op, value);
            case DIFFICULTY:
                // filter the difficulty
                return filterNumber(game.getDifficulty(), op, value);
            case RANK:
                // filter the rank
                return filterNumber(game.getRank(), op, value);
            case RATING:
                // filter the rating
                return filterNumber(game.getRating(), op, value);
            case YEAR:
                // filter the published year
                return filterNumber(game.getYearPublished(), op, value);
            default:
                // for other columns, return false
                return false;
        }
    }

    /**
     * Filters a string value based on the operation and value.
     * @param gameData the string data to be filtered.
     * @param op the operation to be performed.
     * @param value the string value to be compared against.
     * @return If the game data meets the filter condition, return true.
     */
    public static boolean filterString(String gameData, Operations op, String value) {
        // If filtering on a string column, the filter should be case insensitive.
        switch (op) {
            case EQUALS:
                return gameData.equalsIgnoreCase(value);
            case NOT_EQUALS:
                return !gameData.equalsIgnoreCase(value);
            case CONTAINS:
                return gameData.toLowerCase().contains(value.toLowerCase());
            default:
                // for other operations, return false
                return false;
        }
    }

    /**
     * Filters an integer number based on the operation and value.
     * @param gameData the string data to be filtered.
     * @param op the operation to be performed.
     * @param value the string value to be compared against.
     * @return If the game data meets the filter condition, return true.
     */
    public static boolean filterNumber(int gameData, Operations op, String value) {
        int val;
        try {
            val = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return false;
        }
        switch (op) {
            case EQUALS:
                return gameData == val;
            case NOT_EQUALS:
                return gameData != val;
            case GREATER_THAN:
                return gameData > val;
            case LESS_THAN:
                return gameData < val;
            case GREATER_THAN_EQUALS:
                return gameData >= val;
            case LESS_THAN_EQUALS:
                return gameData <= val;
            default:
                // for other operations, return false
                return false;
        }
    }

    /**
     * Filters a double number based on the operation and value.
     * @param gameData the string data to be filtered.
     * @param op the operation to be performed.
     * @param value the string value to be compared against.
     * @return If the game data meets the filter condition, return true.
     */
    public static boolean filterNumber(double gameData, Operations op, String value) {
        double val;
        try {
            val = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return false;
        }
        switch (op) {
            case EQUALS:
                return gameData == val;
            case NOT_EQUALS:
                return gameData != val;
            case GREATER_THAN:
                return gameData > val;
            case LESS_THAN:
                return gameData < val;
            case GREATER_THAN_EQUALS:
                return gameData >= val;
            case LESS_THAN_EQUALS:
                return gameData <= val;
            default:
                // for other operations, return false
                return false;
        }
    }
}
