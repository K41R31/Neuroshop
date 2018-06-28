package Neuroshop.Models;

import java.io.File;
import java.io.IOException;
import java.util.Observable;

public class Helper extends Observable {

    private String filename;

    public String getFilename() {

        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void saveFile() throws IOException {
        File file = new File("Neuroshop\\Ressources\\SavedData" + File.separator + this.filename);
    }

    public void saveWeights() throws IOException {
        File file = new File("Neuroshop\\Ressources\\SavedData\\Temp" + File.separator + this.)
    }


}
