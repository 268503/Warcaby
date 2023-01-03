package serwer.model;

import java.util.ArrayList;
import java.util.List;

public class Plansza {
    private List<Pionek> pionki;
    private int wymiar;
    private int licznikRuchow = 0;
    public int pobierzLicznikRuchow() {
        return licznikRuchow;
    }
    public void ustawLicznikRuchow(int liczba) {
        licznikRuchow = liczba;
    }
    public final static int LIMIT_RUCHOW = 30;
//
//    public Plansza() {
//        // this.wymiar = wymiar;
//        pionki = new ArrayList<>();
//    }
    public void ustawPionki() {
        pionki = new ArrayList<>();
    }

    public void ustawWymiar(int liczba) {
        wymiar = liczba;
    }

    public int pobierzWymiar() {
        return wymiar;
    }
    public List<Pionek> pobierzPionki() {
        return pionki;
    }
    public void wstawPionek(Pionek pionek) {
        pionki.add(pionek);
    }
    public void usunPionek(Pionek pionek) {
        pionki.remove(pionek);
    }
    public Pionek pobierzPionek(int x, int y) {
        if (x < 0 || y < 0 || x >= wymiar || y >= wymiar) {
            return null;
        }
        for (Pionek pionek : pionki) {
            if (pionek.pobierzWspolrzednaX() == x && pionek.pobierzWspolrzednaY() == y) {
                return pionek;
            }
        }
        return null;
    }

    public boolean ruszPionek(char kolorPionka, int xPocz, int yPocz, int xKonc, int yKonc) {

        if (xPocz < 0 || xPocz >= wymiar || yPocz < 0 || yPocz >= wymiar || xKonc < 0 || xKonc >= wymiar || yKonc < 0 || yKonc >= wymiar) {
            return false;
        }
        if (pobierzPionek(xPocz, yPocz) != null) {
            if (kolorPionka == pobierzPionek(xPocz, yPocz).pobierzKolor()) {
                if (pobierzPionek(xKonc, yKonc) == null) {
                    return true;
                }
            }
        }
        return false;
    }



}
