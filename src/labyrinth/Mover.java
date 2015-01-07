/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

/**
 * Class that can move {@link Movable} object in some {@link Direction}.
 * @author melkonyan
 */
public class Mover {
    
    MovableObserver mObserver;
    
    Movable mMovable;
    
    /**
     * Create new mover.
     * @param movable   object that should be moved
     * @param observer  observer that is used to move object 
     */
    public Mover(Movable movable, MovableObserver observer) {
        mMovable = movable;
        mObserver = observer;
    }
    
    /**
     * Move object in the given direction
     * @param dir   direction to move.
     */
    public void move(Direction dir) {
        Position to = mMovable.calcNewPosition(dir);
        Position from = mMovable.getPosition();
        if (mObserver.canMove(mMovable, to)) {
            mMovable.goToPosition(to);
            mObserver.hasMoved(mMovable, from, to);
        }
    }
    
}
