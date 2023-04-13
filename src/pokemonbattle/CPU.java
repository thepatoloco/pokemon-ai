/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonbattle;

import java.util.ArrayList;

/**
 *
 * @author patriciocantu
 */
public class CPU extends Player {
    private AI ai;
    
    public CPU() {
        this.id = idSetUp++;
    }

    public CPU(String name) {
        super(name);
    }

    public CPU(AI ai) {
        this.ai = ai;
    }
    
    public CPU(String name, AI ai) {
        this(name);
        this.ai = ai;
    }

    public AI getAi() {
        return ai;
    }

    public void setAi(AI ai) {
        this.ai = ai;
    }

    @Override
    public String toString() {
        return "CPU{" + "ai=" + ai + '}';
    }
    
    
    /**
     * 
     * @param pokemons 
     */
    @Override
    public void choosePokemon(ArrayList<Pokemon> pokemons){
        //elige el pokemon, comparte su desición y lo recibe
        int decision = this.ai.makeDecision(1,pokemons.size());
        this.shareDecision(decision);
        this.pokemon = new Pokemon(pokemons.get(decision-1));
        PokemonBattle.message(name+" ha elegido a "+pokemon.getName()+".");
    }
    
    /**
     * 
     */
    @Override
    public void chooseAttack(){
        if(this.pokemon.isCanAttack()){
            PokemonBattle.message(name+" esta tomando su decisión.");
            //toma su decisión, la comparte y lanza el ataque
            int decision = this.ai.makeDecision(1,4);
            this.shareDecision(decision);
            pokemon.getAttacks()[decision-1].
                    attack(pokemon,opponent.getPokemon());
        }
    }
    
    public void setCaseId(int caseId, String type){
        this.ai.setCase(caseId, type);
    }
}
