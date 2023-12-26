package src;

public class Knight extends Piece {
    public Knight(Color color, Location location, Board board) {
        super(color,location, board);
    }

    @Override
    public boolean canMoveTo(Location startLoc,Location newLoc) throws InvalidMoveException {
        //can move if col move 1 square and row 2 squares OR  col move 2 squares and row 1
        if ((((Math.abs(startLoc.col - newLoc.col) == 1) && (Math.abs(startLoc.row - newLoc.row) ==2)))
                || (((Math.abs(startLoc.col - newLoc.col) == 2) && (Math.abs(startLoc.row - newLoc.row) ==1)))){
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
        return (getColor() == Color.WHITE) ? 'N' : 'n';
    }

    @Override
    public String toString() {
        return String.valueOf(getSymbol());
    }
}