package Utils;

import Game.Towers.Tower;

public class Types {
    public static final class Towers {
        public static final int MAGE_TOWER = 0;
        public static final int ICE_TOWER = 1;
        public static final int BOULDER_TOWER = 2;
        public static final int SUPPORT_TOWER = 3;

        public static String GetTowerName(int towerType) {
            switch (towerType) {
                case MAGE_TOWER: return "MAGE TOWER";
                case ICE_TOWER: return "ICE TOWER";
                case BOULDER_TOWER: return "BOULDER TOWER";
                case SUPPORT_TOWER: return "SUPPORT TOWER";
            }
            return "";
        }

        public static int GetTowerCost(int towerType) {
            switch (towerType) {
                case MAGE_TOWER: return 50;
                case ICE_TOWER: return 80;
                case BOULDER_TOWER: return 150;
                case SUPPORT_TOWER: return 200;
            }
            return 0;
        }

        public static int GetUpgradeCost(int towerType) {
            switch (towerType) {
                case MAGE_TOWER: return 10;
                case ICE_TOWER: return 15;
                case BOULDER_TOWER: return 30;
                case SUPPORT_TOWER: return 50;
            }
            return 0;
        }

        public static int GetStartDamage(int towerType) {
            switch(towerType) {
                case MAGE_TOWER: return 60;
                case ICE_TOWER: return 10;
                case BOULDER_TOWER: return 40;
                case SUPPORT_TOWER: return 0;
            }
            return 0;
        }

        public static int GetRange(int towerType) {
            switch(towerType) {
                case MAGE_TOWER: return 200;
                case ICE_TOWER: return 100;
                case BOULDER_TOWER: return 150;
                case SUPPORT_TOWER: return 250;
            }
            return 0;
        }

        public static int GetStartAttackCooldown(int towerType) {
            switch(towerType) {
                case MAGE_TOWER: return 80;
                case ICE_TOWER: return 70;
                case BOULDER_TOWER: return 100;
                case SUPPORT_TOWER: return 500;
            }
            return 0;
        }
    }

    public static class Enemies {

        public static final int GRAY = 0;
        public static final int GREEN = 1;
        public static final int RED = 2;
        public static final int BLUE = 3;

        public static final int SMALL = 0;
        public static final int MEDIUM = 1;
        public static final int LARGE = 2;


        public static int GetEnemySize(int enemySize) {
            switch (enemySize) {
                case SMALL: return 24;
                case MEDIUM: return 32;
                case LARGE: return 48;
            }
            return 0;
        }

        public static float GetSpeed(int enemySize) {
            switch (enemySize) {
                case SMALL: return 1.5f;
                case MEDIUM: return 1f;
                case LARGE: return 0.5f;
            }
            return 0;
        }

        public static int GetStartHealth(int enemyType, int enemySize) {
            switch (enemyType) {
                case GRAY, GREEN, BLUE:
                    if(enemySize == SMALL) return (int)(100*0.8);
                    if(enemySize == MEDIUM) return 100;
                    if(enemySize == LARGE) return (int)(100 * 1.2);
                case RED:
                    if(enemySize == SMALL) return (int)(150*0.8);
                    if(enemySize == MEDIUM) return 150;
                    if(enemySize == LARGE) return (int)(150 * 1.2);
            }
            return 0;
        }

        public static int GetEnemyGold(int enemyType, int enemySize) {
            switch (enemyType) {
                case GRAY:
                    if(enemySize == SMALL) return 3;
                    if(enemySize == MEDIUM) return 5;
                    if(enemySize == LARGE) return 8;
                case RED, GREEN, BLUE:
                    if(enemySize == SMALL) return 7;
                    if(enemySize == MEDIUM) return 10;
                    if(enemySize == LARGE) return 15;
            }
            return 0;
        }
    }

    public static class Projectiles {
        public static final int MAGIC = 0;
        public static final int ICE = 1;
        public static final int BOULDER = 2;
        public static final int SUPPORT = 3;

        public static int GetProjectileType(Tower tower) {
            switch (tower.getTowerType()) {
                case Towers.MAGE_TOWER: return MAGIC;
                case Towers.ICE_TOWER: return ICE;
                case Towers.BOULDER_TOWER: return BOULDER;
                case Towers.SUPPORT_TOWER: return SUPPORT;
            }
            return 0;
        }

        public static int GetSpeed(int projectileType) {
            switch(projectileType) {
                case MAGIC: return 2;
                case ICE: return 1;
                case BOULDER: return 3;
                case SUPPORT: return 5;
            }
            return 0;
        }
    }
}
