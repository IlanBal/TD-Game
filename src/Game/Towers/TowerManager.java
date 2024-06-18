package Game.Towers;


import Game.CustomButton;
import Game.Enemies.Enemy;
import Game.GameWindow;
import Game.Projectiles.Projectile;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static Utils.Dimensions.*;
import static Utils.Types.Projectiles.SUPPORT;
import static Utils.Types.Towers.*;

public class TowerManager {
    
    private int maxTowers = 8;
    private int towerAmount = 0;
    private int removedTowers = 0;
    
    private ArrayList<Tower> towerArray = new ArrayList<>();
    private ArrayList<TowerAddButton> addTowerButton = new ArrayList<>();
    private Tower selectedTower;
    private ImageIcon towerImagePath;
    private Image[] towerImage;
    private GameWindow gameWindow;

    private boolean isTowerBuyable = true;

    public TowerManager(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        loadTowerImages();
        addTowerButton.add(new TowerAddButton(gameWindow,"+", CASTLE_X + gameWindow.getCastle().castleWidth + 84, CASTLE_Y + gameWindow.getCastle().castleHeight / 2, 20, 20));
    }

    private void loadTowerImages() {

        towerImage = new Image[4];
        for (int i = 0; i < towerImage.length; i++) {
            towerImagePath = new ImageIcon(gameWindow.getClass().getResource(("Images/tower" + i + ".png")));
            towerImage[i] = towerImagePath.getImage();
        }
    }

    public void addTower(int location, int towerType, int TOWER_X, int TOWER_Y) {
        if (towerArray.size() <= maxTowers) {
            if(removedTowers == 0) {
                if(addTowerButton.size()+1  <= maxTowers) {
                    addTowerButton.add(new TowerAddButton(gameWindow, "+", addTowerButton.get(towerAmount).x + 84, addTowerButton.get(towerAmount).y, 20, 20));
                }
                addTowerButton.set(towerAmount, null);
                towerArray.add(new Tower(gameWindow, TOWER_X, TOWER_Y, 64, 64, towerAmount++, towerType));
            } else {
                towerArray.set(location, new Tower(gameWindow, TOWER_X, TOWER_Y, 64, 64, towerAmount++, towerType));
                addTowerButton.set(location, null);
            }
        }
    }

    public void removeTower(Tower tower) {
        for (int i = 0; i < towerArray.size(); i++) {
            if(towerArray.get(i) != null) {
                if (towerArray.get(i).getBounds().contains(tower.position_X, tower.position_Y)) {
                    gameWindow.addGold(GetTowerCost(i)/2 + GetUpgradeCost(i)*towerArray.get(i).getUpgradeAmount());
                    addTowerButton.set(i, new TowerAddButton(gameWindow, "+", (int) (towerArray.get(i).position_X), (int) (towerArray.get(i).position_Y), 20, 20));
                    towerArray.set(i, null);
                    towerAmount--;
                    removedTowers++;
                }
            }
        }
    }

    public void upgradeTower(Tower tower) {
        for(int i=0; i < towerArray.size(); i++) {
            Tower t = towerArray.get(i);
            if(t != null) {
                if(t.getId() == tower.getId()) {
                    tower.upgradeTower(tower.getTowerType());
                    if(tower.getUpgradeAmount() < 3) {
                        gameWindow.setPlayerGold(gameWindow.getPlayerGold() - GetUpgradeCost(tower.getTowerType()));
                    } else tower.towerDisplayBar.setUpgradable(false);
                }
            }
        }
    }

    public Tower getTowerAt(int x, int y) {
        for (Tower tower : towerArray) {
            if (tower != null && tower.getBounds().contains(x, y))
                return tower;
        }
        return null;
    }

    public void attackEnemyInRange(Tower tower) {
        if(tower != null) {
            for (Enemy enemy : gameWindow.getEnemyManager().getEnemyArray()) {
                if(enemy.isEnemyAlive()) {
                    if (isEnemyInRange(tower, enemy)) {
                        if (tower.isCooldownOver()) {
                            if(tower.getTowerType() == BOULDER_TOWER) {
                                gameWindow.throwRocks(tower, enemy);
                            } else if(tower.getTowerType() == ICE_TOWER) {
                                if(!enemy.isSlowed())
                                    gameWindow.shootIce(tower, enemy);
                            } else if(tower.getTowerType() == SUPPORT_TOWER) {
                                gameWindow.supportTowers(tower);
                            } else
                                gameWindow.shootEnemy(tower, enemy);
                            tower.resetCooldown();
                        }
                    }
                }
            }
        }
    }

    private boolean isEnemyInRange(Tower tower, Enemy enemy) {
        int range = GetDistance((int) (tower.position_X + 32), (int) (tower.position_Y + 64), (int) enemy.position_X, (int) enemy.position_Y);

        return range < tower.getRange();
    }

    public ArrayList<Tower> getTowerArray() {
        return towerArray;
    }

