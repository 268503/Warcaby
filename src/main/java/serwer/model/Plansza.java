package serwer.model;


import java.util.ArrayList;
import java.util.List;

public class Plansza {
    private Pole[][] pola;
    private List<Pionek> pionki;
    private final int wymiar;

    public Plansza(int wymiar, char kolorLewyDolny) {
        this.wymiar = wymiar;
        pionki = new ArrayList<>();
        pola = new Pole[wymiar][wymiar];
        char kolorDrugi;
        if (kolorLewyDolny == 'c') {
            kolorDrugi = 'j';
        }
        else {
            kolorDrugi = 'c';
        }
        for (int x = 0; x < wymiar; x++) {
            for (int y = 0; y < wymiar; y++) {
                if ((x + y) % 2 == 0) {
                    ustawPole(x, y, kolorDrugi);
                }
                else {
                    ustawPole(x, y, kolorLewyDolny);
                    if (y < 3) {
//                        pobierzPole(x, y).ustawPionek(new Pionek('C', x, y));
                        wstawPionek(new Pionek('C', x, y));
                    }
                    else if (y > 4) {
//                        pobierzPole(x, y).ustawPionek(new Pionek('B', x, y));
                        wstawPionek(new Pionek('B', x, y));
                    }
                }
            }
        }
    }

    public Pole pobierzPole(int x, int y) {
        return pola[x][y];
    }

    public void ustawPole(int x, int y, char kolorPola) {
        pola[x][y] = new Pole(kolorPola);
    }

    public int pobierzWymiar() {
        return wymiar;
    }

