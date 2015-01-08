/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.Terminal.Color;
import com.googlecode.lanterna.terminal.TerminalSize;
import generator.Generate;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * Class, that displays map of the labyrinth on the terminal window
 * @author melkonyan
 */
public class Labyrinth {

    private Terminal mTerminal; 
    private Position mStart;
    private MovableObserver mObserver;
    
    private Player mPlayer;
    private Position mPlayerStartPos;
    
    private List<Enter> mEnters; 
    private List<Exit> mExits;
    private List<MovingMonster> mMonsters;
    
    private GameObject[][] mInitMap;
    private GameObject[][] mLiveMap;
  
    private int mMapHeight;
    private int mMapWidth;

    private Frame mVisibleFrame;
    
    /**
     * 
     * @param terminal  terminal window to display labyrinth on
     * @param start     position at which left upper corner of labyrinth will be drawn
     */
    public Labyrinth(Terminal terminal, Position start, MovableObserver observer) {
        mTerminal = terminal;
        mObserver = observer;
        mStart = start;
        
    }
    
    /**
     * Load labyrinth from file.
     * @throws Exception 
     */
    public void createLevel() throws Exception {
        mEnters = new ArrayList<>();
        mExits = new ArrayList<>();
        mMonsters = new ArrayList<>();
        
        Generate.main(null);
        Properties prop = load("", "level.properties");
        mMapHeight = Integer.parseInt(prop.getProperty("Height"));
        mMapWidth = Integer.parseInt(prop.getProperty("Width"));
        Logger.log("Labyrinth.loadLabyrinth: height - " + mMapHeight + "; width - " + mMapWidth);
        mInitMap = new GameObject[mMapHeight][mMapWidth];
        mLiveMap = new GameObject[mMapHeight][mMapWidth];
        Enumeration keys = prop.propertyNames();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            if ("Height".equals(key) || "Width".equals(key)) {
                continue;
            }
            String value = prop.getProperty(key);
            GameObject obj = createGameObject(key, value);
            if (obj instanceof Enter) {
                mEnters.add((Enter) obj);
            } else if (obj instanceof Exit) {
                mExits.add((Exit) obj);
            } else if (obj instanceof MovingMonster) {
                mMonsters.add((MovingMonster) obj);
            }
            Position pos = obj.getPosition();
            mInitMap[pos.getX()][pos.getY()] = obj;
        }
        for (int i = 0; i < mMapHeight; i++) {
            for (int j = 0; j < mMapWidth; j++) {
                if (mInitMap[i][j] == null) {
                    mInitMap[i][j] = new Road(new Position(i, j));
                }
            }
        }
    }
    
    /**
     * Display Labyrinth on Terminal.
     */
    public void draw() {
        updateVisibleFrame();
        for (int i = 0; i < mMapHeight; i++) {
            for (int j = 0; j < mMapWidth; j++) {
                setObject(mInitMap[i][j]);
            }
        }
        drawObject(mPlayer);
        
    }

    /**
     * Draw initial version of labyrinth and move player to initial position.
     */
    public void redraw() {
        mVisibleFrame.clear();
        mPlayer.setPosition(mPlayerStartPos);
        draw();
    }
    
    public void refresh() {
        updateVisibleFrame();
        mVisibleFrame.draw();
    }
    
    /**
     * Return game object that is drawn at given position.
     * @param pos   position of object that should be returned
     * @return      game object
     */
    public GameObject getObject(Position pos) {
        return mLiveMap[pos.getX()][pos.getY()];
    }
    
    /**
     * Save given game object and draw it on it's position.
     * @param obj   object to draw. 
     */
    public void setObject(GameObject obj) {
        mLiveMap[obj.getPosition().getX()][obj.getPosition().getY()] = obj;
        drawObject(obj);
    }
    
    /**
     * Delete given object and draw {@link Road} instead of it. 
     * @param pos   position of object to delete
     */
    public void deleteObject(Position pos) {
        Road road = new Road(pos);
        setObject(road);
    }
    
    /** 
     * Get player
     * @return  player
     */
    public Player getPlayer() {
        return mPlayer;
    }
    
    /** 
     * Set player. Player will not be drawn.
     * @param player    game object of type Player
     */
    public void setPlayer(Player player) {
        mPlayer = player;
        mPlayerStartPos = player.getPosition();
    }
    
    /**
     * Move player to his new position. Delete him from his old position.
     * @param from  old position of the Player
     */
    public void movePlayer(Position from) {
        setObject(getObject(from));
        if (!mVisibleFrame.contains(mPlayer)) {
            mVisibleFrame.clear();
            updateVisibleFrame();
            mVisibleFrame.draw();
        }
        drawObject(mPlayer);
    }
    
    /**
     * Move monster to new position.
     * @param monster   monster to move
     * @param from      old position of object
     */
    public void moveMonster(MovingMonster monster, Position from) {
        setObject(new Road(from));
        setObject(monster);
    }
    
    /**
     * Get all enters of the labyrinth.
     * @return  List of Enter
     */
    public List<Enter> getEnters() {
        return mEnters;
    }
    
    /**
     * Check if position is outside of labyrinth.
     * @param pos   position to check
     * @return      true if position is outside of labyrinth, false otherwise
     */
    public boolean outOfBound(Position pos) {
        return pos.getX() < 0 || pos.getX() >= mMapHeight || pos.getY() < 0 || pos.getY() >= mMapWidth;
    }
    
    public void start() {
        for(MovingMonster monster: mMonsters) {
            monster.start();
        }
    }
    
    public void stop() {
        for(MovingMonster monster: mMonsters) {
            monster.stop();
        }
    }
    
    private void updateVisibleFrame() {
        Position pos = mStart;
        if (mPlayer != null) {
            pos = mPlayer.getPosition();
        }
        TerminalSize s = mTerminal.getTerminalSize();
        int height = s.getRows() - mStart.getX();
        int width = s.getColumns() - mStart.getY();
        Logger.log(height + " " + width);
        int start_x = pos.getX() / height * height;
        int start_y = pos.getY() / width * width;
        int end_x = start_x + Math.min(height, mMapHeight - start_x);
        int end_y = start_y + Math.min(width, mMapWidth - start_y);
        Logger.log(start_x + " " + start_y + " " + end_x + " " + end_y);
        mVisibleFrame = new Frame(new Position(start_x, start_y), new Position(end_x, end_y), mStart);
    }
    
    private void drawObject(GameObject obj) {
        mVisibleFrame.drawObject(obj);
    }
    
    private Properties load(String path, String name) throws IOException {
        Properties prop = new Properties();
        InputStream in = new FileInputStream(name);
        if (in == null) {
            throw new IOException("Could not open properties file.");
        }
        prop.load(in);
        in.close();
        return prop;
    }

    private GameObject createGameObject(Position pos, int type) throws Exception {
        switch (type) {
            case 0:
                return new Wall(pos);
            case 1:
                return new Enter(pos);
            case 2:
                return new Exit(pos);
            case 3:
                return new StaticMonster(pos);
            case 4:
                return new MovingMonster(pos, mObserver);
            case 5:
                return new Keys(pos);
            default:
                throw new Exception("Unknow type of GameObject");
        }
    }

    private GameObject createGameObject(String key, String value) throws Exception {
        int pos1 = key.indexOf(',');
        if (pos1 == -1) {
            throw new Exception("Unable to parse file");
        }
        int y = Integer.parseInt(key.substring(0, pos1));
        int x = Integer.parseInt(key.substring(pos1 + 1, key.length()));
        int type = Integer.parseInt(value);
        return createGameObject(new Position(x, y), type);
    }
    
    /*
     * Visible part of labyrinth. This class is responsible for drawing object on the terminal.
    */
    private class Frame {
        
        //Frame start on the terminal;
        Position mFrameStart;
        
        //Inclusive
        int mStartX, mStartY;
        
        //Exclusive 
        int mEndX, mEndY;
        
        public Frame(Position upperLeft, Position lowerRight, Position frameStart) {
            mStartX = upperLeft.getX();
            mStartY = upperLeft.getY();
            mEndX = lowerRight.getX();
            mEndY = lowerRight.getY();
            mFrameStart = frameStart;
        } 
        
        public void draw() {
            for (int i = mStartX; i < mEndX; i++) {
                for (int j = mStartY; j < mEndY; j++) {
                    drawObject(getObject(new Position(i, j)));
                }
            }
            drawObject(mPlayer);
        }
        
        public void drawObject(GameObject obj) {
            if (!contains(obj)) {
                return;
            }
            Position pos = obj.getPosition();
            mTerminal.moveCursor(pos.getY() - mStartY + mFrameStart.getY(), pos.getX() - mStartX + mFrameStart.getX());
            mTerminal.applyForegroundColor(obj.getColor());
            mTerminal.applyBackgroundColor(Color.BLACK);
            mTerminal.putCharacter(obj.getChar());
            mTerminal.setCursorVisible(false);
        }
        
        public boolean contains(GameObject obj) {
            Position pos = obj.getPosition();
            return pos.getX() >= mStartX && pos.getX() < mEndX 
                    && pos.getY() >= mStartY && pos.getY() < mEndY;
        }
        
        public void clear() {
            for (int i = mStartX; i < mEndX; i++) {
                for (int j = mStartY; j < mEndY; j++) {
                    drawObject(new Road(new Position(i, j)));
                }
            }
        }
    }
    
    
}
