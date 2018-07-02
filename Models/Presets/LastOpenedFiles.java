package Neuroshop.Models.Presets;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class LastOpenedFiles {

    private final File fileLocation = new File("Neuroshop/Ressources/SavedData/LastOpened");
    private ArrayList<File> lastOpened = new ArrayList<>();


    public LastOpenedFiles() {
        readLastOpenedFiles();
    }

    private void readLastOpenedFiles() {
        ArrayList<File> allFiles = new ArrayList<>();
        File[] files = fileLocation.listFiles();
        String fileExtension;
        if (files != null) allFiles.addAll(Arrays.asList(files));
        else return;
        for (File file : allFiles) { //Sucht sich nur "nicht txt oder csv" Dateien raus
            fileExtension = file.getName();
            fileExtension = fileExtension.substring(fileExtension.lastIndexOf(".")+1, fileExtension.length()).toLowerCase();
            if (fileExtension.equals("txt") | fileExtension.equals("csv")) lastOpened.add(file);
        }
    }

    /**
     * Writes the given File into "SavedData"
     * only if the File doesn't exists already.
     */
    private void saveFile(File file) {
        String fileName = file.getName();
        for (File savedFiles : lastOpened) {
            if (fileName.equals(savedFiles.getName())) return;
        }
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter (
                new FileOutputStream(fileLocation+"\\"+fileName), "utf-8"))) {
            writer.write(readFile(file));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the given File
     */
    private void deleteFile(File file) {
        if (file.exists()) file.delete();
    }

    /**
     * Reads and returns the content of the given File
     */
    private String readFile(File file) {
        String line;
        StringBuilder result = new StringBuilder();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                result.append(line);
                result.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public void deleteLastOpened(File file) {
        deleteFile(file);
    }

    public ArrayList<File> getLastOpened() {
        return lastOpened;
    }

    public void setLastOpened(File file) {
        saveFile(file);
    }

    public void updateLastFiles() {
        lastOpened.clear();
        readLastOpenedFiles();
    }
}
