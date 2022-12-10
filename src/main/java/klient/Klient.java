package klient;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import klient.kontroler.Kontroler;
import klient.model.Plansza;
import klient.widok.PlanszaGUI;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Klient extends Application {
    Socket gniazdo;
    Scanner odSerwera;
    PrintWriter doSerwera;
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
                // gniazdo.close();
            } catch (Exception e) {

            }
        });

        final TextInputDialog wprowadzanieIpDialog = new TextInputDialog();
        wprowadzanieIpDialog.setTitle("IP");
        wprowadzanieIpDialog.setGraphic(null);
        wprowadzanieIpDialog.setHeaderText("Podaj adres IP serwera:");
        wprowadzanieIpDialog.showAndWait();
        adresSerwera = wprowadzanieIpDialog.getEditor().getText();
        final BorderPane korzen = new BorderPane();
        final Plansza plansza = new Plansza(8, 'c');
        final PlanszaGUI planszaGUI = new PlanszaGUI(plansza);
//        planszaGUI.autosize();
        korzen.setCenter(planszaGUI);
        scena.setScene(new Scene(korzen, 720, 480));
        scena.show();
        try {
            gniazdo = new Socket(adresSerwera, 55555);
            odSerwera = new Scanner(gniazdo.getInputStream());
            doSerwera = new PrintWriter(gniazdo.getOutputStream(), true);
            Kontroler kontroler = new Kontroler(odSerwera, doSerwera);
            watekKontrolera = new Thread(kontroler);
            watekKontrolera.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
