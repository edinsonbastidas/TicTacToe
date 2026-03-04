package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBean implements Serializable {
    private static final int GRID_SIZE = 9; 
    
    public enum GameState { NULL, O, X }
    
    public enum GamePlayer {
        USER(GameState.X), COMPUTER(GameState.O), NOBODY(GameState.NULL);
        public final GameState state;
        private GamePlayer(GameState state) { this.state = state; }
    }

    private GameState[][] gameStatus = new GameState[GRID_SIZE][GRID_SIZE];
    private boolean userFirst = true;
    private static final Random rand = new Random();

    public GameBean() { startGame(); }

    public void setStartByUser(boolean userFirst) { this.userFirst = userFirst; }

    public void startGame() {
        for (int i = 0; i < GRID_SIZE; i++)
            for (int j = 0; j < GRID_SIZE; j++) gameStatus[i][j] = GameState.NULL;
        if (!userFirst) play(GamePlayer.COMPUTER, 0, 0);
    }

    public List<Line> getGridLines() {
        List<Line> lines = new ArrayList<>();
        for (int i = 0; i < GRID_SIZE; i++) lines.add(new Line(gameStatus[i], i));
        return lines;
    }

    public List<Cell> getGridStatus(Line line) {
        List<Cell> cells = new ArrayList<>();
        GameState[] data = line.getDatas();
        for (int i = 0; i < data.length; i++) cells.add(new Cell(data[i], line.getIndex(), i));
        return cells;
    }

    public void playPlayerTurn(int line, int col) { play(GamePlayer.USER, line, col); }

    public void playComputerTurn() {
        for(int i=0; i<GRID_SIZE; i++) {
            for(int j=0; j<GRID_SIZE; j++) {
                if(gameStatus[i][j] == GameState.NULL) {
                    play(GamePlayer.COMPUTER, i, j);
                    return;
                }
            }
        }
    }

    private void play(GamePlayer player, int line, int col) {
        if (line >= 0 && line < GRID_SIZE && col >= 0 && col < GRID_SIZE) {
            if (gameStatus[line][col] == GameState.NULL) gameStatus[line][col] = player.state;
        }
    }

    public GamePlayer getWinner() {
        // 1. Revisar Filas
        for (int i = 0; i < GRID_SIZE; i++) {
            if (gameStatus[i][0] != GameState.NULL &&
                gameStatus[i][0] == gameStatus[i][1] &&
                gameStatus[i][1] == gameStatus[i][2]) {
                return getPlayerByState(gameStatus[i][0]);
            }
        }

        // 2. Revisar Columnas
        for (int j = 0; j < GRID_SIZE; j++) {
            if (gameStatus[0][j] != GameState.NULL &&
                gameStatus[0][j] == gameStatus[1][j] &&
                gameStatus[1][j] == gameStatus[2][j]) {
                return getPlayerByState(gameStatus[0][j]);
            }
        }

        // 3. Diagonal Principal (\)
        if (gameStatus[0][0] != GameState.NULL &&
            gameStatus[0][0] == gameStatus[1][1] &&
            gameStatus[1][1] == gameStatus[2][2]) {
            return getPlayerByState(gameStatus[0][0]);
        }

        // 4. Diagonal Inversa (/)
        if (gameStatus[0][2] != GameState.NULL &&
            gameStatus[0][2] == gameStatus[1][1] &&
            gameStatus[1][1] == gameStatus[2][0]) {
            return getPlayerByState(gameStatus[0][2]);
        }

        return GamePlayer.NOBODY;
    
    }

    private GamePlayer getPlayerByState(GameState s) {
        for(GamePlayer p : GamePlayer.values()) if(p.state == s) return p;
        return GamePlayer.NOBODY;
    }

    public boolean hasEmptyCell() {
        for (GameState[] row : gameStatus) for (GameState c : row) if (c == GameState.NULL) return true;
        return false;
    }
}