package klient.kontroler;

import javafx.application.Platform;
import javafx.scene.control.TextInputDialog;
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
    private boolean rozpoczetoGre = false;
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
            planszaGUI.ustawKolor(kolor);
            System.out.println(kolor);
            char kolorPrzeciwnika = (kolor == 'B' ? 'C' : 'B');
            while (odSerwera.hasNextLine()) {
                odpowiedz = odSerwera.nextLine();
                System.out.println("serwer: " + odpowiedz);
                if (odpowiedz.startsWith("INFO")) {
                    //TODO: wyświetlić wiadomość
                    System.out.println(odpowiedz.substring(5));
                }
                else if (odpowiedz.startsWith("PODAJ_WARIANT")) {
                    Platform.runLater(() -> {
                        final TextInputDialog wprowadzanieWariantDialog = new TextInputDialog();
                        wprowadzanieWariantDialog.setTitle("Wariant");
                        wprowadzanieWariantDialog.setGraphic(null);
                        wprowadzanieWariantDialog.setHeaderText("Podaj wariant waracabów (1 - TODO, 2 - TODO, 3 - TODO)");
                        wprowadzanieWariantDialog.showAndWait();
                        String wybor = wprowadzanieWariantDialog.getEditor().getText();
                        doSerwera.println("WARIANT " + wybor);});
                }
                else if (odpowiedz.startsWith("STWÓRZ_PLANSZĘ")) {

                    //TODO: zrobić żeby null nie niszczył wszechświata

                    char wariant = odpowiedz.charAt(15);
                    if (wariant == '1') {
                        plansza = new Plansza(8, 'c');
                        // planszaGUI.ustawPlansze(plansza);
                        Platform.runLater(() -> {
                            planszaGUI.odswiez(plansza);
                        });
                    }
                    else if (wariant == '2') {

                    }
                    else if (wariant == '3') {

                    }
                }
                else if (odpowiedz.startsWith("POPRAWNY_NORMALNY_RUCH")) {

                    String[] wspolrzedne = odpowiedz.split(" ");
                    int xPocz = Integer.parseInt(wspolrzedne[1]);
                    int yPocz = Integer.parseInt(wspolrzedne[2]);
                    int xKonc = Integer.parseInt(wspolrzedne[3]);
                    int yKonc = Integer.parseInt(wspolrzedne[4]);

                    plansza.ruszPionek(xPocz, yPocz, xKonc, yKonc);

                    Platform.runLater(() -> {
                        planszaGUI.odswiez(plansza);
                    });
                }
                else if (odpowiedz.startsWith("POPRAWNY_BICIE_RUCH")) {
                    String[] wspolrzedne = odpowiedz.split(" ");
                    int xPocz = Integer.parseInt(wspolrzedne[1]);
                    int yPocz = Integer.parseInt(wspolrzedne[2]);
                    int xKonc = Integer.parseInt(wspolrzedne[3]);
                    int yKonc = Integer.parseInt(wspolrzedne[4]);

                    plansza.ruszPionek(xPocz, yPocz, xKonc, yKonc);
                    for (int i = 1; i < Math.abs(xKonc - xPocz); i++) {
                        plansza.usunPionek(xPocz + (int) Math.signum(xKonc - xPocz) * i, yPocz + (int) Math.signum(yKonc - yPocz) * i);
                    }
                    Platform.runLater(() -> {
                        planszaGUI.odswiez(plansza);
                    });
                }
                else if (odpowiedz.startsWith("NORMALNY_RUCH_PRZECIWNIKA")) {
                    String[] wspolrzedne = odpowiedz.split(" ");
                    int xPocz = Integer.parseInt(wspolrzedne[1]);
                    int yPocz = Integer.parseInt(wspolrzedne[2]);
                    int xKonc = Integer.parseInt(wspolrzedne[3]);
                    int yKonc = Integer.parseInt(wspolrzedne[4]);

                    plansza.ruszPionek(xPocz, yPocz, xKonc, yKonc);

                    Platform.runLater(() -> {
                        planszaGUI.odswiez(plansza);
                    });
                }
                else if (odpowiedz.startsWith("BICIE_RUCH_PRZECIWNIKA")) {
                    String[] wspolrzedne = odpowiedz.split(" ");
                    int xPocz = Integer.parseInt(wspolrzedne[1]);
                    int yPocz = Integer.parseInt(wspolrzedne[2]);
                    int xKonc = Integer.parseInt(wspolrzedne[3]);
                    int yKonc = Integer.parseInt(wspolrzedne[4]);

                    plansza.ruszPionek(xPocz, yPocz, xKonc, yKonc);

                    for (int i = 1; i < Math.abs(xKonc - xPocz); i++) {
                        plansza.usunPionek(xPocz + (int) Math.signum(xKonc - xPocz) * i, yPocz + (int) Math.signum(yKonc - yPocz) * i);
                    }
                    Platform.runLater(() -> {
                        planszaGUI.odswiez(plansza);
                    });
                }
                else if (odpowiedz.startsWith("PROMOCJA")) {
                    String[] wspolrzedne = odpowiedz.split(" ");
                    int x = Integer.parseInt(wspolrzedne[1]);
                    int y = Integer.parseInt(wspolrzedne[2]);

                    plansza.pobierzPole(x, y).pobierzPionek().ustawDamka();
                    Platform.runLater(() -> {
                        planszaGUI.odswiez(plansza);
                    });
                }
                else if (odpowiedz.startsWith("START")) {
                    rozpoczetoGre = true;
                }
                //TODO: zaimplementować obsługę reszty komend
            }
        } catch (Exception e) {
            System.out.println("cos jest nie tak z klientem");
            e.printStackTrace();
        }
    }

    public void wyslijKomende(String komenda) {
        if (rozpoczetoGre) {
            doSerwera.println(komenda);
        }
    }

    public Kontroler(Scanner odSerwera, PrintWriter doSerwera) {
        this.odSerwera = odSerwera;
        this.doSerwera = doSerwera;
    }
}
