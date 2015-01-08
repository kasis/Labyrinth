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
     * @return direction for specified key.
     */
    public static Direction fromKey(Key key) {
        int dx, dy;
        switch(key.getKind()) {
            case ArrowDown:
                dx = 1; 
                dy = 0;
                break;
            case ArrowLeft:
                dx = 0;
                dy = -1;
                break;
            case ArrowUp: 
                dx = -1;
                dy = 0;
                break;
            case ArrowRight: 
                dx = 0;
                dy = 1;
                break;
            default:
                throw new RuntimeException("Unable to create Direction from key " + key);
        }
        return new Direction(dx, dy);
    }
    /**
     * Create direction from integer number.
     * @param   id integer number from 0 to 3
     * @return  appropriate direction: 0 - down, 1 - left, 2 - up, 3 - right  
     */
    public static Direction fromInt(int id) {
        int dx, dy;
        switch(id) {
            case 0: 
                dx = 1;
                dy = 0;
                break;
            case 1: 
                dx = 0;
                dy = -1;
                break;
            case 2: 
                dx = -1; 
                dy = 0;
                break;
            case 3:
                dx = 0;
                dy = 1;
                break;
            default:
                throw new RuntimeException("Unable to create Direction from given int " + id);
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
