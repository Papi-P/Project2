/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Daniel Allen
 */
public class Project2 {

    public static void main(String[] args) {
        //open scanner
        Scanner scan = new Scanner(System.in);

        //define regex to verify the user input. This prevents the need for try/catch.
        Pattern required = Pattern.compile("[^RPSrps]");
        Matcher matcher;

        //variable declarations
        boolean isDead = false;
        boolean isValid = true;
        String line;
        String error = "Unknown Error";

        //while isDead is false
        while (!isDead) {
            System.out.println("Please enter the games:");
            //read line from scanner
            line = "";
            while (line.isEmpty()) {
                line = scan.nextLine();
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
                        error = "Input cannot be more than 255 characters!";
                    }
                    //check length
                    if (s.length() != 2) {
                        isValid = false;
                        error = "Use 2 letters per game!";
                    }
                    //check characters
                    if (required.matcher(s).find()) {
                        isValid = false;
                        error = "Invalid character! - Options:\n    R - Rock\n    P - Paper\n    S - Scissors";
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
                        if (p1Score == 2 && !hasWinner) {
                            System.out.println("Player One " + draws);
                            hasWinner = true;
                        } else if (p2Score == 2 && !hasWinner) {
                            System.out.println("Player Two " + draws);
                            hasWinner = true;
                        }
                    }
                    if (p1Score < 2 && p1Score > p2Score && !hasWinner) {
                        System.out.println("Player One " + draws);
                        hasWinner = true;
                    } else if (p2Score < 2 && p2Score > p1Score && !hasWinner) {
                        System.out.println("Player Two " + draws);
                        hasWinner = true;
                    } else if (p2Score == p1Score && !hasWinner) {
                        System.out.println("It was a draw!");
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
        //use the map to efficiently find the winner and return accordingly
        if (p2move == winningMoves.get(p1move)) {
            return 1;
        } else {
            return 2;
        }
    }
}
