package klient.widok;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import klient.model.Pole;

/**
 * Klasa dla pola widokowego
 */
public class PoleGUI extends Rectangle {
    private final static int DLUGOSC_BOKU = 60;
    private final PionekGUI pionekGUI;

    /**
     * Główny konstruktor
     * @param pole pole modelowe, na podstawie którego tworzone jest pole widokowe
     */
    public PoleGUI(final Pole pole) {
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

    /**
     * pobiera pionek widokowy z tego pola
     * @return pionek z tego pola; zwraca null jeśli pole jest puste
     */
    public PionekGUI pobierzPionekGUI() {
        return pionekGUI;
    }
}
