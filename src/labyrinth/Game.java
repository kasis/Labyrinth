/*
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
import java.io.IOException;
import sun.misc.ProxyGenerator;
/**
 * Class, that contains logic of the game.
 * @author melkonyan
 */
public class Game implements MovableObserver, TerminalObserver.OnClickListener {

    
    private Terminal mTerminal;
    
    private GUIScreen mGui;
    
    private Labyrinth mLab; 
    
    private GamePanel mPanel;
    
    private TerminalObserver mTerminalObserver;
    
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
        mLab = new Labyrinth(mTerminal, new Position(20, 10));
        try {
            Generate.main(null);
            mLab.loadLabyrinth();
            createPlayer();
            mLab.drawLabyrinth();
            
        } catch (Exception e) {
            System.out.println("Error occurred while loading labyrinth");
            e.printStackTrace();
        }
        mPanel = new GamePanel(mTerminal);
        mPanel.setLivesNum(2);
        mPanel.draw();
        mTerminalObserver = new TerminalObserver(mTerminal);
        mTerminalObserver.setOnClickListener(this);
    }
    
    
    private void showMenu() {
        ActionListDialog.showActionListDialog(mGui, "Menu", null, 40, true, new Action() {

            @Override
            public void doAction() {
                onNewGame();
            }
            
            @Override 
            public String toString() {
                return "New game";
            }
        }, new Action() {

            @Override
            public void doAction() {
                mGui.getScreen().stopScreen();
                mTerminalObserver.stop();
            }
            
            @Override 
            public String toString() {
                return "Exit";
            }
        });
        mGui.getScreen().clear();
        mGui.getScreen().refresh();
        mPanel.draw();
        mLab.redraw();
    }
    
    private void showAbout() {
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
                            + new MovingMonster(null).getChar() + " - is a Moving Monster. It is like a Monster but it also can move."
        );
        mGui.getScreen().clear();
        mGui.getScreen().refresh();
        mPanel.draw();
        mLab.redraw();
   
    }
    
    public void createPlayer() {
        mLab.setPlayer(new Player(mLab.getEnters().get(0).getPosition(), this));
    }
    
    
    
    @Override
    public boolean canMove(Movable m, Position to) {
        Logger.log("Labyrinth.canMove: Wants to move to position " + to);
        if (mLab.outOfBound(to)) {
            return false;
        }
        GameObject obstacle = mLab.getObject(to);
        return !(obstacle instanceof Wall) 
                && !(obstacle instanceof Exit && !mLab.getPlayer().hasKey())
                && !(obstacle instanceof Enter);
    }

    @Override
    public void hasMoved(Movable m, Position from, Position to) {
        Logger.log("Labyrinth.hasMoved: Has moved to position " + to);
        if (m instanceof Player) {
            GameObject obstacle = mLab.getObject(to);
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
            }
            mLab.movePlayer(from);
            
        }
    }

    @Override
    public void onClicked(Key key) {
        Logger.log("Labyrinth.onClicked: key " + key + " clicked.");
        System.out.println(key.getCharacter());
        if (key.getCharacter() == 'm') {
            showMenu();
        } else if (key.getCharacter() == 'a') {
            showAbout();
        } else {
            try {
                mLab.getPlayer().move(Direction.fromKey(key));
            } catch (RuntimeException e) {}
        }
    }
    
    private void onNewGame() {
        mPanel.reset();
        mPanel.draw();
        mLab.redraw();
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
        mLab.getPlayer().dropKey();
        mLab.redraw();
    }
    
    
}
