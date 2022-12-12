package serwer.model;


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

    public boolean ruszPionek(char kolorPionka, int xPocz, int yPocz, int xKonc, int yKonc) {

        if (xPocz < 0 || xPocz >= wymiar || yPocz < 0 || yPocz >= wymiar || xKonc < 0 || xKonc >= wymiar || yKonc < 0 || yKonc >= wymiar) {
            return false;
        }

        if (pola[xPocz][yPocz].pobierzPionek() != null) {
            if (kolorPionka == pola[xPocz][yPocz].pobierzPionek().pobierzKolor()) {
                if (pola[xKonc][yKonc].pobierzPionek() == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean zbijPionek(char kolorPionka, int xPocz, int yPocz, int xKonc, int yKonc) {
        boolean pionekPomiedzy = (kolorPionka == 'B' && pola[(xPocz + xKonc) / 2][(yPocz + yKonc) / 2].pobierzPionek().pobierzKolor() == 'C')
                || (kolorPionka == 'C' && pola[(xPocz + xKonc) / 2][(yPocz + yKonc) / 2].pobierzPionek().pobierzKolor() == 'B');
         if(((kolorPionka == 'B' && (yKonc == yPocz - 2 || yKonc == yPocz + 2) && (xKonc == xPocz - 2 || xKonc == xPocz + 2))
                || (kolorPionka == 'C' && (yKonc == yPocz - 2 || yKonc == yPocz + 2) && (xKonc == xPocz - 2 || xKonc == xPocz + 2)))
                && pionekPomiedzy) {
             pola[xKonc][yKonc].ustawPionek(pola[xPocz][yPocz].pobierzPionek());
             pola[xPocz][yPocz].ustawPionek(null);
             pola[(xPocz + xKonc) / 2][(yPocz + yKonc) / 2].ustawPionek(null);
             return true;
         }
         return false;
    }
    public boolean normalnyRuch(char kolorPionka, int xPocz, int yPocz, int xKonc, int yKonc) {
        if((kolorPionka == 'B' && yKonc == yPocz - 1 && (xKonc == xPocz - 1 || xKonc == xPocz + 1))
                || (kolorPionka == 'C' && yKonc == yPocz + 1 && (xKonc == xPocz - 1 || xKonc == xPocz + 1))) {

            pola[xKonc][yKonc].ustawPionek(pola[xPocz][yPocz].pobierzPionek());
            pola[xPocz][yPocz].ustawPionek(null);
            return true;
        }
        return false;
    }

}
