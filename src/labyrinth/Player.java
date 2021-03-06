/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

import com.googlecode.lanterna.terminal.Terminal;

/**
 * Class that represents a player of the labyrinth.
 * Player needs to have a {@link Keys} to exit a labyrinth.
 * When player meets a {@link Monster} he dies and loses his keys.
 * @author melkonyan
 */
public class Player extends GameObject implements Movable {

    private Mover mMover;
    
    private Delay mDelay;
    
    private long WAIT_TIME = 50;
    /**
     * To construct player you need to specify it's position an observer, that looks if player has moved.
     * @param pos       initial position of player.
     * @param observer  class that is called when player wants to move.
     */
    public Player(Position pos, MovableObserver observer) {
        super(pos);
        //Logger.log("Creating player at position " + pos);
        mMover = new Mover(this, observer);
        mDelay = new Delay(WAIT_TIME);
    }
    
    /**
     * Move player in given direction.
     * @param dir   direction to move.
     */
    public void move(Direction dir) {
        if (mDelay.isFree()) {
            //Logger.log("Player.move: Moving into direction " + dir);
            mMover.move(dir);
            mDelay.lock();
        }
    }
    
    @Override
    public char getChar() {
        return '☺';
    }

    @Override
    public Terminal.Color getColor() {
        return Terminal.Color.GREEN;
    }

    @Override
    public Position calcNewPosition(Direction dir) {
        //Logger.log("Player.calcNewPosition: New position for direction " + dir + " is " + dir.getNewPosition(getPosition()));
        return dir.getNewPosition(getPosition());
    }

    @Override
    public void goToPosition(Position pos) {
        //Logger.log("Player.goToPosition: new position - " + pos);
        setPosition(pos);
    }
    
    @Override 
    public String toString() {
        return "Player";
    }

    @Override
    public int getType() {
        return GameObject.PLAYER;
    }
}
