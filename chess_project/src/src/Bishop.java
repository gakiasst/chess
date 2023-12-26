package src;

public class Bishop extends Piece {
    public Bishop(Color color, Location location, Board board) {
        super(color, location, board);
    }

    @Override
    public boolean canMoveTo(Location startLoc,Location newLoc) throws InvalidMoveException {
        //can move only diagonal
        if ((Math.abs(startLoc.col - newLoc.col) >= 1) && (Math.abs(startLoc.row - newLoc.row) >=1)) {
            return true;
        }
        return  false;
    }

    @Override
    public void moveTo(Location newLoc) throws InvalidMoveException {
        setLocation(newLoc);
    }

    @Override
    protected char getSymbol() {
        return (getColor() == Color.WHITE) ? 'B' : 'b';
    }

    @Override
    public String toString() {
        return String.valueOf(getSymbol());
    }
}