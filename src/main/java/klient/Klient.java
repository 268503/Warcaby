package klient;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import klient.kontroler.Kontroler;
import klient.widok.PlanszaGUI;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Klasa aplikacji klienckiej
 */
public class Klient extends Application {
    private PrintWriter doSerwera;
    private Kontroler kontroler;

    /**
     * Wywołuje metodę do tworzenia GUI
     * @param args argumenty z linii komend (nieużywane)
     */
    public static void main(final String[] args) {
        launch();
    }

    /**
     * Łączy się z serwerem, ustawia środowisko graficzne
     * @param scena Scena środowiska graficznego
     */
    @Override
    public void start(final Stage scena) {
        String adresSerwera;
        Thread watekKontrolera;

        scena.setOnCloseRequest(wydarzenie -> {
            try {
                doSerwera.println("WYJDŹ");
            } catch (Exception e) {
                System.out.println("Nie jesteś połączony z serwerem");
            }
        });

        final TextInputDialog wprowadzanieIpDialog = new TextInputDialog("localhost");
        wprowadzanieIpDialog.setTitle("IP");
        wprowadzanieIpDialog.setGraphic(null);
        wprowadzanieIpDialog.setHeaderText("Podaj adres IP serwera:");
        wprowadzanieIpDialog.showAndWait();
        adresSerwera = wprowadzanieIpDialog.getEditor().getText();
        try {
            final Socket gniazdo = new Socket(adresSerwera, 55555);
            final Scanner odSerwera = new Scanner(gniazdo.getInputStream());
            doSerwera = new PrintWriter(gniazdo.getOutputStream(), true);
            kontroler = Kontroler.pobierzInstancje();
            kontroler.ustawDaneWejsciowe(odSerwera);
            kontroler.ustawDaneWyjsciowe(doSerwera);
        } catch (Exception e) {
            System.out.println("Nie udało się połączyć z serwerem");
        }
        final BorderPane korzen = new BorderPane();
        final PlanszaGUI planszaGUI = new PlanszaGUI(kontroler);
        kontroler.ustawPlanszeGUI(planszaGUI);
        korzen.setCenter(planszaGUI);
        scena.setScene(new Scene(korzen, 720, 600));
        scena.show();
        try {
            watekKontrolera = new Thread(kontroler);
            watekKontrolera.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
