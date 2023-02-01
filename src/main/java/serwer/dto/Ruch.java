package serwer.dto;

public class Ruch {
    private int id;
    private Partia partia;
    private int xPocz;
    private int yPocz;
    private int xKonc;
    private int yKonc;
    private int numerRuchuWPartii;
    private boolean promocja;
    private int typRuchu;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getxPocz() {
        return xPocz;
    }

    public void setxPocz(int xPocz) {
        this.xPocz = xPocz;
    }

    public int getyPocz() {
        return yPocz;
    }

    public void setyPocz(int yPocz) {
        this.yPocz = yPocz;
    }

    public int getxKonc() {
        return xKonc;
    }

    public void setxKonc(int xKonc) {
        this.xKonc = xKonc;
    }

    public int getyKonc() {
        return yKonc;
    }

    public void setyKonc(int yKonc) {
        this.yKonc = yKonc;
    }

    public Ruch() {}

    public Ruch(int xPocz, int yPocz, int xKonc, int yKonc, int numerRuchuWPartii, Partia partia, int typRuchu, boolean promocja) {
        this.xPocz = xPocz;
        this.yPocz = yPocz;
        this.xKonc = xKonc;
        this.yKonc = yKonc;
        this.partia = partia;
        this.numerRuchuWPartii = numerRuchuWPartii;
        this.typRuchu = typRuchu;
        this.promocja = promocja;
    }

    public Partia getPartia() {
        return partia;
    }

    public void setPartia(Partia partia) {
        this.partia = partia;
    }

    public int getNumerRuchuWPartii() {
        return numerRuchuWPartii;
    }

    public void setNumerRuchuWPartii(int numerRuchuWPartii) {
        this.numerRuchuWPartii = numerRuchuWPartii;
    }

    public boolean isPromocja() {
        return promocja;
    }

    public void setPromocja(boolean promocja) {
        this.promocja = promocja;
    }


    public int getTypRuchu() {
        return typRuchu;
    }

    public void setTypRuchu(int typRuchu) {
        this.typRuchu = typRuchu;
    }
}
