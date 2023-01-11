package serwer.model;

public class ZarzadcaBudowniczych {
    private PlanszaBudowniczy planszaBudowniczy;
    public void ustawPlanszaBudowniczy(final PlanszaBudowniczy planszaBudowniczy) {
        this.planszaBudowniczy = planszaBudowniczy;
    }
    public void skonstruujPlansze() {
        planszaBudowniczy.poczatkoweUstawienie();
    }
    public boolean zbijPionek(final char kolorPionka, final int xPocz, final int yPocz, final int xKonc, final int yKonc) {
        return planszaBudowniczy.zbijPionek(kolorPionka, xPocz, yPocz, xKonc, yKonc);
    }
    public boolean normalnyRuch(final char kolorPionka, final int xPocz, final int yPocz, final int xKonc, final int yKonc) {
        return planszaBudowniczy.normalnyRuch(kolorPionka, xPocz, yPocz, xKonc, yKonc);
    }
    public boolean moznaDalejBic(final char kolorPionka, final int x, final int y) {
        return planszaBudowniczy.moznaDalejBic(kolorPionka, x, y);
    }
    public boolean czyRemis() {
        return planszaBudowniczy.czyRemis();
    }
    public boolean czyWygrana(final char kolor) {
        return planszaBudowniczy.czyWygrana(kolor);
    }
    public Plansza pobierzPlansza() {
        return planszaBudowniczy.pobierzPlansza();
    }
    public boolean istniejeBicie(final char kolor) {
        return planszaBudowniczy.istniejeBicie(kolor);
    }
    public void zresetujObecneWspolrzedne() {
        planszaBudowniczy.zresetujObecneWspolrzedne();
    }
}
