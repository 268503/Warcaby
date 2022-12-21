package serwer.model;

import java.util.ArrayList;
import java.util.List;

public class Plansza {
    // private final Pole[][] pola;
    private final List<Pionek> pionki;
    private final int wymiar;
    private int licznikRuchow = 0;
    private final static int LIMIT_RUCHOW = 30;

    public Plansza(int wymiar, char kolorLewyDolny) {
        this.wymiar = wymiar;
        pionki = new ArrayList<>();
        // pola = new Pole[wymiar][wymiar];
//        char kolorDrugi;
//        if (kolorLewyDolny == 'c') {
//            kolorDrugi = 'j';
//        }
//        else {
//            kolorDrugi = 'c';
//        }
        for (int x = 0; x < wymiar; x++) {
            for (int y = 0; y < wymiar; y++) {
                if ((x + y) % 2 == 0) {
                    // TODO: ogarnąć builder
                }
                else {
                    //**********normalne warcaby
                    if (y < 3) {
//                        pobierzPole(x, y).ustawPionek(new Pionek('C', x, y));
                        wstawPionek(new Pionek('C', x, y));
                    }
                    else if (y > 4) {
//                        pobierzPole(x, y).ustawPionek(new Pionek('B', x, y));
                        wstawPionek(new Pionek('B', x, y));
                    }
                    //*********koniec
                }
            }
        }

        // !!! testowe początkowe ułożenie planszy (damka)

//        wstawPionek(new Pionek('B', 1, 6));
//        pobierzPionek(1, 6).ustawDamka();
//        wstawPionek(new Pionek('C', 3, 4));
//        wstawPionek(new Pionek('C', 6, 3));
//        wstawPionek(new Pionek('C', 6, 5));
//        wstawPionek(new Pionek('C', 4, 7));

        // !!! testowe początkowe ułożenie planszy (edge case ultimate)
//        wstawPionek(new Pionek('B', 3, 5));
//        wstawPionek(new Pionek('C', 4, 4));
//        wstawPionek(new Pionek('C', 5, 5));
//        wstawPionek(new Pionek('B', 6, 6));
//        wstawPionek(new Pionek('C', 6, 2));
        // !!! testowe początkowe ułożeniee planszy (remis) (
//        wstawPionek(new Pionek('B', 0, 1));
//        pobierzPionek(0, 1).ustawDamka();
//        wstawPionek(new Pionek('B', 3, 6));
//        wstawPionek(new Pionek('C', 4, 3));
//        pobierzPionek(4, 3).ustawDamka();
//        wstawPionek(new Pionek('C', 2, 7));
    }

//    public Pole pobierzPole(int x, int y) {
//        return pola[x][y];
//    }

//    public void ustawPole(int x, int y, char kolorPola) {
//        pola[x][y] = new Pole(kolorPola);
//    }

    public int pobierzWymiar() {
        return wymiar;
    }

