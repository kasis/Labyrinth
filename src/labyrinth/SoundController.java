/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

import maryb.player.Player;
import maryb.player.PlayerEventListener;

/**
 *
 * @author melkonyan
 */
public class SoundController {
    
    private static final String PLAYER_STEP = "sounds/player_step.mp3";
    
    private static final String PLAYER_DEATH = "sounds/player_death.mp3";
    
    private static final String WALL = "sounds/wall.mp3";
    
    private static final String BACKGROUND = "sounds/background.mp3";
    
    private static final String WIN = "sounds/win.mp3";
    
    private static final String KEYS = "sounds/keys.mp3";
    
    private static final String LOSE = "sounds/lose.mp3";
    
    private maryb.player.Player mBackgroundPlayer;
    
    private boolean mSoundOn = true;
    
    public SoundController() {
    }
    
    public void playWin() {
        playSound(WIN, 0.8f);
    }
    
    public void playLose() {
        playSound(LOSE);
    }
    
    public void playDie() {
        playSound(PLAYER_DEATH, 0.8f);
    }
    
    public void playKeys() {
        playSound(KEYS);
    }
    
    public void playWall() {
        playSound(WALL);
    }
    
    public void playBackground() {
        mBackgroundPlayer = playSound(BACKGROUND, 0.5f);
        mBackgroundPlayer.setListener(new PlayerEventListener() {

            @Override
            public void endOfMedia() {
                mBackgroundPlayer.seek(0);
                mBackgroundPlayer.play();
            }

            @Override
            public void stateChanged() {
            }

            @Override
            public void buffer() {
            }
        });
    }
    
    public boolean isOn() {
        return mSoundOn;
    }
    
    public void switchSound() {
        mSoundOn = !mSoundOn;
        if (mSoundOn) {
            mBackgroundPlayer.play();
        } else {
            mBackgroundPlayer.pause();
        }
    }
    
    private Player playSound(String file)  {
        return playSound(file, 1f);
    }
    
    private Player playSound(String file, float volume) {
        return playSound(new Player(), file, volume);
    }
    
    private Player playSound(Player player, String file, float volume) {
        player.setCurrentVolume(volume);
        player.setSourceLocation(file);
        if (mSoundOn) {
            player.play();
        }
        return player;
    }
    
    
    
    
    
}
