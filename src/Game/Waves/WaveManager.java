package Game.Waves;

import Game.Enemies.Enemy;
import Game.GameWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static Utils.Types.Enemies.BLUE;
import static Utils.Types.Enemies.LARGE;

public class WaveManager {

    private final GameWindow gameWindow;
    private final ArrayList<Wave> waveArray = new ArrayList<>();
    ArrayList<Integer> enemyTypeArray = new ArrayList<>();
    ArrayList<Integer> enemySizeArray = new ArrayList<>();
    private int enemySpawnTick, waveStartTick;
    private int waveIndex = 0, enemyIndex;
    private int enemyNum, enemyType, enemySize;
    private boolean waveStartTimer;
    private boolean waveEndTimer;

    public WaveManager(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        createStarterWaves();
    }
    private void createStarterWaves() {
        waveArray.add(new Wave(
                new ArrayList<>(Arrays.asList(0,0,0)),
                new ArrayList<>(Arrays.asList(1,1,1))));
        waveArray.add(new Wave(
                new ArrayList<>(Arrays.asList(0,0,0,0,0)),
                new ArrayList<>(Arrays.asList(1,1,1,1,1))));
        waveArray.add(new Wave(
                new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0)),
                new ArrayList<>(Arrays.asList(1,1,1,1,1,1,1))));
    }

    public void createWave() {
        if ((waveIndex) % 5 == 0 && (waveIndex) % 10 != 0) {

            Random rand = new Random();
            enemyNum = rand.nextInt(3) + 3;
            enemyType = rand.nextInt(3);
            enemySize = LARGE;

            for (int i = 0; i < enemyNum; i++) {
                enemyTypeArray.add(enemyType);
                enemySizeArray.add(enemySize);
            }

            waveArray.add(new Wave(
                    enemyTypeArray,
                    enemySizeArray));
        } else if((waveIndex) % 10 == 0) {

            Random rand = new Random();
            enemyNum = rand.nextInt(2) + 2;
            enemyType = BLUE;
            enemySize = LARGE;

            for (int i = 0; i < enemyNum; i++) {
                enemyTypeArray.add(enemyType);
                enemySizeArray.add(enemySize);
            }

            waveArray.add(new Wave(
                    enemyTypeArray,
                    enemySizeArray));
        } else {
            Random rand = new Random();
            enemyNum = rand.nextInt(6) + 5;
            enemyType = rand.nextInt(3);
            enemySize = rand.nextInt(2);

            for (int i = 0; i < enemyNum; i++) {
                enemyTypeArray.add(enemyType);
                enemySizeArray.add(enemySize);
            }

            waveArray.add(new Wave(
                    enemyTypeArray,
                    enemySizeArray));
        }
    }

    public void update() {
        if(enemySpawnTick < 60 * 1.5)
            enemySpawnTick++;

        if(waveStartTimer) {
            waveStartTick++;
            if (waveStartTick >= 60 * 5) {
                waveEndTimer = true;
            }
        }
        System.out.println(waveArray.size()+", "+getWaveIndex());
    }

    public void incWaveIndex() {
        waveIndex++;
        enemyIndex = 0;
        waveEndTimer = false;
        waveStartTimer = false;

        if(waveIndex == waveArray.size()) {
            createWave();
        }
    }

    public void startWaveTimer() {
        waveStartTimer = true;
    }

    public boolean isWaveTimerOver() {
        return waveEndTimer;
    }

    public int getNextEnemyType() {

        return waveArray.get(waveIndex).getEnemyType().get(enemyIndex);
    }

    public int getNextEnemySize() {
        enemySpawnTick = 0;
        return waveArray.get(waveIndex).getEnemySize().get(enemyIndex++);
    }

    public boolean isEnemySpawnTime() {
        return enemySpawnTick >= 60;
    }

    public boolean isThereMoreEnemySpawn() {
        return enemyIndex < waveArray.get(waveIndex).getEnemyType().size();
    }

    public boolean isThereMoreWaves() {
        return waveIndex + 1 < waveArray.size();
    }

    public boolean isWaveTimeStarted() {
        return waveStartTimer;
    }

    public ArrayList<Wave> getWaveArray() {
        return waveArray;
    }

    public float getTimeLeft() {
        float ticksLeft = (60 * 5 - waveStartTick);
        return ticksLeft / 60.0f;
    }

    public int getWaveIndex() {
        return waveIndex;
    }
}
