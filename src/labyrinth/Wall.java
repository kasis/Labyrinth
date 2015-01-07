/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

import com.googlecode.lanterna.terminal.Terminal;

/**
 * Class that represents a wall of labyrinth. Wall is kind of obstacle where no one can move.
 * @author melkonyan
 */
public class Wall extends GameObject {

    Wall(Position pos) {
        super(pos);
    }
    
    @Override
    public char getChar() {
        return 'X';
    }

    @Override
    public Terminal.Color getColor() {
        return Terminal.Color.WHITE;
    }
    
}
