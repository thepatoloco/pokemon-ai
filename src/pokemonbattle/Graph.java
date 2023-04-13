/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonbattle;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author patriciocantu
 */
public class Graph implements Serializable {
    private float[][] data;
    private int numNodes;

    public Graph() {
        this.numNodes = 0;
    }
    
    public Graph(int numNodes){
        this.numNodes = numNodes;
        this.data = new float[this.numNodes][this.numNodes];
        for(int i=0; i<this.numNodes; i++){
            for(int j=0; j<this.numNodes; j++){
                this.data[i][j]=-1f;
            }
        }
    }

    public Graph(float[][] data) {
        this.data = data;
        this.numNodes = data.length;
    }

    public float[][] getData() {
        return data;
    }

    public void setData(float[][] data) {
        this.data = data;
    }

    public int getNumNodes() {
        return numNodes;
    }

    public void setNumNodes(int numNodes) {
        this.numNodes = numNodes;
    }

    @Override
    public String toString() {
        String result = "";
        for(int i=0; i<data.length;i++){
            for(int j=0; j<data[i].length;j++){
                result += (data[i][j] + " ");
            }
            result += "\n";
        }
        return result;
    }
    
    /**
     * 
     * @param source
     * @param target
     * @param value 
     */
    public void addEdge(int source, int target, float value){
        if(source>=this.numNodes||source<0){
            throw new ArrayIndexOutOfBoundsException("Valor source no existe: " 
                    + source);
        }else if(target>=this.numNodes||target<0){
            throw new ArrayIndexOutOfBoundsException("Valor target no existe: " 
                    + target);
        }
        this.data[source][target] = value;
    }
    
    /**
     * 
     * @param source
     * @param target
     * @return 
     */
    public float getEdge(int source, int target){
        if(source>=this.numNodes||source<0){
            throw new ArrayIndexOutOfBoundsException("Valor source no existe: " 
                    + source);
        }else if(target>=this.numNodes||target<0){
            throw new ArrayIndexOutOfBoundsException("Valor target no existe: " 
                    + target);
        }
        return this.data[source][target];
    }
    
    /**
     * 
     * @param source
     * @return 
     */
    public ArrayList<Integer> getAdjacent(int source){
        ArrayList<Integer> result = new ArrayList();
        for(int i=0; i<this.numNodes;i++){
            if(this.data[source][i]!=-1){
                result.add(i);
            }
        }
        return result;
    }
    
    /**
     * 
     * @param numNodes 
     */
    public void changeNumNodes(int numNodes){
        this.numNodes = numNodes;
        float[][] aux = this.data;
        this.data = new float[this.numNodes][this.numNodes];
        for(int i=0; i<this.data.length; i++){
            for(int j=0; j<this.data[i].length; j++){
                if(i<aux.length&&j<aux.length){
                    this.data[i][j] = aux[i][j];
                }else{
                    this.data[i][j]=-1f;
                }
            }
        }
    }
}
