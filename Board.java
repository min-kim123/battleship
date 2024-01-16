public class Board {
    public char [][] arr;
    public boolean [][] shipArr;
    public int DIMENSION;
    public static int SHIP_SIZE = 4;
    public int guesses;
    public int correct;
    public Board() {
        DIMENSION = 10;
        initializeBoard();
    }
    public Board(int DIMENSION) {
        this.DIMENSION = DIMENSION;
        initializeBoard();

    }
    public void initializeBoard() {
        guesses = 0;
        correct = 0;
        arr = new char[DIMENSION][DIMENSION];
        shipArr = new boolean[DIMENSION][DIMENSION];
        //popular arr with spaces
        for (int i = 0; i < DIMENSION; ++i) {
            for (int j = 0; j < DIMENSION; ++j) {
                arr[i][j] = ' ';
            }
        }
    }
    public void placeShips(int numBoats) {
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
        //printShips();
    }
    public void printShips() {
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
    }

    public void printBoard() {
        for (int i = 0; i < DIMENSION; ++i) {
            System.out.print("   " + (char)(i+65));
        }
        System.out.println();
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
    }

    public boolean printBoard(char letter, int number) {
        boolean hit = true;
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
            hit = false;
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
        return hit;
    }

}
