package serwer;

import serwer.model.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Klasa odpowiedzialna za przeprowadzania rozgrywki
 */
public class Gra {
    private Gracz obecnyGracz;
    private Plansza plansza;
    private final ZarzadcaBudowniczych zarzadcaBudowniczych = new ZarzadcaBudowniczych();

    /**
     * Klasa wewnętrzna dla każdego z graczy
     */
    class Gracz implements Runnable {
        private final char kolor;
        private volatile Gracz przeciwnik;
        private final Socket gniazdo;
        private Scanner odGracza;
        private PrintWriter doGracza;

        /**
         * Główny konstruktor dla Gracza
         * @param gniazdo gniazdo, z którym połączony jest gracz
         * @param kolor kolor danego gracza. 'B' - Biały; 'C' - Czarny
         */
        public Gracz(final Socket gniazdo, final char kolor) {
            this.gniazdo = gniazdo;
            this.kolor = kolor;
        }

        private void ustaw() throws IOException {
            odGracza = new Scanner(gniazdo.getInputStream());
            doGracza = new PrintWriter(gniazdo.getOutputStream(), true);
            doGracza.println("Witaj, " + kolor);
            if (kolor == 'B') {
                obecnyGracz = this;
                doGracza.println("PODAJ_WARIANT");
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
                } else if (komenda.startsWith("RUCH")) {
                    final String[] wspolrzedne = komenda.split(" ");
                    final int xPocz = Integer.parseInt(wspolrzedne[1]);
                    final int yPocz = Integer.parseInt(wspolrzedne[2]);
                    final int xKonc = Integer.parseInt(wspolrzedne[3]);
                    final int yKonc = Integer.parseInt(wspolrzedne[4]);

                    if (obecnyGracz.kolor == kolor && plansza.ruszPionek(kolor, xPocz, yPocz, xKonc, yKonc)) {
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
                                doGracza.println("WYGRANA " + kolor);
                                przeciwnik.doGracza.println("WYGRANA " + kolor);
                                obecnyGracz = null;
                            }
                            else if (zarzadcaBudowniczych.czyRemis()){
                                doGracza.println("REMIS");
                                przeciwnik.doGracza.println("REMIS");
                                obecnyGracz = null;
                            }
                            else {
                                obecnyGracz = obecnyGracz.przeciwnik;
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
                                    doGracza.println("WYGRANA " + kolor);
                                    przeciwnik.doGracza.println("WYGRANA " + kolor);
                                    obecnyGracz = null;
                                }
                                else if (zarzadcaBudowniczych.czyRemis()){
                                    doGracza.println("REMIS");
                                    przeciwnik.doGracza.println("REMIS");
                                    obecnyGracz = null;
                                }
                                else {
                                    obecnyGracz = obecnyGracz.przeciwnik;
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
                        while (przeciwnik == null) {
                            Thread.onSpinWait();
                        }
                        przeciwnik.doGracza.println("STWÓRZ_PLANSZĘ " + wariant);
                    }
                    else if (wariant == '2') {
                        planszaBudowniczy = new PlanszaWariantHiszpanskiBudowniczy();
                        doGracza.println("STWÓRZ_PLANSZĘ " + wariant);
                        while (przeciwnik == null) {
                            Thread.onSpinWait();
                        }
                        przeciwnik.doGracza.println("STWÓRZ_PLANSZĘ " + wariant);
                    }
                    else if (wariant == '3') {
                        planszaBudowniczy = new PlanszaWariantPolskiBudowniczy();
                        doGracza.println("STWÓRZ_PLANSZĘ " + wariant);
                        while (przeciwnik == null) {
                            Thread.onSpinWait();
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
            }
        }

        @Override
        public void run() {
            try {
                ustaw();
                przetwarzajKomendy();
            } catch (Exception e) {
                System.out.println("Coś jest nie tak z serwerem");
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
}
