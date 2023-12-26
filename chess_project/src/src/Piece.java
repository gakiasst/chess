package src;

public abstract class Piece {
    public enum Color {
        WHITE, BLACK
    }
    private final Color color;
    private Location location;
    private final Board board;
    public Piece(Color color, Location location, Board board) {
        this.color = color;
        this.location = location;
        this.board = board;
    }

    public Color getColor() {
        return color;
    }
    public Board getBoard() {
        return board;
    }
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location newPosition) {
        this.location = newPosition;
    }


    public abstract boolean canMoveTo(Location startLoc, Location newLoc) throws InvalidMoveException;
    public abstract void moveTo(Location newLoc) throws InvalidMoveException;

    @Override
    public String toString() {
        char pieceSymbol = getSymbol();
        return (color == Color.WHITE) ? String.valueOf(pieceSymbol) : String.valueOf(Character.toLowerCase(pieceSymbol));
    }

    protected abstract char getSymbol();
}