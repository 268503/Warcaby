package klient.widok;

import javafx.scene.layout.GridPane;
import klient.model.Plansza;

public class PlanszaGUI extends GridPane {
    private Plansza plansza;

    public PlanszaGUI(Plansza plansza) {
        for (int kolumna = 0; kolumna < plansza.pobierzWymiar(); kolumna++) {
            for (int wiersz = 0; wiersz < plansza.pobierzWymiar(); wiersz++) {
                add(new PoleGUI(plansza.pobierzPole(kolumna, wiersz)), kolumna, wiersz);
            }
        }
    }
}
