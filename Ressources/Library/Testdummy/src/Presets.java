import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Presets {

    private NetzModel nm;
    private File file;
    private String filename;
    private int entrys;
    private String name;

    private int[] inputColumns;
    private int maxEpoch;
    private double learnrate;

    private List<Object> presets = new ArrayList<>();

    public void addParamsForPreset (List<Object> presets) throws IOException {
        presets.add(0, nm.getInputColumns());
        presets.add(1, nm.getMaxEpoch());
        presets.add(2, nm.getLearnrate());
        this.save();
    }

    public void setFilename(String filename) {
        this.filename = name;
    }

    public int getNumberOfEntrys() {
        this.entrys = presets.size();
        return this.entrys;
    }

    public void setParamsFromPreset(int[] inputColumns, int maxEpoch, double learnrate) {
        for (Object o : presets) {
            if (o instanceof int[]) {
                int[] iC = (int[]) o;
                this.inputColumns = iC;
            }
            if (o instanceof Number) {
                int mE = (int) o;
                this.maxEpoch = mE;
            }
            if (o instanceof Number) {
                double lR = (double) o;
                this.learnrate = lR;
            }
        }
    }

    public List<Object> getPresets(int i) {
        List<Object> prs = new ArrayList<>();
        for(Object o : this.presets ) {
            if(prs.size() == i) {
                prs.add(o);
            }
        }
        return prs;
    }

    public void save() throws IOException {
        file = new File("Neuroshop\\Ressources\\SavedData\\Presets" + File.separator + this.filename);
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(this);
        }
    }
}
