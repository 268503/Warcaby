package serwer.model;

public class Pionek {
    private final char kolor;
    private boolean damka = false;
    private int wspolrzednaX;
    private int wspolrzednaY;

    public Pionek(char kolor, int x, int y) {
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
    public void przesun(int x, int y) {
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
