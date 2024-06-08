package Utils;

public class Types {
    public static final class Towers {
        public static final int MAGE_TOWER = 0;
        public static final int ICE_TOWER = 1;
        public static final int BOULDER_TOWER = 2;
        public static final int SUPPORT_TOWER = 3;
    }

    public static class Enemies {

            public static final int GRAY = 0;
            public static final int GREEN = 1;
            public static final int RED = 2;
            public static final int BLUE = 3;

            public static final int SMALL = 0;
            public static final int MEDIUM = 1;
            public static final int LARGE = 2;


        public static int GetSpeed(int enemySize) {
            switch (enemySize) {
                case SMALL: return 3;
                case MEDIUM: return 2;
                case LARGE: return 1;
            }
            return 0;
        }
    }
}
