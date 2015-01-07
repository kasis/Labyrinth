/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.Terminal.Color;
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
    
    private Player mPlayer;
    private Position mPlayerStartPos;
    
    private List<Enter> mEnters = new ArrayList<>();
    private List<Exit> mExits = new ArrayList<>();
    
    private GameObject[][] mInitMap;
    private GameObject[][] mLiveMap;
    
    private int mMapHeight;
    private int mMapWidth;

    /**
     * 
     * @param terminal  terminal window to display labyrinth on
     * @param start     position at which left upper corner of labyrinth will be drawn
     */
    public Labyrinth(Terminal terminal, Position start) {
        mTerminal = terminal;
        mStart = start;
    }
    
    /**
     * Load labyrinth from file.
     * @throws Exception 
     */
    public void loadLabyrinth() throws Exception {
        System.out.println(getClass().getProtectionDomain().getCodeSource().getLocation());
        Properties prop = load("", "level.properties");
        mMapWidth = Integer.parseInt(prop.getProperty("Height"));
        mMapHeight = Integer.parseInt(prop.getProperty("Width"));
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
            System.out.println(key + " " + value);
            GameObject obj = createGameObject(key, value);
            if (obj instanceof Enter) {
                mEnters.add((Enter) obj);
            }
            if (obj instanceof Exit) {
                mExits.add((Exit) obj);
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
    public void drawLabyrinth() {
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
        mPlayer.setPosition(mPlayerStartPos);
        drawLabyrinth();
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
     * Set player and draw it.
     * @param player    game object of type Player
     */
    public void setPlayer(Player player) {
        mPlayer = player;
        mPlayerStartPos = player.getPosition();
        drawObject(player);
    }
    
    /**
     * Move player to his new position. Delete him from his old position.
     * @param from  Old position of the Player
     */
    public void movePlayer(Position from) {
        setObject(getObject(from));
        drawObject(mPlayer);
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
    
    private void drawObject(GameObject obj) {
        Position pos = obj.getPosition();
        //System.out.println(pos.getX() + " " + pos.getY() + " " + obj.getChar());
        mTerminal.moveCursor(pos.getX() + mStart.getX(), pos.getY() + mStart.getY());
        mTerminal.applyForegroundColor(obj.getColor());
        mTerminal.applyBackgroundColor(Color.BLACK);
        mTerminal.putCharacter(obj.getChar());
        mTerminal.setCursorVisible(false);
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
                return new MovingMonster(pos);
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
        int x = Integer.parseInt(key.substring(0, pos1));
        int y = Integer.parseInt(key.substring(pos1 + 1, key.length()));
        int type = Integer.parseInt(value);
        return createGameObject(new Position(x, y), type);
    }

    
    
    
}
