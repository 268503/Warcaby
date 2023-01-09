package serwer.model;

import java.util.ArrayList;

public class PlanszaWariantKlasycznyBudowniczy extends PlanszaBudowniczy {
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

//       plansza.wstawPionek(new Pionek('B', 1, 6));
//       plansza.pobierzPionek(1, 6).ustawDamka();
//       plansza.wstawPionek(new Pionek('C', 3, 4));
//       plansza.wstawPionek(new Pionek('C', 6, 3));
//       plansza.wstawPionek(new Pionek('C', 6, 5));
//       plansza.wstawPionek(new Pionek('C', 4, 7));

//         //!!! testowe początkowe ułożenie planszy (edge case ultimate)
//        plansza.wstawPionek(new Pionek('B', 3, 5));
//        plansza.wstawPionek(new Pionek('C', 4, 4));
//        plansza.wstawPionek(new Pionek('C', 5, 5));
//        plansza.wstawPionek(new Pionek('B', 6, 6));
//        plansza.wstawPionek(new Pionek('C', 6, 2));
        // !!! testowe początkowe ułożeniee planszy (remis) (
//        wstawPionek(new Pionek('B', 0, 1));
//        pobierzPionek(0, 1).ustawDamka();
//        wstawPionek(new Pionek('B', 3, 6));
//        wstawPionek(new Pionek('C', 4, 3));
//        pobierzPionek(4, 3).ustawDamka();
//        wstawPionek(new Pionek('C', 2, 7));
        // !!! late game test
//        plansza.wstawPionek(new Pionek('B', 2, 3));
//        plansza.wstawPionek(new Pionek('C', 1, 0));
//        plansza.wstawPionek(new Pionek('B', 1, 2));
//        plansza.wstawPionek(new Pionek('B', 0, 1));
//        plansza.wstawPionek(new Pionek('C', 3, 6));
//        plansza.wstawPionek(new Pionek('C', 5, 2));
//        plansza.wstawPionek(new Pionek('B', 7, 4));
//        plansza.wstawPionek(new Pionek('C', 7, 0));
//        plansza.wstawPionek(new Pionek('C', 7, 6));
//        plansza.pobierzPionek(7, 6).ustawDamka();
        // nie dzialalo w hiszpanskim
//        plansza.wstawPionek(new Pionek('C', 0, 1) );
//        plansza.wstawPionek(new Pionek('B', 4, 1));
//        plansza.pobierzPionek(4, 1).ustawDamka();
//        plansza.wstawPionek(new Pionek('B', 4, 3));
//        plansza.pobierzPionek(4, 3).ustawDamka();
//        plansza.wstawPionek(new Pionek('B', 0, 5));
//        plansza.wstawPionek(new Pionek('B', 4, 5));
//        plansza.wstawPionek(new Pionek('B', 7, 6));
//        plansza.wstawPionek(new Pionek('B', 0, 7));
//        plansza.wstawPionek(new Pionek('B', 2, 7));
//        plansza.wstawPionek(new Pionek('C', 6, 7));
//        plansza.pobierzPionek(6, 7).ustawDamka();
        // lichess
//        plansza.wstawPionek(new Pionek('C', 2, 1));
//        plansza.wstawPionek(new Pionek('C', 1, 2));
//        plansza.wstawPionek(new Pionek('C', 1, 4));
//        plansza.wstawPionek(new Pionek('C', 3, 6));
//        plansza.wstawPionek(new Pionek('B', 4, 7));
//        plansza.pobierzPionek(4, 7).ustawDamka();

    }


    @Override
    public boolean zbijPionek(char kolorPionka, int xPocz, int yPocz, int xKonc, int yKonc) {
        int wymaganaDlugoscBicia = znajdzNajlepszeBicie(plansza, kolorPionka);
        System.out.println("to bicie daje " + glebokoscPoBiciu(kolorPionka, xPocz, yPocz, xKonc, yKonc) + " a trzeba " + wymaganaDlugoscBicia);
        if (!(glebokoscPoBiciu(kolorPionka, xPocz, yPocz, xKonc, yKonc) == wymaganaDlugoscBicia)) {
            return false;
        }
        if (!((xAktualny == -1 && yAktualny == -1) || (xAktualny == xPocz && yAktualny == yPocz))) {
            return false;
        }
        if (!plansza.pobierzPionek(xPocz, yPocz).czyDamka()) {
            if (plansza.pobierzPionek((xPocz + xKonc) / 2, (yPocz + yKonc) / 2) == null) {
                return false;
            }
            boolean pionekPomiedzy = (kolorPionka == 'B' && plansza.pobierzPionek((xPocz + xKonc) / 2, (yPocz + yKonc) / 2).pobierzKolor() == 'C')
                    || (kolorPionka == 'C' && plansza.pobierzPionek((xPocz + xKonc) / 2, (yPocz + yKonc) / 2).pobierzKolor() == 'B');
            if (((kolorPionka == 'B' && (yKonc == yPocz - 2 || yKonc == yPocz + 2) && (xKonc == xPocz - 2 || xKonc == xPocz + 2))
                    || (kolorPionka == 'C' && (yKonc == yPocz - 2 || yKonc == yPocz + 2) && (xKonc == xPocz - 2 || xKonc == xPocz + 2)))
                    && pionekPomiedzy) {
                plansza.pobierzPionek(xPocz, yPocz).przesun(xKonc, yKonc);
                plansza.usunPionek(plansza.pobierzPionek(xPocz, yPocz));
                plansza.usunPionek(plansza.pobierzPionek((xPocz + xKonc) / 2, (yPocz + yKonc) / 2));
                plansza.ustawLicznikRuchow(0);
                if (moznaDalejBic(kolorPionka, xKonc, yKonc)) {
                    xAktualny = xKonc;
                    yAktualny = yKonc;
                }
                else {
                    zresetujObecneWspolrzedne();
                }
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
                    licznik++;
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
            if (moznaDalejBic(kolorPionka, xKonc, yKonc)) {
                xAktualny = xKonc;
                yAktualny = yKonc;
            }
            else {
                zresetujObecneWspolrzedne();
            }
            return true;
        }
        return false;
    }
    @Override
    public boolean normalnyRuch(char kolorPionka, int xPocz, int yPocz, int xKonc, int yKonc) {
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
            return (kolorPionka == 'B' && yKonc == yPocz - 1 && (xKonc == xPocz - 1 || xKonc == xPocz + 1))
                    || (kolorPionka == 'C' && yKonc == yPocz + 1 && (xKonc == xPocz - 1 || xKonc == xPocz + 1));
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
    }
    @Override
    public boolean moznaDalejBic(char kolorPionka, int x, int y) {
        if (!plansza.pobierzPionek(x, y).czyDamka()) {
            return (   (x > 1 && y > 1 && plansza.pobierzPionek(x-1, y-1) != null && plansza.pobierzPionek(x-2, y-2)==null && plansza.pobierzPionek(x-1, y-1).pobierzKolor() != kolorPionka)
                    || (x > 1 && y < plansza.pobierzWymiar() - 2 && plansza.pobierzPionek(x-1, y+1) != null && plansza.pobierzPionek(x-2, y+2)==null && plansza.pobierzPionek(x-1, y+1).pobierzKolor() != kolorPionka)
                    || (x < plansza.pobierzWymiar() - 2 && y > 1 && plansza.pobierzPionek(x+1, y-1) != null && plansza.pobierzPionek(x+2, y-2)==null && plansza.pobierzPionek(x+1, y-1).pobierzKolor() != kolorPionka)
                    || (x < plansza.pobierzWymiar() - 2 && y < plansza.pobierzWymiar() - 2 && plansza.pobierzPionek(x+1, y+1) != null && plansza.pobierzPionek(x+2, y+2)==null && plansza.pobierzPionek(x+1, y+1).pobierzKolor() != kolorPionka));
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

    public int znajdzNajlepszeBicie(Plansza plansza, char kolor) {
//        System.out.println("znajduje najlepsze bicie !! - plansza = \n{");
//        for (Pionek pionek : plansza.pobierzPionki()) {
//            System.out.println("\t" + pionek.pobierzKolor() + " " + ((pionek.czyDamka() ? "Damka: " : "Pionek: ") + pionek.pobierzWspolrzednaX() + " " + pionek.pobierzWspolrzednaY()));
//        }
//        System.out.println("}");
        Plansza kopia = new Plansza();
        kopia.ustawWymiar(WYMIAR_PLANSZY);
        kopia.ustawPionki(plansza.pobierzPionki());
        int maksymalnaDlugoscBicia = 0;
        int dlugosc;
        for (int i = 0; i < WYMIAR_PLANSZY; i++) {
            for (int j = 0; j < WYMIAR_PLANSZY; j++) {
                Pionek pionek = kopia.pobierzPionek(i, j);
                if (pionek != null && pionek.pobierzKolor() == kolor) {
                    dlugosc = rekurencyjneSzukanieBicia(pionek, kopia, 0);
//                    System.out.println("patrze bicie dla " + pionek.pobierzWspolrzednaX() + " " + pionek.pobierzWspolrzednaY() + " dugosc " + dlugosc);
                    if (dlugosc > maksymalnaDlugoscBicia) {
                        maksymalnaDlugoscBicia = dlugosc;
                    }
                }
            }
        }
//        System.out.println("skonczylem patrzec bicia");
        return maksymalnaDlugoscBicia;
    }

    public int rekurencyjneSzukanieBicia(Pionek pionek, Plansza plansza, int glebokosc) {
        int maksymalnaGlebokosc = glebokosc;
        int nowaGlebokosc;
//        String taby = "";
//        for (int i = 0; i < glebokosc; i++) {
//            taby += "\t";
//        }
//        System.out.println(taby + (pionek.czyDamka() ? "Damka: " : "Pionek: ") + pionek.pobierzKolor() + ", " + pionek.pobierzWspolrzednaX() + ", " + pionek.pobierzWspolrzednaY());
        if (!pionek.czyDamka()) {
            Plansza kopia = new Plansza();
            kopia.ustawWymiar(WYMIAR_PLANSZY);
            kopia.ustawPionki(new ArrayList<>(plansza.pobierzPionki()));
            int x = pionek.pobierzWspolrzednaX();
            int y = pionek.pobierzWspolrzednaY();
            char kolorPionka = pionek.pobierzKolor();
            if (x > 1 && y > 1 && plansza.pobierzPionek(x - 1, y - 1) != null && plansza.pobierzPionek(x - 2, y - 2) == null && plansza.pobierzPionek(x - 1, y - 1).pobierzKolor() != kolorPionka) {
                Pionek nowyPionek = new Pionek(kolorPionka, x - 2, y - 2);
                kopia.wstawPionek(nowyPionek);
                kopia.usunPionek(kopia.pobierzPionek(x, y));
                kopia.usunPionek(kopia.pobierzPionek(x - 1, y - 1));
                nowaGlebokosc = rekurencyjneSzukanieBicia(nowyPionek, kopia, glebokosc + 1);
                if (nowaGlebokosc > maksymalnaGlebokosc) {
                    maksymalnaGlebokosc = nowaGlebokosc;
                }
            }
            kopia.ustawPionki(new ArrayList<>(plansza.pobierzPionki()));
            if (x > 1 && y < plansza.pobierzWymiar() - 2 && plansza.pobierzPionek(x - 1, y + 1) != null && plansza.pobierzPionek(x - 2, y + 2) == null && plansza.pobierzPionek(x - 1, y + 1).pobierzKolor() != kolorPionka) {
                Pionek nowyPionek = new Pionek(kolorPionka, x - 2, y + 2);
                kopia.wstawPionek(nowyPionek);
                kopia.usunPionek(kopia.pobierzPionek(x, y));
                kopia.usunPionek(kopia.pobierzPionek(x - 1, y + 1));
                nowaGlebokosc = rekurencyjneSzukanieBicia(nowyPionek, kopia, glebokosc + 1);
                if (nowaGlebokosc > maksymalnaGlebokosc) {
                    maksymalnaGlebokosc = nowaGlebokosc;
                }
            }
            kopia.ustawPionki(new ArrayList<>(plansza.pobierzPionki()));
            if (x < plansza.pobierzWymiar() - 2 && y > 1 && plansza.pobierzPionek(x + 1, y - 1) != null && plansza.pobierzPionek(x + 2, y - 2) == null && plansza.pobierzPionek(x + 1, y - 1).pobierzKolor() != kolorPionka) {
                Pionek nowyPionek = new Pionek(kolorPionka, x + 2, y - 2);
                kopia.wstawPionek(nowyPionek);
                kopia.usunPionek(kopia.pobierzPionek(x, y));
                kopia.usunPionek(kopia.pobierzPionek(x + 1, y - 1));
                nowaGlebokosc = rekurencyjneSzukanieBicia(nowyPionek, kopia, glebokosc + 1);
                if (nowaGlebokosc > maksymalnaGlebokosc) {
                    maksymalnaGlebokosc = nowaGlebokosc;
                }
            }
            kopia.ustawPionki(new ArrayList<>(plansza.pobierzPionki()));
            if (x < plansza.pobierzWymiar() - 2 && y < plansza.pobierzWymiar() - 2 && plansza.pobierzPionek(x + 1, y + 1) != null && plansza.pobierzPionek(x + 2, y + 2) == null && plansza.pobierzPionek(x + 1, y + 1).pobierzKolor() != kolorPionka) {
                Pionek nowyPionek = new Pionek(kolorPionka, x + 2, y + 2);
                kopia.wstawPionek(nowyPionek);
                kopia.usunPionek(kopia.pobierzPionek(x, y));
                kopia.usunPionek(kopia.pobierzPionek(x + 1, y + 1));
                nowaGlebokosc = rekurencyjneSzukanieBicia(nowyPionek, kopia, glebokosc + 1);
                if (nowaGlebokosc > maksymalnaGlebokosc) {
                    maksymalnaGlebokosc = nowaGlebokosc;
                }
            }
//            System.out.println("hmm " + maksymalnaGlebokosc);
            return maksymalnaGlebokosc;
        }
        else {
            int xPocz = pionek.pobierzWspolrzednaX();
            int yPocz = pionek.pobierzWspolrzednaY();
            int xKonc = xPocz;
            int yKonc = yPocz;
            while (xKonc < WYMIAR_PLANSZY - 1 && yKonc < WYMIAR_PLANSZY - 1) {
                xKonc++;
                yKonc++;
            }
            nowaGlebokosc = glebokoscBiciaDamka(plansza, pionek, xKonc, yKonc, glebokosc);
            if (nowaGlebokosc > maksymalnaGlebokosc) {
                maksymalnaGlebokosc = nowaGlebokosc;
            }

            xKonc = pionek.pobierzWspolrzednaX();
            yKonc = pionek.pobierzWspolrzednaY();
            while (xKonc > 0 && yKonc < WYMIAR_PLANSZY - 1) {
                xKonc--;
                yKonc++;
            }
            nowaGlebokosc = glebokoscBiciaDamka(plansza, pionek, xKonc, yKonc, glebokosc);
            if (nowaGlebokosc > maksymalnaGlebokosc) {
                maksymalnaGlebokosc = nowaGlebokosc;
            }

            xKonc = pionek.pobierzWspolrzednaX();
            yKonc = pionek.pobierzWspolrzednaY();
            while (xKonc < WYMIAR_PLANSZY - 1 && yKonc > 0) {
                xKonc++;
                yKonc--;
            }
            nowaGlebokosc = glebokoscBiciaDamka(plansza, pionek, xKonc, yKonc, glebokosc);
            if (nowaGlebokosc > maksymalnaGlebokosc) {
                maksymalnaGlebokosc = nowaGlebokosc;
            }

            xKonc = pionek.pobierzWspolrzednaX();
            yKonc = pionek.pobierzWspolrzednaY();
            while (xKonc > 0 && yKonc > 0) {
                xKonc--;
                yKonc--;
            }
            nowaGlebokosc = glebokoscBiciaDamka(plansza, pionek, xKonc, yKonc, glebokosc);
            if (nowaGlebokosc > maksymalnaGlebokosc) {
                maksymalnaGlebokosc = nowaGlebokosc;
            }
//            taby = "";
//            for (int i = 0; i < glebokosc; i++) {
//                taby += "\t";
//            }
//            System.out.println(taby + "hmm " + maksymalnaGlebokosc);
            return maksymalnaGlebokosc;
        }
    }

    public int glebokoscPoBiciu(char kolorPionka, int xPocz, int yPocz, int xKonc, int yKonc) {
        if (!moznaDalejBic(kolorPionka, xPocz, yPocz)) {
            return 0;
        }
        Plansza kopia = new Plansza();
        kopia.ustawWymiar(WYMIAR_PLANSZY);
        kopia.ustawPionki(new ArrayList<>(plansza.pobierzPionki()));
        kopia.usunPionek(plansza.pobierzPionek(xPocz, yPocz));
        int xBity = -1;
        int yBity = -1;
        for (int i = 1; i <= Math.abs(xKonc - xPocz); i++) {
            if (plansza.pobierzPionek(xPocz + (int) Math.signum(xKonc - xPocz) * i, yPocz + (int) Math.signum(yKonc - yPocz) * i) != null) {
                xBity = xPocz + (int) Math.signum(xKonc - xPocz) * i;
                yBity = yPocz + (int) Math.signum(yKonc - yPocz) * i;
            }
        }
        kopia.usunPionek(plansza.pobierzPionek(xBity, yBity));
        Pionek nowyPionek = new Pionek(kolorPionka, xKonc, yKonc);
        if (plansza.pobierzPionek(xPocz, yPocz).czyDamka()) {
            nowyPionek.ustawDamka();
        }
        kopia.wstawPionek(nowyPionek);
        return rekurencyjneSzukanieBicia(nowyPionek, kopia, 1);
    }

    public int glebokoscBiciaDamka(Plansza plansza, Pionek pionek, int xKonc, int yKonc, int glebokosc) {
        int xPocz = pionek.pobierzWspolrzednaX();
        int yPocz = pionek.pobierzWspolrzednaY();
        int licznik = 0;
        int xBity = -1;
        int yBity = -1;
        int nowaGlebokosc;
        int maksymalnaGlebokosc = glebokosc;
        for (int i = 1; i <= Math.abs(xKonc - xPocz); i++) {
            if (plansza.pobierzPionek(xPocz + (int) Math.signum(xKonc - xPocz) * i, yPocz + (int) Math.signum(yKonc - yPocz) * i) != null)
            {
                xBity = xPocz + (int) Math.signum(xKonc - xPocz) * i;
                yBity = yPocz + (int) Math.signum(yKonc - yPocz) * i;
                if (plansza.pobierzPionek(xBity, yBity).pobierzKolor() == pionek.pobierzKolor()) {
                    break;
                }
                licznik++;
            }
            if (licznik == 1 && plansza.pobierzPionek(xPocz + (int) Math.signum(xKonc - xPocz) * (i), yPocz + (int) Math.signum(yKonc - yPocz) * (i)) == null) {
                Plansza kopia = new Plansza();
                kopia.ustawWymiar(WYMIAR_PLANSZY);
                kopia.ustawPionki(new ArrayList<>(plansza.pobierzPionki()));
                kopia.usunPionek(plansza.pobierzPionek(xBity, yBity));
                kopia.usunPionek(plansza.pobierzPionek(xPocz, yPocz));
                Pionek nowyPionek = new Pionek(pionek.pobierzKolor(), xPocz + (int) Math.signum(xKonc - xPocz) * i, yPocz + (int) Math.signum(yKonc - yPocz) * i);
                nowyPionek.ustawDamka();
                kopia.wstawPionek(nowyPionek);
                nowaGlebokosc = rekurencyjneSzukanieBicia(nowyPionek, kopia, glebokosc + 1);
                if (nowaGlebokosc > maksymalnaGlebokosc) {
                    maksymalnaGlebokosc = nowaGlebokosc;
                }
            }
            if (licznik > 1) {
                break;
            }
        }
        return maksymalnaGlebokosc;
    }
}
