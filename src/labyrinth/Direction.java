/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

import com.googlecode.lanterna.input.Key;

/**
 * Class that represents direction in two-dimensional system. 
 * Is implemented as a unit vector with two coordinates.
 * @author melkonyan
 */
public class Direction {
    
    private int dx;
    
    private int dy;
    
    /**
     * Create direction from keyboard button
     * @param key one of four direction button on keyboard(ArrowUp, ArrowDown, ArrowLeft, ArrowRight)
     */
    public static Direction fromKey(Key key) {
        int dx, dy;
        switch(key.getKind()) {
            case ArrowDown:
                dx = 0; 
                dy = 1;
                break;
            case ArrowLeft:
                dx = -1;
                dy = 0;
                break;
            case ArrowUp: 
                dx = 0;
                dy = -1;
                break;
            case ArrowRight: 
                dx = 1;
                dy = 0;
                break;
            default:
                throw new RuntimeException("Unable to create Direction from key " + key);
        }
        return new Direction(dx, dy);
    }
    
    private Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }
    
    /**
     * Get displacement of the direction.
     * @return x-displacement of direction
     */
    public int getDx() {
        return dx;
    }
    
    /**
     * Get displacement of the direction.
     * @return y-displacement of direction.
     */
    public int getDy() {
        return dy;
    }
    
    /**
     * Calculate new position after making one step in this direction from given position
     * @param from  position to move from
     * @return      new position after moving in this direction
     */
    public Position getNewPosition(Position from) {
        return new Position(from.getX() + dx, from.getY() + dy);
    }
    
    @Override 
    public String toString() {
        return "(" + dx + "," + dy + ")";
    }
}
