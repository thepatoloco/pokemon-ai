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
public class Pokemon implements Serializable {
    private String name;
    private Type[] types;
    private Attack[] attacks;
    private Effectable effect;
    private int hp;
    private int hpMax;
    private float attack;
    private float defense;
    
    private int id;
    public static int idSetUp = 0;
    
    private boolean canAttack = true;

    public Pokemon() {
        id = idSetUp++;
    }

    public Pokemon(String name, Type[] types, Attack[] attacks, int hpMax, 
            int attack, int defense) {
        this();
        if(types.length>2||types.length<1||attacks.length!=4){
            throw new IndexOutOfBoundsException("El pokemon solo puede tener de"
                    + " 1 a 2 tipos, y tiene que tener 4 ataques.");
        }
        this.name = name;
        this.types = types;
        this.attacks = attacks;
        this.hpMax = hpMax;
        this.hp = this.hpMax;
        this.attack = attack;
        this.defense = defense;
    }

    public Pokemon(Pokemon pokemon) {
        this.name = pokemon.name;
        this.types = pokemon.types;
        Attack[] attacks = {new Attack(pokemon.attacks[0]),
            new Attack(pokemon.attacks[1]),new Attack(pokemon.attacks[2]),
            new Attack(pokemon.attacks[3])};
        this.attacks = attacks;
        this.hpMax = pokemon.hpMax;
        this.hp = this.hpMax;
        this.attack = pokemon.attack;
        this.defense = pokemon.defense;
        this.id = pokemon.id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type[] getTypes() {
        return types;
    }

    public void setTypes(Type[] types) {
        this.types = types;
    }

    public Attack[] getAttacks() {
        return attacks;
    }

    public void setAttacks(Attack[] attacks) {
        this.attacks = attacks;
    }

    public Effectable getEffect() {
        return effect;
    }

    public void setEffect(Effectable effect) {
        this.effect = effect;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHpMax() {
        return hpMax;
    }

    public void setHpMax(int hpMax) {
        this.hpMax = hpMax;
    }

    public float getAttack() {
        return attack;
    }

    public void setAttack(float attack) {
        this.attack = attack;
    }

    public float getDefense() {
        return defense;
    }

    public void setDefense(float defense) {
        this.defense = defense;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    @Override
    public String toString() {
        String message = name + ": tipo:" + types[0].getName() + 
                (types.length>1?" y " + types[1].getName():"") + " ps:" + hpMax 
                + " ataque:" + (int)attack + " defensa:" 
                + (int)defense + " ataques: ";
        for(int i=0; i<attacks.length;i++){
            message+= (attacks[i].getName() + (i!=attacks.length-1?", ":""));
        }
        return message;
    }
    
    
    /**
     * Regresa el nombre del pokemon junto a una barra de vida
     * @return el nombre del pokemon junto a una barra de vida
     */
    public String statsToString(){
        String stats = name + " ps [";
        int healthPertent = (int)Math.ceil(10f/(float)hpMax*(float)hp);
        for(int i=10;i>0;i--){
            stats += (healthPertent<i?" ":"-");
        }
        stats += "] " + hp + "/" + hpMax;
        return stats;
    }
    
    /**
     * Se le resta la cantidad de daño a la vida del pokemon, y si es menor que 
     * 0 se convierte en 0
     * @param damage daño que recibira el pokemon
     */
    public void takeDamage(int damage){
        hp -= damage;
        if(hp<0){
            hp=0;
        }
    }
    
    /**
     * Si el pokemon tiene un efecto, este hace efecto sobre el
     * @param trainer entrenador del pokemon
     */
    public void effectAct(Player trainer){
        if(this.effect!=null){
            effect.act(this, trainer);
        }
    }
    
    /**
     * Revisa los tipos del pokemon, y los compara con el tipo de entregado
     * @param type tipo que se busca en el pokemon
     * @return true si el pokemon tiene el tipo entregado
     */
    public boolean hasType(int typeId){
        boolean hasType = false;
        for(int i=0; i<this.types.length; i++){
            hasType = typeId==this.types[i].getId()?true:hasType;
        }
        return hasType;
    }
    
    /**
     * Pregunta si el pokemon puede segur luchando
     * @return true si la vida es menor igual a 0 (si no puede seguir luchando)
     */
    public boolean isFainted(){
        return hp<=0;
    }
}
