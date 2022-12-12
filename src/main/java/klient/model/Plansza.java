package klient.model;

public class Plansza {
    private Pole[][] pola;
    private final int wymiar;

    public Plansza(int wymiar, char kolorLewyDolny) {
        this.wymiar = wymiar;
        pola = new Pole[wymiar][wymiar];
        char kolorDrugi;
        if (kolorLewyDolny == 'c') {
            kolorDrugi = 'j';
        }
        else {
            kolorDrugi = 'c';
        }
        for (int x = 0; x < wymiar; x++) {
            for (int y = 0; y < wymiar; y++) {
                if ((x + y) % 2 == 0) {
                    ustawPole(x, y, kolorDrugi);
                }
                else {
                    ustawPole(x, y, kolorLewyDolny);
                    if (y < 3) {
                        pobierzPole(x, y).ustawPionek(new Pionek('C'));
                    }
                    else if (y > 4) {
                        pobierzPole(x, y).ustawPionek(new Pionek('B'));
                    }
                }
            }
        }
    }

    public Pole pobierzPole(int x, int y) {
        return pola[x][y];
    }

    public void ustawPole(int x, int y, char kolorPola) {
        pola[x][y] = new Pole(kolorPola);
    }

    public int pobierzWymiar() {
        return wymiar;
    }

    public void ruszPionek(int xPocz, int yPocz, int xKonc, int yKonc) {
//        if (pola[xPocz][yPocz].pobierzPionek() != null) {
//            if (kolorPionka == pola[xPocz][yPocz].pobierzPionek().pobierzKolor()) {
//                if (pola[xKonc][yKonc].pobierzPionek() == null) {
//                    pola[xKonc][yKonc].ustawPionek(pola[xPocz][yPocz].pobierzPionek());
//                    pola[xPocz][yPocz].ustawPionek(null);
//                }
//            }
//        }
        pola[xKonc][yKonc].ustawPionek(pola[xPocz][yPocz].pobierzPionek());
        pola[xPocz][yPocz].ustawPionek(null);
    }

    public void usunPionek(int x, int y) {
        pola[x][y].ustawPionek(null);
    }
}
