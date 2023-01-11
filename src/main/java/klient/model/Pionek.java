package klient.model;

/**
 * Klasa dla pionka modelowego klienta
 */
public class Pionek {
    private final char kolor;
    private boolean damka = false;

    /**
     * Główny konstruktor
     * @param kolor kolor pionka. 'B' - Białe; 'C' - Czarne
     */
    public Pionek(final char kolor) {
        this.kolor = kolor;
    }

    /**
     * Pobiera kolor pionka
     * @return Kolor pionka. 'B' - Białe; 'C' - Czarne
     */
    public char pobierzKolor() {
        return kolor;
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
