/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.Terminal.Color;
import java.util.Properties;

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
    
    private GameInfo mInfo;
    
    private int mTerminalWidth;
    
    private int mLivesStartPos;
    
    private int mKeyStartPos;
    
    private int mLevelStartPos; 
    
    private int mMenuStartPos;
    
    private int mAboutStartPos;
    
    public GamePanel(Terminal t, GameInfo info) {
        mTerminal = t;
        mInfo = info;
        mTerminalWidth = t.getTerminalSize().getColumns();
        mLivesStartPos = 1;
        mKeyStartPos = mInfo.getInitLives() + mLivesStartPos + 3;
        mLevelStartPos = mKeyStartPos + 6;
        mMenuStartPos = mLevelStartPos + 10;
        mAboutStartPos = mMenuStartPos + MENU_TEXT.length() + 10;
    }
    
    
    public void draw() {
        paint();
        for (int i = 0; i < mInfo.getCurrentLives(); i++) {
            putChar(mLivesStartPos + i, LIFE_CHAR, LIFE_COLOR);
        }
        
        if (mInfo.hasKey()) {
            putChar(mKeyStartPos, KEY_CHAR, KEY_COLOR);
        }
        
        putString(mLevelStartPos, "Level " + mInfo.getLevel(), TEXT_COLOR);
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
