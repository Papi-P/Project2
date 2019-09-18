/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package level1;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//define enums for information strings. This allows very easy access and customization.
enum endGame {
    PLAYER_ONE_WIN("PLAYER ONE"),
    PLAYER_TWO_WIN("PLAYER TWO"),
    DRAW("DRAW");
    private final String text;

    endGame(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}

enum errors {

    INVALID_CHARACTER("Invalid character! - Options:\n    R - Rock\n    P - Paper\n    S - Scissors"),
    TWO_LETTERS("Use 2 letters per game!"),
    CHARACTER_LIMIT("Input cannot be more than 255 characters!");

    private final String text;

    errors(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}

/**
 *
 * The game “Rock, Paper, Scissors” is frequently used by two players to
 * determine a winner. Each player has three items: paper (P) , scissors (S) and
 * a rock (R). At the count of three, each player shows one of them. The winner
 * of a round is determined by the rules:<br>
 *
 * <!-- Wow my HTML experience actually comes in useful for once !->
 *
 * <ul>
 *     <li>paper covers rock</li>
 *     <li>scissors cut paper</li>
 *     <li>rock breaks scissors</li>
 * </ul>
 *
 * If the two players show the same item then it is a draw, and neither player
 * wins the round. For this particular program, the overall winner is the first
 * player to win two rounds.
 *
 * @author Daniel Allen
 */
public class Level1 {

    public static void main(String[] args) {
        //open scanner
        Scanner scan = new Scanner(System.in);

        //define regex to verify the user input. This prevents the need for try/catch.
        String required = "[RPSrps]+$";

        //variable declarations
        boolean isDead = false;
        boolean isValid = true;
        String line;
        String error = "Unknown Error";

        //while isDead is false
        while (!isDead) {
            System.out.println("Please enter the games:");
            line = "";
            while (line.isEmpty()) {
                //read the line and remove extra spaces.
                line = scan.nextLine().replaceAll("\\s\\s", "\\s").trim();
            }

            //check if program should kill
            if (line.toUpperCase().charAt(0) == 'Q') {
                isDead = true;
            }
            if (!isDead) {
                int p1Score = 0, p2Score = 0, draws = 0;
                isValid = true;

                //create array by splitting all whitespace characters
                String[] games = line.split("\\s");

                //validate each game
                for (String s : games) {
                    //check number of characters
                    if (line.length() > 255) {
                        isValid = false;
                        error = errors.CHARACTER_LIMIT.toString();
                    }
                    //check length
                    if (s.length() != 2) {
                        isValid = false;
                        error = errors.TWO_LETTERS.toString();
                    }
                    //check characters
                    if (!s.matches(required)) {
                        isValid = false;
                        error = errors.INVALID_CHARACTER.toString();
                    }
                }
                //if the game is still valid
                boolean hasWinner = false;
                if (isValid) {
                    for (String s : games) {
                        int winner = getWinner(s);
                        switch (winner) {
                            case 0:
                                draws++;
                                break;
                            case 1:
                                p1Score++;
                                break;
                            case 2:
                                p2Score++;
                                break;
                        }
                        //check if either player has 2 points. If so, end the game and print the winner.
                        if (p1Score == 2 && !hasWinner) {
                            System.out.println(endGame.PLAYER_ONE_WIN + " " + draws);
                            hasWinner = true;
                        } else if (p2Score == 2 && !hasWinner) {
                            System.out.println(endGame.PLAYER_TWO_WIN + " " + draws);
                            hasWinner = true;
                        }
                    }
                    //check if the end of the line is reached before either player has 2 points, and then print the winner and end the game.
                    if (p1Score < 2 && p1Score > p2Score && !hasWinner) {
                        System.out.println(endGame.PLAYER_ONE_WIN + " " + draws);
                        hasWinner = true;
                    } else if (p2Score < 2 && p2Score > p1Score && !hasWinner) {
                        System.out.println(endGame.PLAYER_TWO_WIN + " " + draws);
                        hasWinner = true;
                    } else if (p2Score == p1Score && !hasWinner) {
                        System.out.println(endGame.DRAW);
                    }
                } else {
                    System.out.println(error);
                }
            }
        }
        scan.close();
    }

    //map each of the winning characters to a losing character
    private static final HashMap<Character, Character> winningMoves = new HashMap();

    static {
        winningMoves.put('R', 'S');
        winningMoves.put('P', 'R');
        winningMoves.put('S', 'P');
    }

    /**
     * Find the winner of a game and return it as an integer.
     *
     * @param game The two-character sequence of the game.
     * @return Draw&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-
     * 0<br>Player 1 Wins - 1<br>Player 2 Wins - 2
     */
    private static int getWinner(String game) {
        char p1move = game.toUpperCase().charAt(0);
        char p2move = game.toUpperCase().charAt(1);

        //check if the game is a draw and return 0 if it is
        if (p1move == p2move) {
            return 0;
        }
        //use the map to find the winner and return accordingly
        if (p2move == winningMoves.get(p1move)) {
            return 1;
        } else {
            return 2;
        }
    }
}
