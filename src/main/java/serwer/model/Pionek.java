package serwer.model;

/**
 * Klasa dla pionka serwera
 */
public class Pionek {
    private final char kolor;
    private boolean damka = false;
    private int wspolrzednaX;
    private int wspolrzednaY;

    /**
     * Główny konstruktor
     * @param kolor kolor pionka. 'B' - Białe; 'C' - Czarne
     * @param x współrzędna x pionka
     * @param y współrzędna y pionka
     */
    public Pionek(final char kolor, final int x, final int y) {
        this.kolor = kolor;
        wspolrzednaX = x;
        wspolrzednaY = y;
    }

    /**
     * Pobiera kolor pionka
     * @return Kolor pionka. 'B' - Białe; 'C' - Czarne
     */
    public char pobierzKolor() {
        return kolor;
    }

    /**
     * Pobiera współrzędną x pionka
     * @return współrzędna x pionka
     */
    public int pobierzWspolrzednaX() {
        return wspolrzednaX;
    }

    /**
     * Pobiera współrzędną y pionka
     * @return współrzędna y pionka
     */
    public int pobierzWspolrzednaY() {
        return wspolrzednaY;
    }

    /**
     * Przemieszcza pionek na wskazane miejsce
     * @param x współrzędna x, na której ma stanąć pionek
     * @param y współrzędna y, na której ma stanąć pionek
     */
    public void przesun(final int x, final int y) {
        wspolrzednaX = x;
        wspolrzednaY = y;
    }

    /**
     * Sprawdza czy pionek jest damką
     * @return informacja czy pionek jest damką
     */
    public boolean czyDamka() {
        return damka;
    }

    /**
     * Ustawia typ pionka jako damka
     */
    public void ustawDamka() {
        this.damka = true;
    }
}
