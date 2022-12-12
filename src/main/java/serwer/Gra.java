package serwer;

import serwer.model.Plansza;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Gra {
    private Gracz obecnyGracz;
    private Plansza plansza;
    //    public Gracz pobierzObecnyGracz() {
//        return obecnyGracz;
//    }
//
//    public void ustawObecnyGracz(Gracz obecnyGracz) {
//        this.obecnyGracz = obecnyGracz;
//    }
    class Gracz implements Runnable {
        private char kolor;
        private volatile Gracz przeciwnik;
        private Socket gniazdo;
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
            }
            else {
                przeciwnik = obecnyGracz;
                przeciwnik.przeciwnik = this;
                // przeciwnik.doGracza.println("INFO Twój ruch");
                doGracza.println("START");
                przeciwnik.doGracza.println("START");
            }
        }

        private void przetwarzajKomendy() {
            while (odGracza.hasNextLine()) {
                String komenda = odGracza.nextLine();
                System.out.println(kolor + " " + komenda);
                if (komenda.startsWith("WYJDŹ")) {
                    return;
                }
                else if (komenda.startsWith("RUCH")) {
                    String[] wspolrzedne = komenda.split(" ");
                    int xPocz = Integer.parseInt(wspolrzedne[1]);
                    int yPocz = Integer.parseInt(wspolrzedne[2]);
                    int xKonc = Integer.parseInt(wspolrzedne[3]);
                    int yKonc = Integer.parseInt(wspolrzedne[4]);

//                    if (obecnyGracz.kolor == kolor && plansza.ruszPionek(kolor, xPocz, yPocz, xKonc, yKonc)) {
//                        przeciwnik.doGracza.println("RUCH_PRZECIWNIKA " + komenda.substring(5));
//                        doGracza.println("POPRAWNY_RUCH " + komenda.substring(5));
//                        obecnyGracz = obecnyGracz.przeciwnik;
//                    }
                    if (obecnyGracz.kolor == kolor && plansza.ruszPionek(kolor, xPocz, yPocz, xKonc, yKonc)) {
                        if (plansza.normalnyRuch(kolor, xPocz, yPocz, xKonc, yKonc)) {
                            przeciwnik.doGracza.println("NORMALNY_RUCH_PRZECIWNIKA " + komenda.substring(5));
                            doGracza.println("POPRAWNY_NORMALNY_RUCH " + komenda.substring(5));
                            obecnyGracz = obecnyGracz.przeciwnik;
                        }
                        else if (plansza.zbijPionek(kolor, xPocz, yPocz, xKonc, yKonc)) {
                            przeciwnik.doGracza.println("BICIE_RUCH_PRZECIWNIKA " + komenda.substring(5));
                            doGracza.println("POPRAWNY_BICIE_RUCH " + komenda.substring(5));
                            obecnyGracz = obecnyGracz.przeciwnik;
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
                System.out.println("cos jest nie tak z serwerem");
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
