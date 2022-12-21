package klient.widok;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import klient.model.Pole;

public class PoleGUI extends Rectangle {
    private final static int DLUGOSC_BOKU = 60;
    private PionekGUI pionekGUI;
    public PoleGUI(Pole pole) {
        pionekGUI = new PionekGUI(pole.pobierzPionek());
        if (pole.pobierzKolorPola() == 'j') {
            setFill(Color.rgb(241, 218, 180));
        }
        else {
            setFill(Color.rgb(182, 134, 101));
        }
        setHeight(DLUGOSC_BOKU);
        setWidth(DLUGOSC_BOKU);
    }

    public PionekGUI pobierzPionekGUI() {
        return pionekGUI;
    }

    public void ustawPionekGUI(PionekGUI pionekGUI) {
        this.pionekGUI = pionekGUI;
    }
}
