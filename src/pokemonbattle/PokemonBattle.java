/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonbattle;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author patriciocantu
 */
public class PokemonBattle {
    private static Scanner scan = new Scanner(System.in);
    
    private static ArrayList<Type> types = new ArrayList();
    private static ArrayList<Attack> attacks = new ArrayList();
    private static ArrayList<Pokemon> pokemons = new ArrayList();
    private static Node ai = new Node();
    
    private static Player[] players = new Player[2];
    
    private static boolean training = false;
    
    /**
     * Crea todos los tipos, ataques y pokemones que se usaran
     */
    private static void setUpGame(){
        setUpTypes();
        setUpAttacks();
        setUpPokemons();
    }
    
    /**
     * Crea los tipos de pokemon que existe, y crea la tabla de tipos
     */
    private static void setUpTypes(){
        String[] typesNames = {"Acero","Agua","Bicho","Dragón","Electri",
            "Fantasm","Fuego","Hada","Hielo","Lucha","Normal","Planta",
            "Psíquic","Roca","Siniest","Tierra","Veneno","Volador"};
        for(int i=0; i<typesNames.length;i++){
            types.add(new Type(typesNames[i]));
        }
        float[][] typeTable = {{0.5f,0.5f,1,1,0.5f,1,0.5f,2,2,1,1,1,1,2,1,1,1,1}
            ,{1,0.5f,1,0.5f,1,1,2,1,1,1,1,0.5f,1,2,1,2,1,1}
            ,{0.5f,1,1,1,1,0.5f,0.5f,0.5f,1,0.5f,1,2,2,1,2,1,0.5f,0.5f}
            ,{0.5f,1,1,2,1,1,1,-1,1,1,1,1,1,1,1,1,1,1}
            ,{1,2,1,0.5f,0.5f,1,1,1,1,1,1,0.5f,1,1,1,-1,1,2}
            ,{1,1,1,1,1,2,1,1,1,1,-1,1,2,1,0.5f,1,1,1,1}
            ,{2,0.5f,2,0.5f,1,1,0.5f,1,2,1,1,2,1,0.5f,1,1,1,1}
            ,{0.5f,1,1,2,1,1,0.5f,1,1,2,1,1,1,1,2,1,0.5f,1}
            ,{0.5f,0.5f,1,2,1,1,0.5f,1,0.5f,1,1,2,1,1,1,2,1,2}
            ,{2,1,0.5f,1,1,-1,1,0.5f,2,1,2,1,0.5f,2,2,1,0.5f,0.5f}
            ,{0.5f,1,1,1,1,-1,1,1,1,1,1,1,1,0.5f,1,1,1,1}
            ,{0.5f,2,0.5f,0.5f,1,1,0.5f,1,1,1,1,0.5f,1,2,1,2,0.5f,0.5f}
            ,{0.5f,1,1,1,1,1,1,1,1,2,1,1,0.5f,1,-1,1,2,1}
            ,{0.5f,1,2,1,1,1,2,1,2,0.5f,1,1,1,1,1,0.5f,1,2}
            ,{1,1,1,1,1,2,1,0.5f,1,0.5f,1,1,2,1,0.5f,1,1,1}
            ,{2,1,0.5f,1,2,1,2,1,1,1,1,0.5f,1,2,1,1,2,-1}
            ,{-1,1,1,1,1,0.5f,1,2,1,1,1,2,1,0.5f,1,0.5f,0.5f,1}
            ,{0.5f,1,2,1,0.5f,1,1,1,1,2,1,2,1,0.5f,1,1,1,1}};
        Type.setTypeGraph(typeTable);
    }
    
