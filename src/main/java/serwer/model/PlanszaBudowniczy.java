package serwer.model;

/**
 * Abstrakcyjna klasa dla budowniczych planszy
 */
public abstract class PlanszaBudowniczy {
    /**
     * Współrzędna x pionka, który jest w trakcie ruchu;
     * wartość -1 oznacza, że żaden pionek aktualnie nie wykonuje ruchu
     */
    protected int xAktualny = -1;

    /**
     * Współrzędna y pionka, który jest w trakcie ruchu
     * wartość -1 oznacza, że żaden pionek aktualnie nie wykonuje ruchu
     */
    protected int yAktualny = -1;

    /**
     * Budowana plansza
     */
    protected Plansza plansza;

    /**
     * Pobiera budowaną planszę
     * @return budowana plansza
     */
    public Plansza pobierzPlansza() {
        return plansza;
    }

    /**
     * Ustawia początkowy układ planszy
     */
    abstract public void poczatkoweUstawienie();

    /**
     * Wykonuje bicie z pola o współrzędnych początkowych na pole o współrzędnych końcowych,
     * jeśli ten ruch jest poprawny
     * @param kolorPionka kolor pionka, który wykonuje bicie
     * @param xPocz początkowa współrzędna x
     * @param yPocz początkowa współrzędna y
     * @param xKonc końcowa współrzędna x
     * @param yKonc końcowa współrzędna y
     * @return true jeżeli bicie się powiodło, false w przeciwnym przypadku
     */
    abstract public boolean zbijPionek(char kolorPionka, int xPocz, int yPocz, int xKonc, int yKonc);

    /**
     * Wykonuje normalny ruch z pola o współrzędnych początkowych na pole o współrzędnych końcowych,
     * jeśli ten ruch jest poprawny
     * @param kolorPionka kolor ruszanego pionka
     * @param xPocz początkowa współrzędna x
     * @param yPocz początkowa współrzędna y
     * @param xKonc końcowa współrzędna x
     * @param yKonc końcowa współrzędna y
     * @return true jeżeli ruch się powiódł, false w przeciwnym przypadku
     */
    abstract public boolean normalnyRuch(char kolorPionka, int xPocz, int yPocz, int xKonc, int yKonc);

    /**
     * Sprawdza czy można wykonać normalny ruch z pola o współrzędnych początkowych na pole o współrzędnych końcowych
     * @param kolorPionka kolor pionka, którego ruch jest sprawdzany
     * @param xPocz początkowa współrzędna x
     * @param yPocz początkowa współrzędna y
     * @param xKonc końcowa współrzędna x
     * @param yKonc końcowa współrzędna y
     * @return true jeżeli ruch jest poprawny, false w przeciwnym przypadku
     */
    abstract public boolean moznaNormalnyRuch(char kolorPionka, int xPocz, int yPocz, int xKonc, int yKonc);

    /**
     * Sprawdza czy dla pionka o podanych współrzędnych jest dostępne bicie
     * @param kolorPionka kolor pionka, dla którego jest sprawdzana dostępność bicia
     * @param x współrzędna x pionka
     * @param y współrzędna y pionka
     * @return true jeśli dla danego pionka jest dostępne bicie, false w przeciwnym przypadku
     */
    abstract public boolean moznaDalejBic(char kolorPionka, int x, int y);

    /**
     * Sprawdza jaką długość ma najlepsze bicie gracza danego koloru
     * @param plansza plansza, na której toczy się rozgrywka
     * @param kolor kolor gracza, dla którego szukamy najlepszego bicia
     * @return długość najlepszego bicia
     */
    abstract public int znajdzNajlepszeBicie(final Plansza plansza, final char kolor);

    /**
     * Rekurencyjnie wyznacza maksymalną długość bicia danym pionkiem dla danego stanu planszy
     * @param pionek pionek, który jest w trakcie bicia
     * @param plansza stan planszy, dla którego znajdujemy długość bicia danym pionkiem
     * @param glebokosc obecna długośc bicia
     * @return maksymalna długośc bicia danym pionkiem dla danego stanu planszy
     */
    abstract public int rekurencyjneSzukanieBicia(final Pionek pionek, final Plansza plansza, final int glebokosc);

    /**
     * Wyznacza maksymalną długośc bicia po biciu pionkiem zadanego koloru
     * ze współrzędnych początkowych na współrzędne końcowe
     * @param kolorPionka kolor pionka, dla którego sprawdzana jest długość bicia
     * @param xPocz początkowa współrzędna x
     * @param yPocz początkowa współrzędna y
     * @param xKonc końcowa współrzędna x
     * @param yKonc końcowa współrzędna y
     * @return maksymalna długośc bicia po biciu pionkiem zadanego koloru
     * ze współrzędnych początkowych na współrzędne końcowe
     */
    abstract public int glebokoscPoBiciu(final char kolorPionka, final int xPocz, final int yPocz, final int xKonc, final int yKonc);