    public List<Pionek> pobierzPionki() {
        return pionki;
    }
    public void wstawPionek(Pionek pionek) {
        pionki.add(pionek);
    }
    public void usunPionek(Pionek pionek) {
        pionki.remove(pionek);
    }
    public Pionek pobierzPionek(int x, int y) {
        if (x < 0 || y < 0 || x >= wymiar || y >= wymiar) {
            return null;
        }
        for (Pionek pionek : pionki) {
            if (pionek.pobierzWspolrzednaX() == x && pionek.pobierzWspolrzednaY() == y) {
                return pionek;
            }
        }
        return null;
    }
    public boolean ruszPionek(char kolorPionka, int xPocz, int yPocz, int xKonc, int yKonc) {

        if (xPocz < 0 || xPocz >= wymiar || yPocz < 0 || yPocz >= wymiar || xKonc < 0 || xKonc >= wymiar || yKonc < 0 || yKonc >= wymiar) {
            return false;
        }
        if (pobierzPionek(xPocz, yPocz) != null) {
            if (kolorPionka == pobierzPionek(xPocz, yPocz).pobierzKolor()) {
                if (pobierzPionek(xKonc, yKonc) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean zbijPionek(char kolorPionka, int xPocz, int yPocz, int xKonc, int yKonc) {
        if (!pobierzPionek(xPocz, yPocz).czyDamka()) {
            if (pobierzPionek((xPocz + xKonc) / 2, (yPocz + yKonc) / 2) == null) {
                return false;
            }
            boolean pionekPomiedzy = (kolorPionka == 'B' && pobierzPionek((xPocz + xKonc) / 2, (yPocz + yKonc) / 2).pobierzKolor() == 'C')
                    || (kolorPionka == 'C' && pobierzPionek((xPocz + xKonc) / 2, (yPocz + yKonc) / 2).pobierzKolor() == 'B');
            if (((kolorPionka == 'B' && (yKonc == yPocz - 2 || yKonc == yPocz + 2) && (xKonc == xPocz - 2 || xKonc == xPocz + 2))
                    || (kolorPionka == 'C' && (yKonc == yPocz - 2 || yKonc == yPocz + 2) && (xKonc == xPocz - 2 || xKonc == xPocz + 2)))
                    && pionekPomiedzy) {
                pobierzPionek(xPocz, yPocz).przesun(xKonc, yKonc);
                usunPionek(pobierzPionek(xPocz, yPocz));
                usunPionek(pobierzPionek((xPocz + xKonc) / 2, (yPocz + yKonc) / 2));
                licznikRuchow = 0;
                return true;
            }
        }
        else {
            if (Math.abs(xKonc - xPocz) != Math.abs(yKonc - yPocz)) {
                return false;
            }
            int licznik = 0;
            int xBity = -1;
            int yBity = -1;
            for (int i = 1; i <= Math.abs(xKonc - xPocz); i++) {
                if (pobierzPionek(xPocz + (int) Math.signum(xKonc - xPocz) * i, yPocz + (int) Math.signum(yKonc - yPocz) * i) != null) {
                    xBity = xPocz + (int) Math.signum(xKonc - xPocz) * i;
                    yBity = yPocz + (int) Math.signum(yKonc - yPocz) * i;
                    if (pobierzPionek(xBity, yBity).pobierzKolor() == kolorPionka) {
                        return false;
                    }
                    licznik ++;
                    if (licznik > 1) {
                        return false;
                    }
                }
            }
            if (licznik == 0) {
                return false;
            }
            usunPionek(pobierzPionek(xBity, yBity));
            pobierzPionek(xPocz, yPocz).przesun(xKonc, yKonc);
            usunPionek(pobierzPionek(xPocz, yPocz));
            licznikRuchow = 0;
            return true;
        }
        return false;
    }
    public boolean normalnyRuch(char kolorPionka, int xPocz, int yPocz, int xKonc, int yKonc) {
//        if (!pobierzPionek(xPocz, yPocz).czyDamka()) {
//            if ((kolorPionka == 'B' && yKonc == yPocz - 1 && (xKonc == xPocz - 1 || xKonc == xPocz + 1))
//                    || (kolorPionka == 'C' && yKonc == yPocz + 1 && (xKonc == xPocz - 1 || xKonc == xPocz + 1))) {
//                pobierzPionek(xPocz, yPocz).przesun(xKonc, yKonc);
//                usunPionek(pobierzPionek(xPocz, yPocz));
//                return true;
//            }
//        }
//        else {
//            if (Math.abs(xKonc - xPocz) != Math.abs(yKonc - yPocz)) {
//                return false;
//            }
//            for (int i = 1; i <= Math.abs(xKonc - xPocz); i++) {
//                if (pobierzPionek(xPocz + (int) Math.signum(xKonc - xPocz) * i, yPocz + (int) Math.signum(yKonc - yPocz) * i) != null) {
//                    return false;
//                }
//            }
//            pobierzPionek(xPocz, yPocz).przesun(xKonc, yKonc);
//            usunPionek(pobierzPionek(xPocz, yPocz));
//            return true;
//        }
//        return false;
        if (moznaNormalnyRuch(kolorPionka, xPocz, yPocz, xKonc, yKonc))
        {
            if (pobierzPionek(xPocz, yPocz).czyDamka()) {
                licznikRuchow++;
            }
            else {
                licznikRuchow = 0;
            }
            pobierzPionek(xPocz, yPocz).przesun(xKonc, yKonc);
            usunPionek(pobierzPionek(xPocz, yPocz));
            return true;
        }
        return false;
    }
    public boolean moznaNormalnyRuch(char kolorPionka, int xPocz, int yPocz, int xKonc, int yKonc) {
        if (!pobierzPionek(xPocz, yPocz).czyDamka()) {
            if ((kolorPionka == 'B' && yKonc == yPocz - 1 && (xKonc == xPocz - 1 || xKonc == xPocz + 1))
                    || (kolorPionka == 'C' && yKonc == yPocz + 1 && (xKonc == xPocz - 1 || xKonc == xPocz + 1))) {
                return true;
            }
        }
        else {
            if (Math.abs(xKonc - xPocz) != Math.abs(yKonc - yPocz)) {
                return false;
            }
            for (int i = 1; i <= Math.abs(xKonc - xPocz); i++) {
                if (pobierzPionek(xPocz + (int) Math.signum(xKonc - xPocz) * i, yPocz + (int) Math.signum(yKonc - yPocz) * i) != null) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    public boolean moznaDalejBic(char kolorPionka, int x, int y) {
        if (!pobierzPionek(x, y).czyDamka()) {
            return (   (x > 1 && y > 1 && pobierzPionek(x-1, y-1) != null && pobierzPionek(x-2, y-2)==null && pobierzPionek(x-1, y-1).pobierzKolor() != kolorPionka)
                    || (x > 1 && y < wymiar - 2 && pobierzPionek(x-1, y+1) != null && pobierzPionek(x-2, y+2)==null && pobierzPionek(x-1, y+1).pobierzKolor() != kolorPionka)
                    || (x < wymiar - 2 && y > 1 && pobierzPionek(x+1, y-1) != null && pobierzPionek(x+2, y-2)==null && pobierzPionek(x+1, y-1).pobierzKolor() != kolorPionka)
                    || (x < wymiar - 2 && y < wymiar - 2 && pobierzPionek(x+1, y+1) != null && pobierzPionek(x+2, y+2)==null && pobierzPionek(x+1, y+1).pobierzKolor() != kolorPionka));
        }
        else {
            int i = 1;
            while (x + i < wymiar && y + i < wymiar && pobierzPionek(x + i, y + i) == null) {
                i++;
            }
            if (x + i != wymiar && y + i != wymiar && pobierzPionek(x + i, y + i).pobierzKolor() != kolorPionka && ruszPionek(kolorPionka, x, y, x + (i + 1), y + (i + 1))) {
                System.out.println("bicie ++");
                return true;
            }
            i = 1;
            while (x + i < wymiar && y - i >= 0 && pobierzPionek(x + i, y - i) == null) {
                i++;
            }
            if (x + i != wymiar && y - i != -1 && pobierzPionek(x + i, y - i).pobierzKolor() != kolorPionka && ruszPionek(kolorPionka, x, y, x + (i + 1), y - (i + 1))) {
                System.out.println("bicie +-");
                return true;
            }
            i = 1;
            while (x - i >= 0 && y + i < wymiar && pobierzPionek(x - i, y + i) == null) {
                i++;
            }
            if (x - i != -1 && y + i != wymiar && pobierzPionek(x - i, y + i).pobierzKolor() != kolorPionka && ruszPionek(kolorPionka, x, y, x - (i + 1), y + (i + 1))) {
                System.out.println("bicie -+");
                return true;
            }
            i = 1;
            while (x - i >= 0 && y - i >= 0 && pobierzPionek(x - i, y - i) == null) {
                i++;
            }
            if (x - i != -1 && y - i != -1 && pobierzPionek(x - i, y - i).pobierzKolor() != kolorPionka && ruszPionek(kolorPionka, x, y, x - (i + 1), y - (i + 1))) {
                System.out.println("bicie --");
                return true;
            }
            return false;
        }
    }
    public boolean czyRemis() {
        System.out.println(licznikRuchow + " ruchow damka bez progresu");
        if (licznikRuchow == LIMIT_RUCHOW)
        {
            return true;
        }
        return false;
    }
    public boolean czyWygrana(char kolor) {
        for (Pionek pionek : pionki) {
            if (pionek.pobierzKolor() != kolor) {
                if (czyMoznaRuszyc(pionek)) {
                    return false;
                }
            }
        }
        return true;
    }
    public boolean czyMoznaRuszyc(Pionek pionek) {
        char kolor = pionek.pobierzKolor();
        int x = pionek.pobierzWspolrzednaX();
        int y = pionek.pobierzWspolrzednaY();
        if (   (ruszPionek(kolor, x, y, x + 1, y - 1) && moznaNormalnyRuch(kolor, x, y, x + 1, y - 1))
            || (ruszPionek(kolor, x, y, x - 1, y - 1) && moznaNormalnyRuch(kolor, x, y, x - 1, y - 1))
            || (ruszPionek(kolor, x, y, x + 1, y + 1) && moznaNormalnyRuch(kolor, x, y, x + 1, y + 1))
            || (ruszPionek(kolor, x, y, x - 1, y + 1) && moznaNormalnyRuch(kolor, x, y, x - 1, y + 1))) {
            return true;
        }
        return moznaDalejBic(pionek.pobierzKolor(), pionek.pobierzWspolrzednaX(), pionek.pobierzWspolrzednaY());
    }
}
