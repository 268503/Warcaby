package serwer;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Klasa serwera, na którym przeprowadzana jest rozgrywka
 */
public class Serwer {
    /**
     * Główna metoda serwera, która uruchamia grę
     * @param args argumenty z linii komend (nieużywane)
     */
    public static void main(final String[] args) throws Exception {
        try (ServerSocket nasluchiwacz = new ServerSocket(55555)) {
            System.out.println("Serwer do warcabów działa");
            final ExecutorService pula = Executors.newFixedThreadPool(2);
            final Gra gra = new Gra();
            pula.execute(gra.new Gracz(nasluchiwacz.accept(), 'B'));
            pula.execute(gra.new Gracz(nasluchiwacz.accept(), 'C'));
        }
    }

}
