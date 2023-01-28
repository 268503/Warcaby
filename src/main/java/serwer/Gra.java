package serwer;

import serwer.model.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Klasa odpowiedzialna za przeprowadzanie rozgrywki
 */
public class Gra {
    private Gracz obecnyGracz;
    private Plansza plansza;
    private final ZarzadcaBudowniczych zarzadcaBudowniczych = new ZarzadcaBudowniczych();

    boolean graZBotem = false;
    public static class Gracz {
        protected char kolor;
        protected Scanner odGracza;
        protected PrintWriter doGracza;
        protected volatile Gracz przeciwnik;
        protected void wykonajRuch() {}
    }
    /**
     * Klasa wewnętrzna dla każdego z graczy
     */
    public class Czlowiek extends Gracz implements Runnable {
//        private final char kolor;
//        private volatile Gracz przeciwnik;
        private final Socket gniazdo;
//        private Scanner odGracza;
//        private PrintWriter doGracza;

        /**
         * Główny konstruktor dla Gracza
         * @param gniazdo gniazdo, z którym połączony jest gracz
         * @param kolor kolor danego gracza. 'B' - Biały; 'C' - Czarny
         */
        public Czlowiek(final Socket gniazdo, final char kolor) {
            this.gniazdo = gniazdo;
            this.kolor = kolor;
        }

        private void ustaw() throws IOException {
            odGracza = new Scanner(gniazdo.getInputStream());
            doGracza = new PrintWriter(gniazdo.getOutputStream(), true);
            doGracza.println("Witaj, " + kolor);
            if (kolor == 'B') {
                obecnyGracz = this;
                doGracza.println("CZY_BOT");
            } else {
                przeciwnik = obecnyGracz;
                przeciwnik.przeciwnik = this;
                doGracza.println("START");
                przeciwnik.doGracza.println("START");
            }
        }

