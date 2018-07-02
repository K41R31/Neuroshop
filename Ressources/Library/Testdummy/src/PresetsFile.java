import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PresetsFile {

    private static List<Object> presets;
    private static String USER_FOLDER = "src/Folder" + File.separator;

    public static List<Object> getPresets() {
        if(presets == null) {
            presets = new ArrayList<>();

        File folder = new File(USER_FOLDER);
        for(File f : folder.listFiles()) {
            try {
                Object loadedPreset = PresetsFile.load(f);
                presets.add(loadedPreset);
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
          }
        }
        return presets;
    }

    public static void addPresets(List<Object> presets) {
        try {
            String filename = "Preset" + getPresets().size();
            presets.
        }
    }

    public static Object load(File file) throws FileNotFoundException, ClassNotFoundException, IOException {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Object) ois.readObject();
        }
    }
}
