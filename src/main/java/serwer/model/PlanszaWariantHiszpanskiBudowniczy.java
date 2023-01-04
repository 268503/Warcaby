package serwer.model;

import java.util.ArrayList;

public class PlanszaWariantHiszpanskiBudowniczy extends PlanszaBudowniczy {
    private static final int WYMIAR_PLANSZY = 8;
    @Override
    public boolean istniejeBicie(char kolor) {
        boolean bicieDostepne = false;
        for (Pionek pionek : plansza.pobierzPionki()) {
            if (!bicieDostepne && moznaDalejBic(pionek.pobierzKolor(), pionek.pobierzWspolrzednaX(), pionek.pobierzWspolrzednaY()) && pionek.pobierzKolor() == kolor) {
                bicieDostepne = true;
            }
        }
        return bicieDostepne;
    }
    @Override
    public void poczatkoweUstawienie() {
        plansza = new Plansza();
        plansza.ustawPionki(new ArrayList<>());
        plansza.ustawWymiar(WYMIAR_PLANSZY);
        for (int x = 0; x < WYMIAR_PLANSZY; x++) {
            for (int y = 0; y < WYMIAR_PLANSZY; y++) {
                if ((x + y) % 2 == 1) {
                    // TODO: ogarnąć builder
                }
                else {
                    //**********normalne warcaby
                    if (y < 3) {
//                        pobierzPole(x, y).ustawPionek(new Pionek('C', x, y));
                        plansza.wstawPionek(new Pionek('C', x, y));
                    }
                    else if (y > 4) {
//                        pobierzPole(x, y).ustawPionek(new Pionek('B', x, y));
                        plansza.wstawPionek(new Pionek('B', x, y));
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


    @Override
    public boolean zbijPionek(char kolorPionka, int xPocz, int yPocz, int xKonc, int yKonc) {
        if (!plansza.pobierzPionek(xPocz, yPocz).czyDamka()) {
            if (plansza.pobierzPionek((xPocz + xKonc) / 2, (yPocz + yKonc) / 2) == null) {
                return false;
            }
            boolean pionekPomiedzy = (kolorPionka == 'B' && plansza.pobierzPionek((xPocz + xKonc) / 2, (yPocz + yKonc) / 2).pobierzKolor() == 'C')
                    || (kolorPionka == 'C' && plansza.pobierzPionek((xPocz + xKonc) / 2, (yPocz + yKonc) / 2).pobierzKolor() == 'B');
            if (((kolorPionka == 'B' && (yKonc == yPocz - 2 /*|| yKonc == yPocz + 2*/) && (xKonc == xPocz - 2 || xKonc == xPocz + 2))
                    || (kolorPionka == 'C' && (/*yKonc == yPocz - 2 ||*/ yKonc == yPocz + 2) && (xKonc == xPocz - 2 || xKonc == xPocz + 2)))
                    && pionekPomiedzy) {
                plansza.pobierzPionek(xPocz, yPocz).przesun(xKonc, yKonc);
                plansza.usunPionek(plansza.pobierzPionek(xPocz, yPocz));
                plansza.usunPionek(plansza.pobierzPionek((xPocz + xKonc) / 2, (yPocz + yKonc) / 2));
                plansza.ustawLicznikRuchow(0);
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
                if (plansza.pobierzPionek(xPocz + (int) Math.signum(xKonc - xPocz) * i, yPocz + (int) Math.signum(yKonc - yPocz) * i) != null) {
                    xBity = xPocz + (int) Math.signum(xKonc - xPocz) * i;
                    yBity = yPocz + (int) Math.signum(yKonc - yPocz) * i;
                    if (plansza.pobierzPionek(xBity, yBity).pobierzKolor() == kolorPionka) {
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
            plansza.usunPionek(plansza.pobierzPionek(xBity, yBity));
            plansza.pobierzPionek(xPocz, yPocz).przesun(xKonc, yKonc);
            plansza.usunPionek(plansza.pobierzPionek(xPocz, yPocz));
            plansza.ustawLicznikRuchow(0);
            return true;
        }
        return false;
    }
    @Override
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
        if (istniejeBicie(kolorPionka)) {
            return false;
        }
        if (moznaNormalnyRuch(kolorPionka, xPocz, yPocz, xKonc, yKonc))
        {
            if (plansza.pobierzPionek(xPocz, yPocz).czyDamka()) {
                plansza.ustawLicznikRuchow(plansza.pobierzLicznikRuchow() + 1);
            }
            else {
                plansza.ustawLicznikRuchow(0);
            }
            plansza.pobierzPionek(xPocz, yPocz).przesun(xKonc, yKonc);
            plansza.usunPionek(plansza.pobierzPionek(xPocz, yPocz));
            return true;
        }
        return false;
    }
    @Override
    public boolean moznaNormalnyRuch(char kolorPionka, int xPocz, int yPocz, int xKonc, int yKonc) {
        if (!plansza.pobierzPionek(xPocz, yPocz).czyDamka()) {
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
                if (plansza.pobierzPionek(xPocz + (int) Math.signum(xKonc - xPocz) * i, yPocz + (int) Math.signum(yKonc - yPocz) * i) != null) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    @Override
    public boolean moznaDalejBic(char kolorPionka, int x, int y) {
        if (!plansza.pobierzPionek(x, y).czyDamka()) {
            return (   (kolorPionka == 'B' && x > 1 && y > 1 && plansza.pobierzPionek(x-1, y-1) != null && plansza.pobierzPionek(x-2, y-2)==null && plansza.pobierzPionek(x-1, y-1).pobierzKolor() != kolorPionka)
                    || (kolorPionka == 'C' && x > 1 && y < plansza.pobierzWymiar() - 2 && plansza.pobierzPionek(x-1, y+1) != null && plansza.pobierzPionek(x-2, y+2)==null && plansza.pobierzPionek(x-1, y+1).pobierzKolor() != kolorPionka)
                    || (kolorPionka == 'B' && x < plansza.pobierzWymiar() - 2 && y > 1 && plansza.pobierzPionek(x+1, y-1) != null && plansza.pobierzPionek(x+2, y-2)==null && plansza.pobierzPionek(x+1, y-1).pobierzKolor() != kolorPionka)
                    || (kolorPionka == 'C' && x < plansza.pobierzWymiar() - 2 && y < plansza.pobierzWymiar() - 2 && plansza.pobierzPionek(x+1, y+1) != null && plansza.pobierzPionek(x+2, y+2)==null && plansza.pobierzPionek(x+1, y+1).pobierzKolor() != kolorPionka));
        }
        else {
            int i = 1;
            while (x + i < plansza.pobierzWymiar() && y + i < plansza.pobierzWymiar() && plansza.pobierzPionek(x + i, y + i) == null) {
                i++;
            }
            if (x + i != plansza.pobierzWymiar() && y + i != plansza.pobierzWymiar() && plansza.pobierzPionek(x + i, y + i).pobierzKolor() != kolorPionka && plansza.ruszPionek(kolorPionka, x, y, x + (i + 1), y + (i + 1))) {
                return true;
            }
            i = 1;
            while (x + i < plansza.pobierzWymiar() && y - i >= 0 && plansza.pobierzPionek(x + i, y - i) == null) {
                i++;
            }
            if (x + i != plansza.pobierzWymiar() && y - i != -1 && plansza.pobierzPionek(x + i, y - i).pobierzKolor() != kolorPionka && plansza.ruszPionek(kolorPionka, x, y, x + (i + 1), y - (i + 1))) {
                return true;
            }
            i = 1;
            while (x - i >= 0 && y + i < plansza.pobierzWymiar() && plansza.pobierzPionek(x - i, y + i) == null) {
                i++;
            }
            if (x - i != -1 && y + i != plansza.pobierzWymiar() && plansza.pobierzPionek(x - i, y + i).pobierzKolor() != kolorPionka && plansza.ruszPionek(kolorPionka, x, y, x - (i + 1), y + (i + 1))) {
                return true;
            }
            i = 1;
            while (x - i >= 0 && y - i >= 0 && plansza.pobierzPionek(x - i, y - i) == null) {
                i++;
            }
            if (x - i != -1 && y - i != -1 && plansza.pobierzPionek(x - i, y - i).pobierzKolor() != kolorPionka && plansza.ruszPionek(kolorPionka, x, y, x - (i + 1), y - (i + 1))) {
                return true;
            }
            return false;
        }
    }
    public boolean czyRemis() {
        if (plansza.pobierzLicznikRuchow() == plansza.LIMIT_RUCHOW)
        {
            return true;
        }
        return false;
    }
    public boolean czyWygrana(char kolor) {
        for (Pionek pionek : plansza.pobierzPionki()) {
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
        if (   (plansza.ruszPionek(kolor, x, y, x + 1, y - 1) && moznaNormalnyRuch(kolor, x, y, x + 1, y - 1))
                || (plansza.ruszPionek(kolor, x, y, x - 1, y - 1) && moznaNormalnyRuch(kolor, x, y, x - 1, y - 1))
                || (plansza.ruszPionek(kolor, x, y, x + 1, y + 1) && moznaNormalnyRuch(kolor, x, y, x + 1, y + 1))
                || (plansza.ruszPionek(kolor, x, y, x - 1, y + 1) && moznaNormalnyRuch(kolor, x, y, x - 1, y + 1))) {
            return true;
        }
        return moznaDalejBic(pionek.pobierzKolor(), pionek.pobierzWspolrzednaX(), pionek.pobierzWspolrzednaY());
    }
}
