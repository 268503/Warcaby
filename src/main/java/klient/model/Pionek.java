package klient.model;

public class Pionek {
    private final char kolor;
    private boolean damka = false;
    public Pionek(char kolor) {
        this.kolor = kolor;
    }
    public char pobierzKolor() {
        return kolor;
    }
    public boolean czyDamka() {
        return damka;
    }
    public void ustawDamka() {
        this.damka = true;
    }
}
