package klient.model;

public class Plansza {
    private final Pole[][] pola;
    private final int wymiar;

    public Plansza(final int wymiar, final char kolorLewyDolny, final int wariant) {
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
                }
                if (pobierzPole(x, y).pobierzKolorPola() == 'c') {
                    if (y < (wariant == 3 ? 4 : 3)) {
                        pobierzPole(x, y).ustawPionek(new Pionek('C'));
                    }
                    else if (y > (wariant == 3 ? 5 : 4)) {
                        pobierzPole(x, y).ustawPionek(new Pionek('B'));
                    }
                }
            }
        }
    }

    public Pole pobierzPole(final int x, final int y) {
        return pola[x][y];
    }

    public void ustawPole(final int x, final int y, final char kolorPola) {
        pola[x][y] = new Pole(kolorPola);
    }

    public int pobierzWymiar() {
        return wymiar;
    }

    public void ruszPionek(final int xPocz, final int yPocz, final int xKonc, final int yKonc) {
        pola[xKonc][yKonc].ustawPionek(pola[xPocz][yPocz].pobierzPionek());
        pola[xPocz][yPocz].ustawPionek(null);
    }

    public void usunPionek(final int x, final int y) {
        pola[x][y].ustawPionek(null);
    }
}
