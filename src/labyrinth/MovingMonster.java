/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

import com.googlecode.lanterna.terminal.Terminal;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Class that represents a monster that can move inside of labyrinth.
 * @author melkonyan
 */
public class MovingMonster extends Monster implements Movable {

    private MonsterMover mMover;
    
    MovingMonster(Position pos, MovableObserver observer) {
        super(pos);
        mMover = new MonsterMover(observer);
    }
    
    public void start() {
        mMover.start();
    }
    
    public void stop() {
        mMover.stop();
    }
    
    @Override
    public char getChar() {
        return '☣';
    }

    @Override
    public Terminal.Color getColor() {
        return Terminal.Color.RED;
    }

    @Override
    public Position calcNewPosition(Direction dir) {
        return dir.getNewPosition(getPosition());
    }

    @Override
    public void goToPosition(Position pos) {
        setPosition(pos);
    }
    
    @Override 
    public String toString() {
        return "MovingMonster";
    }

    @Override
    public int getType() {
        return GameObject.MOVING_MONSTER;
    }
    
    private class MonsterMover extends Mover {

        private MovableObserver mObserver;
    
        private Direction mCurrDirection;
        
        private int mCurrDirId;
        
        private Random mRandom = new Random();
        
        private long mSpeed = 200;
        
        Timer mTimer;
        
        public MonsterMover(MovableObserver observer) {
            super(MovingMonster.this, observer);
            mCurrDirId = mRandom.nextInt(4);
        }
        
        public void move() {
            int initId = mCurrDirId;
            if (tryMove()) {
                return;
            }
            int turn = mRandom.nextInt(2) > 0 ? 1 : -1;
            mCurrDirId = (mCurrDirId + turn + 4) % 4;
            mCurrDirection = Direction.fromInt(mCurrDirId);
            if (tryMove()) {
                return;
            }
            mCurrDirId = (mCurrDirId - turn + 4) % 4;
            if (tryMove()) {
                return;
            }
            mCurrDirId = (initId + 2) % 4;
            tryMove();
        }
        
        private boolean tryMove() {
            mCurrDirection = Direction.fromInt(mCurrDirId);
            Position from = getMovable().getPosition();
            Position to = getMovable().calcNewPosition(mCurrDirection);
            if (getObserver().canMove(getMovable(), to)) {
                move(mCurrDirection);
                return true;
            }
            return false;
        }
        
        public void start() {
            if (mTimer != null) {
                throw new RuntimeException("Moving monster is already started");
            }
            mTimer = new Timer();
            
            mTimer.scheduleAtFixedRate(
                new TimerTask() {

                    @Override
                    public void run() {
                        move();
                    }
                    
                 }, mSpeed, mSpeed);
        }
        
        public void stop() {
            if (mTimer != null) {
                mTimer.cancel();
                mTimer = null;
                Logger.log("MovingMonster has stopped.");
            }
        }
    }
}
