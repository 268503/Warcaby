package serwer.model;

public class Pole {
    private final char kolorPola;
    private Pionek pionek;

    public Pole(char kolor) {
        kolorPola = kolor;
    }

    public char pobierzKolorPola() {
        return kolorPola;
    }

    public Pionek pobierzPionek() {
        return pionek;
    }

    public void ustawPionek(Pionek pionek) {
        this.pionek = pionek;
    }
}
