package Neuroshop.Models.TempWeights;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class TempweightList extends Observable {

    private String name;
    private String filename;
    private List<Tempweight> tempweights;

    public TempweightList() {
        this.tempweights = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void addTempweights (Tempweight tempweight) {
    tempweights.add(tempweight);
    setChanged();

    notifyObservers("addTempweights");
    }

    public List<Tempweight> getTempweights(int i) {
        List<Tempweight> tmp = new ArrayList<>();
        for(Tempweight k : this.tempweights) {
            if(k.getEpoch() == i) {
                tmp.add(k);
            }
        }
        return tmp;
    }

    public void save() throws IOException {
        File file = new File("Neuroshop\\Ressources\\SavedData\\Temp" + File.separator + this.filename);
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this);
        }
    }
}
