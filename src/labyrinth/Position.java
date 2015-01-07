/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

/**
 * Class that represents position of the object in the labyrinth.
 * @author melkonyan
 */
public class Position {
    
    private int x;
    
    private int y;
    
    /**
     * Construct new position given to coordinates.
     * @param x     x-coordinate
     * @param y     y-coordinate
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * 
     * @return x-coordinate 
     */
    public int getX() {
        return x;
    } 
    
    /** 
     * 
     * @return y-coordinate
     */
    public int getY() {
        return y;
    }
    
    @Override 
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
