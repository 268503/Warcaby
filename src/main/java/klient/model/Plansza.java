package klient.model;

/**
 * Klasa dla planszy modelowej klienta
 */
public class Plansza {
    private final Pole[][] pola;
    private final int wymiar;

    /**
     * Główny konstruktor
     * @param wymiar rozmiar planszy
     * @param kolorLewyDolny kolor pola w lewym dolnym rogu. 'j' - pole jasne; 'c' - pole ciemne
     * @param wariant numer wariantu. 1 - warcaby klasyczne, 2 - warcaby hiszpanskie, 3 - warcaby polskie
     */
    public Plansza(final int wymiar, final char kolorLewyDolny, final int wariant) {
        this.wymiar = wymiar;
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
                }
                if (pobierzPole(x, y).pobierzKolorPola() == 'c') {
                    if (y < (wariant == 3 ? 4 : 3)) {
                        pobierzPole(x, y).ustawPionek(new Pionek('C'));
                    }
                    else if (y > (wariant == 3 ? 5 : 4)) {
                        pobierzPole(x, y).ustawPionek(new Pionek('B'));
                    }
                }
            }
        }
    }

    /**
     * Pobiera pole o podanych współrzędnych
     * @param x współrzędna x
     * @param y współrzędna y
     * @return pole o podanych współrzędnych
     */
    public Pole pobierzPole(final int x, final int y) {
        return pola[x][y];
    }

    /**
     * Ustawia pole o podanych współrzędnych na podany kolor
     * @param x współrzędna x
     * @param y współrzędna y
     * @param kolorPola kolor pola. 'j' - pole jasne; 'c' - pole ciemne
     */
    public void ustawPole(final int x, final int y, final char kolorPola) {
        pola[x][y] = new Pole(kolorPola);
    }

    /**
     * Pobiera rozmiar planszy
     * @return rozmiar planszy
     */
    public int pobierzWymiar() {
        return wymiar;
    }

    /**
     * Przenosi pionek
     * @param xPocz współrzędna x pola, na którym znajduje się pionek
     * @param yPocz współrzędna y pola, na którym znajduje się pionek
     * @param xKonc współrzędna x pola, na które przenoszony jest pionek
     * @param yKonc współrzędna y pola, na które przenoszony jest pionek
     */
    public void ruszPionek(final int xPocz, final int yPocz, final int xKonc, final int yKonc) {
        pola[xKonc][yKonc].ustawPionek(pola[xPocz][yPocz].pobierzPionek());
        pola[xPocz][yPocz].ustawPionek(null);
    }

    /**
     * Usuwa pionek z planszy
     * @param x współrzędna x pola, na którym znajduje się pionek
     * @param y współrzędna y pola, na którym znajduje się pionek
     */
    public void usunPionek(final int x, final int y) {
        pola[x][y].ustawPionek(null);
    }
}
