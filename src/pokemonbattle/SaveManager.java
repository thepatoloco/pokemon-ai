/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemonbattle;

import java.io.*;

/**
 *
 * @author patriciocantu
 */
public class SaveManager {
    private static int slot = 1;
    private static final String pathName = System.getProperty("java.io.tmpdir");
    private static final String directoryName = "/com.pato.PokemonBattle";
    private static final String fileName = "/DataSlot";
    private static final String extensionName = ".pato";

    public static int getSlot() {
        return slot;
    }

    public static void setSlot(int slot) {
        SaveManager.slot = slot;
    }
    
    /**
     * Guarda los datos del usuario
     * @param data arbol del usuario
     */
    public static void saveData(Data data){
        File directory = new File(pathName+directoryName);
        String fullPath = pathName+directoryName+fileName+slot+extensionName;
        File file = new File(fullPath);
        try{
            if (! directory.exists()){
                directory.mkdir();
            }
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fileOut = new FileOutputStream(fullPath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(data);
            objectOut.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    /**
     * Carga los datos guardados, y si no hay datos guardados regresa null
     * @return los datos guardados
     */
    public static Data loadData(){
        Data data = null;
        String fullPath = pathName+directoryName+fileName+slot+extensionName;
        try{
            if(new File(fullPath).exists()){
                FileInputStream fileIn = new FileInputStream(fullPath);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                data = (Data)objectIn.readObject();
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return data;
    }
}
