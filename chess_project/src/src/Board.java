package src;

import java.util.Arrays;

public class Board {
    private static final int BOARD_SIZE = 8;
    private Piece[][] chessboard;

    public Board() {
        chessboard = new Piece[BOARD_SIZE][BOARD_SIZE];
        init();
    }

    public void init() {
        placePieces(Piece.Color.BLACK, 0, 1);
        placePieces(Piece.Color.WHITE, BOARD_SIZE - 1, BOARD_SIZE - 2);
    }

    private void placePieces(Piece.Color color, int backRow, int frontRow) {
        chessboard[backRow][0] = new Rook(color, new Location(backRow, 0), this);
        chessboard[backRow][1] = new Knight(color, new Location(backRow, 1), this);
        chessboard[backRow][2] = new Bishop(color, new Location(backRow, 2), this);
        chessboard[backRow][3] = new Queen(color, new Location(backRow, 3), this);
        chessboard[backRow][4] = new King(color, new Location(backRow, 4), this);
        chessboard[backRow][5] = new Bishop(color, new Location(backRow, 5), this);
        chessboard[backRow][6] = new Knight(color, new Location(backRow, 6), this);
        chessboard[backRow][7] = new Rook(color, new Location(backRow, 7), this);

        // Place pawns
        for (int col = 0; col < BOARD_SIZE; col++) {
            chessboard[frontRow][col] = new Pawn(color, new Location(frontRow, col), this);
        }
    }

    public Piece getPieceAt(Location loc) {
        return chessboard[loc.getRow()][loc.getCol()];
    }

    public void movePiece(Location from, Location to) throws InvalidMoveException, InvalidLocationException {
        chessboard[to.getRow()][to.getCol()] = chessboard[from.getRow()][from.getCol()];
        chessboard[from.getRow()][from.getCol()] = null;
    }
    public void movePieceCapturing(Location from, Location to) {
        chessboard[to.getRow()][to.getCol()] = null;
        //Piece piece = chessboard[from.getRow()][from.getCol()];
        chessboard[to.getRow()][to.getCol()] =  chessboard[from.getRow()][from.getCol()];
        chessboard[from.getRow()][from.getCol()] = null;
    }

    public boolean isValidLocation(String move) {
        //input validation
        if (move.length() != 4) {
            return false;
        }
        char fromCol = move.charAt(0);
        char fromRow = move.charAt(1);
        char toCol = move.charAt(2);
        char toRow = move.charAt(3);

        if (((fromCol >= 'a' && fromCol <= 'h') && (toCol >= 'a' && toCol <= 'h')) &&
                ((fromRow >= '1' && fromRow <= '8') && (toRow >= '1' && toRow <= '8'))) {
            return true;
        }
        return false;
    }

    public boolean isValidMove(String move, Boolean isTurn) throws InvalidLocationException, InvalidMoveException {
        Location from = new Location(move.substring(0, 2));
        Location to = new Location(move.substring(2, 4));

        Piece pieceFrom = getPieceAt(from);
        Piece pieceTo = getPieceAt(to);

        //trying to move empty piece
        if (pieceFrom == null) {
            return false;
        }
        // moving right pieces (not different color)
        if ((isTurn && pieceFrom.getColor() == Piece.Color.WHITE) || (!isTurn && pieceFrom.getColor() == Piece.Color.BLACK)) {
            // attacking opponents pieces (not the same color)
            if (pieceTo == null || ((isTurn && pieceTo.getColor() == Piece.Color.BLACK) || (!isTurn && pieceTo.getColor() == Piece.Color.WHITE))) {
                //checking move validity depending on type of piece
                if (pieceFrom.canMoveTo(from, to)) {
                    return true;
                }
            }
        }
        return false;
    }


    public Boolean opponentPieceExists(String move) throws InvalidLocationException {
        Location to = new Location(move.substring(2, 4));
        Piece pieceTo = getPieceAt(to);

        if (pieceTo == null) {
            return true;
        }
        return false;

    }


    public boolean freeHorizontalPath(Location from, Location to) {
        return true;
    }

    public boolean freeVerticalPath(Location from, Location to) {
        return true;
    }

    public boolean freeDiagonalPath(Location from, Location to) {
        return true;
    }

    public boolean freeAntidiagonalPath(Location from, Location to) {
        return true;
    }

    @Override
    public String toString() {
        StringBuilder boardString = new StringBuilder();
        boardString.append("    a   b   c   d   e   f   g   h\n");
        int count = 8;
        for (int i = 0; i < BOARD_SIZE; i++) {
            boardString.append(count).append(" | ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (chessboard[i][j] == null) {
                    boardString.append("  | ");
                } else {
                    boardString.append(chessboard[i][j]).append(" | ");
                }
            }
            boardString.append(count);
            count--;
            boardString.append("\n");
        }
        boardString.append("    a   b   c   d   e   f   g   h\n");

        return boardString.toString();
    }
}
