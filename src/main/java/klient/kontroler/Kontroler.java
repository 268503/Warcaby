package klient.kontroler;

import java.io.PrintWriter;
import java.util.Scanner;

public class Kontroler implements Runnable {

    private final Scanner odSerwera;
    private final PrintWriter doSerwera;
    @Override
    public void run() {
        try {
            String odpowiedz = odSerwera.nextLine();
            char kolor = odpowiedz.charAt(7);
            char kolorPrzeciwnika = (kolor == 'B' ? 'C' : 'B');
            while (odSerwera.hasNextLine()) {
                odpowiedz = odSerwera.nextLine();
                if (odpowiedz.startsWith("INFO")) {
                    //TODO: wyświetlić wiadomość
                    System.out.println(odpowiedz.substring(5));
                }
                else if (odpowiedz.startsWith("RUCH_PRZECIWNIKA")) {
                    //TODO: zaimplementować
                }
                //TODO: zaimplementować obsługę reszty komend
            }
        } catch (Exception e) {
            System.out.println("cos jest nie tak z klientem");
            e.printStackTrace();
        }
    }
    public Kontroler(Scanner odSerwera, PrintWriter doSerwera) {
        this.odSerwera = odSerwera;
        this.doSerwera = doSerwera;
    }
}
