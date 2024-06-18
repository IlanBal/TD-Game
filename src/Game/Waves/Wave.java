package Game.Waves;

import java.util.ArrayList;

public class Wave {

    private ArrayList<Integer> enemyType;
    private ArrayList<Integer> enemySize;

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
