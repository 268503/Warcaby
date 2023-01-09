package klient.model;

public class Plansza {
    private final Pole[][] pola;
    private final int wymiar;

    public Plansza(int wymiar, char kolorLewyDolny, int wariant) {
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
                        // !!! dobre początkowe ułożenie planszy
                        pobierzPole(x, y).ustawPionek(new Pionek('C'));
                    }
                    else if (y > (wariant == 3 ? 5 : 4)) {
                        // !!! dobre początkowe ułożenie planszy
                        pobierzPole(x, y).ustawPionek(new Pionek('B'));
                    }
                }
            }
        }

        // !!! testowe początkowe ułożenie planszy (damka)
//
//        pobierzPole(1,6).ustawPionek(new Pionek('B'));
//        pobierzPole(1, 6).pobierzPionek().ustawDamka();
//        pobierzPole(3, 4).ustawPionek(new Pionek('C'));
//        pobierzPole(6, 3).ustawPionek(new Pionek('C'));
//        pobierzPole(6, 5).ustawPionek(new Pionek('C'));
//        pobierzPole(4, 7).ustawPionek(new Pionek('C'));

        // !!! testowe początkowe ułożenie planszy (edge case ultimate)

//        pobierzPole(3, 5).ustawPionek(new Pionek('B'));
//        pobierzPole(4, 4).ustawPionek(new Pionek('C'));
//        pobierzPole(5, 5).ustawPionek(new Pionek('C'));
//        pobierzPole(6, 6).ustawPionek(new Pionek('B'));
//        pobierzPole(6, 2).ustawPionek(new Pionek('C'));

        // !!! testowe początkowe ułożeniee planszy (remis) (
//        pobierzPole(0, 1).ustawPionek(new Pionek('B'));
//        pobierzPole(0, 1).pobierzPionek().ustawDamka();
//        pobierzPole(3, 6).ustawPionek(new Pionek('B'));
//        pobierzPole(4, 3).ustawPionek(new Pionek('C'));
//        pobierzPole(4, 3).pobierzPionek().ustawDamka();
//        pobierzPole(2, 7 ).ustawPionek(new Pionek('C'));
        // late game test chyba

//        pobierzPole(2, 3).ustawPionek(new Pionek('B'));
//        pobierzPole(1, 0).ustawPionek(new Pionek('C'));
//        pobierzPole(1, 2).ustawPionek(new Pionek('B'));
//        pobierzPole(0, 1).ustawPionek(new Pionek('B'));
//        pobierzPole(3, 6).ustawPionek(new Pionek('C'));
//        pobierzPole(5, 2).ustawPionek(new Pionek('C'));
//        pobierzPole(7, 4).ustawPionek(new Pionek('B'));
//        pobierzPole(7, 0).ustawPionek(new Pionek('C'));
//        pobierzPole(7, 6).ustawPionek(new Pionek('C'));
//        pobierzPole(7, 6).pobierzPionek().ustawDamka();
        // nie działało w hiszpańskim
//        pobierzPole(0, 1).ustawPionek(new Pionek('C'));
//        pobierzPole(6, 7).ustawPionek(new Pionek('C'));
//        pobierzPole(4, 1).ustawPionek(new Pionek('B'));
//        pobierzPole(4, 3).ustawPionek(new Pionek('B'));
//        pobierzPole(4, 5).ustawPionek(new Pionek('B'));
//        pobierzPole(0, 5).ustawPionek(new Pionek('B'));
//        pobierzPole(0, 7).ustawPionek(new Pionek('B'));
//        pobierzPole(2, 7).ustawPionek(new Pionek('B'));
//        pobierzPole(7, 6).ustawPionek(new Pionek('B'));
//        pobierzPole(6, 7).pobierzPionek().ustawDamka();
//        pobierzPole(4, 1).pobierzPionek().ustawDamka();
//        pobierzPole(4, 3).pobierzPionek().ustawDamka();
        // nie działało w hiszpańskim ale w hiszpanskim
//        pobierzPole(7, 1).ustawPionek(new Pionek('C'));
//        pobierzPole(1, 7).ustawPionek(new Pionek('C'));
//        pobierzPole(3, 1).ustawPionek(new Pionek('B'));
//        pobierzPole(3, 3).ustawPionek(new Pionek('B'));
//        pobierzPole(3, 5).ustawPionek(new Pionek('B'));
//        pobierzPole(7, 5).ustawPionek(new Pionek('B'));
//        pobierzPole(7, 7).ustawPionek(new Pionek('B'));
//        pobierzPole(5, 7).ustawPionek(new Pionek('B'));
//        pobierzPole(0, 6).ustawPionek(new Pionek('B'));
//        pobierzPole(1, 7).pobierzPionek().ustawDamka();
//        pobierzPole(3, 1).pobierzPionek().ustawDamka();
//        pobierzPole(3, 3).pobierzPionek().ustawDamka();
        // lichess
//        pobierzPole(2, 1).ustawPionek(new Pionek('C'));
//        pobierzPole(1, 2).ustawPionek(new Pionek('C'));
//        pobierzPole(1, 4).ustawPionek(new Pionek('C'));
//        pobierzPole(3, 6).ustawPionek(new Pionek('C'));
//        pobierzPole(4, 7).ustawPionek(new Pionek('B'));
//        pobierzPole(4, 7).pobierzPionek().ustawDamka();
        // hiszpański lichess
//        pobierzPole(1, 3).ustawPionek(new Pionek('C'));
//        pobierzPole(3, 3).ustawPionek(new Pionek('C'));
//        pobierzPole(5, 3).ustawPionek(new Pionek('C'));
//        pobierzPole(4, 4).ustawPionek(new Pionek('B'));
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
        pola[xKonc][yKonc].ustawPionek(pola[xPocz][yPocz].pobierzPionek());
        pola[xPocz][yPocz].ustawPionek(null);
    }

    public void usunPionek(int x, int y) {
        pola[x][y].ustawPionek(null);
    }
}