    public List<Pionek> pobierzPionki() {
        return pionki;
    }
    public void wstawPionek(Pionek pionek) {
        pionki.add(pionek);
    }
    public void usunPionek(Pionek pionek) {
        pionki.remove(pionek);
    }
    //TODO: pobieranie pionkow potencjalnie
    public Pionek pobierzPionek(int x, int y) {
        //todo: mapy?
        if (x < 0 || y < 0 || x >= wymiar || y >= wymiar) {
            return null;
        }
        for (Pionek pionek : pionki) {
            if (pionek.pobierzWspolrzednaX() == x && pionek.pobierzWspolrzednaY() == y) {
                return pionek;
            }
        }
        return null;
    }
    public boolean ruszPionek(char kolorPionka, int xPocz, int yPocz, int xKonc, int yKonc) {

        if (xPocz < 0 || xPocz >= wymiar || yPocz < 0 || yPocz >= wymiar || xKonc < 0 || xKonc >= wymiar || yKonc < 0 || yKonc >= wymiar) {
            return false;
        }

//        if (pola[xPocz][yPocz].pobierzPionek() != null) {
//            if (kolorPionka == pola[xPocz][yPocz].pobierzPionek().pobierzKolor()) {
//                if (pola[xKonc][yKonc].pobierzPionek() == null) {
//                    return true;
//                }
//            }
//        }
//        return false;
        if (pobierzPionek(xPocz, yPocz) != null) {
            if (kolorPionka == pobierzPionek(xPocz, yPocz).pobierzKolor()) {
                if (pobierzPionek(xKonc, yKonc) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean zbijPionek(char kolorPionka, int xPocz, int yPocz, int xKonc, int yKonc) {
//        if (pola[(xPocz + xKonc) / 2][(yPocz + yKonc) / 2].pobierzPionek() == null) {
//            return false;
//        }
//        boolean pionekPomiedzy = (kolorPionka == 'B' && pola[(xPocz + xKonc) / 2][(yPocz + yKonc) / 2].pobierzPionek().pobierzKolor() == 'C')
//                || (kolorPionka == 'C' && pola[(xPocz + xKonc) / 2][(yPocz + yKonc) / 2].pobierzPionek().pobierzKolor() == 'B');
//         if (((kolorPionka == 'B' && (yKonc == yPocz - 2 || yKonc == yPocz + 2) && (xKonc == xPocz - 2 || xKonc == xPocz + 2))
//                || (kolorPionka == 'C' && (yKonc == yPocz - 2 || yKonc == yPocz + 2) && (xKonc == xPocz - 2 || xKonc == xPocz + 2)))
//                && pionekPomiedzy) {
//             pola[xKonc][yKonc].ustawPionek(pola[xPocz][yPocz].pobierzPionek());
//             pola[xPocz][yPocz].ustawPionek(null);
//             pola[(xPocz + xKonc) / 2][(yPocz + yKonc) / 2].ustawPionek(null);
//             return true;
//         }
//         return false;
        if (pobierzPionek((xPocz + xKonc) / 2, (yPocz + yKonc) / 2) == null) {
            return false;
        }
        boolean pionekPomiedzy = (kolorPionka == 'B' && pobierzPionek((xPocz + xKonc) / 2, (yPocz + yKonc) / 2).pobierzKolor() == 'C')
                || (kolorPionka == 'C' && pobierzPionek((xPocz + xKonc) / 2, (yPocz + yKonc) / 2).pobierzKolor() == 'B');
        if (((kolorPionka == 'B' && (yKonc == yPocz - 2 || yKonc == yPocz + 2) && (xKonc == xPocz - 2 || xKonc == xPocz + 2))
                || (kolorPionka == 'C' && (yKonc == yPocz - 2 || yKonc == yPocz + 2) && (xKonc == xPocz - 2 || xKonc == xPocz + 2)))
                && pionekPomiedzy) {
            //wstawPionek(pobierzPionek(xPocz, yPocz));

            pobierzPionek(xPocz, yPocz).przesun(xKonc, yKonc);
            usunPionek(pobierzPionek(xPocz, yPocz));
            usunPionek(pobierzPionek((xPocz + xKonc) / 2, (yPocz + yKonc) / 2));
            return true;
        }
        return false;
    }
    public boolean normalnyRuch(char kolorPionka, int xPocz, int yPocz, int xKonc, int yKonc) {
//        if ((kolorPionka == 'B' && yKonc == yPocz - 1 && (xKonc == xPocz - 1 || xKonc == xPocz + 1))
//                || (kolorPionka == 'C' && yKonc == yPocz + 1 && (xKonc == xPocz - 1 || xKonc == xPocz + 1))) {
//
//            pola[xKonc][yKonc].ustawPionek(pola[xPocz][yPocz].pobierzPionek());
//            pola[xPocz][yPocz].ustawPionek(null);
//            return true;
//        }
//        return false;
        if ((kolorPionka == 'B' && yKonc == yPocz - 1 && (xKonc == xPocz - 1 || xKonc == xPocz + 1))
                || (kolorPionka == 'C' && yKonc == yPocz + 1 && (xKonc == xPocz - 1 || xKonc == xPocz + 1))) {
            //wstawPionek(pobierzPionek(xPocz, yPocz));
            pobierzPionek(xPocz, yPocz).przesun(xKonc, yKonc);
            usunPionek(pobierzPionek(xPocz, yPocz));
            return true;
        }
        return false;
    }
    public boolean moznaDalejBic(char kolorPionka, int x, int y) {
//        return ((x > 1 && y > 1 && pola[x-1][y-1].pobierzPionek() != null && pola[x-2][y-2].pobierzPionek()==null && pola[x-1][y-1].pobierzPionek().pobierzKolor() != kolorPionka)
//            || (x > 1 && y < wymiar - 2 && pola[x-1][y+1].pobierzPionek() != null && pola[x-2][y+2].pobierzPionek()==null && pola[x-1][y+1].pobierzPionek().pobierzKolor() != kolorPionka)
//            || (x < wymiar - 2 && y > 1 && pola[x+1][y-1].pobierzPionek() != null && pola[x+2][y-2].pobierzPionek()==null && pola[x+1][y-1].pobierzPionek().pobierzKolor() != kolorPionka)
//            || (x < wymiar - 2 && y < wymiar - 2 && pola[x+1][y+1].pobierzPionek() != null && pola[x+2][y+2].pobierzPionek()==null && pola[x+1][y+1].pobierzPionek().pobierzKolor() != kolorPionka));
        return (   (x > 1 && y > 1 && pobierzPionek(x-1, y-1) != null && pobierzPionek(x-2, y-2)==null && pobierzPionek(x-1, y-1).pobierzKolor() != kolorPionka)
                || (x > 1 && y < wymiar - 2 && pobierzPionek(x-1, y+1) != null && pobierzPionek(x-2, y+2)==null && pobierzPionek(x-1, y+1).pobierzKolor() != kolorPionka)
                || (x < wymiar - 2 && y > 1 && pobierzPionek(x+1, y-1) != null && pobierzPionek(x+2, y-2)==null && pobierzPionek(x+1, y-1).pobierzKolor() != kolorPionka)
                || (x < wymiar - 2 && y < wymiar - 2 && pobierzPionek(x+1, y+1) != null && pobierzPionek(x+2, y+2)==null && pobierzPionek(x+1, y+1).pobierzKolor() != kolorPionka));
    }
}
