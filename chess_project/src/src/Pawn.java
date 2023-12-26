package src;

public class Pawn extends Piece {
    public Pawn(Color color, Location location, Board board) {
        super(color, location, board);
    }

    @Override
    public boolean canMoveTo(Location loc,Location newLoc) throws InvalidMoveException {
        // validation of  pawn move not implemented
        return true;
    }

    @Override
    public void moveTo(Location newLoc) throws InvalidMoveException {
        setLocation(newLoc);
    }
    @Override
    protected char getSymbol() {
        return (getColor() == Color.WHITE) ? 'P' : 'p';
    }

    @Override
    public String toString() {
        return String.valueOf(getSymbol());
    }
}