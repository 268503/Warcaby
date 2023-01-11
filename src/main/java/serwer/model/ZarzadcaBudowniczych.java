package serwer.model;

/**
 * Klasa do zarządzania budowniczymi planszy
 */
public class ZarzadcaBudowniczych {
    private PlanszaBudowniczy planszaBudowniczy;

    /**
     * Przypisuje budowniczego planszy do zarządcy
     * @param planszaBudowniczy budowniczy planszy do przypisania
     */
    public void ustawPlanszaBudowniczy(final PlanszaBudowniczy planszaBudowniczy) {
        this.planszaBudowniczy = planszaBudowniczy;
    }

    /**
     * Tworzy planszę, ustawia początkowe ułożenie
     */
    public void skonstruujPlansze() {
        planszaBudowniczy.poczatkoweUstawienie();
    }

    /**
     * Wywołuje metodę budowniczego konkretnego do zbijania pionka
     * @see serwer.model.PlanszaBudowniczy#zbijPionek(char, int, int, int, int)
     * @param kolorPionka kolor pionka, który wykonuje bicie
     * @param xPocz początkowa współrzędna x
     * @param yPocz początkowa współrzędna y
     * @param xKonc końcowa współrzędna x
     * @param yKonc końcowa współrzędna y
     * @return true jeżeli bicie się powiodło, false w przeciwnym przypadku
     */
    public boolean zbijPionek(final char kolorPionka, final int xPocz, final int yPocz, final int xKonc, final int yKonc) {
        return planszaBudowniczy.zbijPionek(kolorPionka, xPocz, yPocz, xKonc, yKonc);
    }

    /**
     * Wywołuje metodę budowniczego konkretnego do normalnego ruchu pionkiem
     * @see serwer.model.PlanszaBudowniczy#normalnyRuch(char, int, int, int, int)
     * @param kolorPionka kolor ruszanego pionka
     * @param xPocz początkowa współrzędna x
     * @param yPocz początkowa współrzędna y
     * @param xKonc końcowa współrzędna x
     * @param yKonc końcowa współrzędna y
     * @return true jeżeli ruch się powiódł, false w przeciwnym przypadku
     */
    public boolean normalnyRuch(final char kolorPionka, final int xPocz, final int yPocz, final int xKonc, final int yKonc) {
        return planszaBudowniczy.normalnyRuch(kolorPionka, xPocz, yPocz, xKonc, yKonc);
    }

    /**
     * Wywołuje metodę budowniczego konkretnego do sprawdzania czy można dalej bić
     * @see serwer.model.PlanszaBudowniczy#moznaDalejBic(char, int, int)
     * @param kolorPionka kolor pionka, dla którego jest sprawdzana dostępność bicia
     * @param x współrzędna x pionka
     * @param y współrzędna y pionka
     * @return true jeśli dla danego pionka jest dostępne bicie, false w przeciwnym przypadku
     */
    public boolean moznaDalejBic(final char kolorPionka, final int x, final int y) {
        return planszaBudowniczy.moznaDalejBic(kolorPionka, x, y);
    }

    /**
     * Wywołuje metodę budowniczego konkretnego do sprawdzania czy nastąpił remis
     * @see PlanszaBudowniczy#czyRemis()
     * @return true w przypadku remisu, false gdy remisu nie ma
     */
    public boolean czyRemis() {
        return planszaBudowniczy.czyRemis();
    }

    /**
     * Wywołuje metodę budowniczego konkretnego do sprawdzania czy nastąpiła wygrana gracza danego koloru
     * @see PlanszaBudowniczy#czyWygrana(char)
     * @param kolor kolor gracza, dla którego sprawdzany jest warunek zwycięstwa
     * @return true jeśli gracz o danym kolorze zwycieżył, false w przeciwnym przypadku
     */
    public boolean czyWygrana(final char kolor) {
        return planszaBudowniczy.czyWygrana(kolor);
    }

    /**
     * Pobiera planszę budowniczego konkretnego
     * @return plansza budowniczego konkretnego
     */
    public Plansza pobierzPlansza() {
        return planszaBudowniczy.pobierzPlansza();
    }

    /**
     * Wywołuje metodę budowniczego konkretnego do sprawdzania czy istnieje bicie
     * @see PlanszaBudowniczy#istniejeBicie(char)
     * @param kolor kolor gracza
     * @return true jesli jest dostępne bicie, false w przeciwnym przypadku
     */
    public boolean istniejeBicie(final char kolor) {
        return planszaBudowniczy.istniejeBicie(kolor);
    }

    /**
     * Wywołuje metodę budowniczego konkretnego do ustawiania, że żaden pionek aktualnie nie jest w trkacie ruchu
     * @see PlanszaBudowniczy#zresetujObecneWspolrzedne()
     */
    public void zresetujObecneWspolrzedne() {
        planszaBudowniczy.zresetujObecneWspolrzedne();
    }
}
