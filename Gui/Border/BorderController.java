package Neuroshop.Gui.Border;

import Neuroshop.Models.OptionsModel;

import java.util.Observable;
import java.util.Observer;

public class BorderController implements Observer {

    private OptionsModel optionsModel;

    @Override
    public void update(Observable o, Object arg) {
    }

    public void initModel(OptionsModel optionsModel) {
        this.optionsModel = optionsModel;
    }
}