    public void paint(Graphics g) {
        if(selectedTower != null) {
            drawSelectedTowerRange(g, selectedTower);
        }

        for (Tower tower : towerArray) {
            if(tower != null)
                tower.paint(g, towerImage[tower.getTowerType()]);
        }

        if(towerAmount<maxTowers) {
            for(TowerAddButton button: addTowerButton) {
                if(button != null) {
                    button.paint(g, null);
                    button.paintBar(g);
                }
            }
        }
    }

    private void drawSelectedTowerRange(Graphics g, Tower selectedTower) {
        g.setColor(Color.WHITE);
        g.drawArc((int) (selectedTower.position_X + 32 - selectedTower.getRange()), (int) (selectedTower.position_Y + 64 - selectedTower.getRange()),
                (int) selectedTower.getRange()*2,(int) selectedTower.getRange()*2, 0, 180);
    }

    public void update() {
        for(int i=0;i<towerArray.size(); i++) {
            Tower tower = towerArray.get(i);
            if(tower != null) {
                tower.update();
                attackEnemyInRange(tower);

                if(tower.isSupportOver()) {
                    tower.setSupported(false);
                    for(int j=0; j < gameWindow.getProjectileManager().getProjectileArray().size(); j++) {
                        Projectile projectile = gameWindow.getProjectileManager().getProjectileArray().get(j);
                        if(projectile.getProjectileType() == SUPPORT) {
                            gameWindow.getProjectileManager().getProjectileArray().remove(projectile);
                        }
                    }
                    tower.resetSupport();
                }
            }
        }
    }

    public void mouseClicked(int x, int y) {

        for(int i=0; i<addTowerButton.size(); i++) {
            if (addTowerButton.get(i) != null) {
                if (addTowerButton.get(i).getBounds().contains(x, y)) {
                    addTowerButton.get(i).showActionBar();
                    return;
                } else addTowerButton.get(i).hideActionBar();

            if(addTowerButton.get(i).towerSelectionBar != null) {
                for (CustomButton buttons : addTowerButton.get(i).towerSelectionBar.towerButton) {
                    if (buttons.getBounds().contains(x, y) ) {
                        if(gameWindow.getPlayerGold() >= GetTowerCost(buttons.getId())) {
                            addTowerButton.get(i).towerSelectionBar.setButtonPressed(true);
                            addTowerButton.get(i).towerSelectionBar.setBarShown(false);
                            gameWindow.setPlayerGold(gameWindow.getPlayerGold() - GetTowerCost(buttons.getId()));
                            addTower(i, buttons.getId(), addTowerButton.get(i).x, addTowerButton.get(i).y);
                            return;
                        } else isTowerBuyable = false;

                    } else addTowerButton.get(i).towerSelectionBar.setBarShown(false);
                }
            }
            }
        }

        if(getTowerAt(x,y) != null) {
            if(selectedTower != null) {
                Tower tower = getTowerAt(x, y);
                if (tower.getId() != selectedTower.getId()) {
                    selectedTower.towerDisplayBar.setBarShown(false);
                    selectedTower = tower;
                } else {
                    selectedTower.towerDisplayBar.setBarShown(true);
                    selectedTower.showActionBar();
                }
            }

            selectedTower = getTowerAt(x, y);
        }

        if (selectedTower != null) {
            if (selectedTower.getBounds().contains(x, y)) {
                selectedTower.showActionBar();
            } else if(selectedTower.towerDisplayBar.getUpgradeButton().getBounds().contains(x, y)) {
                selectedTower.towerDisplayBar.setButtonPressed(true);
                upgradeTower(selectedTower);
                selectedTower.towerDisplayBar.setButtonPressed(false);
            } else if (selectedTower.towerDisplayBar.getSellButton().getBounds().contains(x, y)) {
                selectedTower.towerDisplayBar.setButtonPressed(true);
                removeTower(selectedTower);
            } else {
                selectedTower.hideActionBar();
                selectedTower = null;
            }
        }
    }

    public void mouseMoved(int x, int y) {
        for (TowerAddButton button : addTowerButton) {
            if(button !=null) {
                button.setMouseOver(false);

                if (button.towerSelectionBar != null) {
                    for (CustomButton buttons : button.towerSelectionBar.towerButton) {
                        buttons.setMouseOver(false);
                    }
                }
            }
        }

        if(selectedTower != null) {
            selectedTower.towerDisplayBar.getUpgradeButton().setMouseOver(false);
            selectedTower.towerDisplayBar.getSellButton().setMouseOver(false);
        }

        for (TowerAddButton button : addTowerButton) {
            if(button != null) {
                if (button.getBounds().contains(x, y)) {
                    button.setMouseOver(true);
                }

                if (button.towerSelectionBar != null) {
                    for (CustomButton buttons : button.towerSelectionBar.towerButton) {
                        if (buttons.getBounds().contains(x, y)) {
                            buttons.setMouseOver(true);
                        }
                    }
                }
            }
        }

        if(selectedTower != null) {
            if (selectedTower.towerDisplayBar.getUpgradeButton().getBounds().contains(x, y)) {
                selectedTower.towerDisplayBar.getUpgradeButton().setMouseOver(true);
            } else if (selectedTower.towerDisplayBar.getSellButton().getBounds().contains(x, y)) {
                selectedTower.towerDisplayBar.getSellButton().setMouseOver(true);
            }
        }
    }
}
