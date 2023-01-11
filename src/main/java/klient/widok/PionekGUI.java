package klient.widok;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import klient.model.Pionek;

/**
 * Klasa dla Pionka widokowego
 */
public class PionekGUI extends Circle {
    /**
     * Główny konstruktor
     * @param pionek pionek modelowy, na podstawie którego tworzony jest pionek widokowy
     */
    public PionekGUI(final Pionek pionek) {
        if (pionek != null) {
            if (pionek.pobierzKolor() == 'B') {
                setFill(Color.rgb(254, 254, 241));
            } else {
                setFill(Color.rgb(20, 19, 18));
            }
            if (pionek.czyDamka()) {
                setStroke(Color.GOLD);
                setStrokeWidth(3);
            } else {
                setStroke(Color.BLACK);
            }
            setRadius(20);
        }
    }
}
