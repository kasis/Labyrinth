/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

import com.googlecode.lanterna.terminal.Terminal;

/**
 * Game object that represent exit of the labyrinth.
 * @author melkonyan
 */
public class Exit extends GameObject {

    Exit(Position pos) {
        super(pos);
    }
    
    @Override
    public char getChar() {
        return '♊';
    }

    @Override
    public Terminal.Color getColor() {
        return Terminal.Color.WHITE;
    }

    @Override
    public int getType() {
        return GameObject.EXIT;
    }
    
}
