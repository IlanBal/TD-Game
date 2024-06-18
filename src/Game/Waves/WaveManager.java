package Game.Waves;

import Game.Enemies.Enemy;
import Game.GameWindow;

import java.util.ArrayList;
import java.util.Arrays;

public class WaveManager {

    private GameWindow gameWindow;
    private ArrayList<Wave> waveArray = new ArrayList<>() ;
    private int enemySpawnTick, waveStartTick;
    private int waveIndex = 0, enemyIndex;
    private int enemyNum;
    private boolean waveStartTimer;
    private boolean waveEndTimer;

    public WaveManager(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        createWaves();
    }
    private void createWaves() {
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

    public void update() {
        if(enemySpawnTick < 60 * 1.5)
            enemySpawnTick++;

        if(waveStartTimer) {
            waveStartTick++;
            if(waveStartTick >= 60 * 5) {
                waveEndTimer = true;
            }
        }
    }

    public void incWaveIndex() {
        waveIndex++;
        enemyIndex = 0;
        waveEndTimer = false;
        waveStartTimer = false;
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
