/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinth;

/**
 * Abstract class for representing all monsters that can live in labyrinth.
 * @author melkonyan
 */
public abstract class Monster extends GameObject {
    
    Monster(Position pos) {
        super(pos);
    }
}
