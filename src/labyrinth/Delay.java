/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

/**
 *
 * @author melkonyan
 */
public class Delay {
    
    private final long mPeriod;
    
    private long mTimeLocked;
    
    public Delay(long period) {
        mPeriod = period;
    }
    
    public void lock() {
        mTimeLocked = System.currentTimeMillis();
    }
    
    public boolean isFree() {
        return System.currentTimeMillis() - mTimeLocked > mPeriod;
    }
    
}
