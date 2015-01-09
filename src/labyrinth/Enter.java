/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

import com.googlecode.lanterna.terminal.Terminal;

/**
 * Class that represents enter of labyrinth.
 * @author melkonyan
 */
public class Enter extends GameObject{

    Enter(Position pos) {
        super(pos);
    }
    
    @Override
    public char getChar() {
        return '♏';
    }

    @Override
    public Terminal.Color getColor() {
        return Terminal.Color.WHITE;
    }

    @Override
    public int getType() {
        return GameObject.ENTER;
    }
    
}
