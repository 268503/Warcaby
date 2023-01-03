package serwer.model;

public class ZarzadcaBudowniczych {
    private PlanszaBudowniczy planszaBudowniczy;
    public void ustawPlanszaBudowniczy(PlanszaBudowniczy planszaBudowniczy) {
        this.planszaBudowniczy = planszaBudowniczy;
    }
    public void skonstruujPlansze() {
        planszaBudowniczy.poczatkoweUstawienie();
    }
    public boolean zbijPionek(char kolorPionka, int xPocz, int yPocz, int xKonc, int yKonc) {
        return planszaBudowniczy.zbijPionek(kolorPionka, xPocz, yPocz, xKonc, yKonc);
    }
    public boolean normalnyRuch(char kolorPionka, int xPocz, int yPocz, int xKonc, int yKonc) {
        return planszaBudowniczy.normalnyRuch(kolorPionka, xPocz, yPocz, xKonc, yKonc);
    }
//    public boolean moznaNormalnyRuch(char kolorPionka, int xPocz, int yPocz, int xKonc, int yKonc) {
//        return planszaBudowniczy.moznaNormalnyRuch(kolorPionka, xPocz, yPocz, xKonc, yKonc);
//    }
    public boolean moznaDalejBic(char kolorPionka, int x, int y) {
        return planszaBudowniczy.moznaDalejBic(kolorPionka, x, y);
    }
    public boolean czyRemis() {
        return planszaBudowniczy.czyRemis();
    }
    public boolean czyWygrana(char kolor) {
        return planszaBudowniczy.czyWygrana(kolor);
    }
    public boolean czyMoznaRuszyc(Pionek pionek)
    {
        return czyMoznaRuszyc(pionek);
    }
    public Plansza pobierzPlansza() {
        return planszaBudowniczy.pobierzPlansza();
    }
    public boolean istniejeBicie(char kolor) {
        return planszaBudowniczy.istniejeBicie(kolor);
    }
}
