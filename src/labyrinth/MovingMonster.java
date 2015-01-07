/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

import com.googlecode.lanterna.terminal.Terminal;

/**
 * Class that represents a monster that can move inside of labyrinth.
 * @author melkonyan
 */
public class MovingMonster extends Monster implements Movable {

    MovingMonster(Position pos) {
        super(pos);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void goToPosition(Position pos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
