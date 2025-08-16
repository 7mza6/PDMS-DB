package Componentes.Cities;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



public class CitiesCRUDS {
     public static ArrayList<CitiesClass> list;
     private static final String FILE_PATH = "./src/Componentes/Cities/City.json";

     public static ArrayList<CitiesClass> Read() {
        if (list == null) {
            list = new ArrayList<>();
        }
        try {
            FileReader read = new FileReader(FILE_PATH);
            Type CityType = new TypeToken<ArrayList<CitiesClass>>() {
            }.getType();
            Gson gson = new Gson();
            list = gson.fromJson(read, CityType);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CitiesCRUDS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
     
     public static String Search(String CityName){
         ArrayList<CitiesClass> CityList = Read();
         for(CitiesClass  cc : CityList){
             System.out.println(cc.getCityName().equals(CityName));
             if(cc.getCityName().equals(CityName)){
                 return cc.getCityName();
             }
         }
         return null;
     }

}














