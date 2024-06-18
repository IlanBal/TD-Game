package Game.Waves;

import java.util.ArrayList;

public class Wave {

    private final ArrayList<Integer> enemyType;
    private final ArrayList<Integer> enemySize;

    public Wave(ArrayList<Integer> enemyType, ArrayList<Integer> enemySize) {
        this.enemyType = enemyType;
        this.enemySize = enemySize;
    }

    public ArrayList<Integer> getEnemyType() {
        return enemyType;
    }

    public ArrayList<Integer> getEnemySize() {
        return enemySize;
    }
}