        private void przetwarzajKomendy() {
            while (odGracza.hasNextLine() && obecnyGracz != null) {
                final String komenda = odGracza.nextLine();
                System.out.println(kolor + " " + komenda);
                if (komenda.startsWith("WYJDŹ")) {
                    return;
                }
                else if (komenda.startsWith("RUCH")) {
//                    System.out.println("siema");
                    final String[] wspolrzedne = komenda.split(" ");
                    final int xPocz = Integer.parseInt(wspolrzedne[1]);
                    final int yPocz = Integer.parseInt(wspolrzedne[2]);
                    final int xKonc = Integer.parseInt(wspolrzedne[3]);
                    final int yKonc = Integer.parseInt(wspolrzedne[4]);
//                    System.out.println(obecnyGracz.kolor + " " + kolor);
//                    if (obecnyGracz.kolor == kolor) {
//                        System.out.println("gita");
//                    }
//                    if (plansza.ruszPionek(kolor, xPocz, yPocz, xKonc, yKonc)) {
//                        System.out.println("gitb");
//                    }
                    if (obecnyGracz.kolor == kolor && plansza.ruszPionek(kolor, xPocz, yPocz, xKonc, yKonc)) {
//                        System.out.println("dzien nody");
                        if (zarzadcaBudowniczych.istniejeBicie(obecnyGracz.kolor)) {
                            doGracza.println("INFO Masz bicie");
                        }
                        if (zarzadcaBudowniczych.normalnyRuch(kolor, xPocz, yPocz, xKonc, yKonc)) {
                            przeciwnik.doGracza.println("NORMALNY_RUCH_PRZECIWNIKA " + komenda.substring(5));
                            doGracza.println("POPRAWNY_NORMALNY_RUCH " + komenda.substring(5));
                            if ((kolor == 'B' && yKonc == 0) || (kolor == 'C' && yKonc == plansza.pobierzWymiar() - 1)) {
                                plansza.pobierzPionek(xKonc, yKonc).ustawDamka();
                                doGracza.println("PROMOCJA " + xKonc + " " + yKonc);
                                przeciwnik.doGracza.println("PROMOCJA " + xKonc + " " + yKonc);
                            }
                            if (zarzadcaBudowniczych.czyWygrana(kolor)) {
                                doGracza.println("INFO Wygrywa " + kolor);
                                przeciwnik.doGracza.println("INFO Wygrywa " + kolor);
                                obecnyGracz = null;
                            }
                            else if (zarzadcaBudowniczych.czyRemis()){
                                doGracza.println("INFO Remis");
                                przeciwnik.doGracza.println("INFO Remis");
                                obecnyGracz = null;
                            }
                            else {
                                obecnyGracz = przeciwnik;
                                przeciwnik.wykonajRuch();
                            }
                        } else if (zarzadcaBudowniczych.zbijPionek(kolor, xPocz, yPocz, xKonc, yKonc)) {
                            przeciwnik.doGracza.println("BICIE_RUCH_PRZECIWNIKA " + komenda.substring(5));
                            doGracza.println("POPRAWNY_BICIE_RUCH " + komenda.substring(5));
                            if (!zarzadcaBudowniczych.moznaDalejBic(kolor, xKonc, yKonc)) {
                                if ((kolor == 'B' && yKonc == 0) || (kolor == 'C' && yKonc == plansza.pobierzWymiar() - 1)) {
                                    plansza.pobierzPionek(xKonc, yKonc).ustawDamka();
                                    doGracza.println("PROMOCJA " + xKonc + " " + yKonc);
                                    przeciwnik.doGracza.println("PROMOCJA " + xKonc + " " + yKonc);
                                }
                                if (zarzadcaBudowniczych.czyWygrana(kolor)) {
                                    doGracza.println("INFO Wygrywa " + kolor);
                                    przeciwnik.doGracza.println("INFO Wygrywa " + kolor);
                                    obecnyGracz = null;
                                }
                                else if (zarzadcaBudowniczych.czyRemis()){
                                    doGracza.println("INFO Remis");
                                    przeciwnik.doGracza.println("INFO Remis");
                                    obecnyGracz = null;
                                }
                                else {
                                    obecnyGracz = przeciwnik;
                                    przeciwnik.wykonajRuch();
                                }
                            }
                            else {
                                doGracza.println("INFO Kontynuuj bicie");
                            }
                        }
                    }
                    else {
                        doGracza.println("NIEPOPRAWNY_RUCH");
                    }
                }
                else if (komenda.startsWith("WARIANT")) {
                    char wariant = '-';
                    if (komenda.length() >= 9) {
                        wariant = komenda.charAt(8);
                    }
                    PlanszaBudowniczy planszaBudowniczy = null;
                    if (wariant == '1') {
                        planszaBudowniczy = new PlanszaWariantKlasycznyBudowniczy();
                        doGracza.println("STWÓRZ_PLANSZĘ " + wariant);
                        while (przeciwnik == null && !graZBotem) {
                            Thread.onSpinWait();
                        }
                        if (graZBotem) {
                            przeciwnik = new Bot('C', planszaBudowniczy, zarzadcaBudowniczych);
                            przeciwnik.przeciwnik = this;
                            obecnyGracz = this;
                            doGracza.println("START");
                        }
                        przeciwnik.doGracza.println("STWÓRZ_PLANSZĘ " + wariant);
                    }
                    else if (wariant == '2') {
                        planszaBudowniczy = new PlanszaWariantHiszpanskiBudowniczy();
                        doGracza.println("STWÓRZ_PLANSZĘ " + wariant);
                        while (przeciwnik == null && !graZBotem) {
                            Thread.onSpinWait();
                        }
                        if (graZBotem) {
                            przeciwnik = new Bot('C', planszaBudowniczy, zarzadcaBudowniczych);
                            przeciwnik.przeciwnik = this;
                            obecnyGracz = this;
                            doGracza.println("START");
                        }
                        przeciwnik.doGracza.println("STWÓRZ_PLANSZĘ " + wariant);
                    }
                    else if (wariant == '3') {
                        planszaBudowniczy = new PlanszaWariantPolskiBudowniczy();
                        doGracza.println("STWÓRZ_PLANSZĘ " + wariant);
                        while (przeciwnik == null && !graZBotem) {
                            Thread.onSpinWait();
                        }
                        if (graZBotem) {
                            przeciwnik = new Bot('C', planszaBudowniczy, zarzadcaBudowniczych);
                            przeciwnik.przeciwnik = this;
                            obecnyGracz = this;
                            doGracza.println("START");
                        }
                        przeciwnik.doGracza.println("STWÓRZ_PLANSZĘ " + wariant);
                    }
                    else {
                        doGracza.println("PODAJ_WARIANT");
                    }
                    if (planszaBudowniczy != null) {
                        zarzadcaBudowniczych.ustawPlanszaBudowniczy(planszaBudowniczy);
                        zarzadcaBudowniczych.skonstruujPlansze();
                        plansza = zarzadcaBudowniczych.pobierzPlansza();
                    }
                }
                else if (komenda.startsWith("BOT")) {
                    char wybor = komenda.charAt(4);
                    if (wybor == '1')
                    {
                        graZBotem = true;
                    }
                    doGracza.println("PODAJ_WARIANT");
                }
            }
        }



