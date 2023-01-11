package klient.model;

public class Pole {
    private final char kolorPola;
    private Pionek pionek;

    public Pole(final char kolor) {
        kolorPola = kolor;
    }

    public char pobierzKolorPola() {
        return kolorPola;
    }

    public Pionek pobierzPionek() {
        return pionek;
    }

    public void ustawPionek(final Pionek pionek) {
        this.pionek = pionek;
    }
}
