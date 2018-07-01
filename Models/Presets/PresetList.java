package Neuroshop.Models.Presets;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class PresetList extends Observable {

    private String name;

    private List<Presets> presets;

    public PresetList() {
        this.presets = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addPresets(Presets preset) {
        presets.add(preset);
        setChanged();
        notifyObservers("addPresets");
    }

    public List<Presets> getPresets(int i) {
        List<Presets> prs = new ArrayList<>();
        for(Presets k : this.presets ) {
            if(k.getNumberOfEntrys() == i) {
                prs.add(k);
            }
        }
        return prs;
    }


}
