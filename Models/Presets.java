package Neuroshop.Models;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Presets extends Observable {

    private String filename;
    private List<TempweightList> tempWeightLists;

    public Presets() {
        this.tempWeightLists = new ArrayList<>();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
        this.setChanged();
        this.notifyObservers("setFilename");
    }

    public void addTempweightList(TempweightList tmpList) throws IOException {
        this.tempWeightLists.add(tmpList);
        this.save();
        setChanged();
        notifyObservers("addTempweightList");
    }

    public List<TempweightList> getTempWeightList() {
        return this.tempWeightLists;

    }

    public void save() throws IOException {
        File file = new File ("Neuroshop\\Ressources\\SavedData\\Temp" + File.separator + this.filename);
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this);
        }

    }
}
