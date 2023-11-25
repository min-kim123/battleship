/*
* Author: Min Kim
* Program Description: A game of Battleship where you can determine the amount of boats placed.
* Date: 11/12/23
* */
import java.util.Scanner;
public class Min_Kim_Asg7 {
    public static void main (String[] args) {
        final int SHIP_SIZE = 4;
        final int DIMENSION = 10;
        int guesses = 0;
        char arr[][] = new char[DIMENSION][DIMENSION];
        boolean shipArr[][] = new boolean[DIMENSION][DIMENSION];
        for (int i = 0; i < DIMENSION; ++i) {
            for (int j = 0; j < DIMENSION; ++j) {
                arr[i][j] = ' ';
            }
        }
        Scanner scanner = new Scanner(System.in);
        String input = null;
        char letter = ' ';
        int number = 0;
        int correct = 0;
        int numBoats = 0;
        for (int i = 0; i < DIMENSION; ++i) {
            System.out.print("   " + (char)(i+65));
        }
        System.out.println();
        for (int i = 0; i < DIMENSION; ++i) {
            System.out.println(" +---+---+---+---+---+---+---+---+---+---+");
            System.out.print(i);
            for (int j = 0; j < DIMENSION; ++j) {
                System.out.print("|   ");
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println(" +---+---+---+---+---+---+---+---+---+---+");
        while (true) {//get amount of boats
            System.out.println("How many boats? (1-10): ");
            numBoats = scanner.nextInt();
            String x = scanner.nextLine();//cleanup
            if (numBoats > 0 && numBoats < 11) {
                break;
            }
            else {
                System.out.println("Please enter a number 1-10.");
            }
        }

        placeShip(DIMENSION, SHIP_SIZE, numBoats, shipArr, arr);

        System.out.println("ship placement: ");
        for (int i = 0; i < DIMENSION; ++i) {
            System.out.print("   " + (char)(i+97));
        }
        System.out.println();
        for (int i = 0; i < DIMENSION; ++i) {
            System.out.println(" +---+---+---+---+---+---+---+---+---+---+");
            System.out.print(i);
            for (int j = 0; j < DIMENSION; ++j) {
                System.out.print("|  ");
                if (shipArr[i][j]== true) {
                    System.out.print("@");
                }
                else {
                    System.out.print(" ");
                }
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println(" +---+---+---+---+---+---+---+---+---+---+");
        while (true) {//main game
            if (correct != SHIP_SIZE*numBoats) {
                System.out.println("Please enter a letter (A-J) followed by a number 0-9. Enter q to give up and see ships.");
            }
            else {
                System.out.println("You win!");
                System.out.println("Guesses: " + guesses);
                break;
            }

            input = scanner.nextLine();
            if (input.length() == 1 && input.charAt(0) == 'q') {
                System.out.println("ship placement: ");
                for (int i = 0; i < DIMENSION; ++i) {
                    System.out.print("   " + (char)(i+97));
                }
                System.out.println();
                for (int i = 0; i < DIMENSION; ++i) {
                    System.out.println(" +---+---+---+---+---+---+---+---+---+---+");
                    System.out.print(i);
                    for (int j = 0; j < DIMENSION; ++j) {
                        System.out.print("|  ");
                        if (shipArr[i][j]== true) {
                            System.out.print("@");
                        }
                        else {
                            System.out.print(" ");
                        }
                    }
                    System.out.print("|");
                    System.out.println();
                }
                System.out.println(" +---+---+---+---+---+---+---+---+---+---+");
                break;
            }
            if (input.length() == 2) {
                if ((int)input.charAt(0) > 64 && (int)input.charAt(0) < 75 && (int)input.charAt(1) > 47 && (int) input.charAt(1)< 58) {
                    letter = input.charAt(0);
                    number = ((int)input.charAt(1))-48;
                    ++guesses;
                    //check if this is already full
                    if (arr[number][((int)(letter))-65] != ' ') {
                        System.out.println("Coordinate has already been guessed.");
                    }
                    else {
                        correct = printBoard(DIMENSION, arr, shipArr, number, letter, correct);
                    }
                }
            }
        }
    }
    public static void placeShip(int DIMENSION, int SHIP_SIZE, int numBoats, boolean shipArr[][], char arr[][]) {
        //initialize
        int orientation [] = {0,0,0,0};//north, east, south, west
        int possibleOrientations = 0;
        int row = 0;
        int column = 0;
        boolean possible = false;
        //array to test
        boolean testArr[][] = new boolean[DIMENSION][DIMENSION];
        for (int i = 0; i < numBoats; ++i) {
            //reset values
            possibleOrientations = 0;
            for (int j = 0; j < 4; ++j) {
                orientation[j] = 0;
            }
            while (true) {
                //get random row and column
                row = (int)(Math.random()*10);
                column = (int)(Math.random()*10);
                //get possible orientations by checking if it stays within boundaries and if it doesn't overlap with another ship
                if (shipArr[row][column] == false) {//available index
                    if (row >= SHIP_SIZE-1) {//can stretch towards north
                        //if there is overlap-
                        for (int a = 0; a < SHIP_SIZE; ++a) {
                            if (shipArr[row-a][column] == false) {
                                if (a == 3) {
                                    possible = true;
                                }
                            }
                            else {
                                break;
                            }
                        }
                        if (possible == true) {
                            orientation[0] = 1;
                            ++possibleOrientations;
                            possible = false;
                        }
                    }
                    if (column <= DIMENSION-SHIP_SIZE) {//can stretch towards east
                        //if there is overlap
                        for (int a = 0; a < SHIP_SIZE; ++a) {
                            if (shipArr[row][column+a] == false) {
                                if (a == 3) {
                                    possible = true;
                                }
                            }
                            else {
                                break;
                            }
                        }
                        if (possible == true) {
                            orientation[1] = 1;
                            ++possibleOrientations;
                            possible = false;
                        }
                    }
                    if (row <= DIMENSION-SHIP_SIZE) {//can stretch towards south
                        //if there is overlap
                        for (int a = 0; a < SHIP_SIZE; ++a) {
                            if (shipArr[row+a][column] == false) {
                                if (a == 3) {
                                    possible = true;
                                }
                            }
                            else {
                                break;
                            }
                        }
                        if (possible == true) {
                            orientation[2] = 1;
                            ++possibleOrientations;
                            possible = false;
                        }
                    }
                    if (column >= SHIP_SIZE-1) {//can stretch towards west
                        //if there is overlap
                        for (int a = 0; a < SHIP_SIZE; ++a) {
                            if (shipArr[row][column-a] == false) {
                                if (a == 3) {
                                    possible = true;
                                }
                            }
                            else {
                                break;
                            }
                        }
                        if (possible == true) {
                            orientation[3] = 1;
                            ++possibleOrientations;
                            possible = false;
                        }
                    }
                }
                if (possibleOrientations != 0) {//get random orientation from available orientations
                    int counter = 0;
                    int rand = (int)(Math.random()*possibleOrientations + 1);
                    int directionIndex = 0;
                    for (int b = 0; b < 4; ++b) {//get the random index of the orientation
                        if (orientation[b] == 1) {
                            ++counter;
                            if (counter == rand) {
                                directionIndex = b;
                            }
                        }
                    }
                    //official shiparray
                    if (directionIndex == 0) {
                        for (int a = 0; a < SHIP_SIZE; ++a) {
                            shipArr[row-a][column] = true;
                        }
                    }
                    else if (directionIndex == 1) {
                        for (int a = 0; a < SHIP_SIZE; ++a) {
                            shipArr[row][column+a] = true;
                        }
                    }
                    else if (directionIndex == 2) {
                        for (int a = 0; a < SHIP_SIZE; ++a) {
                            shipArr[row+a][column] = true;
                        }
                    }
                    else if (directionIndex == 3) {
                        for (int a = 0; a < SHIP_SIZE; ++a) {
                            shipArr[row][column-a] = true;
                        }
                    }
                    break;
                }
            }
        }
    }

    public static int printBoard(int DIMENSION, char arr[][], boolean shipArr[][], int number, char letter, int correct) {//print board
        for (int i = 0; i < DIMENSION; ++i) {
            System.out.print("   " + (char)(i+65));
        }
        System.out.println();
        if (shipArr[number][(int)(letter-65)] == true) {
            ++correct;
            arr[number][(int)(letter-65)] = 'X';
        }
        else {
            arr[number][(int)(letter-65)] = '#';
        }
        for (int i = 0; i < DIMENSION; ++i) {
            System.out.println(" +---+---+---+---+---+---+---+---+---+---+");
            System.out.print(i);
            for (int j = 0; j < DIMENSION; ++j) {
                System.out.print("| "+arr[i][j]+" ");
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println(" +---+---+---+---+---+---+---+---+---+---+");
        return correct;
    }
}