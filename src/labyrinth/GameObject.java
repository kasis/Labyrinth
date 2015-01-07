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
