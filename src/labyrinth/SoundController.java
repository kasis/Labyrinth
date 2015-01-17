/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

import javazoom.jlgui.basicplayer.BasicPlayer;
import maryb.player.Player;
/**
 *
 * @author melkonyan
 */
public class SoundController {
    
    
    private static final String PLAYER_STEP = "sounds/player_step.mp3";
    
    private static final String PLAYER_DEATH = "sounds/player_death.mp3";
    
    private static final String WALL = "sounds/wall.mp3";
    
    private static final String BACKGROUND = "sounds/background1";
    
    private static final String WIN = "sounds/win.mp3";
    
    private static final String KEYS = "sounds/keys.mp3";
    
    private static final String LOSE = "sounds/lose.mp3";
    
    private BasicPlayer mBackground;
            
    private maryb.player.Player mBackgroundPlayer;
    
    private Player mDefaultPlayer = new Player();
    
    public SoundController() {
        mDefaultPlayer.setCurrentVolume(1f);
    }
    
    public void playWin() {
        playSound(WIN);
    }
    
    public void playLose() {
        playSound(LOSE);
    }
    
    public void playDie() {
        playSound(PLAYER_DEATH);
    }
    
    public void playKeys() {
        playSound(KEYS);
    }
    
    public void playWall() {
        playSound(WALL);
    }
    
    public void playBackground() {
        mBackgroundPlayer = new Player();
        mBackgroundPlayer.setCurrentVolume(0.5f);
        mBackgroundPlayer.setSourceLocation(BACKGROUND);
        mBackgroundPlayer.play();
    }
    
    private void playSound(String file)  {
        Player player = new Player();
        player.setCurrentVolume(1f);
        player.setSourceLocation(file);
        player.play();
    }
    
    
    
    
}
