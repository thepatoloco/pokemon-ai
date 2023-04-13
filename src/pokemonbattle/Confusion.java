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
public class Confusion implements Effectable, Serializable  {
    private String name = "confundido";
    private int turnsLeft = 3;

    public Confusion() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTurnsLeft() {
        return turnsLeft;
    }

    public void setTurnsLeft(int turnsLeft) {
        this.turnsLeft = turnsLeft;
    }

    @Override
    public String toString() {
        return "Confusion{" + "name=" + name + ", turnsLeft=" + turnsLeft + '}';
    }

    
    /**
     * El pokemon o puede golpearse a si mismo, o puede atacar con normalidad
     * @param pokemon 
     */
    public void act(Pokemon pokemon, Player trainer) {
        PokemonBattle.message(pokemon.getName() + " está confundido.");
        //hay 50% de probabilidad de que el pokemon se golpee a si mismo
        if(Math.random()<0.5f){
            PokemonBattle.setLuckCase(0);
            //el pokemon se golpea a si mismo
            int damage = Math.round(pokemon.getHpMax()/5);//se calcula el daño
            PokemonBattle.message(pokemon.getName() + " está tan confundido que"
                    + " se golpeo a si mismo.");
            pokemon.takeDamage(damage);//el pokemon recibe el daño
        }else{
            PokemonBattle.setLuckCase(1);
            //el pokemon si puede atacar
            pokemon.setCanAttack(true);
            trainer.chooseAttack();
            pokemon.setCanAttack(false);
        }
        this.turnsLeft--;
        //si ya no quedan turnos quitar el efecto
        if(this.turnsLeft<=0){
            pokemon.setEffect(null);
            pokemon.setCanAttack(true);
            PokemonBattle.message(pokemon.getName()+" ya no está confundido.");
        }
    }

    /**
     * El pokemon obtiene el efecto y ya no puede atacar
     * @param pokemon 
     */
    public void giveEffect(Pokemon pokemon) {
        //pokemon recibe el efecto, y ya no puede atacar.
        pokemon.setEffect(new Confusion());
        pokemon.setCanAttack(false);
        //mostrar mensaje
        PokemonBattle.message(pokemon.getName() + " ha sido confundido.");
    }
}
