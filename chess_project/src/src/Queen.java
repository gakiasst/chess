package src;

public class Queen extends Piece {
    public Queen(Color color, Location location, Board board) {
        super(color, location, board);
    }

    @Override
    public boolean canMoveTo(Location startLoc,Location newLoc) throws InvalidMoveException {
        //must change row and col or change only row or only col to be able to move
        if ((((Math.abs(startLoc.col - newLoc.col) >= 1) && (Math.abs(startLoc.row - newLoc.row) >=1)))
                || (((Math.abs(startLoc.col - newLoc.col) == 0) || (Math.abs(startLoc.row - newLoc.row) ==0)))){
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
        return (getColor() == Color.WHITE) ? 'Q' : 'q';
    }

    @Override
    public String toString() {
        return String.valueOf(getSymbol());
    }
}