    /**
     * Crea todos los ataques que tendran los pokemons del juego
     */
    private static void setUpAttacks(){
        //ataques de Raichu
        attacks.add(new Attack("Ataque rápido",types.get(10),40,1f,30));
        attacks.add(new Attack("Chispa",types.get(4),65,1f,20,
                new Paralyze(),0.3f));
        attacks.add(new Attack("Atizar",types.get(10),80,0.75f,20));
        attacks.add(new Attack("Trueno",types.get(4),110,0.70f,10,
                new Paralyze(),0.3f));
        //ataques de Charizard
        attacks.add(new Attack("Ataque ala",types.get(17),60,1f,35));
        attacks.add(new Attack("Garra dragon",types.get(3),80,1f,15));
        attacks.add(new Attack("Ascuas",types.get(6),40,1f,25,new Burn(),0.1f));
        attacks.add(new Attack("Infierno",types.get(6),100,0.5f,5,
                new Burn(),1f));
        //ataques de Blastoise
        attacks.add(new Attack("Mordisco",types.get(14),60,1f,25));
        attacks.add(new Attack("Pulso de agua",types.get(1),60,1f,20,
                new Confusion(),0.2f));
        attacks.add(new Attack("Cabezazo",types.get(10),130,1f,10));
        attacks.add(new Attack("Hidrobomba",types.get(1),110,0.8f,5));
        //ataques de Venusaur
        attacks.add(new Attack("Látigo cepa",types.get(11),45,1f,25));
        attacks.add(new Attack("Doble filo",types.get(10),120,1f,15));
        attacks.add(new Attack("Tormenta floral",types.get(11),90,1f,15));
        attacks.add(new Attack("Bomba lodo",types.get(16),90,1f,10,
                new Poison(),0.3f));
    }
    
    /**
     * Crea todos los pokemons del juego
     */
    private static void setUpPokemons(){
        //Raichu
        Type[] raichuTypes = {types.get(4)};
        Attack[] raichuAttacks = {new Attack(attacks.get(0)),
            new Attack(attacks.get(1)),new Attack(attacks.get(2)),
            new Attack(attacks.get(3))};
        pokemons.add(new Pokemon("Raichu",raichuTypes,raichuAttacks,
                135,110,88));
        //Charizard
        Type[] charizardTypes = {types.get(6),types.get(17)};
        Attack[] charizardAttacks = {new Attack(attacks.get(4)),
            new Attack(attacks.get(5)),new Attack(attacks.get(6)),
            new Attack(attacks.get(7))};
        pokemons.add(new Pokemon("Charizard",charizardTypes,charizardAttacks,
                153,116,101));
        //Blastoise
        Type[] blastoiseTypes = {types.get(1)};
        Attack[] blastoiseAttacks = {new Attack(attacks.get(8)),
            new Attack(attacks.get(9)),new Attack(attacks.get(10)),
            new Attack(attacks.get(11))};
        pokemons.add(new Pokemon("Blastoise",blastoiseTypes,blastoiseAttacks,
                154,84,103));
        //Venusaur
        Type[] venusaurTypes = {types.get(11),types.get(16)};
        Attack[] venusaurAttacks = {new Attack(attacks.get(12)),
            new Attack(attacks.get(13)),new Attack(attacks.get(14)),
            new Attack(attacks.get(15))};
        pokemons.add(new Pokemon("Venusaur",venusaurTypes,venusaurAttacks,
                155,111,112));
        
    }
    
    /**
     * Muestra la tabla de efectividad entre los distintos tipos de pokemon
     */
    private static void printTableTypes(){
        for(int i=0; i<=Type.getNumTypes();i++){
            for(int j=0; j<=Type.getNumTypes();j++){
                if(i==0&&j==0){
                    System.out.print("\t");
                }else if(i==0){
                    System.out.print(types.get(j-1).getName()+"\t");
                }else if(j==0){
                    System.out.print(types.get(i-1).getName()+"\t");
                }else{
                    System.out.print(Type.getMultiplier(i-1,j-1)+"\t");
                }
            }
            System.out.print("\n");
        }
    }
    
    /**
     * Le pregunta al usuario que espacio de guardado usará, y carga los datos 
     * del espacio elegido
     */
    private static void loadData(){
        message("¿Qué espacio de guardado usará?");
        for(int i=1;i<=3;i++){
            SaveManager.setSlot(i);
            System.out.println(i + ".-" + SaveManager.loadData());
        }
        SaveManager.setSlot(getScannerInt(1,3));
        Data data = SaveManager.loadData();
        if(data!=null){
            // si el espacio no esta vacio cargar los datos
            types = data.getTypes();
            Type.setTypeTable(data.getTypeTable());
            Type.idSetUp = types.size();
            attacks = data.getAttacks();
            pokemons = data.getPokemons();
            Pokemon.idSetUp = pokemons.size();
            ai = data.getAi();
        }else{
            //si el espacio esta vacio generar los datos base
            setUpGame();
        }
    }
    
