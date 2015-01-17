/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Class that listens for user input. 
 * After some key is pressed by user all {@link OnClickListener}s are notified.   
 * @author melkonyan
 */
public class TerminalObserver {
    
    private static final long TIMER_PERIOD = 50;
    
    private static final long TIMER_DELAY = 20;
    
    private OnClickListener mListener;
    
    private Terminal mTerminal;
    
    Timer mTimer; 
    
    /**
     * Create and start new observer. 
     * @param terminal  terminal that should be observed.
     */
    public TerminalObserver(Terminal terminal) {
        mTerminal = terminal;
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(
                new TimerTask() {

                    @Override
                    public void run() {
                        Key input = mTerminal.readInput();
                        System.out.println("Reading input: " + input);
                        
                        if (mListener != null && input != null) {
                            mListener.onClicked(input);
                        }
                    }
                }, 
                TIMER_DELAY, TIMER_PERIOD);
    }
   
    /**
     * Set listener, that will be notified after user clicks some button on keyboard.
     * @param listener 
     */
    public void setOnClickListener(OnClickListener listener) {
        mListener = listener;
    }
    
    /**
     * Stop observer.
     */
    public void stop() {
        mTimer.cancel();
    }
    
    /**
     * Interface that should be implemented in order to be able to receive notifications from {@link TerminalObserver}.
     */
    public static interface OnClickListener {
       
       /**
        * Method that is called after some button is clicked by user.
        * @param key    type of button that was clicked
        */
       public void onClicked(Key key); 
    }
    
    
    
}
