/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

import com.googlecode.lanterna.terminal.Terminal;

/**
 * Class that represents a monster that can not move.
 * @author melkonyan
 */
public class StaticMonster extends Monster {

    StaticMonster(Position pos) {
        super(pos);
    }
    
    @Override
    public char getChar() {
        return '☠';
    }

    @Override
    public Terminal.Color getColor() {
        return Terminal.Color.BLUE;
    }

    @Override
    public int getType() {
        return GameObject.STATIC_MONSTER;
    }
    
    
    
}
