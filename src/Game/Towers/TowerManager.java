package Game.Towers;


import Game.CustomButton;
import Game.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static Utils.Dimensions.CASTLE_X;
import static Utils.Dimensions.CASTLE_Y;

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
                towerArray.add(new Tower(gameWindow, TOWER_X, TOWER_Y, towerAmount++, towerType));
            } else {
                towerArray.set(location, new Tower(gameWindow, TOWER_X, TOWER_Y, towerAmount++, towerType));
                addTowerButton.set(location, null);
            }
        }
    }

    public void removeTower(Tower tower) {
        for (int i = 0; i < towerArray.size(); i++) {
            if(towerArray.get(i) != null) {
                if (towerArray.get(i).getBounds(64, 64).contains(tower.position_X, tower.position_Y)) {
                    System.out.println("tower: " + i);
                    addTowerButton.set(i, new TowerAddButton(gameWindow, "+", towerArray.get(i).position_X, towerArray.get(i).position_Y, 20, 20));
                    towerArray.set(i, null);
                    towerAmount--;
                    removedTowers++;
                }
            }
        }
    }

    public void upgradeTower(Tower tower) {

    }

    public Tower getTowerAt(int x, int y) {
        for (Tower tower : towerArray) {
            if (tower != null && tower.getBounds(64, 64).contains(x, y))
                return tower;
        }
        return null;
    }

    public void paint(Graphics g) {
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

    public void mouseClicked(int x, int y) {

        for(int i=0; i<addTowerButton.size(); i++) {
            if (addTowerButton.get(i) != null) {
                if (addTowerButton.get(i).getBounds().contains(x, y)) {
                    addTowerButton.get(i).showActionBar();
                    return;
                } else addTowerButton.get(i).hideActionBar();

            if(addTowerButton.get(i).towerSelectionBar != null) {
                for (CustomButton buttons : addTowerButton.get(i).towerSelectionBar.towerButton) {
                    if (buttons.getBounds().contains(x, y)) {
                        addTowerButton.get(i).towerSelectionBar.setButtonPressed(true);
                        addTowerButton.get(i).towerSelectionBar.setBarShown(false);
                        System.out.println("buttons: " + addTowerButton.size() + ", towers: "+towerArray.size());
                        addTower(i, buttons.getId(), addTowerButton.get(i).x, addTowerButton.get(i).y);
                        System.out.println("buttons: " + addTowerButton.size() + ", towers: "+towerArray.size()+"\n");
                        return;
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
                }
            } selectedTower = getTowerAt(x, y);
        }

        if (selectedTower != null) {
            if(selectedTower.getBounds(64, 64).contains(x, y)) {
                selectedTower.showActionBar();
                return;
            } else selectedTower.hideActionBar();
        }


        if (selectedTower != null) {
            if(selectedTower.towerDisplayBar.getUpgradeButton().getBounds().contains(x, y)) {
                selectedTower.towerDisplayBar.setButtonPressed(true);
                upgradeTower(selectedTower);
            } else selectedTower.towerDisplayBar.setBarShown(false);

            if (selectedTower.towerDisplayBar.getSellButton().getBounds().contains(x, y)) {
                selectedTower.towerDisplayBar.setButtonPressed(true);
                System.out.println("buttons: " + addTowerButton.size() + ", towers: "+towerArray.size());
                removeTower(selectedTower);
                System.out.println("buttons: " + addTowerButton.size() + ", towers: "+towerArray.size() + "\n");
            } else selectedTower.towerDisplayBar.setBarShown(false);
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
