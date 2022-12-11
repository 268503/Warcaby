package klient.widok;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import klient.kontroler.Kontroler;
import klient.model.Plansza;

public class PlanszaGUI extends GridPane {
    private Plansza plansza;
    private final Kontroler kontroler;
    private int statusRuchu = 0;
    private int xPocz, yPocz;
    public PlanszaGUI(Plansza plansza, Kontroler kontroler) {
        this.kontroler = kontroler;
        this.plansza = plansza;
        odswiez();
    }

    public void odswiez() {
        for (Node element : getChildren()) {
            element = null;
        }

        for (int kolumna = 0; kolumna < plansza.pobierzWymiar(); kolumna++) {
            for (int wiersz = 0; wiersz < plansza.pobierzWymiar(); wiersz++) {
                PoleGUI poleGUI = new PoleGUI(plansza.pobierzPole(kolumna, wiersz));
                final int wspolrzednaX = kolumna;
                final int wspolrzednaY = wiersz;
                poleGUI.setOnMousePressed(zdarzenie -> {
                    if (statusRuchu == 1) {
                        plansza.ruszPionek(Kontroler.pobierzKolor(), xPocz, yPocz, wspolrzednaX, wspolrzednaY);
                        statusRuchu = 0;
                        kontroler.wyslijKomende("RUCH " + xPocz + yPocz + wspolrzednaX + wspolrzednaY);
                        odswiez();
                    }

                });
                add(poleGUI, kolumna, wiersz);
                PionekGUI pionekGUI = new PoleGUI(plansza.pobierzPole(kolumna, wiersz)).pobierzPionekGUI();
                pionekGUI.setOnMousePressed(zdarzenie -> {
                    if (statusRuchu == 0) {
                        xPocz = wspolrzednaX;
                        yPocz = wspolrzednaY;
                        statusRuchu = 1;
                    }

                });
                add(pionekGUI, kolumna, wiersz);
            }
        }
        for (Node element : getChildren()) {
            setHalignment(element, HPos.CENTER);
        }
        setAlignment(Pos.CENTER);
    }

    // !!!
    public Plansza pobierzPlansze() {
        return plansza;
    }
    // !!!

}
