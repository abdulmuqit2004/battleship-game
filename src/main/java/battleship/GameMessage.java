package battleship;

import java.io.Serializable;

public class GameMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    public String type;
    public int x, y;
    public int[][] shipGrid;
    public String message;
    public int playerId;
    public boolean hit;
}
