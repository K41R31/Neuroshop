package Neuroshop.Models;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class LastOpenedFiles {

    private final File fileLocation = new File("Neuroshop/Ressources/SavedData");
    private ArrayList<File> lastOpened = new ArrayList<>();


    public LastOpenedFiles() {
        readLastOpenedFiles();
    }

    private void readLastOpenedFiles() {
        File[] files = fileLocation.listFiles();
        if (files != null) lastOpened.addAll(Arrays.asList(files));
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
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName), "utf-8"))) {
            writer.write(readFile(file));
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
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                result.append(line);
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
}
