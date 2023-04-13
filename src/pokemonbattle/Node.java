/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonbattle;

import java.util.ArrayList;
import java.io.Serializable;

/**
 *
 * @author patriciocantu
 */
public class Node implements Serializable{
    private ArrayList<Node> sons = new ArrayList();
    
    private int caseId;
    private String type = "";
    private int winRate = 0;
    private int playRate =0;

    public Node() {
    }

    public Node(int caseId) {
        this.caseId = caseId;
    }

    public Node(int caseId, String type) {
        this.caseId = caseId;
        this.type = type;
    }

    public Node(int caseId, ArrayList<Node> sons, String type, int winRate) {
        this.caseId = caseId;
        this.sons = sons;
        this.type = type;
        this.winRate = winRate;
    }
    
    public Node(Node node) {
        this.caseId = node.caseId;
        this.sons = node.sons;
        this.type = node.type;
        this.winRate = node.winRate;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public ArrayList<Node> getSons() {
        return sons;
    }

    public void setSons(ArrayList<Node> sons) {
        this.sons = sons;
    }

    public int getWinRate() {
        return winRate;
    }

    public void setWinRate(int winRate) {
        this.winRate = winRate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPlayRate() {
        return playRate;
    }

    public void setPlayRate(int playRate) {
        this.playRate = playRate;
    }

    @Override
    public String toString() {
        return "Node{" + "caseId=" + caseId + ", sons=" + sons + ", winRate=" 
                + winRate + '}';
    }
    
    
    /**
     * añade un nuevo hijo y los ordena mediante el valor de caseId
     * @param caseId caseId del nuevo hijo
     */
    public void addSon(int caseId){
        this.sons.add(new Node(caseId));
        this.sons = this.selectionSons();
    }
    
    /**
     * añade un nuevo hijo y los ordena mediante el valor de caseId
     * @param son nuevo hijo
     */
    public void addSon(Node son){
        this.sons.add(son);
        this.sons = this.selectionSons();
    }
    
    /**
     * 
     * @param position
     * @return obtiene el hijo mediante la posición
     */
    public Node getSon(int position){
        return this.sons.get(position);
    }
    
    /**
     * Pregunta por la cantidad de hijos del nodo
     * @return cantidad de hijos del nodo
     */
    public int sonsQuant(){
        return sons.size();
    }
    
    /**
     * ordena los hijos de un nodo de menor a mayor con el metodo de selección
     * @param data arraylist
     * @return arraylist ordenado de menor a mayor
     */
    private ArrayList<Node> selectionSons(){
        ArrayList<Node> aux = new ArrayList(this.sons);
        for(int i=0;i<aux.size()-1;i++){
            for(int j=i+1;j<aux.size();j++){
                if(aux.get(i).getCaseId()>aux.get(j).getCaseId()){
                    Node oldI = aux.get(i);
                    aux.set(i, aux.get(j));
                    aux.set(j, oldI);
                }
            }
        }
        return aux;
    }
    
    /**
     * Regresa el largo del arbol desde la raiz hasta la rama mas larga
     * @return 
     */
    public static int getLength(Node node){
        int[] sonsLength = new int[node.sonsQuant()];
        int length = 0;
        //obtiene la longitud de todos los hijos
        for(int i=0; i<node.sonsQuant();i++){
            sonsLength[i] = getLength(node.getSon(i));
        }
        //compara la longitud de todos los hijos
        for(int i=0; i<sonsLength.length; i++){
            length = sonsLength[i]>length?sonsLength[i]:length;
        }
        return length + 1;
    }
    
    /**
     * Regresa el identificador del nodo con un color para diferenciarse
     * @return String con el identificador y su color
     */
    public String getData(){
        String text = "";
        switch(type){
            case "luck":
                text += Colors.ANSI_PURPLE;
                break;
            case "opponent":
                text += Colors.ANSI_RED;
                break;
            case "choice":
                text += Colors.ANSI_BLUE;
                break;
        }
        text += (this.caseId + Colors.ANSI_RESET);
        return text;
    }
    
    /**
     * Regresa la cantidad de nodos total de un arbol
     * @param tree
     * @return cantidad total de nodos
     */
    public static int treeSize(Node tree){
        int size = 1;
        for(int i=0; i<tree.sonsQuant();i++){
            size += treeSize(tree.getSon(i));
        }
        return size;
    }
    
    /**
     * Convierte el arbol en un String
     * @param tree
     * @return arbol en String
     */
    public static String treeToString(Node tree){
        String text = "";
        for(int i=0;i<getLength(tree);i++){
            text+=(treeLevelToString(tree,i,0) + "\n");
        }
        return text;
    }
    
    /**
     * Convierte el nivel de un arbol en String
     * @param tree arbol
     * @param level nivel que se quiere imprimir
     * @param thisLevel nivel actual
     * @return String con los valores del nivel pedido
     */
    private static String treeLevelToString(Node tree,int level,int thisLevel){
        String text = "";
        for(int i=0; i<Math.round((float)tree.sonsQuant()/2);i++){
            text+=treeLevelToString(tree.getSon(i),level,thisLevel+1);
        }
        text+=(level==thisLevel?(tree.getData() + " "):"  ");
        for(int i=Math.round((float)tree.sonsQuant()/2);i<tree.sonsQuant();i++){
            text+=treeLevelToString(tree.getSon(i),level,thisLevel+1);
        }
        return text;
    }
}
