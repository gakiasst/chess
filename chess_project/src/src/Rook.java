package src;

public class Rook extends Piece {
    public Rook(Color color, Location location, Board board) {
        super(color, location, board);
    }

    @Override
    public boolean canMoveTo(Location startLoc,Location newLoc) throws InvalidMoveException {
        //move only at same row or same col
        if ((Math.abs(startLoc.col - newLoc.col) == 0) || (Math.abs(startLoc.row - newLoc.row) ==0)) {
            return true;
        }
        return false;
    }

    @Override
    public void moveTo(Location newLoc) throws InvalidMoveException {
        setLocation(newLoc);
    }

    @Override
    protected char getSymbol() {
        return (getColor() == Color.WHITE) ? 'R' : 'r';
    }

    @Override
    public String toString() {
        return String.valueOf(getSymbol());
    }
}