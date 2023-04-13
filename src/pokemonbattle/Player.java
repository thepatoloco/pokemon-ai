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
public class Player {
    protected String name;
    protected Pokemon pokemon;
    protected Player opponent;
    
    protected int id;
    public static int idSetUp = 0;

    public Player() {
        this.id = idSetUp++;
    }

    public Player(String name) {
        this();
        this.name = name;
    }

    public Player(String name, Pokemon pokemon) {
        this(name);
        this.pokemon = pokemon;
    }

    public Player(String name, Pokemon pokemon, Player opponent) {
        this(name, pokemon);
        this.opponent = opponent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public Player getOpponent() {
        return opponent;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Player{" + "name=" + name + ", pokemon=" + pokemon + 
                ", id=" + id + '}';
    }
    
    
    /**
     * 
     * @param pokemons 
     */
    public void choosePokemon(ArrayList<Pokemon> pokemons){
        PokemonBattle.message(name+", elige a tu pokemon.");
        for(int i=0;i<pokemons.size();i++){
            System.out.println((i+1) + "." + pokemons.get(i));
        }
        //elige el pokemon, comparte su desición y lo recibe
        int decision = PokemonBattle.getScannerInt(1,pokemons.size());
        this.shareDecision(decision);
        this.pokemon = new Pokemon(pokemons.get(decision-1));
        PokemonBattle.message(name+" ha elegido a "+pokemon.getName()+".");
    }
    
    /**
     * 
     */
    public void chooseAttack(){
        if(this.pokemon.isCanAttack()){
            PokemonBattle.message(name+", ¿Que hará "+pokemon.getName()+"?");
            for(int i=0;i<pokemon.getAttacks().length;i++){
                System.out.println((i+1) + "." + pokemon.getAttacks()[i]);
            }
            //toma su decisión, la comparte y lanza el ataque
            int decision = PokemonBattle.getScannerInt(1,4);
            this.shareDecision(decision);
            pokemon.getAttacks()[decision-1].
                    attack(pokemon,opponent.getPokemon());
        }
    }
    
    /**
     * Si el oponente es una cpu, comparte con el su decisión
     */
    public void shareDecision(int caseId){
        if(this.opponent.getClass() == CPU.class){
            ((CPU)opponent).setCaseId(caseId, "opponent");
        }
    }
}
