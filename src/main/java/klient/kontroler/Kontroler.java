package klient.kontroler;

import javafx.application.Platform;
import javafx.scene.control.TextInputDialog;
import klient.widok.PlanszaGUI;
import klient.model.Plansza;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Klasa pośrednicząca między modelem a widokiem, odpowiedzialna za komunikację z serwerem
 */
public class Kontroler implements Runnable {

    private static final Kontroler instancja = new Kontroler();
    private Scanner odSerwera;
    private PrintWriter doSerwera;
    private PlanszaGUI planszaGUI;
    private Plansza plansza;
    private boolean rozpoczetoGre = false;

    /**
     * Przypisuje planszę widokową
     * @param planszaGUI Obiekt planszy do przypisania
     */
    public void ustawPlanszeGUI(final PlanszaGUI planszaGUI) {
        this.planszaGUI = planszaGUI;
    }

    @Override
    public void run() {
        try {
            String odpowiedz = odSerwera.nextLine();
            final char kolor = odpowiedz.charAt(7);
            planszaGUI.ustawKolor(kolor);
            System.out.println(kolor);
            while (odSerwera.hasNextLine()) {
                odpowiedz = odSerwera.nextLine();
                if (odpowiedz.startsWith("INFO")) {
                    System.out.println(odpowiedz.substring(5));
                }
                else if (odpowiedz.startsWith("PODAJ_WARIANT")) {
                    Platform.runLater(() -> {
                        final TextInputDialog wprowadzanieWariantDialog = new TextInputDialog();
                        wprowadzanieWariantDialog.setTitle("Wariant");
                        wprowadzanieWariantDialog.setGraphic(null);
                        wprowadzanieWariantDialog.setHeaderText("Podaj wariant waracabów (1 - warcaby klasyczne, 2 - warcaby hiszpanskie, 3 - warcaby polskie)");
                        wprowadzanieWariantDialog.showAndWait();
                        final String wybor = wprowadzanieWariantDialog.getEditor().getText();
                        doSerwera.println("WARIANT " + wybor);});
                }
                else if (odpowiedz.startsWith("STWÓRZ_PLANSZĘ")) {
                    final char wariant = odpowiedz.charAt(15);
                    if (wariant == '1') {
                        plansza = new Plansza(8, 'c', 1);
                        Platform.runLater(() -> planszaGUI.odswiez(plansza));
                    }
                    else if (wariant == '2') {
                        plansza = new Plansza(8, 'j', 2);
                        Platform.runLater(() -> planszaGUI.odswiez(plansza));
                    }
                    else if (wariant == '3') {
                        plansza = new Plansza(10, 'c', 3);
                        Platform.runLater(() -> planszaGUI.odswiez(plansza));
                    }
                }
                else if (odpowiedz.startsWith("POPRAWNY_NORMALNY_RUCH")) {

                    final String[] wspolrzedne = odpowiedz.split(" ");
                    final int xPocz = Integer.parseInt(wspolrzedne[1]);
                    final int yPocz = Integer.parseInt(wspolrzedne[2]);
                    final int xKonc = Integer.parseInt(wspolrzedne[3]);
                    final int yKonc = Integer.parseInt(wspolrzedne[4]);

                    plansza.ruszPionek(xPocz, yPocz, xKonc, yKonc);

                    Platform.runLater(() -> planszaGUI.odswiez(plansza));
                }
                else if (odpowiedz.startsWith("POPRAWNY_BICIE_RUCH")) {
                    final String[] wspolrzedne = odpowiedz.split(" ");
                    final int xPocz = Integer.parseInt(wspolrzedne[1]);
                    final int yPocz = Integer.parseInt(wspolrzedne[2]);
                    final int xKonc = Integer.parseInt(wspolrzedne[3]);
                    final int yKonc = Integer.parseInt(wspolrzedne[4]);

                    plansza.ruszPionek(xPocz, yPocz, xKonc, yKonc);
                    for (int i = 1; i < Math.abs(xKonc - xPocz); i++) {
                        plansza.usunPionek(xPocz + (int) Math.signum(xKonc - xPocz) * i, yPocz + (int) Math.signum(yKonc - yPocz) * i);
                    }
                    Platform.runLater(() -> planszaGUI.odswiez(plansza));
                }
                else if (odpowiedz.startsWith("NORMALNY_RUCH_PRZECIWNIKA")) {
                    final String[] wspolrzedne = odpowiedz.split(" ");
                    final int xPocz = Integer.parseInt(wspolrzedne[1]);
                    final int yPocz = Integer.parseInt(wspolrzedne[2]);
                    final int xKonc = Integer.parseInt(wspolrzedne[3]);
                    final int yKonc = Integer.parseInt(wspolrzedne[4]);

                    plansza.ruszPionek(xPocz, yPocz, xKonc, yKonc);

                    Platform.runLater(() -> planszaGUI.odswiez(plansza));
                }
                else if (odpowiedz.startsWith("BICIE_RUCH_PRZECIWNIKA")) {
                    final String[] wspolrzedne = odpowiedz.split(" ");
                    final int xPocz = Integer.parseInt(wspolrzedne[1]);
                    final int yPocz = Integer.parseInt(wspolrzedne[2]);
                    final int xKonc = Integer.parseInt(wspolrzedne[3]);
                    final int yKonc = Integer.parseInt(wspolrzedne[4]);

                    plansza.ruszPionek(xPocz, yPocz, xKonc, yKonc);

                    for (int i = 1; i < Math.abs(xKonc - xPocz); i++) {
                        plansza.usunPionek(xPocz + (int) Math.signum(xKonc - xPocz) * i, yPocz + (int) Math.signum(yKonc - yPocz) * i);
                    }
                    Platform.runLater(() -> planszaGUI.odswiez(plansza));
                }
                else if (odpowiedz.startsWith("PROMOCJA")) {
                    final String[] wspolrzedne = odpowiedz.split(" ");
                    final int x = Integer.parseInt(wspolrzedne[1]);
                    final int y = Integer.parseInt(wspolrzedne[2]);

                    plansza.pobierzPole(x, y).pobierzPionek().ustawDamka();
                    Platform.runLater(() -> planszaGUI.odswiez(plansza));
                }
                else if (odpowiedz.startsWith("START")) {
                    rozpoczetoGre = true;
                }
            }
        } catch (Exception e) {
            System.out.println("cos jest nie tak z klientem");
        }
    }

    /**
     * Przesyła komunikat do serwera
     * @param komenda Komunikat do przekazania
     */
    public void wyslijKomende(final String komenda) {
        if (rozpoczetoGre) {
            doSerwera.println(komenda);
        }
    }

    private Kontroler() {}

    /**
     * Pobiera instancję kontrolera, będącego singletonem
     * @return instancja kontrolera
     */
    public static Kontroler pobierzInstancje() {
        return instancja;
    }

    /**
     * Ustawia obiekt odpowiadający za dane wejściowe
     * @param odSerwera obiekt odpowiadający za dane wejściowe
     */
    public void ustawDaneWejsciowe (final Scanner odSerwera) {
        this.odSerwera = odSerwera;
    }

    /**
     * Ustawia obiekt odpowiadający za dane wyjściowe
     * @param doSerwera obiekt odpowiadający za dane wyjściowe
     */
    public void ustawDaneWyjsciowe (final PrintWriter doSerwera) {
        this.doSerwera = doSerwera;
    }
}
