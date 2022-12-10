package klient.widok;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import klient.model.Plansza;

public class PlanszaGUI extends GridPane {
    private Plansza plansza;

    public PlanszaGUI(Plansza plansza) {
        for (int kolumna = 0; kolumna < plansza.pobierzWymiar(); kolumna++) {
            for (int wiersz = 0; wiersz < plansza.pobierzWymiar(); wiersz++) {
                add(new PoleGUI(plansza.pobierzPole(kolumna, wiersz)), kolumna, wiersz);
                add(new PoleGUI(plansza.pobierzPole(kolumna, wiersz)).pobierzPionekGUI(), kolumna, wiersz);
            }
        }
        for (Node node : getChildren()) {
            setHalignment(node, HPos.CENTER);
        }
        setAlignment(Pos.CENTER);
    }
}
