package klient.widok;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import klient.model.Pionek;

public class PionekGUI extends Circle {
    public PionekGUI(Pionek pionek) {
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
    //public void promuj() //???
}