    /**
     * Guarda los datos actuales del juego
     */
    private static void saveData(){
        SaveManager.saveData(new Data(pokemons,attacks,types,Type.getTypeTable()
                ,ai));
    }
    
    
    /**
     * Obtiene del scanner un entero que se encuentre en el rango dado
     * @param min número mínimo que puede recibir
     * @param max número máximo que puede recibir
     * @return entero entregado por el usuario dentro del rango
     */
    public static int getScannerInt(int min, int max){
        boolean canContinue = false;
        String answer = "";
        int answerInt = 0;
        do{
            answer = scan.nextLine();
            try{
                answerInt = Integer.parseInt(answer);
                if(answerInt < min || answerInt > max){
                    throw new IllegalArgumentException("El numero tiene que "
                            + "estar entre " + min + " y " + max +".");
                }
                canContinue = true;
            }catch(Exception e){
                System.out.println(e);
            }
        }while(!canContinue);
        return answerInt;
    }
    
    /**
     * Obtiene la respuesta del usuario de una pregunta de si o no
     * @param question pregunta que respondera el usuario
     * @return true si la respuesta fue si
     */
    private static boolean yesNoQuestion(String question){
        String answer = "";
        do{
            System.out.println(question + "(si/no)");
            try{
                answer = scan.nextLine();
                if(!answer.equals("si")&&!answer.equals("no")){
                    throw new IllegalArgumentException("La respuesta tiene que "
                            + "ser si o no");
                }
            }catch(Exception e){
                System.out.println(e);
            }
        }while(!answer.equals("si")&&!answer.equals("no"));
        return answer.equals("si");
    }
    
    /**
     * Genera un entero aleatorio entre un rango de dos números
     * @param min número mínimo
     * @param max número máximo
     * @return el número aleatorio dentro del rango
     */
    public static int randomInt(int min, int max){
        return (int)Math.floor(Math.random()*(max-min+1)+min);
    }
    
