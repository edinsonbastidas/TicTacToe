package game;
public class Cell {
    private int cellLine;
    private int cellCol;
    private GameBean.GameState state;
    public Cell(GameBean.GameState state, int cellLine, int cellCol) {
        this.state = state; this.cellLine = cellLine; this.cellCol = cellCol;
    }
    public GameBean.GameState getState() { return state; }
    public int getLine() { return cellLine; }
    public int getCol() { return cellCol; }
}