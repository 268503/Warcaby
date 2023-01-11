package klient.widok;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import klient.kontroler.Kontroler;
import klient.model.Plansza;

/**
 * Klasa dla planszy widokowej
 */
public class PlanszaGUI extends GridPane {
    private char kolor;
    private final Kontroler kontroler;
    private int statusRuchu = 0;
    private int xPocz, yPocz;

    /**
     * Główny konstruktor
     * @param kontroler kontroler odpowiedzialny za komunikację z planszą modelową
     */
    public PlanszaGUI(final Kontroler kontroler) {
        this.kontroler = kontroler;
    }

    /**
     * Przypisuje kolor do pola
     * @param kolor kolor gracza, dla którego jest tworzona plansza
     */
    public void ustawKolor(final char kolor) {
        this.kolor = kolor;
    }

    /**
     * Aktualizuje widok planszy
     * @param plansza plansza modelowa, na podstawie której przeprowadzana jest aktualizacja
     */
    public void odswiez(final Plansza plansza) {
        getChildren().clear();

        if (kolor == 'B') {
            for (int kolumna = 0; kolumna < plansza.pobierzWymiar(); kolumna++) {
                for (int wiersz = 0; wiersz < plansza.pobierzWymiar(); wiersz++) {
                    final PoleGUI poleGUI = new PoleGUI(plansza.pobierzPole(kolumna, wiersz));
                    final int wspolrzednaX = kolumna;
                    final int wspolrzednaY = wiersz;
                    poleGUI.setOnMousePressed(zdarzenie -> {
                        if (statusRuchu == 1) {
                            statusRuchu = 0;
                            kontroler.wyslijKomende("RUCH " + xPocz + " " + yPocz + " " + wspolrzednaX + " "  + wspolrzednaY);
                            odswiez(plansza);
                        }
                    });
                    add(poleGUI, kolumna, wiersz);
                    final PionekGUI pionekGUI = new PoleGUI(plansza.pobierzPole(kolumna, wiersz)).pobierzPionekGUI();
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
        }
        else {
            for (int kolumna = 0; kolumna < plansza.pobierzWymiar(); kolumna++) {
                for (int wiersz = 0; wiersz < plansza.pobierzWymiar(); wiersz++) {
                    final PoleGUI poleGUI = new PoleGUI(plansza.pobierzPole(kolumna, wiersz));
                    final int wspolrzednaX = kolumna;
                    final int wspolrzednaY = wiersz;
                    poleGUI.setOnMousePressed(zdarzenie -> {
                        if (statusRuchu == 1) {
                            statusRuchu = 0;
                            kontroler.wyslijKomende("RUCH " + xPocz + " " + yPocz + " " + wspolrzednaX + " "  + wspolrzednaY);
                            odswiez(plansza);
                        }
                    });
                    add(poleGUI, plansza.pobierzWymiar() - 1 - kolumna, plansza.pobierzWymiar() - 1 - wiersz);
                    final PionekGUI pionekGUI = new PoleGUI(plansza.pobierzPole(kolumna, wiersz)).pobierzPionekGUI();
                    pionekGUI.setOnMousePressed(zdarzenie -> {
                        if (statusRuchu == 0) {
                            xPocz = wspolrzednaX;
                            yPocz = wspolrzednaY;
                            statusRuchu = 1;
                        }
                    });
                    add(pionekGUI, plansza.pobierzWymiar() - 1 - kolumna, plansza.pobierzWymiar() - 1 - wiersz);
                }
            }
        }

        for (final Node element : getChildren()) {
            setHalignment(element, HPos.CENTER);
        }
        setAlignment(Pos.CENTER);
    }
}