    /**
     * Imprime el mensaje letra por letra, y se espera 200 milisegundos despues 
     * de imprimir el mensaje (si es que no esta en modo entrenamiento)
     * @param message mensaje que se va a imprimir
     */
    public static void message(String message){
        //si esta en modo entrenamiento imprimir el mensaje inmediatamente
        if(training){
            System.out.println(message);
            return;
        }
        //imprime el mensaje letra por letra con pausas
        for(int i=0; i<message.length();i++){
            System.out.print(message.charAt(i));
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        System.out.println();
        //pasua al terminar de imprimir el mensaje
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
    
    
    /**
     * Se muestran las opciones de modos de juego, y el jugador elige que modo 
     * de juego quiere jugar
     * @return 
     */
    private static int chooseGameMode(){
        message("¿Que modo de juego querras jugar?");
        System.out.println("1.CPU vs CPU\n2.Jugador vs CPU\n"
                + "3.Jugador vs Jugador");
        return getScannerInt(1,3);
    }
    
    /**
     * Inicializa los jugadores segun el modo de juego
     * @param gameMode modo de juego
     * @return los jugadores listos para la batalla
     */
    private static Player[] startGame(int gameMode){
        Player[] players = new Player[2];
        //se detecta el caso cpu vs cpu, jugador vs cpu o jugador vs jugador
        switch(gameMode){
            case 1:
                training();
                return null;
            case 2:
                players[0] = createPlayer();
                players[1] = new CPU("PokeBot", new AI(ai));
                break;
            case 3:
                players[0] = createPlayer();
                players[1] = createPlayer();
                break;
        }
        players = setUpPlayers(players);
        //Se muestra el orden de los jugadores
        message(players[0].getName() + " es el jugador 1 y " 
                + players[1].getName() + " es el jugador 2.");
        //los jugadores eligen pokemon en el nuevo orden
        players[0].choosePokemon(pokemons);
        players[1].choosePokemon(pokemons);
        return players;
    }
    
    /**
     * Realiza las conexiones de los jugadores, y los ordena de forma aleatoria
     * @param players
     * @return 
     */
    private static Player[] setUpPlayers(Player[] players){
        //se conectan los jugadores entre si como oponentes
        players[0].setOpponent(players[1]);
        players[1].setOpponent(players[0]);
        //50% de probabilidad de que se invierta el orden de los jugadores
        if(Math.random()<0.5f){
            Player[] newPlayers = {players[1],players[0]};
            players = newPlayers;
        }
        //se le entrega el orden a los jugadores si es que son cpu
        setLuckCase(players[0],1);
        setLuckCase(players[1],2);
        return players;
    }
    
    /**
     * Se crea un jugador preguntandole su nombre
     * @return 
     */
    private static Player createPlayer(){
        System.out.println("Escriba el nombre del jugador:");
        Player player = new Player(scan.nextLine());
        return player;
    }
    
    /**
     * Se realizan todas las acciones necesarias en una ronda de juego
     * @param players 
     */
    private static void round(Player[] players){
        if(players.length!=2){
            throw new IndexOutOfBoundsException("Solo puede haber 2 jugadores");
        }
        for (Player player : players) {
            System.out.println(players[0].getPokemon().statsToString());
            System.out.println(players[1].getPokemon().statsToString());
            player.chooseAttack();
            //si algun pokemon fue derrotado finalizar batalla
            if(players[0].getPokemon().isFainted()||
                    players[1].getPokemon().isFainted()){
                return;
            }
            player.getPokemon().effectAct(player);
            //si algun pokemon fue derrotado finalizar batalla
            if(players[0].getPokemon().isFainted()||
                    players[1].getPokemon().isFainted()){
                return;
            }
        }
    }
    
    /**
     * Hay una cantidad x de batallas de cpu contra cpu para que la inteligencia
     * artificial aprenda de forma mas rápida
     */
    private static void training(){
        message("¿Cuantas rondas de entrenamiento habrá?(1-100000)");
        int rounds = getScannerInt(1,100000);
        training = true;
        for(int i=0;i<rounds;i++){
            System.out.println("Ronda " + (i+1));
            players[0] = new CPU("RoboTrainer", new AI(ai));
            players[1] = new CPU("PokeBot", new AI(ai));
            players = setUpPlayers(players);
            //Se muestra el orden de los jugadores
            message(players[0].getName() + " es el jugador 1 y " 
                    + players[1].getName() + " es el jugador 2.");
            //los jugadores eligen pokemon en el nuevo orden
            players[0].choosePokemon(pokemons);
            players[1].choosePokemon(pokemons);
            while(!players[0].getPokemon().isFainted()&&
                    !players[1].getPokemon().isFainted()){
                round(players);
            }
            //Imprimir estados finales del juego
            System.out.println(players[0].getPokemon().statsToString());
            System.out.println(players[1].getPokemon().statsToString());
            setWinner(players);
        }
        training = false;
    }
    
    /**
     * Si alguno de los jugadores es un cpu, se le entrega el resultado de un 
     * evento de suerte
     * @param caseId numero de caso
     */
    public static void setLuckCase(int caseId){
        for (Player player : players) {
            if (player.getClass() == CPU.class) {
                ((CPU)player).setCaseId(caseId, "luck");
            }
        }
    }
    
    /**
     * Si el jugador es un cpu, se le entrega el resultado de un evento de 
     * suerte
     * @param player jugador que recibira el resultado
     * @param caseId numero de caso
     */
    public static void setLuckCase(Player player, int caseId){
        if (player.getClass() == CPU.class) {
            ((CPU)player).setCaseId(caseId, "luck");
        }
    }
    
    /**
     * Detectar al ganador, imprimir mensaje de victoria, y entregar a la ia 
     * el resultado
     * @param players 
     */
    private static void setWinner(Player[] players){
        if(!players[0].getPokemon().isFainted()){
            message("Felicidades " + players[0].getName() + "! Has Ganado!");
            //Si son cpu, le envia el resultado del juego
            if(players[0].getClass() == CPU.class){
                ((CPU)players[0]).getAi().setEnding(true);
            }
            if(players[1].getClass() == CPU.class){
                ((CPU)players[1]).getAi().setEnding(false);
            }
        }else{
            message("Felicidades " + players[1].getName() + "! Has Ganado!");
            //Si son cpu, le envia el resultado del juego
            if(players[0].getClass() == CPU.class){
                ((CPU)players[0]).getAi().setEnding(false);
            }
            if(players[1].getClass() == CPU.class){
                ((CPU)players[1]).getAi().setEnding(true);
            }
        }
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        loadData();
        do{
            players = startGame(chooseGameMode());
            if(players == null){
                players = new Player[2];
                continue;
            }
            while(!players[0].getPokemon().isFainted()&&
                    !players[1].getPokemon().isFainted()){
                round(players);
            }
            //Imprimir estados finales del juego
            System.out.println(players[0].getPokemon().statsToString());
            System.out.println(players[1].getPokemon().statsToString());
            setWinner(players);
            //System.out.println(Node.treeToString(ai));
        }while(yesNoQuestion("¿Desea volver a jugar?"));
        saveData();
    }
    
}
