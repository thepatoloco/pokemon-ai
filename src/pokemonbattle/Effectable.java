/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonbattle;

/**
 *
 * @author patriciocantu
 */
public interface Effectable {
    /**
     * Regresa el nombre del efecto
     * @return String con el nombre del efecto
     */
    public String getName();
    
    /**
     * Permite actuar el efecto sobre el pokemon afectado
     * @param pokemon el pokemon afectado
     * @param trainer entrenador del pokemon afectado
     */
    public void act(Pokemon pokemon, Player trainer);
    
    /**
     * Le da el efecto al pokemon seleccionado
     * @param pokemon que recibira el efecto
     */
    public void giveEffect(Pokemon pokemon);
}
