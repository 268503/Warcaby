package klient.model;

/**
 * Klasa dla pola modelowego klienta
 */
public class Pole {
    private final char kolorPola;
    private Pionek pionek;

    /**
     * Główny konstruktor
     * @param kolor kolor pola. 'j' - pole jasne; 'c' - pole ciemne
     */
    public Pole(final char kolor) {
        kolorPola = kolor;
    }

    /**
     * Pobiera kolor pola
     * @return kolor pola. 'j' - pole jasne; 'c' - pole ciemne
     */
    public char pobierzKolorPola() {
        return kolorPola;
    }

    /**
     * Pobiera pionek z pola
     * @return pionek z tego pola; zwraca null jeśli pole jest puste
     */
    public Pionek pobierzPionek() {
        return pionek;
    }

    /**
     * Przypisuje pionek do pola
     * @param pionek pionek do przypisania
     */
    public void ustawPionek(final Pionek pionek) {
        this.pionek = pionek;
    }
}
