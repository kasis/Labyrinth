package labyrinth;

import java.util.Properties;

public class GameInfo {

        private static final String PROPERTIES_LIVES = "Lives";

        private static final String PROPERTIES_HAS_KEY = "Has_key";

        private static final String PROPERTIES_LEVEL = "Level";

        private boolean mHasKey = false;

        private int mInitLivesNum = 3;
        
        private int mCurrLivesNum = mInitLivesNum;
        
        private int mLevelNum = 1;
        
        
        public GameInfo(int initLivesNum) {
            mInitLivesNum = initLivesNum;
            mCurrLivesNum = initLivesNum;
        }
        
	public void addKey() {
            mHasKey = true;
	}

	public void deleteKey() {
           mHasKey = false;
	}

	public void setKey(boolean hasKey) {
           mHasKey = hasKey;
	}

	public boolean hasKey() {
		return mHasKey;
	}

	
        public int getInitLives() {
            return mInitLivesNum;
        } 
        
	public void setCurrentLives(int livesNum) {
            mCurrLivesNum = livesNum;
	}

        public int getCurrentLives() {
            return mCurrLivesNum;
        }
        
        public boolean hasMoreLives() {
            return mCurrLivesNum > 0;
        }
        
        public void deleteLife() {
            mCurrLivesNum --;
        }
        
	public void nextLevel() {
            mLevelNum++;
	}

	public void setLevel(int levelNum) {
            mLevelNum = levelNum;
	}

	public int getLevel() {
            return mLevelNum;
        }
        
        public void save(Properties prop) {
            prop.setProperty(PROPERTIES_LIVES, ""+mCurrLivesNum);
            prop.setProperty(PROPERTIES_HAS_KEY, ""+mHasKey);
            prop.setProperty(PROPERTIES_LEVEL, ""+mLevelNum);
        }

        public void load(Properties prop) {
            mCurrLivesNum = Integer.parseInt(prop.getProperty(PROPERTIES_LIVES));
            mLevelNum = Integer.parseInt(prop.getProperty(PROPERTIES_LEVEL));
            mHasKey = Boolean.parseBoolean(prop.getProperty(PROPERTIES_HAS_KEY));
            Logger.log("GamePanel.load: " + mCurrLivesNum + " " + mLevelNum);
        }
    
	public void reset() {
            mHasKey = false;
            mLevelNum = 1;
            mCurrLivesNum = mInitLivesNum;
        }
    

}
