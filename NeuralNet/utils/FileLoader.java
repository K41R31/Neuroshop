package Neuroshop.NeuralNet.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileLoader {
     public static Double[][] readDataFromFile(String fileURI){
         Double[][] dInput = new Double[0][];

         List<String> lines = null;
         try{
             lines = Files.readAllLines(Paths.get(fileURI), StandardCharsets.UTF_8);
             dInput = new Double[lines.size()][];

             for(int i = 0; i<lines.size(); i++){
                 dInput[i] = converStringArrayToDoubleArray(lines.get(i).split(","));
             }
         } catch (IOException e) {
             e.printStackTrace();
         }

         return dInput;
     }

     private static Double[] converStringArrayToDoubleArray(String[] num){
         if(num != null) {
             Double dInput[] = new Double[num.length];
             for(int i = 0; i <num.length; i++) {
                 dInput[i] = Double.parseDouble(num[i]);
             }
             return dInput;
         }
         return null;
     }
}
