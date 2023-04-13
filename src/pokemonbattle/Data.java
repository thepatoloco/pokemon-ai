/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonbattle;

import java.util.ArrayList;
import java.io.Serializable;

/**
 *
 * @author patriciocantu
 */
public class Data implements Serializable{
    private ArrayList<Pokemon> pokemons;
    private ArrayList<Attack> attacks;
    private ArrayList<Type> types;
    private Graph typeTable;
    private Node ai;

    public Data() {
    }

    public Data(ArrayList<Pokemon> pokemons, ArrayList<Attack> attacks, 
            ArrayList<Type> types, Graph typeTable, Node ai) {
        this.pokemons = pokemons;
        this.attacks = attacks;
        this.types = types;
        this.typeTable = typeTable;
        this.ai = ai;
    }

    public ArrayList<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(ArrayList<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    public ArrayList<Attack> getAttacks() {
        return attacks;
    }

    public void setAttacks(ArrayList<Attack> attacks) {
        this.attacks = attacks;
    }

    public ArrayList<Type> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<Type> types) {
        this.types = types;
    }

    public Graph getTypeTable() {
        return typeTable;
    }

    public void setTypeTable(Graph typeTable) {
        this.typeTable = typeTable;
    }

    public Node getAi() {
        return ai;
    }

    public void setAi(Node ai) {
        this.ai = ai;
    }

    @Override
    public String toString() {
        return "Pokemons: " + pokemons.size() + " Ataques: " + attacks.size() 
                + " IA: " + Node.treeSize(ai) + " nodos";
    }
    
}
