/*
Author: Min Kim
Program Description: A multiplayer battleship game where you can decide the number of boats to be placed on the board. The players have their own boards.
Uncomment line 160 in Board.java to see ship placement before game starts
Date: 11/30/23
 */

import java.util.Scanner;

public class BattleshipGame {
    public BattleshipGame() {//constructor
        Scanner scanner = new Scanner(System.in);
        String dummy = null;
        char playAgain = ' ';
        //put game in play again loop
        System.out.println("Welcome to Multiplayer Battleship!");
        do {
            gamePlay();
            while (true) {
                System.out.println("Play again? (y/n)");
                dummy = scanner.nextLine();
                if (dummy.length() == 1) {
                    playAgain = dummy.charAt(0);
                    break;
                }
            }
        }
        while (playAgain == 'y');
    }
    public void gamePlay() {//main gameplay
        Scanner scanner = new Scanner(System.in);
        boolean p1 = true;
        int DIMENSION = 10;
        int numBoats = 0;
        String name = null;
        char letter = ' ';
        int number = 0;
        String x = null;
        while (true) {
            System.out.println("How many boats on each board(1-8)?");
            numBoats = scanner.nextInt();
            x = scanner.nextLine();//cleanup
            if (numBoats > 0 && numBoats < 9) {
                break;
            }
        }
        //instantiate players
        System.out.print("Name of Player 1?: ");
        name = scanner.nextLine();
        Player player1 = new Player(DIMENSION, name, numBoats);

        System.out.print("Name of Player 2?: ");
        name = scanner.nextLine();
        Player player2 = new Player(DIMENSION, name, numBoats);

        player1.board.placeShips(player1.ship.getNumShips());
        player2.board.placeShips(player2.ship.getNumShips());

        p1 = whoseTurn();
        Player player;

        //instantiate boards
        while(true) {
            if (p1) {
                //play p1 game
                player = player1;
            }
            else {
                //play p2 game
                player = player2;
            }
            System.out.println(player.name + "'s turn");
            turn(player, scanner, letter, number, numBoats);
            System.out.println();
            if (player.board.correct == numBoats*player.board.SHIP_SIZE) {//win
                System.out.println(player.name + " is the winner!");
                System.out.println("Number of guesses: " + player.board.guesses);
                return;
            }
            else {
                if (p1) {
                    p1 = false;
                }
                else {
                    p1 = true;
                }
            }
            System.out.println("-----------------------------------------------------------------");
        }
    }
    public void turn(Player player, Scanner scanner, char letter, int number, int numBoats) {
        String input = null;
        player.board.printBoard();
        while (true) {
            System.out.println("Please enter a letter (A-J) followed by a number 0-9.");
            input = scanner.nextLine();

            if (input.length() == 2) {
                if ((int)input.charAt(0) > 64 && (int)input.charAt(0) < 75 && (int)input.charAt(1) > 47 && (int) input.charAt(1)< 58) {
                    letter = input.charAt(0);
                    number = ((int)input.charAt(1))-48;
                    //check if this is already full
                    if (player.board.arr[number][((int)(letter))-65] != ' ') {
                        System.out.println("Coordinate has already been guessed.");
                    }
                    else {
                        ++player.board.guesses;
                        if (!player.board.printBoard(letter, number)) {
                            //if they didn't hit a ship
                            return;

                        }
                        else {
                            if (player.board.correct == player.ship.getNumShips()*player.board.SHIP_SIZE) {
                                return;
                            }
                            System.out.println("You hit a ship! It's "+ player.name + "'s turn again");
                        }
                    }
                }
            }
        }
    }
    public boolean whoseTurn() {
        int turn = (int)(Math.random()* 2);
        if(turn == 0) {
            return true;
        }
        return false;
    }
}
