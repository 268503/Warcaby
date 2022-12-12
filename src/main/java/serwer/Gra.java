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
                przeciwnik.doGracza.println("INFO Twój ruch");
            }
        }

        private void przetwarzajKomendy() {
            while (odGracza.hasNextLine()) {
                String komenda = odGracza.nextLine();
                System.out.println(komenda);
                if (komenda.startsWith("WYJDŹ")) {
                    return;
                }
                else if (komenda.startsWith("RUCH")) {
                    //TODO: zaimplementować obsługę komendy ruchu
//                    if (check()) {
//                        przeciwnik.wyslij(ruch)
//                    }
//                    else
//                    {
//                        gracz.wyslij(zlyruch)
//                    }
                    if (obecnyGracz.kolor == kolor && plansza.ruszPionek(kolor, Integer.parseInt(komenda.substring(5, 6)), Integer.parseInt(komenda.substring(6, 7)), Integer.parseInt(komenda.substring(7, 8)), Integer.parseInt(komenda.substring(8, 9)))) {
                        przeciwnik.doGracza.println("RUCH_PRZECIWNIKA " + komenda.substring(5));
                        doGracza.println("POPRAWNY_RUCH" + komenda.substring(5));
                        obecnyGracz = obecnyGracz.przeciwnik;
                    }
                    else {
                        doGracza.println("NIEPOPRAWNY_RUCH");
                    }
                }
                else if (komenda.startsWith("WARIANT")) { //TODO: nie wysylac info o wariancie jesli gra juz trwa
                    char wariant = komenda.charAt(8);
                    if (wariant == '1') {
                        plansza = new Plansza(8, 'c');
                        doGracza.println("STWORZ_WARIANT " + wariant);
                        while (przeciwnik == null) {
                            Thread.onSpinWait();
                        }
                        System.out.println("im out");
                        przeciwnik.doGracza.println("STWORZ_WARIANT " + wariant);
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

                }
            }

        }
    }
}
