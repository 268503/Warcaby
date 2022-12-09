package serwer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

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
