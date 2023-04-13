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
public class Attack implements Serializable {
    private String name;
    private Type type;
    private float power;
    private float accuracy;
    private int pp;
    private int ppMax;
    
    private Effectable effect;
    private float effectProb;

    public Attack() {
    }

    public Attack(String name,Type type,float power,float accuracy,int ppMax){
        if(accuracy>1f||accuracy<0f){
            throw new IndexOutOfBoundsException("La precisión del ataque tiene "
                    + "que ser un flotante entre "
                    + "0 y 1.");
        }
        this.name = name;
        this.type = type;
        this.power = power;
        this.accuracy = accuracy;
        this.ppMax = ppMax;
        this.pp = this.ppMax;
        this.effect = null;
        this.effectProb = 0f;
    }

    public Attack(String name, Type type, float power, float accuracy, 
            int ppMax, Effectable effect, float effectProb) {
        if(accuracy>1f||accuracy<0f||effectProb>1f||effectProb<0f){
            throw new IndexOutOfBoundsException("La precisión del ataque, y la "
                    + "probabilidad del efecto tiene que ser un flotante entre "
                    + "0 y 1.");
        }
        this.name = name;
        this.type = type;
        this.power = power;
        this.accuracy = accuracy;
        this.ppMax = ppMax;
        this.pp = this.ppMax;
        this.effect = effect;
        this.effectProb = effectProb;
    }
    
    public Attack(Attack attack) {
        this.name = attack.name;
        this.type = attack.type;
        this.power = attack.power;
        this.accuracy = attack.accuracy;
        this.ppMax = attack.ppMax;
        this.pp = this.ppMax;
        this.effect = attack.effect;
        this.effectProb = attack.effectProb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getPp() {
        return pp;
    }

    public void setPp(int pp) {
        this.pp = pp;
    }

    public int getPpMax() {
        return ppMax;
    }

    public void setPpMax(int ppMax) {
        this.ppMax = ppMax;
    }

    public Effectable getEffect() {
        return effect;
    }

    public void setEffect(Effectable effect) {
        this.effect = effect;
    }

    public float getEffectProb() {
        return effectProb;
    }

    public void setEffectProb(float effectProb) {
        this.effectProb = effectProb;
    }
    
    @Override
    public String toString() {
        return name + " tipo " + type.getName() + " PP " + pp + "/" + ppMax;
    }
    
    
    /**
     * 
     * @param attacker
     * @param defender 
     */
    public void attack(Pokemon attacker, Pokemon defender){
        if(this.canAttack()){
            PokemonBattle.message(attacker.getName() + " ha usado " + this.name
                    + ".");
            this.pp--;//le resta pp al ataque
            //genera número aleatorio para ver si fallo el ataque
            if(Math.random()>this.accuracy){
                PokemonBattle.message(attacker.getName() + " falló el ataque.");
                PokemonBattle.setLuckCase(0);//se envía el caso de fallo
                return;
            }
            PokemonBattle.setLuckCase(1);//se envía el caso de acertado
            float damage = ((22*this.power* //calcular el daño del ataque
                    (attacker.getAttack()/defender.getDefense()))/50f)+2;
            float multiplier = 1;//calcular el multiplicador de tipos
            for(int i=0;i<defender.getTypes().length;i++){
                multiplier *= type.getMultiplier(defender.getTypes()[i]);
            }
            //mostrar mensaje y atacar
            PokemonBattle.message(this.name + multToString(multiplier)
                    + ", hizo " + Math.round(damage*multiplier) + " de daño.");
            defender.takeDamage(Math.round(damage*multiplier));
            giveEffect(defender);//ve si le da el efecto al pokemon defensor
        }else{
            this.struggle(attacker, defender);
        }
    }
    
    /**
     * 
     * @param defender 
     */
    private void giveEffect(Pokemon defender){
        if(this.effect!=null){
            if(Math.random()<=this.effectProb){
                this.effect.giveEffect(defender);
                PokemonBattle.setLuckCase(1);//se envía el caso de acertado
            }else{
                PokemonBattle.setLuckCase(0);//se envía el caso de fallo
            }
        }
    }
    
    /**
     * 
     * @param multiplier
     * @return 
     */
    private String multToString(float multiplier){
        if(multiplier == 0){
            return " no fue efectivo";
        }else if(multiplier < 1){
            return " fue poco efectivo";
        }else if(multiplier > 1){
            return " fue super efectivo";
        }else{
            return " fue efectivo";
        }
    }
    
    /**
     * 
     * @param attacker
     * @param defender 
     */
    private void struggle(Pokemon attacker, Pokemon defender){
        PokemonBattle.message(attacker.getName() + " no tiene pps suficiente "
                + "para usar " + this.name + ".");
        //calcula el daño que hará el ataque
        float damage = ((22*50*
                (attacker.getAttack()/defender.getDefense()))/50)+2;
        //se distribullen los daños al defensor y al atacante
        defender.takeDamage(Math.round(damage));
        attacker.takeDamage(Math.round(damage/2));
        //se muestra el mensaje
        PokemonBattle.message("Forcejeo hizo "+Math.round(damage)+" de daño.");
        PokemonBattle.message(attacker.getName() + " se hizo " 
                + Math.round(damage/2) + " de daño.");
    }
    
    /**
     * 
     * @return 
     */
    public boolean canAttack(){
        return this.pp>0;
    }
}
