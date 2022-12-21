package serwer;

import serwer.model.Pionek;
import serwer.model.Plansza;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Gra {
    private Gracz obecnyGracz;
    private Plansza plansza;
    class Gracz implements Runnable {
        private final char kolor;
        private volatile Gracz przeciwnik;
        private final Socket gniazdo;
        private Scanner odGracza;
        private PrintWriter doGracza;

        public Gracz(Socket gniazdo, char kolor) {
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
                String komenda = odGracza.nextLine();
                System.out.println(kolor + " " + komenda);
                if (komenda.startsWith("WYJDŹ")) {
                    return;
                } else if (komenda.startsWith("RUCH")) {
                    String[] wspolrzedne = komenda.split(" ");
                    int xPocz = Integer.parseInt(wspolrzedne[1]);
                    int yPocz = Integer.parseInt(wspolrzedne[2]);
                    int xKonc = Integer.parseInt(wspolrzedne[3]);
                    int yKonc = Integer.parseInt(wspolrzedne[4]);

                    if (obecnyGracz.kolor == kolor && plansza.ruszPionek(kolor, xPocz, yPocz, xKonc, yKonc)) {
                        boolean bicieDostepne = false;
                        for (Pionek pionek : plansza.pobierzPionki()) {
                            if (!bicieDostepne && plansza.moznaDalejBic(pionek.pobierzKolor(), pionek.pobierzWspolrzednaX(), pionek.pobierzWspolrzednaY()) && pionek.pobierzKolor() == obecnyGracz.kolor) {
                                doGracza.println("INFO Masz bicie");
                                bicieDostepne = true;
                            }
                        }
                        if (!bicieDostepne && plansza.normalnyRuch(kolor, xPocz, yPocz, xKonc, yKonc)) {
                            przeciwnik.doGracza.println("NORMALNY_RUCH_PRZECIWNIKA " + komenda.substring(5));
                            doGracza.println("POPRAWNY_NORMALNY_RUCH " + komenda.substring(5));

                            if ((kolor == 'B' && yKonc == 0) || (kolor == 'C' && yKonc == plansza.pobierzWymiar() - 1)) {
                                plansza.pobierzPionek(xKonc, yKonc).ustawDamka();
                                doGracza.println("PROMOCJA " + xKonc + " " + yKonc);
                                przeciwnik.doGracza.println("PROMOCJA " + xKonc + " " + yKonc);
                            }
                            //TODO potencjalnie wrzucić do metody
                            if (plansza.czyWygrana(kolor)) {
                                doGracza.println("WYGRANA " + kolor);
                                przeciwnik.doGracza.println("WYGRANA " + kolor);
                                obecnyGracz = null;
                            }
                            else if (plansza.czyRemis()){
                                doGracza.println("REMIS");
                                przeciwnik.doGracza.println("REMIS");
                                obecnyGracz = null;
                            }
                            else {
                                obecnyGracz = obecnyGracz.przeciwnik;
                            }
//                            obecnyGracz = obecnyGracz.przeciwnik;
                        } else if (plansza.zbijPionek(kolor, xPocz, yPocz, xKonc, yKonc)) {
                            przeciwnik.doGracza.println("BICIE_RUCH_PRZECIWNIKA " + komenda.substring(5));
                            doGracza.println("POPRAWNY_BICIE_RUCH " + komenda.substring(5));
                            if (!plansza.moznaDalejBic(kolor, xKonc, yKonc)) {
                                if ((kolor == 'B' && yKonc == 0) || (kolor == 'C' && yKonc == plansza.pobierzWymiar() - 1)) {
                                    plansza.pobierzPionek(xKonc, yKonc).ustawDamka();
                                    doGracza.println("PROMOCJA " + xKonc + " " + yKonc);
                                    przeciwnik.doGracza.println("PROMOCJA " + xKonc + " " + yKonc);
                                }
                                if (plansza.czyWygrana(kolor)) {
                                    doGracza.println("ESSA WIN BY " + kolor);
                                    przeciwnik.doGracza.println("ESSA WIN BY " + kolor);
                                    obecnyGracz = null;
                                }
                                else if (plansza.czyRemis()){
                                    doGracza.println("remis :((( " + kolor);
                                    przeciwnik.doGracza.println("remis (((((" + kolor);
                                    obecnyGracz = null;
                                }
                                else {
                                    obecnyGracz = obecnyGracz.przeciwnik;
                                }
//                                obecnyGracz = obecnyGracz.przeciwnik;
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
                else if (komenda.startsWith("WARIANT")) { //TODO: nie wysylac info o wariancie jesli gra juz trwa
                    char wariant = komenda.charAt(8);
                    if (wariant == '1') {
                        plansza = new Plansza(8, 'c');
                        doGracza.println("STWÓRZ_PLANSZĘ " + wariant);
                        while (przeciwnik == null) {
                            Thread.onSpinWait();
                        }
                        przeciwnik.doGracza.println("STWÓRZ_PLANSZĘ " + wariant);
                        //TODO: gra.wystartuj()
                    }
                    else if (wariant == '2') {

                    }
                    else if (wariant == '3') {

                    }
                    else {
                        doGracza.println("PODAJ_WARIANT");
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
                e.printStackTrace();
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
