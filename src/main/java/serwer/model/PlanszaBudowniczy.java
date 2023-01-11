package serwer.model;

public abstract class PlanszaBudowniczy {
    protected int xAktualny = -1;
    protected int yAktualny = -1;
    protected Plansza plansza;
    public Plansza pobierzPlansza() {
        return plansza;
    }
    abstract public void poczatkoweUstawienie();
    abstract public boolean zbijPionek(char kolorPionka, int xPocz, int yPocz, int xKonc, int yKonc);
    abstract public boolean normalnyRuch(char kolorPionka, int xPocz, int yPocz, int xKonc, int yKonc);
    abstract public boolean moznaNormalnyRuch(char kolorPionka, int xPocz, int yPocz, int xKonc, int yKonc);
    abstract public boolean moznaDalejBic(char kolorPionka, int x, int y);
    public boolean istniejeBicie(final char kolor) {
        boolean bicieDostepne = false;
        for (final Pionek pionek : plansza.pobierzPionki()) {
            if (!bicieDostepne && moznaDalejBic(pionek.pobierzKolor(), pionek.pobierzWspolrzednaX(), pionek.pobierzWspolrzednaY()) && pionek.pobierzKolor() == kolor) {
                bicieDostepne = true;
            }
        }
        return bicieDostepne;
    }
    public boolean czyRemis() {
        return plansza.pobierzLicznikRuchow() == Plansza.LIMIT_RUCHOW;
    }
    public boolean czyWygrana(final char kolor) {
        for (final Pionek pionek : plansza.pobierzPionki()) {
            if (pionek.pobierzKolor() != kolor && czyMoznaRuszyc(pionek)) {
                return false;
            }
        }
        return true;
    }
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
    public void zresetujObecneWspolrzedne() {
        xAktualny = -1;
        yAktualny = -1;
    }
}
