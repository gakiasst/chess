package src;

public class Location {
    public int row;
    public int col;

    private static final String INVALID_LOCATION_MESSAGE = "Invalid location format";

    public Location(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Location(String loc) throws InvalidLocationException {
        if (loc.length() != 2) {
            throw new InvalidLocationException(INVALID_LOCATION_MESSAGE);
        }

        char colChar = loc.charAt(0);
        char rowChar = loc.charAt(1);

        if (colChar < 'a' || colChar > 'h' || rowChar < '1' || rowChar > '8') {
            throw new InvalidLocationException(INVALID_LOCATION_MESSAGE);
        }

        this.col = colChar - 'a';
        this.row = '8' - rowChar ;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        char colChar = (char) ('a' + col);
        char rowChar = (char) ('1' + row);
        return "" + colChar + rowChar;
    }
}
