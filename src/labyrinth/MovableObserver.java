/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

/**
 * Interface that describes an object that is needed for each {@link Mobable} object.
 * @author melkonyan
 */
public interface MovableObserver {
    
    /**
     * Check if movable object can go to given position.
     * @param m     object that wants to move
     * @param to    position that object wants to move to
     * @return      true if object can move to specified position, false otherwise
     */
    public boolean canMove(Movable m, Position to);
    
    /** 
     * This method is called after movable object has moved to new position.
     * @param m     object that has moved to new position
     * @param from  old position of the object
     * @param to    new position of the object
     */
    public void hasMoved(Movable m, Position from, Position to);
    
    
}
