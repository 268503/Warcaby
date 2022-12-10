package klient;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import klient.kontroler.Kontroler;

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
        final TextInputDialog wprowadzanieIpDialog = new TextInputDialog();
        wprowadzanieIpDialog.setTitle("IP");
        wprowadzanieIpDialog.setGraphic(null);
        wprowadzanieIpDialog.setHeaderText("Podaj adres IP serwera:");
        wprowadzanieIpDialog.showAndWait();
        adresSerwera = wprowadzanieIpDialog.getEditor().getText();
        final BorderPane korzen = new BorderPane();
        final Button przyciskRuch = new Button("RUCH");
        final Button przyciskWyjdz = new Button("WYJDŹ");
        przyciskRuch.setOnAction(actionEvent -> {
            doSerwera.println("RUCH 1 2 3 4 5");
        });
        przyciskWyjdz.setOnAction(actionEvent -> {
            doSerwera.println("WYJDŹ");
        });
        korzen.setTop(przyciskRuch);
        korzen.setBottom(przyciskWyjdz);
        scena.setScene(new Scene(korzen, 720, 480));
        scena.show();
        try {
            gniazdo = new Socket(adresSerwera, 55555);
            odSerwera = new Scanner(gniazdo.getInputStream());
            doSerwera = new PrintWriter(gniazdo.getOutputStream(), true);
            Kontroler kontroler = new Kontroler(odSerwera, doSerwera);
            new Thread(kontroler).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
