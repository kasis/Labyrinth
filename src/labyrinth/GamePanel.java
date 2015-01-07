/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.Terminal.Color;

/**
 *
 * @author melkonyan
 */
public class GamePanel {
    
    private static final char LIFE_CHAR = '❤';
    
    private static final Color LIFE_COLOR = Color.RED;
    
    private static final char KEY_CHAR = '☦';
    
    private static final Color KEY_COLOR = Color.YELLOW;
    
    private static final Color TEXT_COLOR = Color.BLACK;
    
    private static final String MENU_TEXT = "M - Options menu";
    
    private static final String ABOUT_TEXT = "A - About";
    
    private Terminal mTerminal;
    
    private int mTerminalWidth;
    
    private int mLivesNum;
    
    private int mInitLivesNum;
    
    private int mMaxLivesNum = 5;
    
    private boolean mHasKey;
    
    private int mLevelNum = 1;
    
    private int mLivesStartPos;
    
    private int mKeyStartPos;
    
    private int mLevelStartPos; 
    
    private int mMenuStartPos;
    
    private int mAboutStartPos;
    
    public GamePanel(Terminal t) {
        mTerminal = t;
        mTerminalWidth = t.getTerminalSize().getColumns();
        mLivesStartPos = 1;
        mKeyStartPos = mMaxLivesNum + mLivesStartPos + 3;
        mLevelStartPos = mKeyStartPos + 6;
        mMenuStartPos = mLevelStartPos + 10;
        mAboutStartPos = mMenuStartPos + MENU_TEXT.length() + 10;
    }
    
    public void setLivesNum(int num) {
        mLivesNum = num;
        mInitLivesNum = num;
    }
    
    public boolean hasMoreLives() {
        return mLivesNum > 0;
    }
    
    public void deleteLife() {
        mLivesNum--;
    }
    
    public boolean hasKey() {
       return mHasKey; 
    }
    
    public void addKey() {
        mHasKey = true;
    }
    
    public void deleteKey() {
        mHasKey = false;
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
    
    public void reset() {
        mHasKey = false;
        mLevelNum = 1;
        mLivesNum = mInitLivesNum;
    }
    
    public void draw() {
        paint();
        for (int i = 0; i < mLivesNum; i++) {
            putChar(mLivesStartPos + i, LIFE_CHAR, LIFE_COLOR);
        }
        
        if (hasKey()) {
            putChar(mKeyStartPos, KEY_CHAR, KEY_COLOR);
        }
        
        putString(mLevelStartPos, "Level " + mLevelNum, TEXT_COLOR);
        putString(mMenuStartPos, MENU_TEXT, TEXT_COLOR);
        putString(mAboutStartPos, ABOUT_TEXT, TEXT_COLOR);
    }
    
    
    private void paint() {
        for (int i = 0; i < mTerminalWidth; i++) {
            mTerminal.moveCursor(i, 0);
            mTerminal.applyBackgroundColor(Color.WHITE);
            mTerminal.putCharacter(' ');
        }
    }
    
    private void putChar(int x, char c, Color color) {
        mTerminal.moveCursor(x, 0);
        mTerminal.applyForegroundColor(color);
        mTerminal.putCharacter(c);
    }
    
    private void putString(int start, String s, Color color) {
        for (int i = 0; i < s.length(); i++) {
            putChar(start + i, s.charAt(i), color);
        }
    }
}
