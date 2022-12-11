package klient.kontroler;

import javafx.application.Platform;
import klient.model.Plansza;
import klient.widok.PionekGUI;
import klient.widok.PlanszaGUI;

import java.io.PrintWriter;
import java.util.Scanner;

public class Kontroler implements Runnable {

    private final Scanner odSerwera;
    private final PrintWriter doSerwera;
    private static char kolor;
    private PlanszaGUI planszaGUI;
    public void ustawPlanszeGUI(PlanszaGUI planszaGUI) {
        this.planszaGUI = planszaGUI;
    }
    public static char pobierzKolor() {
        return kolor;
    }
    @Override
    public void run() {
        try {
            String odpowiedz = odSerwera.nextLine();
            kolor = odpowiedz.charAt(7);
            System.out.println(kolor);
            char kolorPrzeciwnika = (kolor == 'B' ? 'C' : 'B');
            while (odSerwera.hasNextLine()) {
                odpowiedz = odSerwera.nextLine();
                if (odpowiedz.startsWith("INFO")) {
                    //TODO: wyświetlić wiadomość
                    System.out.println(odpowiedz.substring(5));
                }
                else if (odpowiedz.startsWith("RUCH_PRZECIWNIKA")) {
//                    "RUCH_PRZEICWNIka 1 2 3 4";
//                    plansza.ruszPionek(kolorPrzeciwnika, 1, 2, 3, 4);
//                    plansza.update();
                    //TODO: zaimplementować
                    System.out.println(odpowiedz);
                    planszaGUI.pobierzPlansze().ruszPionek(kolorPrzeciwnika, Integer.parseInt(odpowiedz.substring(17, 18)), Integer.parseInt(odpowiedz.substring(18, 19)), Integer.parseInt(odpowiedz.substring(19, 20)), Integer.parseInt(odpowiedz.substring(20, 21)));
//                    planszaGUI.odswiez();
                    Platform.runLater(() -> {
                        planszaGUI.odswiez();
                    });
                }
                //TODO: zaimplementować obsługę reszty komend
            }
        } catch (Exception e) {
            System.out.println("cos jest nie tak z klientem");
            e.printStackTrace();
        }
    }

    public void wyslijKomende(String komenda) {
        doSerwera.println(komenda);
    }

    public Kontroler(Scanner odSerwera, PrintWriter doSerwera) {
        this.odSerwera = odSerwera;
        this.doSerwera = doSerwera;
    }
}
