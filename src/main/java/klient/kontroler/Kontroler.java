package klient.kontroler;

import javafx.application.Platform;
import javafx.scene.control.TextInputDialog;
import klient.widok.PionekGUI;
import klient.widok.PlanszaGUI;
import klient.model.Plansza;

import java.io.PrintWriter;
import java.util.Scanner;

public class Kontroler implements Runnable {

    private final Scanner odSerwera;
    private final PrintWriter doSerwera;
    private static char kolor;
    private PlanszaGUI planszaGUI;
    private Plansza plansza;
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
                    plansza.ruszPionek(kolorPrzeciwnika, Integer.parseInt(odpowiedz.substring(17, 18)), Integer.parseInt(odpowiedz.substring(18, 19)), Integer.parseInt(odpowiedz.substring(19, 20)), Integer.parseInt(odpowiedz.substring(20, 21)));
                    Platform.runLater(() -> {
                        planszaGUI.odswiez();
                    });
                }
                else if (odpowiedz.startsWith("PODAJ_WARIANT")) {
                    Platform.runLater(() -> {
                        final TextInputDialog wprowadzanieWariantDialog = new TextInputDialog();
                        wprowadzanieWariantDialog.setTitle("Wariant");
                        wprowadzanieWariantDialog.setGraphic(null);
                        wprowadzanieWariantDialog.setHeaderText("Podaj wariant waracabów (1 - TODO, 2 - TODO, 3 - TODO");
                        wprowadzanieWariantDialog.showAndWait();
                        String wybor = wprowadzanieWariantDialog.getEditor().getText();
                        doSerwera.println("WARIANT " + wybor);});
                }
                else if (odpowiedz.startsWith("STWORZ_WARIANT")) {
                    char wariant = odpowiedz.charAt(15);
                    if (wariant == '1') {
                        plansza = new Plansza(8, 'c');
                        planszaGUI.ustawPlansze(plansza);
                        Platform.runLater(() -> {
                            planszaGUI.odswiez();
                        });
                    }
                    else if (wariant == '2') {

                    }
                    else if (wariant == '3') {

                    }
                }
                else if (odpowiedz.startsWith("POPRAWNY_RUCH")) {
                    plansza.ruszPionek(kolor, Integer.parseInt(odpowiedz.substring(13, 14)), Integer.parseInt(odpowiedz.substring(14, 15)), Integer.parseInt(odpowiedz.substring(15, 16)), Integer.parseInt(odpowiedz.substring(16, 17)));
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
