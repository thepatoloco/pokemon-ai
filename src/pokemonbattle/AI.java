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
public class AI {
    private static Node tree = new Node();
    private Node actualNode;
    private int id;
    public static int idSetUp = 0;

    public AI() {
        this.id = idSetUp++;
    }

    public AI(Node root) {
        this();
        tree = root;
        this.actualNode = tree;
    }

    public static Node getTree() {
        return tree;
    }

    public static void setTree(Node tree) {
        AI.tree = tree;
    }

    public Node getActualNode() {
        return actualNode;
    }

    public void setActualNode(Node actualNode) {
        this.actualNode = actualNode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AI{" + "tree=" + tree + ", actualNode=" + actualNode 
                + ", id=" + id + '}';
    }
    
    
    /**
     * La inteligencia artificial elige un numero dentro del rango recibido
     * @param min
     * @param max
     * @return el numero elegido
     */
    public int makeDecision(int min, int max){
        int decision = 0;
        //hay un 10% de probabilidad de mutación
        if(Math.random()<0.1f){
            //toma una decisión aleatoria
            decision = PokemonBattle.randomInt(min, max);
        }else{
            ArrayList <Node> bestSons = getBestSons(actualNode);
            if(bestSons == null){
                //toma una decisión aleatoria si no tiene ningun hijo
                decision = PokemonBattle.randomInt(min, max);
            }else{
                if(calculateWins(bestSons.get(0))<0&&
                        !hasAllSons(actualNode,min,max)){
                    //los mejores hijos son los hijos faltantes
                    bestSons = getMissingSons(actualNode, min, max);
                }else if(calculateWins(bestSons.get(0))==0
                        &&!hasAllSons(actualNode,min,max)){
                    //los mejores hijos tambien son los hijos faltantes
                    bestSons.addAll(getMissingSons(actualNode, min, max));
                }
                //elige uno de los mejores hijos de forma aleatoria
                decision = bestSons.get(PokemonBattle.
                        randomInt(0,bestSons.size()-1)).getCaseId();
            }
        }
        setCase(decision, "choice");
        return decision;
    }
    
    /**
     * Recibe un caso y su tipo, y se movera al nodo con el numero del caso, y 
     * si no existe ese nodo lo crea
     * @param caseId 
     */
    public void setCase(int caseId, String type){
        for(int i=0; i<actualNode.sonsQuant();i++){
            if(actualNode.getSon(i).getCaseId()==caseId){
                actualNode = actualNode.getSon(i);
                return;
            }
        }
        actualNode.addSon(new Node(caseId, type));
        setCase(caseId, type);
    }
    
    /**
     * Recibe el resultado final de la batalla actual
     * @param result true si se gano la batalla
     */
    public void setEnding(boolean result){
        this.actualNode.setPlayRate(this.actualNode.getPlayRate()+1);
        if(result){
            this.actualNode.setWinRate(this.actualNode.getWinRate()+1);
        }else{
            this.actualNode.setWinRate(this.actualNode.getWinRate()-1);
        }
    }
    
    /**
     * Regresa los hijos con mayor winRate de un nodo
     * @param node Nodo que se va a revizar
     * @return los mejores hijos del nodo
     */
    private ArrayList<Node> getBestSons(Node node){
        if(node.sonsQuant()<=0){
            return null;
        }else{
            ArrayList<Node> bestSons = new ArrayList();
            bestSons.add(node.getSon(0));
            for(int i=1;i<node.getSons().size();i++){
                if(calculateWins(node.getSon(i))
                        >calculateWins(bestSons.get(0))){
                    bestSons = new ArrayList();
                    bestSons.add(node.getSon(i));
                }else if(calculateWins(node.getSon(i))
                        ==calculateWins(bestSons.get(0))){
                    bestSons.add(node.getSon(i));
                }
            }
            return bestSons;
        }
    }
    
    /**
     * Regresa los hijos que no exisen en un nodo dentro de cierto rango
     * @param node Nodo que se va a revizar
     * @param min número mínimo del rango
     * @param max número máximo del rango
     * @return Los hijos que faltan dentro del rango
     */
    private ArrayList<Node> getMissingSons(Node node,int min,int max){
        ArrayList<Node> missingSons = new ArrayList();
        for(int i=min;i<=max;i++){
            boolean hasThisCase = false;
            for(int j=0;j<node.sonsQuant();j++){
                if(node.getSon(j).getCaseId()==i){
                    hasThisCase = true;
                }
            }
            if(!hasThisCase){
                missingSons.add(new Node(i));
            }
        }
        return missingSons;
    }
    
    /**
     * Pregunta a cierto nodo si tiene todos los nodos dentro de cierto rango
     * @param min número mínimo del rango
     * @param max número máximo
     * @return true si tiene todos los hijos dentro del rango
     */
    private boolean hasAllSons(Node node,int min,int max){
        return node.sonsQuant() >= (max-min+1);
    }
    
    /**
     * Calcula el winrate de cierto nodo
     * @param node nodo del que se cualculara su winrate
     * @return winrate
     */
    public static float calculateWins(Node node){
        if(totalPlayRate(node)!=0){
            return (float)totalWinRate(node)/(float)totalPlayRate(node);
        }else{
            return 0.5f;
        }
    }
    
    /**
     * Obtiene el winrate de cierto nodo tomando en cuenta sus hijos
     * @param node nodo del que se obtendra su winrate
     * @return winrate del nodo y sus hijos
     */
    public static int totalWinRate(Node node){
        int winRate = 0;
        for(int i=0; i<node.sonsQuant(); i++){
            winRate += totalWinRate(node.getSon(i));
        }
        winRate += node.getWinRate();
        return winRate;
    }
    
    /**
     * Obtiene el playrate de cierto nodo tomando en cuenta sus hijos
     * @param node nodo del que se obtendra su playrate
     * @return playrate del nodo y sus hijos
     */
    public static int totalPlayRate(Node node){
        int playRate = 0;
        for(int i=0; i<node.sonsQuant(); i++){
            playRate += totalPlayRate(node.getSon(i));
        }
        playRate += node.getPlayRate();
        return playRate;
    }
}
