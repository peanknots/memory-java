package BP;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;



public class Kaarten {


    public static String[][] board = new String[4][5];
    public static String[][] cards = new String[4][5];
    public static Scanner scanner = new Scanner(System.in);

    public static void printBoard() {
        for (int i = 0; i < 4; i++) {
            System.out.print("|");
            for (int j = 0; j < 5; j++) {
                System.out.print(board[i][j]);
                System.out.print("|");
            }
            System.out.println();
        }
    }

    public static void shuffleCards() {
        Random random = new Random();
        ArrayList<String> letters = new ArrayList<String>();
        letters.add("A"); letters.add("B");
        letters.add("C"); letters.add("D");
        letters.add("E"); letters.add("F");
        letters.add("G"); letters.add("H");
        letters.add("I"); letters.add("J");
        letters.add("A"); letters.add("B");
        letters.add("C"); letters.add("D");
        letters.add("E"); letters.add("F");
        letters.add("G"); letters.add("H");
        letters.add("I"); letters.add("J");

        int index;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (letters.isEmpty()){
                    break;
                }
                index = random.nextInt(letters.size());
                cards[i][j] = letters.get(index);
                letters.remove(index);
            }
        }
    }

    public static void checkInput(String[][] cards) {
        int score = 0;
        int wrongAnswers = 0;

        while (!gameOver()) {
            System.out.println("Row: (1-4)");
            int row1 = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Column: (1-5)");
            int column1 = scanner.nextInt();
            scanner.nextLine();

            if (!board[row1 - 1][column1 - 1].equals(" ♡ ")) {
                System.out.println("Already Entered!!");
                System.out.println();

                printBoard();
                continue;
            } else {
                board[row1 - 1][column1 - 1] = " " + cards[row1 - 1][column1 - 1] + " ";
                printBoard();
            }

            System.out.println("Row: (1-4)");
            int row2 = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Column: (1-5)");
            int column2 = scanner.nextInt();
            scanner.nextLine();

            if (!board[row2 - 1][column2 - 1].equals(" ♡ ")) {
                System.out.println("Already Entered!!");
                board[row1 - 1][column1 - 1] = " _ ";
                System.out.println();

                printBoard();
                continue;
            } else {
                board[row2 - 1][column2 - 1] = " " + cards[row2 - 1][column2 - 1] + " ";

                if (board[row1 - 1][column1 - 1].equals(board[row2 - 1][column2 - 1])) {
                    printBoard();
                    System.out.println("Correct");
                    score += 2; // Adding 2 points for each correct combination
                } else {
                    printBoard();
                    System.out.println("False");
                    board[row1 - 1][column1 - 1] = " ♡ ";
                    board[row2 - 1][column2 - 1] = " ♡ ";
                    wrongAnswers++;
                    if (wrongAnswers > 10) {
                        System.out.println("Too many moves: " + wrongAnswers);
                        break;
                    }
                }
            }
        }
    }

    public static boolean gameOver() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (board[i][j].equals(" ♡ ")) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main() {
        while (true) {
            System.out.println("Press 'n' for a new game, 'q' to quit");
            String input = scanner.nextLine();
            if (input.equals("q")) {
                System.out.println("Exiting");
                break;
            } else if (input.equals("n")) {
                shuffleCards();
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 5; j++) {
                        board[i][j] = " ♡ ";
                    }
                }
                printBoard();
                checkInput(cards);
                break;
            } else {
                System.out.println("Invalid Character");
                continue;
            }
        }
    }
}