        @Override
        public void run() {
            try {
                ustaw();
                przetwarzajKomendy();
            } catch (Exception e) {
                System.out.println("Coś jest nie tak z serwerem");
                e.printStackTrace();
            } finally {
                if (przeciwnik != null && przeciwnik.doGracza != null) {
                    przeciwnik.doGracza.println("PRZECIWNIK_WYSZEDŁ");
                }
                try {
                    gniazdo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public class Bot extends Gracz{
        final private char kolor;
        final private PlanszaBudowniczy planszaBudowniczy;
        final private ZarzadcaBudowniczych zarzadcaBudowniczych;
        final private Random los = new Random();
        private final static int CZAS_MIEDZY_RUCHAMI = 500;

        public Bot(char kolor, PlanszaBudowniczy planszaBudowniczy, ZarzadcaBudowniczych zarzadcaBudowniczych) {
            this.kolor = kolor;
            this.planszaBudowniczy = planszaBudowniczy;
            this.zarzadcaBudowniczych = zarzadcaBudowniczych;
            odGracza = new Scanner(InputStream.nullInputStream());
            doGracza = new PrintWriter(Writer.nullWriter());
        }

        @Override
        public void wykonajRuch() {
            try {
                Thread.sleep(CZAS_MIEDZY_RUCHAMI);
            }
            catch (Exception e) {

            }
            List<Pionek> pionki = new ArrayList<>();
            List<Pionek> wszystkiePionki = planszaBudowniczy.pobierzPlansza().pobierzPionki();
            for (Pionek pionek : wszystkiePionki) {
                if (pionek.pobierzKolor() == kolor) {
                    pionki.add(pionek);
                }
            }
            int numerPionka = los.nextInt(pionki.size());
            Pionek pionekDoRuszenia = pionki.get(numerPionka);
            int kierunek;
            boolean wykonanoRuch = false;
            int wspolrzednaX;
            int wspolrzednaY;

            if (planszaBudowniczy.istniejeBicie(kolor)) {
                int wymaganaDlugoscBicia = planszaBudowniczy.znajdzNajlepszeBicie(plansza, kolor);
//                System.out.println("potrzeba bic " + wymaganaDlugoscBicia);
//                System.out.println("ISTNIEJE BICIE wybrano pionek x: " + pionekDoRuszenia.pobierzWspolrzednaX() + " y: " + pionekDoRuszenia.pobierzWspolrzednaY());
                while (planszaBudowniczy.rekurencyjneSzukanieBicia(pionekDoRuszenia, plansza, 0) != wymaganaDlugoscBicia) {
                    numerPionka = los.nextInt(pionki.size());
                    pionekDoRuszenia = pionki.get(numerPionka);
//                    System.out.println("wybrano pionek x: " + pionekDoRuszenia.pobierzWspolrzednaX() + " y: " + pionekDoRuszenia.pobierzWspolrzednaY());
                }
                wspolrzednaX = pionekDoRuszenia.pobierzWspolrzednaX();
                wspolrzednaY = pionekDoRuszenia.pobierzWspolrzednaY();
                while (!wykonanoRuch) {
                    kierunek = los.nextInt(4);
//                    System.out.println("losuje kierunek");
                    switch (kierunek) {
                        case 0:         // ++
                            if (pionekDoRuszenia.czyDamka()) {
                                int maksymalnaOdleglosc = Math.min(plansza.pobierzWymiar() - 1 - wspolrzednaX, plansza.pobierzWymiar() - 1 - wspolrzednaY);
                                if (maksymalnaOdleglosc > 0) {
                                    int odlegloscRuchu = los.nextInt(maksymalnaOdleglosc) + 1;
                                    if (plansza.ruszPionek(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX + odlegloscRuchu, wspolrzednaY + odlegloscRuchu)
                                            && (planszaBudowniczy.glebokoscPoBiciu(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX + odlegloscRuchu, wspolrzednaY + odlegloscRuchu) == wymaganaDlugoscBicia)
                                            && zarzadcaBudowniczych.zbijPionek(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX + odlegloscRuchu, wspolrzednaY + odlegloscRuchu)) {
                                        przeciwnik.doGracza.println("BICIE_RUCH_PRZECIWNIKA " + wspolrzednaX + " " + wspolrzednaY + " " + (wspolrzednaX + odlegloscRuchu) + " " + (wspolrzednaY + odlegloscRuchu));
                                        wymaganaDlugoscBicia--;
                                        wspolrzednaX = wspolrzednaX + odlegloscRuchu;
                                        wspolrzednaY = wspolrzednaY + odlegloscRuchu;
                                        try {
                                            Thread.sleep(CZAS_MIEDZY_RUCHAMI);
                                        }
                                        catch (Exception e) {

                                        }
                                        if (wymaganaDlugoscBicia == 0) {
                                            wykonanoRuch = true;
                                        }
                                    }
                                }
                            }
                            else {
                                if (plansza.ruszPionek(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX + 2, wspolrzednaY + 2)
                                        && (planszaBudowniczy.glebokoscPoBiciu(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX + 2, wspolrzednaY + 2) == wymaganaDlugoscBicia)
                                        && zarzadcaBudowniczych.zbijPionek(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX + 2, wspolrzednaY + 2)) {
                                    przeciwnik.doGracza.println("BICIE_RUCH_PRZECIWNIKA " + wspolrzednaX + " " + wspolrzednaY + " " + (wspolrzednaX + 2) + " " + (wspolrzednaY + 2));
                                    wymaganaDlugoscBicia--;
                                    wspolrzednaX = wspolrzednaX + 2;
                                    wspolrzednaY = wspolrzednaY + 2;
                                    try {
                                        Thread.sleep(CZAS_MIEDZY_RUCHAMI);
                                    }
                                    catch (Exception e) {

                                    }
                                    if (wymaganaDlugoscBicia == 0) {
                                        wykonanoRuch = true;
                                    }
                                }
                            }
                            break;
                        case 1:         // +-
                            if (pionekDoRuszenia.czyDamka()) {
                                int maksymalnaOdleglosc = Math.min(plansza.pobierzWymiar() - 1 - wspolrzednaX, wspolrzednaY);
                                if (maksymalnaOdleglosc > 0) {
                                    int odlegloscRuchu = los.nextInt(maksymalnaOdleglosc) + 1;
                                    if (plansza.ruszPionek(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX + odlegloscRuchu, wspolrzednaY - odlegloscRuchu)
                                            && (planszaBudowniczy.glebokoscPoBiciu(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX + odlegloscRuchu, wspolrzednaY - odlegloscRuchu) == wymaganaDlugoscBicia)
                                            && zarzadcaBudowniczych.zbijPionek(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX + odlegloscRuchu, wspolrzednaY - odlegloscRuchu)) {
                                        przeciwnik.doGracza.println("BICIE_RUCH_PRZECIWNIKA " + wspolrzednaX + " " + wspolrzednaY + " " + (wspolrzednaX + odlegloscRuchu) + " " + (wspolrzednaY - odlegloscRuchu));
                                        wymaganaDlugoscBicia--;
                                        wspolrzednaX = wspolrzednaX + odlegloscRuchu;
                                        wspolrzednaY = wspolrzednaY - odlegloscRuchu;
                                        try {
                                            Thread.sleep(CZAS_MIEDZY_RUCHAMI);
                                        }
                                        catch (Exception e) {

                                        }
                                        if (wymaganaDlugoscBicia == 0) {
                                            wykonanoRuch = true;
                                        }
                                    }
                                }
                            }
                            else {
                                if (plansza.ruszPionek(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX + 2, wspolrzednaY - 2)
                                        && (planszaBudowniczy.glebokoscPoBiciu(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX + 2, wspolrzednaY - 2) == wymaganaDlugoscBicia)
                                        && zarzadcaBudowniczych.zbijPionek(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX + 2, wspolrzednaY - 2)) {
                                    przeciwnik.doGracza.println("BICIE_RUCH_PRZECIWNIKA " + wspolrzednaX + " " + wspolrzednaY + " " + (wspolrzednaX + 2) + " " + (wspolrzednaY - 2));
                                    wymaganaDlugoscBicia--;
                                    wspolrzednaX = wspolrzednaX + 2;
                                    wspolrzednaY = wspolrzednaY - 2;
                                    try {
                                        Thread.sleep(CZAS_MIEDZY_RUCHAMI);
                                    }
                                    catch (Exception e) {

                                    }
                                    if (wymaganaDlugoscBicia == 0) {
                                        wykonanoRuch = true;
                                    }
                                }
                            }
                            break;
                        case 2:         // -+
                            if (pionekDoRuszenia.czyDamka()) {
                                int maksymalnaOdleglosc = Math.min(wspolrzednaX, plansza.pobierzWymiar() - 1 - wspolrzednaY);
                                if (maksymalnaOdleglosc > 0) {
                                    int odlegloscRuchu = los.nextInt(maksymalnaOdleglosc) + 1;
                                    if (plansza.ruszPionek(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX - odlegloscRuchu, wspolrzednaY + odlegloscRuchu)
                                            && (planszaBudowniczy.glebokoscPoBiciu(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX - odlegloscRuchu, wspolrzednaY + odlegloscRuchu) == wymaganaDlugoscBicia)
                                            && zarzadcaBudowniczych.zbijPionek(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX - odlegloscRuchu, wspolrzednaY + odlegloscRuchu)) {
                                        przeciwnik.doGracza.println("BICIE_RUCH_PRZECIWNIKA " + wspolrzednaX + " " + wspolrzednaY + " " + (wspolrzednaX - odlegloscRuchu) + " " + (wspolrzednaY + odlegloscRuchu));
                                        wymaganaDlugoscBicia--;
                                        wspolrzednaX = wspolrzednaX - odlegloscRuchu;
                                        wspolrzednaY = wspolrzednaY + odlegloscRuchu;
                                        try {
                                            Thread.sleep(CZAS_MIEDZY_RUCHAMI);
                                        }
                                        catch (Exception e) {

                                        }
                                        if (wymaganaDlugoscBicia == 0) {
                                            wykonanoRuch = true;
                                        }
                                    }
                                }
                            }
                            else {
                                if (plansza.ruszPionek(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX - 2, wspolrzednaY + 2)
                                        && (planszaBudowniczy.glebokoscPoBiciu(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX - 2, wspolrzednaY + 2) == wymaganaDlugoscBicia)
                                        && zarzadcaBudowniczych.zbijPionek(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX - 2, wspolrzednaY + 2)) {
                                    przeciwnik.doGracza.println("BICIE_RUCH_PRZECIWNIKA " + wspolrzednaX + " " + wspolrzednaY + " " + (wspolrzednaX - 2) + " " + (wspolrzednaY + 2));
                                    wymaganaDlugoscBicia--;
                                    wspolrzednaX = wspolrzednaX - 2;
                                    wspolrzednaY = wspolrzednaY + 2;
                                    try {
                                        Thread.sleep(CZAS_MIEDZY_RUCHAMI);
                                    }
                                    catch (Exception e) {

                                    }
                                    if (wymaganaDlugoscBicia == 0) {
                                        wykonanoRuch = true;
                                    }
                                }
                            }
                            break;
                        case 3:         // --
                            if (pionekDoRuszenia.czyDamka()) {
                                int maksymalnaOdleglosc = Math.min(wspolrzednaX, wspolrzednaY);
                                if (maksymalnaOdleglosc > 0) {
                                    int odlegloscRuchu = los.nextInt(maksymalnaOdleglosc) + 1;
                                    if (plansza.ruszPionek(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX - odlegloscRuchu, wspolrzednaY - odlegloscRuchu)
                                            && (planszaBudowniczy.glebokoscPoBiciu(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX - odlegloscRuchu, wspolrzednaY - odlegloscRuchu) == wymaganaDlugoscBicia)
                                            && zarzadcaBudowniczych.zbijPionek(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX - odlegloscRuchu, wspolrzednaY - odlegloscRuchu)) {
                                        przeciwnik.doGracza.println("BICIE_RUCH_PRZECIWNIKA " + wspolrzednaX + " " + wspolrzednaY + " " + (wspolrzednaX - odlegloscRuchu) + " " + (wspolrzednaY - odlegloscRuchu));
                                        wymaganaDlugoscBicia--;
                                        wspolrzednaX = wspolrzednaX - odlegloscRuchu;
                                        wspolrzednaY = wspolrzednaY - odlegloscRuchu;
                                        try {
                                            Thread.sleep(CZAS_MIEDZY_RUCHAMI);
                                        }
                                        catch (Exception e) {

                                        }
                                        if (wymaganaDlugoscBicia == 0) {
                                            wykonanoRuch = true;
                                        }
                                    }
                                }
                            }
                            else {
                                if (plansza.ruszPionek(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX - 2, wspolrzednaY - 2)
                                        && (planszaBudowniczy.glebokoscPoBiciu(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX - 2, wspolrzednaY - 2) == wymaganaDlugoscBicia)
                                        && zarzadcaBudowniczych.zbijPionek(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX - 2, wspolrzednaY - 2)) {
                                    przeciwnik.doGracza.println("BICIE_RUCH_PRZECIWNIKA " + wspolrzednaX + " " + wspolrzednaY + " " + (wspolrzednaX - 2) + " " + (wspolrzednaY - 2));
                                    wymaganaDlugoscBicia--;
                                    wspolrzednaX = wspolrzednaX - 2;
                                    wspolrzednaY = wspolrzednaY - 2;
                                    try {
                                        Thread.sleep(CZAS_MIEDZY_RUCHAMI);
                                    }
                                    catch (Exception e) {

                                    }
                                    if (wymaganaDlugoscBicia == 0) {
                                        wykonanoRuch = true;
                                    }
                                }
                            }
                            break;
                    }
                }
                obecnyGracz = obecnyGracz.przeciwnik;
            }
            else {
                while (!planszaBudowniczy.czyMoznaRuszyc(pionekDoRuszenia)) {
                    numerPionka = los.nextInt(pionki.size());
                    pionekDoRuszenia = pionki.get(numerPionka);
//                    System.out.println("wybrano pionek x: " + pionekDoRuszenia.pobierzWspolrzednaX() + " y: " + pionekDoRuszenia.pobierzWspolrzednaY());
                }
                wspolrzednaX = pionekDoRuszenia.pobierzWspolrzednaX();
                wspolrzednaY = pionekDoRuszenia.pobierzWspolrzednaY();
                while (!wykonanoRuch) {
                    kierunek = los.nextInt(4);
                    switch (kierunek) {
                        case 0:         // ++
                            if (pionekDoRuszenia.czyDamka()) {
                                int maksymalnaOdleglosc = Math.min(plansza.pobierzWymiar() - 1 - wspolrzednaX, plansza.pobierzWymiar() - 1 - wspolrzednaY);
                                if (maksymalnaOdleglosc > 0) {
                                    int odlegloscRuchu = los.nextInt(maksymalnaOdleglosc) + 1;
                                    if (planszaBudowniczy.pobierzPlansza().ruszPionek(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX + odlegloscRuchu, wspolrzednaY + odlegloscRuchu) && planszaBudowniczy.moznaNormalnyRuch(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX + odlegloscRuchu, wspolrzednaY + odlegloscRuchu)) {
                                        zarzadcaBudowniczych.normalnyRuch(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX + odlegloscRuchu, wspolrzednaY + odlegloscRuchu);
                                        przeciwnik.doGracza.println("NORMALNY_RUCH_PRZECIWNIKA " + wspolrzednaX + " " + wspolrzednaY + " " + (wspolrzednaX + odlegloscRuchu) + " " + (wspolrzednaY + odlegloscRuchu));
                                        obecnyGracz = obecnyGracz.przeciwnik;
                                        wykonanoRuch = true;
                                        wspolrzednaX += odlegloscRuchu;
                                        wspolrzednaY += odlegloscRuchu;
                                    }
                                }
                            } else {
                                if (planszaBudowniczy.pobierzPlansza().ruszPionek(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX + 1, wspolrzednaY + 1) && planszaBudowniczy.moznaNormalnyRuch(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX + 1, wspolrzednaY + 1)) {
                                    zarzadcaBudowniczych.normalnyRuch(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX + 1, wspolrzednaY + 1);
                                    przeciwnik.doGracza.println("NORMALNY_RUCH_PRZECIWNIKA " + wspolrzednaX + " " + wspolrzednaY + " " + (wspolrzednaX + 1) + " " + (wspolrzednaY + 1));
                                    obecnyGracz = obecnyGracz.przeciwnik;
                                    wykonanoRuch = true;
                                    wspolrzednaX += 1;
                                    wspolrzednaY += 1;
                                }
                            }
                            break;
                        case 1:         // +-
                            if (pionekDoRuszenia.czyDamka()) {
                                int maksymalnaOdleglosc = Math.min(plansza.pobierzWymiar() - 1 - wspolrzednaX, wspolrzednaY);
                                if (maksymalnaOdleglosc > 0) {
                                    int odlegloscRuchu = los.nextInt(maksymalnaOdleglosc) + 1;
                                    if (planszaBudowniczy.pobierzPlansza().ruszPionek(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX + odlegloscRuchu, wspolrzednaY - odlegloscRuchu) && planszaBudowniczy.moznaNormalnyRuch(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX + odlegloscRuchu, wspolrzednaY - odlegloscRuchu)) {
                                        zarzadcaBudowniczych.normalnyRuch(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX + odlegloscRuchu, wspolrzednaY - odlegloscRuchu);
                                        przeciwnik.doGracza.println("NORMALNY_RUCH_PRZECIWNIKA " + wspolrzednaX + " " + wspolrzednaY + " " + (wspolrzednaX + odlegloscRuchu) + " " + (wspolrzednaY - odlegloscRuchu));
                                        obecnyGracz = obecnyGracz.przeciwnik;
                                        wykonanoRuch = true;
                                        wspolrzednaX += odlegloscRuchu;
                                        wspolrzednaY -= odlegloscRuchu;
                                    }
                                }
                            }
                            else {
                                if (planszaBudowniczy.pobierzPlansza().ruszPionek(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX + 1, wspolrzednaY - 1) && planszaBudowniczy.moznaNormalnyRuch(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX + 1, wspolrzednaY - 1)) {
                                    zarzadcaBudowniczych.normalnyRuch(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX + 1, wspolrzednaY - 1);
                                    przeciwnik.doGracza.println("NORMALNY_RUCH_PRZECIWNIKA " + wspolrzednaX + " " + wspolrzednaY + " " + (wspolrzednaX + 1) + " " + (wspolrzednaY - 1));
                                    obecnyGracz = obecnyGracz.przeciwnik;
                                    wykonanoRuch = true;
                                    wspolrzednaX += 1;
                                    wspolrzednaY -= 1;
                                }
                            }
                            break;
                        case 2:         // -+
                            if (pionekDoRuszenia.czyDamka()) {
                                int maksymalnaOdleglosc = Math.min(wspolrzednaX, plansza.pobierzWymiar() - 1 - wspolrzednaY);
                                if (maksymalnaOdleglosc > 0) {
                                    int odlegloscRuchu = los.nextInt(maksymalnaOdleglosc) + 1;
                                    if (planszaBudowniczy.pobierzPlansza().ruszPionek(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX - odlegloscRuchu, wspolrzednaY + odlegloscRuchu) && planszaBudowniczy.moznaNormalnyRuch(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX - odlegloscRuchu, wspolrzednaY + odlegloscRuchu)) {
                                        zarzadcaBudowniczych.normalnyRuch(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX - odlegloscRuchu, wspolrzednaY + odlegloscRuchu);
                                        przeciwnik.doGracza.println("NORMALNY_RUCH_PRZECIWNIKA " + wspolrzednaX + " " + wspolrzednaY + " " + (wspolrzednaX - odlegloscRuchu) + " " + (wspolrzednaY + odlegloscRuchu));
                                        obecnyGracz = obecnyGracz.przeciwnik;
                                        wykonanoRuch = true;
                                        wspolrzednaX -= odlegloscRuchu;
                                        wspolrzednaY += odlegloscRuchu;
                                    }
                                }
                            }
                            else {
                                if (planszaBudowniczy.pobierzPlansza().ruszPionek(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX - 1, wspolrzednaY + 1) && planszaBudowniczy.moznaNormalnyRuch(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX - 1, wspolrzednaY + 1)) {
                                    zarzadcaBudowniczych.normalnyRuch(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX - 1, wspolrzednaY + 1);
                                    przeciwnik.doGracza.println("NORMALNY_RUCH_PRZECIWNIKA " + wspolrzednaX + " " + wspolrzednaY + " " + (wspolrzednaX - 1) + " " + (wspolrzednaY + 1));
                                    obecnyGracz = obecnyGracz.przeciwnik;
                                    wykonanoRuch = true;
                                    wspolrzednaX -= 1;
                                    wspolrzednaY += 1;
                                }
                            }
                            break;
                        case 3:         // --
                            if (pionekDoRuszenia.czyDamka()) {
                                int maksymalnaOdleglosc = Math.min(wspolrzednaX, wspolrzednaY);
                                if (maksymalnaOdleglosc > 0) {
                                    int odlegloscRuchu = los.nextInt(maksymalnaOdleglosc) + 1;
                                    if (planszaBudowniczy.pobierzPlansza().ruszPionek(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX - odlegloscRuchu, wspolrzednaY - odlegloscRuchu) && planszaBudowniczy.moznaNormalnyRuch(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX - odlegloscRuchu, wspolrzednaY - odlegloscRuchu)) {
                                        zarzadcaBudowniczych.normalnyRuch(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX - odlegloscRuchu, wspolrzednaY - odlegloscRuchu);
                                        przeciwnik.doGracza.println("NORMALNY_RUCH_PRZECIWNIKA " + wspolrzednaX + " " + wspolrzednaY + " " + (wspolrzednaX - odlegloscRuchu) + " " + (wspolrzednaY - odlegloscRuchu));
                                        obecnyGracz = obecnyGracz.przeciwnik;
                                        wykonanoRuch = true;
                                        wspolrzednaX -= odlegloscRuchu;
                                        wspolrzednaY -= odlegloscRuchu;
                                    }
                                }
                            }
                            else {
                                if (planszaBudowniczy.pobierzPlansza().ruszPionek(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX - 1, wspolrzednaY - 1) && planszaBudowniczy.moznaNormalnyRuch(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX - 1, wspolrzednaY - 1)) {
                                    zarzadcaBudowniczych.normalnyRuch(kolor, wspolrzednaX, wspolrzednaY, wspolrzednaX - 1, wspolrzednaY - 1);
                                    przeciwnik.doGracza.println("NORMALNY_RUCH_PRZECIWNIKA " + wspolrzednaX + " " + wspolrzednaY + " " + (wspolrzednaX - 1) + " " + (wspolrzednaY - 1));
                                    obecnyGracz = obecnyGracz.przeciwnik;
                                    wykonanoRuch = true;
                                    wspolrzednaX -= 1;
                                    wspolrzednaY -= 1;
                                }
                            }
                            break;
                    }
                }
            }
            if (wspolrzednaY == (kolor == 'C' ? plansza.pobierzWymiar() - 1 : 0)) {
                plansza.pobierzPionek(wspolrzednaX, wspolrzednaY).ustawDamka();
                przeciwnik.doGracza.println("PROMOCJA " + wspolrzednaX + " " + wspolrzednaY);
            }
        }
    }

}
