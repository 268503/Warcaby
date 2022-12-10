package serwer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Gra {
    private Gracz obecnyGracz;

    //    public Gracz pobierzObecnyGracz() {
//        return obecnyGracz;
//    }
//
//    public void ustawObecnyGracz(Gracz obecnyGracz) {
//        this.obecnyGracz = obecnyGracz;
//    }
    class Gracz implements Runnable {
        private char kolor;
        private Gracz przeciwnik;
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
            doGracza.println("Witaj, Biały");
            if (kolor == 'B') {
                obecnyGracz = this;
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
                }
            }
        }

        @Override
        public void run() {
            try {
                ustaw();
                przetwarzajKomendy();
            } catch (Exception e) {
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
