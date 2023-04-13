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
public class Type implements Serializable {
    private String name;
    private static Graph typeTable = new Graph(0);
    private int id;
    public static int idSetUp = 0;

    public Type() {
        this.id = idSetUp;
        this.idSetUp++;
        typeTable.changeNumNodes(idSetUp);
    }

    public Type(String name) {
        this();
        if(name.length()>7){
            throw new IndexOutOfBoundsException("El nombre \"" + name + "\" es "
                    + "demasiado largo.");
        }else{
            this.name = name;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Graph getTypeTable() {
        return typeTable;
    }

    public static void setTypeTable(Graph typeTable) {
        Type.typeTable = typeTable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Type{" + "name=" + name + ", id=" + id + '}';
    }
    
    
    /**
     * Coloca las fortalezas del tipo
     * @param strengths 
     */
    public void setStrengths(float[] strengths){
        if(strengths.length!=typeTable.getNumNodes()){
            throw new IllegalArgumentException("La cantidad de datos entregados"
                    + " (" + strengths.length + ") tenía que ser: "
                    + typeTable.getNumNodes());
        }else{
            for(int i=0; i<strengths.length;i++){
                typeTable.addEdge(this.id, i, strengths[i]);
            }
        }
    }
    
    /**
     * Coloca las debilidades del tipo
     * @param weaknesses 
     */
    public void setWeaknesses(float[] weaknesses){
        if(weaknesses.length!=typeTable.getNumNodes()){
            throw new IllegalArgumentException("La cantidad de datos entregados"
                    + " (" + weaknesses.length + ") tenía que ser: "
                    + typeTable.getNumNodes());
        }else{
            for(int i=0; i<weaknesses.length;i++){
                typeTable.addEdge(i, this.id, weaknesses[i]);
            }
        }
    }
    
    /**
     * Regresa el multiplicador del tipo contra otro tipo con el id 
     * @param defender tipo que recibe el ataque
     * @return multiplicador
     */
    public float getMultiplier(int defender){
        return typeTable.getEdge(this.id, defender)==-1?0:
                typeTable.getEdge(this.id, defender);
    }
    
    /**
     * Regresa el multiplicador del tipo contra otro tipo
     * @param defender tipo que recibe el ataque
     * @return multiplicador
     */
    public float getMultiplier(Type defender){
        return this.getMultiplier(defender.getId());
    }
    
    /**
     * Cambia el grafo de la tabla de tipos
     * @param data 
     */
    public static void setTypeGraph(float[][] data){
        if(data.length!=typeTable.getNumNodes()||
                data[0].length!=typeTable.getNumNodes()){
            throw new IndexOutOfBoundsException("La nueva tabla de tipos no "
                    + "tiene un tamaño compatible.");
        }else{
            typeTable.setData(data);
        }
    }
    
    /**
     * Obtiene el multiplicador con un tipo atacante y un tipo defensor
     * @param attacker tipo atacante
     * @param defender tipo defensor
     * @return multiplicador
     */
    public static float getMultiplier(int attacker, int defender){
        return typeTable.getEdge(attacker, defender)==-1?0:
                (float)typeTable.getEdge(attacker, defender);
    }
    
    /**
     * Obtiene la cantidad de tipos
     * @return cantidad de tipos
     */
    public static int getNumTypes(){
        return idSetUp;
    }
}
