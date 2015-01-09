/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

import com.googlecode.lanterna.terminal.Terminal.Color;

/**
 * Class that represents single drawable object of labyrinth.
 * @author melkonyan
 */
public abstract class GameObject {

    public static final int WALL = 0;
    
    public static final int ENTER = 1;
    
    public static final int EXIT = 2;
    
    public static final int STATIC_MONSTER = 3;
    
    public static final int MOVING_MONSTER = 4;
    
    public static final int KEYS = 5;
            
    public static final int PLAYER = -1;
    
    public static final int ROAD = -1;
    
    private Position mPosition;
    
    /**
     * Character to use when drawing object on the terminal.
     * @return  character
     */
    public abstract char getChar();
        
    /**
     * Color to use when drawing this object.
     * @return  class that represents color of the object 
     */
    public abstract Color getColor();
    
    /** 
     * Return type of object;
     * @return  integer corresponding to type of object
     */
    public abstract int getType();
    
    /**
     * Construct object with given position. 
     * @param pos   position of the object
     */
    public GameObject(Position pos) {
        mPosition = pos;
    }

    /**
     * Get position of the object.
     * @return  position at which object is drawn
     */
    public Position getPosition() {
        return mPosition;
    }
    
    /**
     * Set new position for the object.
     * @param pos   new position of the object
     */
    protected void setPosition(Position pos) {
        mPosition = pos;
    }
}
