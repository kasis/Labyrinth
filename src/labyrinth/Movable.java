/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

/**
 * Interface that describes an object that can move or be moved.
 * @author melkonyan
 */
public interface Movable {
    
    /**
     * Get current position of the object.
     * @return current position of the object
     */
    public Position getPosition();
    
    /**
     * Calculate new position of the object, if it would move in given position. 
     * But do not update actual position of the object.
     * @param dir   direction to move
     * @return      new position after moving in given direction
     */
    public Position calcNewPosition(Direction dir);
    
    /**
     * Go to given position. Actual position of the object should be replaced with given one.
     * @param pos   position to move to.
     */
    public void goToPosition(Position pos);
    
    
}
