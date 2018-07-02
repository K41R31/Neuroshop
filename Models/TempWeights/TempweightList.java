package Neuroshop.Models.TempWeights;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class TempweightList extends Observable {

    private String name;

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
}
