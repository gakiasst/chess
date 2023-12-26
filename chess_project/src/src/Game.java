package src;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class Game {
    private static final String SAVE_FOLDER = "src/savedGames/";
    private Board chessboard;
    private boolean isWhiteTurn;
    List<String> moves = new ArrayList<String>();

    public Game() {
        chessboard = new Board();
        chessboard.init();
        System.out.println(" ♔♕♖♗♘♙ WELCOME TO CHESS ♔♕♖♗♘♙ ");
        System.out.println(chessboard.toString());
        System.out.print("============GAME COMMANDS===========\n" +
                         ":h - help \n"+
                         ":s - save game\n"+
                         ":o - open saved game\n"+
                         ":x - exit game\n" +
                         "========================\n");
        isWhiteTurn = true; // White player  starts first
    }

    public void play() throws InvalidLocationException, InvalidMoveException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            //chessboard.printBoard(); // Print the current state of the chessboard
            System.out.println(isWhiteTurn ? "White's turn." : "Black's turn.");
            System.out.print("Enter your move or command: ");
            String input = scanner.nextLine().trim();

            if (input.startsWith(":")) {
                handleCommand(input);
            } else {
                handleMove(input,isWhiteTurn);
            }

        }
    }
    private void handleCommand(String command) {
        switch (command.toLowerCase()) {
            case ":s":
                saveGame();
                break;
            case ":o":
                openGame();
                break;
            case ":x":
                exitGame();
                break;
            case ":h":
                printHelp();
                break;
            default:
                System.out.println("Invalid command!! Type ':h' for help.");
        }
    }

    public void handleMove(String moveString, Boolean isTurn) {
        boolean validMove = false;

        while (!validMove) {
            try {
                if(moveString.startsWith(":")) {
                 String comm = moveString.substring(0,2);
                    handleCommand(comm);
                }
                if (!chessboard.isValidLocation(moveString)) {
                    throw new InvalidLocationException("Invalid move. Check the move format and coordinates!");

                }
                if (!chessboard.isValidMove(moveString,isTurn)) {
                    throw new InvalidMoveException("Invalid move!Empty starting position or you tried to move opponent's Piece or you attacking your one piece!");
                }
                // if move is valid
                if(chessboard.opponentPieceExists(moveString)){
                    Location from = new Location(moveString.substring(0, 2));
                    Location to = new Location(moveString.substring(2, 4));
                    chessboard.movePieceCapturing(from,to);
                } else {
                    Location from = new Location(moveString.substring(0, 2));
                    Location to = new Location(moveString.substring(2, 4));
                    chessboard.movePiece(from,to);
                }
                System.out.println(chessboard.toString());
                isWhiteTurn = !isWhiteTurn; // Toggle player turn
                System.out.println("Move successful!");
                moves.add(moveString);
                validMove = true; // Break out of the loop

            } catch (InvalidLocationException | InvalidMoveException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println(isWhiteTurn ? "White's turn." : "Black's turn.");
                System.out.print("Enter your move again: ");
                Scanner scanner = new Scanner(System.in);
                moveString = scanner.nextLine().trim();
            }
        }
    }



    private void saveGame() {
        try {
            // Get the file name from user input
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the file name to save the game: ");
            String fileName = scanner.nextLine();

            // Create a directory if it doesn't exist
            String directoryPath = "src/savedGames/";
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            // Construct the full path
            Path filePath = Paths.get(directoryPath, fileName + ".txt");
            try (FileOutputStream fos = new FileOutputStream(filePath.toFile());
                ObjectOutputStream oos = new ObjectOutputStream(fos)){
                oos.writeObject(moves);
                System.out.println("Game saved.");
                } catch(IOException e){
                    System.out.println("Error saving the game: " + e.getMessage());
                }
            }catch (Exception e) {
                e.printStackTrace(); // Handle exception appropriately
            }
        }



    private void openGame() {
        try {
            File saveFolder = new File(SAVE_FOLDER);
            File[] saveFiles = saveFolder.listFiles();

            if (saveFiles != null && saveFiles.length > 0) {
                System.out.println("List of save games:");

                for (int i = 0; i < saveFiles.length; i++) {
                    System.out.println((i + 1) + ". " + saveFiles[i].getName());
                }

                System.out.print("Enter the number of the save game to load: ");
                try (Scanner scanner = new Scanner(System.in)){
                int choice = scanner.nextInt();

                if (choice >= 1 && choice <= saveFiles.length) {
                    File selectedSaveFile = saveFiles[choice - 1];

                    try (FileInputStream fin = new FileInputStream(selectedSaveFile);
                         ObjectInputStream ois = new ObjectInputStream(fin)) {

                        List<String> loadedGameData = (List<String>) ois.readObject();
                        System.out.println("Game loaded successfully.");

                        //start a new game and apply  saved moves
                        chessboard = new Board();
                        chessboard.init();
                        if ((loadedGameData.size() % 2 == 1 )){
                            //odd moves made = blacks turn
                            isWhiteTurn = false;
                        } else{
                            isWhiteTurn = true;
                        }
                        for (int i = 0; i < loadedGameData.size(); i++) {
                           // System.out.println(loadedGameData.get(i));
                            Location from = new Location(loadedGameData.get(i).substring(0,2));
                            Location to = new Location(loadedGameData.get(i).substring(2,4));
                            chessboard.movePiece(from,to);
                        }
                        System.out.println(chessboard.toString());
                        handleMove(" ",isWhiteTurn);


                    } catch (IOException e) {
                        System.out.println("Error loading the game: " + e.getMessage());
                    }
                } else {
                    System.out.println("Invalid choice. Please enter a valid number.");
                }
                }
            } else {
                System.out.println("No save games found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void exitGame() {
        System.out.println("Do you want to exit the game? (y/n)");
        Scanner scanner = new Scanner(System.in);
        String confirmation = scanner.nextLine().trim().toLowerCase();
        if (confirmation.equals("y")) {
            System.out.println("Exiting the game. Goodbye!");
            System.exit(0);
        }
    }
    private void printHelp() {
        System.out.println("Two players participate in a game of chess." +
                " Each player initially has 16 pieces, one each white and the other black. " +
                "Each player's pieces are initially 1 king, 1 queen, 2 rooks, 2 knights, 2 officers and 8 pawns," +
                " which are symbolized either by graphics symbols or letters of the English alphabet (uppercase for white and lowercase for black).\n" +
                "\n" +
                "The pieces are first placed on the chessboard (a rectangular arrangement of 8 8 squares) as shown above." +
                " The lines of the chessboard are numbered with letters of of the Latin alphabet, while the columns with numbers. " +
                "Players take turns playing, each time moving one of their pieces according to the following rules:\n" +
                "• The tower only moves horizontally or vertically, as far (number of squares) as it wants the player. \n" +
                "• The knight moves along three (3) squares in the following two ways: (a) 1 square vertically (top or bottom) " +
                "and 2 squares horizontally (left or right), or (b) 2 squares ca- vertically (top or bottom) and 1 square horizontally (left or right)." +
                " His movements are similar to form a \"C\".\n" +
                "• The officer only moves diagonally, as far as the player wishes.\n" +
                "• The queen moves like the rook or knight (ie horizontally, vertically or diagonally). \n" +
                "• The king moves horizontally, vertically or diagonally, but only one square at a time. \n" +
                "• All the above pieces can make one move if in the final position there is no piece of the same color. " +
                "Since in the final position there is a piece of the opposite color, the move can be made and then the \"opposing\" piece is" +
                " removed from the chessboard (thereby reducing the opponent's \"power\"). \n" +
                "• The rook, knight and queen cannot jump over other pieces ie during their movement there must be no other pieces (of any color) " +
                "in between of the initial and final positions.Instead, the horse can jump over any others pieces are in the intermediate positions. \n" +
                "• The pawn has the most complex movement rules of all the pieces. It generally only moves front (i.e. white pawns up and black pawns down," +
                " next to top figure), by 1 square each time. Especially on its first move, a pawn can to move 1 or 2 squares (the player chooses the move if there is no obstacle)." +
                "In final position of the pawn's movement is forbidden to have another piece, even the counter- of a positive color (ie the pawn does not remove opposing pieces in the usual way). " +
                "Also, if it moves 2 squares, it is forbidden to have another square in between piece." +
                " In addition a pawn can move diagonally forward (instead of straight ahead) if it comes to this move to remove an opposing piece from the chessboard " +
                "(this is the only way where a pawn removes an opposing piece).A player wins when he restricts the opponent's king so that he cannot move without threatened (mate). " +
                "It is also possible to end a game in a draw if it cannot be achieved checkmate by no player. You are not asked to implement anything related to the end game, just each time " +
                "moves will be checked for validity according to the above rules. Program interface Players will play chess through the command line. Each player can in his turn to enter either a move or a command." +
                " Movements are entered by giving the coordinates of the position of the piece to be moved and the coordinates of its final position (without spaces between them), e.g. the movement of the pawn from the original " +
                "its position e2 by two squares is given as e2e4. Movements are checked for validity and in case of an invalid move an informative message is printed (the player does not lose his turn)." +
                " The messages must separate at least the following cases of invalid moves:1. Syntax errors, e.g. abcd, d67er7. A motion input is considered syntactically correct when consists of four characters, of which the first" +
                " and third are letters and o second and fourth are numeric digits.3 2. Coordinates (start or end position) out of bounds, e.g. a9b1, e2z5. 3. Non-existence of track in the original position. " +
                "4. Moving a piece of the wrong color, e.g. black piece movement when white plays. 5. Movement not allowed for the piece, eg. move for d2d5 pawn. In this case you should to be seen and the type of piece moving, and it" +
                " is desirable that the message should provide as much information as possible about the movement error, e.g. “The tower can move only horizontally or vertically\"." +
                " Commands are given by putting a colon before the corresponding letter, so that they stand out easily from the moves. The commands requested are the following: :h – Display help text A text will appear explaining how to use the game," +
                " briefly describing it how to enter moves and commands and the available commands. :s – Save game Saves the (valid) game moves up to that point. His name is requested file to save to." +
                " :o – Load game Loads a previously saved game and allows it to be continued from point of storage. Once a batch has started, the user is first asked if they want to to interrupt it. It asks for the name of the file to load from." +
                ":x – Exit Termination of the program. Once a batch has started, the user is first asked if wishes to interrupt it. After each move or command the current state of the chessboard will be displayed.");
    }



    public static void main(String[] args) throws InvalidLocationException, InvalidMoveException {
        Game chessGame = new Game();
        chessGame.play();
    }
}
