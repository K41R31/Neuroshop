package Neuroshop.Models.Presets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class PresetsModel extends Observable {

    private File file;
    private String filename;
    private List<TempweightList> tempWeightLists;
    private List<PresetList> presetLists;

    public PresetsModel() {
        this.tempWeightLists = new ArrayList<>();
        this.presetLists = new ArrayList<>();
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
        tempWeightLists.add(tmpList);
        save("TempWeights");
        setChanged();
        notifyObservers("addTempweightList");
    }

    public List<TempweightList> getTempWeightList() {
        return this.tempWeightLists;
    }

    public void addPresetsList(PresetList preList) throws IOException {
        presetLists.add(preList);
        save("PresetsModel");
        setChanged();
        notifyObservers("addPresetList");
    }

    public List<PresetList> getPresetLists() {
        return this.presetLists;
    }

    public void save(String fileLocation) throws IOException {
        switch(fileLocation) {
            case "TempWeights":
                file = new File ("Neuroshop\\Ressources\\SavedData\\Temp" + File.separator + this.filename);
                break;
            case "PresetsModel":
                file = new File ("Neuroshop\\Ressources\\SavedData\\PresetsModel" + File.separator + this.filename);
        }
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this);
        }
    }

}