    /**
     * Dla bicia damką na współrzędne końcowe sprawdza maksymalną długość dalszego bicia dla danego stanu planszy
     * @param plansza stan planszy, dla którego znajdowana jest długość bicia damką
     * @param pionek damka, dla której znajdowana jest długość bicia
     * @param xKonc końcowa współrzędna x
     * @param yKonc końcowa współrzędna y
     * @param glebokosc obecna długość bicia
     * @return maksymalna długość dalszego bicia dla danego stanu planszy
     */
    abstract public int glebokoscBiciaDamka(final Plansza plansza, final Pionek pionek, final int xKonc, final int yKonc, final int glebokosc);

    /**
     * Dla bicia pionkiem na współrzędne końcowe sprawdza maksymalną długość dalszego bicia dla danego stanu planszy
     * @param pionek pionek, dla którego znajdowana jest długość bicia
     * @param plansza stan planszy, dla którego znajdowana jest długość bicia pionkiem
     * @param xKonc końcowa współrzędna x
     * @param yKonc końcowa współrzędna y
     * @param glebokosc obecna długość bicia
     * @return maksymalna długość dalszego bicia dla danego stanu planszy
     */
    abstract public int glebokoscBiciaPionkiem(final Pionek pionek, final Plansza plansza, final int xKonc, final int yKonc, final int glebokosc);

    /**
     * Sprawdza czy jest dostępne bicie dowolnym pionkiem danego koloru na planszy
     * @param kolor kolor gracza
     * @return true jesli jest dostępne bicie, false w przeciwnym przypadku
     */
    public boolean istniejeBicie(final char kolor) {
        boolean bicieDostepne = false;
        for (final Pionek pionek : plansza.pobierzPionki()) {
            if (!bicieDostepne && moznaDalejBic(pionek.pobierzKolor(), pionek.pobierzWspolrzednaX(), pionek.pobierzWspolrzednaY()) && pionek.pobierzKolor() == kolor) {
                bicieDostepne = true;
            }
        }
        return bicieDostepne;
    }

    /**
     * Sprawdza czy w partii nastąpił remis
     * @return true w przypadku remisu, false gdy remisu nie ma
     */
    public boolean czyRemis() {
        return plansza.pobierzLicznikRuchow() == Plansza.LIMIT_RUCHOW;
    }

    /**
     * Sprawdza czy gracz o danym kolorze wygrał partię
     * @param kolor kolor gracza, dla którego sprawdzany jest warunek zwycięstwa
     * @return true jeśli gracz o danym kolorze zwycieżył, false w przeciwnym przypadku
     */
    public boolean czyWygrana(final char kolor) {
        for (final Pionek pionek : plansza.pobierzPionki()) {
            if (pionek.pobierzKolor() != kolor && czyMoznaRuszyc(pionek)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Sprawdza czy dany pionek może wykonać dowolny ruch
     * @param pionek Pionek dla którego jest sprawdzana dostępność ruchu
     * @return true jeśli pionek może się ruszyć, false w przeciwnym przypadku
     */
    public boolean czyMoznaRuszyc(final Pionek pionek) {
        final char kolor = pionek.pobierzKolor();
        final int x = pionek.pobierzWspolrzednaX();
        final int y = pionek.pobierzWspolrzednaY();
        if (   (plansza.ruszPionek(kolor, x, y, x + 1, y - 1) && moznaNormalnyRuch(kolor, x, y, x + 1, y - 1))
                || (plansza.ruszPionek(kolor, x, y, x - 1, y - 1) && moznaNormalnyRuch(kolor, x, y, x - 1, y - 1))
                || (plansza.ruszPionek(kolor, x, y, x + 1, y + 1) && moznaNormalnyRuch(kolor, x, y, x + 1, y + 1))
                || (plansza.ruszPionek(kolor, x, y, x - 1, y + 1) && moznaNormalnyRuch(kolor, x, y, x - 1, y + 1))) {
            return true;
        }
        return moznaDalejBic(pionek.pobierzKolor(), pionek.pobierzWspolrzednaX(), pionek.pobierzWspolrzednaY());
    }

    /**
     * Ustawia, że żaden pionek aktualnie nie jest w trkacie ruchu
     */
    public void zresetujObecneWspolrzedne() {
        xAktualny = -1;
        yAktualny = -1;
    }
}
