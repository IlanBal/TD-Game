package Game.Towers;

import Game.Enemies.Enemy;
import Game.GameObject;

public class PlayerTower extends GameObject {

    private int id;
    private int type;



    public PlayerTower(int position_X, int position_Y, int id, int type) {
        super(position_X, position_Y);
        this.id = id;
        this.type = type;
    }
}
