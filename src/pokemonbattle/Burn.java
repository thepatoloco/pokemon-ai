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
public class Burn implements Effectable, Serializable {
    private String name = "quemado";

    public Burn() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Burn{" + "name=" + name + '}';
    }
    
    
    /**
     * El pokemon pierde 1/8 de su vida
     * @param pokemon pokemon que sufre del efecto
     */
    public void act(Pokemon pokemon, Player trainer) {
        PokemonBattle.message(pokemon.getName() + " está quemado.");
        //calcula el daño que causara
        int damage = Math.round(pokemon.getHpMax()/8);
        //muestra mensaje y causa daño
        PokemonBattle.message(pokemon.getName() + " recibió " + damage + 
                " de daño.");
        pokemon.takeDamage(damage);
    }

    /**
     * Si el pokemon no es tipo fuego, recibe el efecto de quemado
     * @param pokemon pokemon que obtiene el efecto
     */
    public void giveEffect(Pokemon pokemon) {
        //si el pokemon no es tipo fuego le da el efecto
        if(!pokemon.hasType(6)){
            pokemon.setEffect(new Burn());
            pokemon.setCanAttack(true);//el pokemon si puede atacar
            PokemonBattle.message(pokemon.getName() + " ha sido quemado.");
        }
    }
}
