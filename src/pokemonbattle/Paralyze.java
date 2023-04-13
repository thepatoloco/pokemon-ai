/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonbattle;

import java.io.Serializable;

/**
 *
 * @author patriciocantu
 */
public class Paralyze implements Effectable, Serializable {
    private String name = "paralizado";

    public Paralyze() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Paralyze{" + "name=" + name + '}';
    }

    
    /**
     * El pokemon o puede atacar con normalidad, o puede quedarse quiedo
     * @param pokemon 
     */
    public void act(Pokemon pokemon, Player trainer) {
        PokemonBattle.message(pokemon.getName() + " está paralizado.");
        //hay 25% de probabilidad de que el pokemon no pueda atacar
        if(Math.random()<0.25f){
            PokemonBattle.setLuckCase(0);
            //el pokemon no puede atacar
            PokemonBattle.message(pokemon.getName() + " no pudo atacar porque "
                    + "está paralizado.");
        }else{
            PokemonBattle.setLuckCase(1);
            //el pokemon si puede atacar
            pokemon.setCanAttack(true);
            trainer.chooseAttack();
            pokemon.setCanAttack(false);
        }
    }

    /**
     * Si el pokemon no es tipo eléctrico, recibe el efecto y ya no puede atacar
     * @param pokemon 
     */
    public void giveEffect(Pokemon pokemon) {
        //si el pokemon no es tipo eléctrico se paraliza
        if(!pokemon.hasType(4)){
            //pokemon recibe el efecto, y ya no puede atacar.
            pokemon.setEffect(new Paralyze());
            pokemon.setCanAttack(false);
            //mostrar mensaje
            PokemonBattle.message(pokemon.getName() + " ha sido paralizado.");
        }
    }
    
}
