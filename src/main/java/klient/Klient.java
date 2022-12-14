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

public class Klient extends Application {
    private Socket gniazdo;
    private Scanner odSerwera;
    private PrintWriter doSerwera;
    private Kontroler kontroler;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage scena) {
        String adresSerwera;
        Thread watekKontrolera;

        scena.setOnCloseRequest(wydarzenie -> {
            try {
                doSerwera.println("WYJDŹ");
            } catch (Exception e) {

            }
        });

        final TextInputDialog wprowadzanieIpDialog = new TextInputDialog("localhost");
        wprowadzanieIpDialog.setTitle("IP");
        wprowadzanieIpDialog.setGraphic(null);
        wprowadzanieIpDialog.setHeaderText("Podaj adres IP serwera:");
        wprowadzanieIpDialog.showAndWait();
        adresSerwera = wprowadzanieIpDialog.getEditor().getText();
        try {
            gniazdo = new Socket(adresSerwera, 55555);
            odSerwera = new Scanner(gniazdo.getInputStream());
            doSerwera = new PrintWriter(gniazdo.getOutputStream(), true);
            kontroler = new Kontroler(odSerwera, doSerwera);
        } catch (Exception e) {

        }
        final BorderPane korzen = new BorderPane();
        final PlanszaGUI planszaGUI = new PlanszaGUI(kontroler);
        kontroler.ustawPlanszeGUI(planszaGUI);
        korzen.setCenter(planszaGUI);
        scena.setScene(new Scene(korzen, 720, 480));
        scena.show();
        try {
            watekKontrolera = new Thread(kontroler);
            watekKontrolera.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
