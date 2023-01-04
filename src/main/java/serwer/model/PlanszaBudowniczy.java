package serwer.model;

public abstract class PlanszaBudowniczy {
    protected int xAktualny = -1;
    protected int yAktualny = -1;
    protected Plansza plansza;
    public Plansza pobierzPlansza() {
        return plansza;
    }
    abstract public boolean istniejeBicie(char kolor);
    abstract public void poczatkoweUstawienie();
    abstract public boolean zbijPionek(char kolorPionka, int xPocz, int yPocz, int xKonc, int yKonc);
    abstract public boolean normalnyRuch(char kolorPionka, int xPocz, int yPocz, int xKonc, int yKonc);
    abstract public boolean moznaNormalnyRuch(char kolorPionka, int xPocz, int yPocz, int xKonc, int yKonc);
    abstract public boolean moznaDalejBic(char kolorPionka, int x, int y);
    public boolean czyRemis() {
        System.out.println(plansza.pobierzLicznikRuchow() + " ruchow damka bez progresu");
        if (plansza.pobierzLicznikRuchow() == plansza.LIMIT_RUCHOW)
        {
            return true;
        }
        return false;
    }
    public boolean czyWygrana(char kolor) {
        for (Pionek pionek : plansza.pobierzPionki()) {
            if (pionek.pobierzKolor() != kolor) {
                if (czyMoznaRuszyc(pionek)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean czyMoznaRuszyc(Pionek pionek) {
        char kolor = pionek.pobierzKolor();
        int x = pionek.pobierzWspolrzednaX();
        int y = pionek.pobierzWspolrzednaY();
        if (   (plansza.ruszPionek(kolor, x, y, x + 1, y - 1) && moznaNormalnyRuch(kolor, x, y, x + 1, y - 1))
                || (plansza.ruszPionek(kolor, x, y, x - 1, y - 1) && moznaNormalnyRuch(kolor, x, y, x - 1, y - 1))
                || (plansza.ruszPionek(kolor, x, y, x + 1, y + 1) && moznaNormalnyRuch(kolor, x, y, x + 1, y + 1))
                || (plansza.ruszPionek(kolor, x, y, x - 1, y + 1) && moznaNormalnyRuch(kolor, x, y, x - 1, y + 1))) {
            return true;
        }
        return moznaDalejBic(pionek.pobierzKolor(), pionek.pobierzWspolrzednaX(), pionek.pobierzWspolrzednaY());
    }
    public void zresetujObecneWspolrzedne() {
        xAktualny = -1;
        yAktualny = -1;
    }
}
