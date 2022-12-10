package serwer;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Serwer {
    public static void main(String[] args) throws Exception {
        try (ServerSocket nasluchiwacz = new ServerSocket(55555)) {
            System.out.println("Serwer do warcabów działa");
            ExecutorService pool = Executors.newFixedThreadPool(2);
            Gra gra = new Gra();
            pool.execute(gra.new Gracz(nasluchiwacz.accept(), 'B'));
            pool.execute(gra.new Gracz(nasluchiwacz.accept(), 'C'));
        }
    }

}
