package Neuroshop.Models.Presets;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class SigmoidList extends Observable {

    private List<SigmoidObj> sigFnc;

    public SigmoidList() {
        this.sigFnc = new ArrayList<>();
    }

    public void addSigmoids (SigmoidObj sg) {
        this.sigFnc.add(sg);
        setChanged();
        notifyObservers("addSigmoids");
    }

    public List<SigmoidObj> getSigmoidObj(int i) {
        List<SigmoidObj> sgm = new ArrayList<>();
        for(SigmoidObj k : this.sigFnc) {
            if(k.getNumberOfHiddenLayers() == i) {
                sgm.add(k);
            }
        }
        return sgm;
    }


}

