/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

import com.googlecode.lanterna.terminal.Terminal;

/**
 * Class that represents a road of labyrinth. 
 * Road is a kind of empty space, where everyone can move
 * @author melkonyan
 */
public class Road extends GameObject {

    Road(Position pos) {
        super(pos);
    }
    
    @Override
    public char getChar() {
        return ' ';
    }

    @Override
    public Terminal.Color getColor() {
        return Terminal.Color.BLACK;
    }

    @Override
    public int getType() {
        return GameObject.ROAD;
    }
    
    
    
    
}
