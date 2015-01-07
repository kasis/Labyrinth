/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

import com.googlecode.lanterna.terminal.Terminal;

/**
 * Game object that represents keys. They are used to exit the labyrinth. 
 * Could be picked up by player.
 * @author melkonyan
 */
public class Keys extends GameObject {

    Keys(Position pos) {
        super(pos);
    }
    
    @Override
    public char getChar() {
        return '☦';
    }

    @Override
    public Terminal.Color getColor() {
        return Terminal.Color.YELLOW;
    }
    
}
