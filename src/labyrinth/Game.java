/*m
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

import generator.Generate;
import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.gui.Action;
import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.gui.dialog.ActionListDialog;
import com.googlecode.lanterna.gui.dialog.MessageBox;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JFileChooser;
/**
 * Class, that contains logic of the game.
 * @author melkonyan
 */
public class Game implements MovableObserver, TerminalObserver.OnClickListener {

    private static final String PROPERTIES_PLAYER_X = "Player_x";
    
    private static final String PROPERTIES_PLAYER_Y = "Player_y";
    
    private Terminal mTerminal;
    
    private GUIScreen mGui;
    
    private Labyrinth mLab; 
    
    private GamePanel mPanel;
    
    private TerminalObserver mTerminalObserver;
    
    private About mAbout = new About();
    
    private OptionsMenu mOptionsMenu = new OptionsMenu();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        /*Terminal terminal = TerminalFacade.createSwingTerminal();
        terminal.putCharacter('a');
        terminal.moveCursor(1,1);
        System.out.println("ok");
        terminal.enterPrivateMode();
        */
        Game lab = new Game();
    }

    public Game() {
        
        System.out.println(new File(".").getAbsolutePath());
        GUIScreen gui = TerminalFacade.createGUIScreen();
        mGui = gui;
        Screen screen = gui.getScreen();
        
        mTerminal = screen.getTerminal();
        screen.startScreen();
        mLab = new Labyrinth(mTerminal, new Position(1, 0), this);
        mPanel = new GamePanel(mTerminal);
        mPanel.setLivesNum(2);
        onNewGame();
        mTerminalObserver = new TerminalObserver(mTerminal);
        mTerminalObserver.setOnClickListener(this);
    }
    
    public void createPlayer() {
        mLab.setPlayer(new Player(mLab.getEnters().get(0).getPosition(), this));
    }
    
    
    
    @Override
    public boolean canMove(Movable m, Position to) {
        //Logger.log("Labyrinth.canMove: Wants to move to position " + to);
        if (mLab.outOfBound(to)) {
            return false;
        }
        GameObject obstacle = mLab.getObject(to);
        if (m instanceof Player) {
            return !(obstacle instanceof Wall) 
                    && !(obstacle instanceof Exit && !mLab.getPlayer().hasKey())
                        && !(obstacle instanceof Enter);
        } else if (m instanceof MovingMonster) {
            return obstacle instanceof Road;
        } else {
            //Who is moving then?
            return false;
        }
    }
        

    @Override
    public void hasMoved(Movable m, Position from, Position to) {
        //Logger.log("Labyrinth.hasMoved: " + m + " moved to position " + to);
        GameObject obstacle = mLab.getObject(to);
        if (m instanceof Player) {
            if (obstacle instanceof Keys) {
                mLab.deleteObject(to);
                mLab.getPlayer().pickUpKey();
                mPanel.addKey();
                mPanel.draw();
            } else if (obstacle instanceof Monster) {
                onDie();
                return;
            } else if (obstacle instanceof Exit) {
                onWin();
                return;
            }
            mLab.movePlayer(from);
        } else if (m instanceof MovingMonster) {
            if (mLab.getPlayer().getPosition().equals(to)) {
                onDie();
            }
            mLab.moveMonster((MovingMonster) m, from);
        
        }
    }

    @Override
    public void onClicked(Key key) {
        //Logger.log("Labyrinth.onClicked: key " + key + " clicked.");
        System.out.println(key.getCharacter());
        if (key.getCharacter() == 'm') {
            mOptionsMenu.show();
        } else if (key.getCharacter() == 'a') {
            mAbout.show();
        } else {
            try {
                mLab.getPlayer().move(Direction.fromKey(key));
            } catch (RuntimeException e) {}
        }
    }
    
    public void load() {
        JFileChooser fc = new JFileChooser(new File("."));
        int choice = fc.showOpenDialog(null);
        if (choice == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fc.getSelectedFile();
                Properties prop = new Properties();
                prop.load(new FileInputStream(file));
                Logger.log("Game.load: File loaded.");
                load(prop);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    public void load(Properties prop) throws Exception {
        Player player;
        player = new Player(new Position(
                Integer.parseInt(prop.getProperty(PROPERTIES_PLAYER_X)),
                Integer.parseInt(prop.getProperty(PROPERTIES_PLAYER_Y))
        ), this);
        mLab.setPlayer(player);
        mLab.setData(prop);
        mPanel.load(prop);
        mLab.redraw();
    }
    
    public void save() {
        JFileChooser fc = new JFileChooser(new File("."));
        int choice = fc.showSaveDialog(null);
        if (choice == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fc.getSelectedFile();
                Properties prop = new Properties();
                Logger.log(file.getAbsolutePath());
                save(prop);
                prop.store(new FileOutputStream(file.getAbsolutePath()), null);
                
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    
    
    public void save(Properties prop) {
        prop.setProperty(PROPERTIES_PLAYER_X, ""+mLab.getPlayer().getPosition().getX());
        prop.setProperty(PROPERTIES_PLAYER_Y, ""+mLab.getPlayer().getPosition().getY());
        mPanel.save(prop);
        mLab.save(prop);
    }
    
    private void onNewLevel() {
        try {
            mLab.createLevel();
            createPlayer();
            mLab.draw();
            mLab.start();
        } catch (Exception e) {
            System.out.println("Error occurred while loading labyrinth");
            e.printStackTrace();
        }
    }
    
    private void onNewGame() {
        mPanel.reset();
        mPanel.draw();
        onNewLevel();
    }
    
    private void onDie() {
        mLab.getPlayer().dropKey();
        if (mPanel.hasMoreLives()) {
            mPanel.deleteLife();
            mPanel.deleteKey();
        } else {
            onNewGame();
        }
        mPanel.draw();
        mLab.redraw();
        
        
    }
    
    private void onWin() {
        mPanel.nextLevel();
        mPanel.deleteKey();
        mPanel.draw();
        mLab.stop();
        onNewLevel();
    }
    
    private abstract class GameMenu{
        private boolean mCanceled;
        
        public void show() {
            mLab.stop();
            mCanceled = true;
            showWindow();
            mGui.getScreen().clear();
            mGui.getScreen().refresh();
            mPanel.draw();
            mLab.refresh();
            if (mCanceled) {
                mLab.start();
            }
            
        }
        
        protected void setCanceled(boolean canceled) {
            mCanceled = canceled;
        }
        protected abstract void showWindow();
    }
    
    private class About extends GameMenu {
        @Override 
        protected void showWindow() {
            MessageBox.showMessageBox(mGui, "About", 
                    "Welcome to Labyrinth, dear Player!\n"
                            + "Your goal is to find an exit of the Labyrinth and to not be killed by monsters \nthat are living here.\n"
                            + "In order to exit the Labyrinth you also need to collect a Key at first.\n"
                            + "Here are the types of element and creatures in the Labyrinth:\n"
                            + new Player(null, null).getChar() + " - is You who is smiling yet...\n"
                            + new Wall(null).getChar() + " - is a Wall. No one can go throung it.\n"
                            + new Enter(null).getChar() + " - is an Enter. This is a start of your journey.\n"
                            + new Exit(null).getChar() + " - is an Exit. This is a goal You want to reach.\n"
                            + new Keys(null).getChar() + " - is a Key. You need to find it to exit the Labyrinth.\n"
                            + new StaticMonster(null).getChar() + " - is a Monster. All it wants is to kill you.\n"
                            + new MovingMonster(null, null).getChar() + " - is a Moving Monster. It is like a Monster but it also can move."
                );
        }       
    }
    
    private class OptionsMenu extends GameMenu {
        @Override
        protected void showWindow() {
            ActionListDialog.showActionListDialog(mGui, "Menu", null, 40, true, 
                new Action() {

                    @Override
                    public void doAction() {
                        onNewGame();
                        setCanceled(false);
                    }

                    @Override 
                    public String toString() {
                        return "New game";
                    }
                    
                }, 
                new Action() {

                    @Override
                    public void doAction() {
                        load();
                    }
                    
                    @Override 
                    public String toString() {
                        return "Load game";
                    } 
                
                }, 
                new Action() {

                    @Override
                    public void doAction() {
                        save();
                    }
                    
                    @Override 
                    public String toString() {
                        return "Save game";
                    } 
                
                }, 
                
                new Action() {

                    @Override
                    public void doAction() {
                        mGui.getScreen().stopScreen();
                        mTerminalObserver.stop();
                        setCanceled(false);
                    }

                    @Override 
                    public String toString() {
                        return "Exit";
                }
            });
        }
    }
}
    