package serwer.model;

public class Pionek {
    private final char kolor;
    private boolean damka = false;
    private int wspolrzednaX;
    private int wspolrzednaY;

    public Pionek(final char kolor, final int x, final int y) {
        this.kolor = kolor;
        wspolrzednaX = x;
        wspolrzednaY = y;
    }

    public char pobierzKolor() {
        return kolor;
    }
    public int pobierzWspolrzednaX() {
        return wspolrzednaX;
    }
    public int pobierzWspolrzednaY() {
        return wspolrzednaY;
    }
    public void przesun(final int x, final int y) {
        wspolrzednaX = x;
        wspolrzednaY = y;
    }
    public boolean czyDamka() {
        return damka;
    }
    public void ustawDamka() {
        this.damka = true;
    }
}
