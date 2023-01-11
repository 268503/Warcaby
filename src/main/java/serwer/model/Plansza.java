package serwer.model;

import java.util.List;

public class Plansza {
    private List<Pionek> pionki;
    private int wymiar;
    private int licznikRuchow = 0;
    public final static int LIMIT_RUCHOW = 30;
    public int pobierzLicznikRuchow() {
        return licznikRuchow;
    }
    public void ustawLicznikRuchow(final int liczba) {
        licznikRuchow = liczba;
    }
    public void ustawPionki(final List<Pionek> pionki) {
        this.pionki = pionki;
    }

    public void ustawWymiar(final int liczba) {
        wymiar = liczba;
    }

    public int pobierzWymiar() {
        return wymiar;
    }
    public List<Pionek> pobierzPionki() {
        return pionki;
    }
    public void wstawPionek(final Pionek pionek) {
        pionki.add(pionek);
    }
    public void usunPionek(final Pionek pionek) {
        pionki.remove(pionek);
    }
    public Pionek pobierzPionek(final int x, final int y) {
        if (x < 0 || y < 0 || x >= wymiar || y >= wymiar) {
            return null;
        }
        for (final Pionek pionek : pionki) {
            if (pionek.pobierzWspolrzednaX() == x && pionek.pobierzWspolrzednaY() == y) {
                return pionek;
            }
        }
        return null;
    }

    public boolean ruszPionek(final char kolorPionka, final int xPocz, final int yPocz, final int xKonc, final int yKonc) {

        if (xPocz < 0 || xPocz >= wymiar || yPocz < 0 || yPocz >= wymiar || xKonc < 0 || xKonc >= wymiar || yKonc < 0 || yKonc >= wymiar) {
            return false;
        }
        if (pobierzPionek(xPocz, yPocz) != null && kolorPionka == pobierzPionek(xPocz, yPocz).pobierzKolor()) {
            return pobierzPionek(xKonc, yKonc) == null;
        }
        return false;
    }
}
