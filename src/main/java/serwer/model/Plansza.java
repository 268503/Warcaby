package serwer.model;

import java.util.List;

/**
 * Klasa dla planszy serwera
 */
public class Plansza {
    private List<Pionek> pionki;
    private int wymiar;
    private int licznikRuchow = 0;
    /**
     * Ilość ruchów wykonana łącznie przez gracza czarnego i białego samymi damkami z rzędu,
     * po której następuje remis
     */
    public final static int LIMIT_RUCHOW = 30;

    /**
     * Pobiera aktualną ilość ruchów samymi damkami z rzędu
     * @return ilość ruchów samymi damkami z rzędu
     */
    public int pobierzLicznikRuchow() {
        return licznikRuchow;
    }

    /**
     * Ustawia ilość ruchów samymi damkami z rzędu
     * @param liczba ilość ruchów samymi damkami z rzędu
     */
    public void ustawLicznikRuchow(final int liczba) {
        licznikRuchow = liczba;
    }

    /**
     * Przypisuje listę pionków do planszy
     * @param pionki lista pionków do przypisania
     */
    public void ustawPionki(final List<Pionek> pionki) {
        this.pionki = pionki;
    }

    /**
     * Przypisuje rozmiar planszy
     * @param liczba rozmiar planszy do przypisania
     */
    public void ustawWymiar(final int liczba) {
        wymiar = liczba;
    }

    /**
     * Pobiera rozmiar planszy
     * @return rozmiar planszy
     */
    public int pobierzWymiar() {
        return wymiar;
    }

    /**
     * Pobiera listę pionków znajdujących się na planszy
     * @return lista pionków znajdujących się na planszy
     */
    public List<Pionek> pobierzPionki() {
        return pionki;
    }

    /**
     * Dodaje pionek do listy pionków znajdujących się na planszy
     * @param pionek pionek do dodania
     */
    public void wstawPionek(final Pionek pionek) {
        pionki.add(pionek);
    }

    /**
     * Usuwa pionek z listy pionków znajdujących się na planszy
     * @param pionek pionek do usunięcia
     */
    public void usunPionek(final Pionek pionek) {
        pionki.remove(pionek);
    }

    /**
     * Pobiera pionek o zadanych współrzędnych
     * @param x współrzędna x pobieranego pionka
     * @param y współrzędna y pobieranego pionka
     * @return pionek z zadanych współrzędnych; null jeśli nie ma takiego pionka
     */
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

    /**
     * Sprawdza czy na współrzędnych początkowych znajduje się pionek podanego koloru
     * oraz czy na współrzędnych końcowych nie znajduje się żaden pionek
     * @param kolorPionka kolor sprawdzanego pionka
     * @param xPocz początkowa współrzędna x
     * @param yPocz początkowa współrzędna y
     * @param xKonc końcowa współrzędna x
     * @param yKonc końcowa współrzędna y
     * @return true jeśli na współrzędnych początkowych znajduje się pionek podanego koloru oraz
     * na współrzędnych końcowych nie znajduje się żaden pionek; w przecwinym przypadku false
     */
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
