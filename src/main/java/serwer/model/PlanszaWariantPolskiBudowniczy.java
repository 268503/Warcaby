package serwer.model;

import java.util.ArrayList;

/**
 * Konkretny budowniczy dla wariantu polskiego warcabów
 */
public class PlanszaWariantPolskiBudowniczy extends PlanszaBudowniczy {
    private static final int WYMIAR_PLANSZY = 10;
    @Override
    public void poczatkoweUstawienie() {
        plansza = new Plansza();
        plansza.ustawPionki(new ArrayList<>());
        plansza.ustawWymiar(WYMIAR_PLANSZY);
        for (int x = 0; x < WYMIAR_PLANSZY; x++) {
            for (int y = 0; y < WYMIAR_PLANSZY; y++) {
                if ((x + y) % 2 == 1) {
                    if (y < 4) {
                        plansza.wstawPionek(new Pionek('C', x, y));
                    }
                    else if (y > 5) {
                        plansza.wstawPionek(new Pionek('B', x, y));
                    }
                }
            }
        }
    }

    @Override
    public boolean zbijPionek(final char kolorPionka, final int xPocz, final int yPocz, final int xKonc, final int yKonc) {
        final int wymaganaDlugoscBicia = znajdzNajlepszeBicie(plansza, kolorPionka);
        if (glebokoscPoBiciu(kolorPionka, xPocz, yPocz, xKonc, yKonc) != wymaganaDlugoscBicia) {
            return false;
        }
        if (!((xAktualny == -1 && yAktualny == -1) || (xAktualny == xPocz && yAktualny == yPocz))) {
            return false;
        }
        if (!plansza.pobierzPionek(xPocz, yPocz).czyDamka()) {
            if (plansza.pobierzPionek((xPocz + xKonc) / 2, (yPocz + yKonc) / 2) == null) {
                return false;
            }
            final boolean pionekPomiedzy = (kolorPionka == 'B' && plansza.pobierzPionek((xPocz + xKonc) / 2, (yPocz + yKonc) / 2).pobierzKolor() == 'C')
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
    public boolean normalnyRuch(final char kolorPionka, final int xPocz, final int yPocz, final int xKonc, final int yKonc) {
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
    public boolean moznaNormalnyRuch(final char kolorPionka, final int xPocz, final int yPocz, final int xKonc, final int yKonc) {
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
    public boolean moznaDalejBic(final char kolorPionka, final int x, final int y) {
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

    @Override
    public int znajdzNajlepszeBicie(final Plansza plansza, final char kolor) {
        final Plansza kopia = new Plansza();
        kopia.ustawWymiar(WYMIAR_PLANSZY);
        kopia.ustawPionki(plansza.pobierzPionki());
        int maksymalnaDlugoscBicia = 0;
        int dlugosc;
        for (int i = 0; i < WYMIAR_PLANSZY; i++) {
            for (int j = 0; j < WYMIAR_PLANSZY; j++) {
                final Pionek pionek = kopia.pobierzPionek(i, j);
                if (pionek != null && pionek.pobierzKolor() == kolor) {
                    dlugosc = rekurencyjneSzukanieBicia(pionek, kopia, 0);
                    if (dlugosc > maksymalnaDlugoscBicia) {
                        maksymalnaDlugoscBicia = dlugosc;
                    }
                }
            }
        }
        return maksymalnaDlugoscBicia;
    }

    @Override
    public int rekurencyjneSzukanieBicia(final Pionek pionek, final Plansza plansza, final int glebokosc) {
        int maksymalnaGlebokosc = glebokosc;
        int nowaGlebokosc;
        if (!pionek.czyDamka()) {
            final Plansza kopia = new Plansza();
            kopia.ustawWymiar(WYMIAR_PLANSZY);
            kopia.ustawPionki(new ArrayList<>(plansza.pobierzPionki()));
            final int x = pionek.pobierzWspolrzednaX();
            final int y = pionek.pobierzWspolrzednaY();
            nowaGlebokosc = glebokoscBiciaPionkiem(pionek, kopia, x - 2, y - 2, glebokosc);
            if (nowaGlebokosc > maksymalnaGlebokosc) {
                maksymalnaGlebokosc = nowaGlebokosc;
            }
            kopia.ustawPionki(new ArrayList<>(plansza.pobierzPionki()));

            nowaGlebokosc = glebokoscBiciaPionkiem(pionek, kopia, x - 2, y + 2, glebokosc);
            if (nowaGlebokosc > maksymalnaGlebokosc) {
                maksymalnaGlebokosc = nowaGlebokosc;
            }
            kopia.ustawPionki(new ArrayList<>(plansza.pobierzPionki()));

            nowaGlebokosc = glebokoscBiciaPionkiem(pionek, kopia, x + 2, y - 2, glebokosc);
            if (nowaGlebokosc > maksymalnaGlebokosc) {
                maksymalnaGlebokosc = nowaGlebokosc;
            }
            kopia.ustawPionki(new ArrayList<>(plansza.pobierzPionki()));

            nowaGlebokosc = glebokoscBiciaPionkiem(pionek, kopia, x + 2, y + 2, glebokosc);
            if (nowaGlebokosc > maksymalnaGlebokosc) {
                maksymalnaGlebokosc = nowaGlebokosc;
            }
        }
        else {
            final int xPocz = pionek.pobierzWspolrzednaX();
            final int yPocz = pionek.pobierzWspolrzednaY();
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
        }
        return maksymalnaGlebokosc;
    }

    @Override
    public int glebokoscPoBiciu(final char kolorPionka, final int xPocz, final int yPocz, final int xKonc, final int yKonc) {
        if (!moznaDalejBic(kolorPionka, xPocz, yPocz)) {
            return 0;
        }
        final Plansza kopia = new Plansza();
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
        final Pionek nowyPionek = new Pionek(kolorPionka, xKonc, yKonc);
        if (plansza.pobierzPionek(xPocz, yPocz).czyDamka()) {
            nowyPionek.ustawDamka();
        }
        kopia.wstawPionek(nowyPionek);
        return rekurencyjneSzukanieBicia(nowyPionek, kopia, 1);
    }

    @Override
    public int glebokoscBiciaPionkiem(final Pionek pionek, final Plansza plansza, final int xKonc, final int yKonc, final int glebokosc) {
        final int xPocz = pionek.pobierzWspolrzednaX();
        final int yPocz = pionek.pobierzWspolrzednaY();
        final char kolorPionka = pionek.pobierzKolor();
        if (plansza.ruszPionek(kolorPionka, xPocz, yPocz, xKonc, yKonc) && plansza.pobierzPionek((xPocz + xKonc) / 2, (yPocz + yKonc) / 2) != null && plansza.pobierzPionek((xPocz + xKonc) / 2, (yPocz + yKonc) / 2).pobierzKolor() != kolorPionka) {
            final Pionek nowyPionek = new Pionek(kolorPionka, xKonc, yKonc);
            plansza.wstawPionek(nowyPionek);
            plansza.usunPionek(plansza.pobierzPionek(xPocz, yPocz));
            plansza.usunPionek(plansza.pobierzPionek((xPocz + xKonc) / 2, (yPocz + yKonc) / 2));
            return rekurencyjneSzukanieBicia(nowyPionek, plansza, glebokosc + 1);
        }
        return glebokosc;
    }

    @Override
    public int glebokoscBiciaDamka(final Plansza plansza, final Pionek pionek, final int xKonc, final int yKonc, final int glebokosc) {
        final int xPocz = pionek.pobierzWspolrzednaX();
        final int yPocz = pionek.pobierzWspolrzednaY();
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
                final Plansza kopia = new Plansza();
                kopia.ustawWymiar(WYMIAR_PLANSZY);
                kopia.ustawPionki(new ArrayList<>(plansza.pobierzPionki()));
                kopia.usunPionek(plansza.pobierzPionek(xBity, yBity));
                kopia.usunPionek(plansza.pobierzPionek(xPocz, yPocz));
                final Pionek nowyPionek = new Pionek(pionek.pobierzKolor(), xPocz + (int) Math.signum(xKonc - xPocz) * i, yPocz + (int) Math.signum(yKonc - yPocz) * i);
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
