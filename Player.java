public class Player {
    public Board board;
    public String name;
    public Ship ship;
    public Player(int DIMENSION, String name, int numShips) {
        board = new Board(DIMENSION);
        ship = new Ship(numShips);
        this.name = name;
    }
}
