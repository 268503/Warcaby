package main;

import org.junit.Test;
import serwer.model.*;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Testy {
    /**
     *
     */
    @Test
    public void testNormalnegoRuchuPionkaWariantKlasyczny() {
        ZarzadcaBudowniczych zarzadcaBudowniczych = new ZarzadcaBudowniczych();
        PlanszaBudowniczy planszaBudowniczy = new PlanszaWariantKlasycznyBudowniczy();
        zarzadcaBudowniczych.ustawPlanszaBudowniczy(planszaBudowniczy);
        zarzadcaBudowniczych.skonstruujPlansze();
        Plansza plansza = zarzadcaBudowniczych.pobierzPlansza();

        zarzadcaBudowniczych.zresetujObecneWspolrzedne();
        plansza.ustawPionki(new ArrayList<>());
        plansza.wstawPionek(new Pionek('B', 2, 5));
        plansza.wstawPionek(new Pionek('B', 4, 5));
        plansza.wstawPionek(new Pionek('C', 3, 2));
        plansza.wstawPionek(new Pionek('C', 5, 2));

        assertTrue(zarzadcaBudowniczych.normalnyRuch('B', 2, 5, 1, 4));
        assertTrue(zarzadcaBudowniczych.normalnyRuch('C', 3, 2, 4, 3));
        assertFalse(zarzadcaBudowniczych.normalnyRuch('B', 4, 5, 5, 6));
        assertFalse(zarzadcaBudowniczych.normalnyRuch('C', 5, 2, 5, 3));
    }

    @Test
    public void testPojedynczegoBiciaPionkiemWariantKlasyczny() {
        ZarzadcaBudowniczych zarzadcaBudowniczych = new ZarzadcaBudowniczych();
        PlanszaBudowniczy planszaBudowniczy = new PlanszaWariantKlasycznyBudowniczy();
        zarzadcaBudowniczych.ustawPlanszaBudowniczy(planszaBudowniczy);
        zarzadcaBudowniczych.skonstruujPlansze();
        Plansza plansza = zarzadcaBudowniczych.pobierzPlansza();

        zarzadcaBudowniczych.zresetujObecneWspolrzedne();
        plansza.ustawPionki(new ArrayList<>());
        plansza.wstawPionek(new Pionek('B', 3, 3));
        plansza.wstawPionek(new Pionek('C', 4, 2));
        assertTrue(zarzadcaBudowniczych.moznaDalejBic('B', 3, 3));
        assertTrue(zarzadcaBudowniczych.zbijPionek('B', 3, 3, 5, 1));

        zarzadcaBudowniczych.zresetujObecneWspolrzedne();
        plansza.ustawPionki(new ArrayList<>());
        plansza.wstawPionek(new Pionek('B', 3, 3));
        plansza.wstawPionek(new Pionek('C', 4, 2));
        assertFalse(zarzadcaBudowniczych.zbijPionek('B', 3, 3, 6, 0));

        zarzadcaBudowniczych.zresetujObecneWspolrzedne();
        plansza.ustawPionki(new ArrayList<>());
        plansza.wstawPionek(new Pionek('B', 3, 3));
        plansza.wstawPionek(new Pionek('C', 4, 2));
        assertFalse(zarzadcaBudowniczych.normalnyRuch('B', 3, 3, 4, 2));

        zarzadcaBudowniczych.zresetujObecneWspolrzedne();
        plansza.ustawPionki(new ArrayList<>());
        plansza.wstawPionek(new Pionek('C', 3, 3));
        plansza.wstawPionek(new Pionek('B', 4, 2));
        assertTrue(zarzadcaBudowniczych.zbijPionek('B', 4, 2, 2, 4));
    }

    @Test
    public void testNormalnegoRuchuDamkaWariantKlasyczny() {
        ZarzadcaBudowniczych zarzadcaBudowniczych = new ZarzadcaBudowniczych();
        PlanszaBudowniczy planszaBudowniczy = new PlanszaWariantKlasycznyBudowniczy();
        zarzadcaBudowniczych.ustawPlanszaBudowniczy(planszaBudowniczy);
        zarzadcaBudowniczych.skonstruujPlansze();
        Plansza plansza = zarzadcaBudowniczych.pobierzPlansza();

        zarzadcaBudowniczych.zresetujObecneWspolrzedne();
        plansza.ustawPionki(new ArrayList<>());
        plansza.wstawPionek(new Pionek('B', 2, 5));
        plansza.pobierzPionek(2, 5).ustawDamka();
        assertTrue(zarzadcaBudowniczych.normalnyRuch('B', 2, 5, 0, 3));

        zarzadcaBudowniczych.zresetujObecneWspolrzedne();
        plansza.ustawPionki(new ArrayList<>());
        plansza.wstawPionek(new Pionek('B', 4, 5));
        plansza.pobierzPionek(4, 5).ustawDamka();
        assertFalse(zarzadcaBudowniczych.normalnyRuch('B', 4, 5, 7, 3));

        zarzadcaBudowniczych.zresetujObecneWspolrzedne();
        plansza.ustawPionki(new ArrayList<>());
        plansza.wstawPionek(new Pionek('C', 3, 2));
        plansza.pobierzPionek(3, 2).ustawDamka();
        assertTrue(zarzadcaBudowniczych.normalnyRuch('C', 3, 2, 4, 3));

        zarzadcaBudowniczych.zresetujObecneWspolrzedne();
        plansza.ustawPionki(new ArrayList<>());
        plansza.wstawPionek(new Pionek('C', 5, 2));
        plansza.pobierzPionek(5, 2).ustawDamka();
        assertFalse(zarzadcaBudowniczych.normalnyRuch('C', 5, 2, 5, 6));
    }

    @Test
    public void testPojedynczegoBiciaDamkaWariantKlasyczny() {
        ZarzadcaBudowniczych zarzadcaBudowniczych = new ZarzadcaBudowniczych();
        PlanszaBudowniczy planszaBudowniczy = new PlanszaWariantKlasycznyBudowniczy();
        zarzadcaBudowniczych.ustawPlanszaBudowniczy(planszaBudowniczy);
        zarzadcaBudowniczych.skonstruujPlansze();
        Plansza plansza = zarzadcaBudowniczych.pobierzPlansza();

        zarzadcaBudowniczych.zresetujObecneWspolrzedne();
        plansza.ustawPionki(new ArrayList<>());
        plansza.wstawPionek(new Pionek('B', 3, 3));
        plansza.pobierzPionek(3, 3).ustawDamka();
        plansza.wstawPionek(new Pionek('C', 5, 5));
        assertTrue(zarzadcaBudowniczych.moznaDalejBic('B', 3, 3));
        assertTrue(zarzadcaBudowniczych.zbijPionek('B', 3, 3, 7, 7));

        zarzadcaBudowniczych.zresetujObecneWspolrzedne();
        plansza.ustawPionki(new ArrayList<>());
        plansza.wstawPionek(new Pionek('B', 3, 3));
        plansza.pobierzPionek(3, 3).ustawDamka();
        plansza.wstawPionek(new Pionek('C', 4, 2));
        assertFalse(zarzadcaBudowniczych.zbijPionek('B', 3, 3, 2, 4));

        zarzadcaBudowniczych.zresetujObecneWspolrzedne();
        plansza.ustawPionki(new ArrayList<>());
        plansza.wstawPionek(new Pionek('B', 3, 3));
        plansza.wstawPionek(new Pionek('C', 4, 2));
        plansza.pobierzPionek(4, 2).ustawDamka();
        assertFalse(zarzadcaBudowniczych.normalnyRuch('C', 4, 2, 5, 3));
    }

    @Test
    public void testWielokrotnegoBiciaPionkiemWariantKlasyczny() {
        ZarzadcaBudowniczych zarzadcaBudowniczych = new ZarzadcaBudowniczych();
        PlanszaBudowniczy planszaBudowniczy = new PlanszaWariantKlasycznyBudowniczy();
        zarzadcaBudowniczych.ustawPlanszaBudowniczy(planszaBudowniczy);
        zarzadcaBudowniczych.skonstruujPlansze();
        Plansza plansza = zarzadcaBudowniczych.pobierzPlansza();

        zarzadcaBudowniczych.zresetujObecneWspolrzedne();
        plansza.ustawPionki(new ArrayList<>());
        plansza.wstawPionek(new Pionek('B', 2, 5));
        plansza.wstawPionek(new Pionek('C', 3, 2));
        plansza.wstawPionek(new Pionek('C', 3, 4));
        assertTrue(zarzadcaBudowniczych.moznaDalejBic('B', 2, 5));
        assertTrue(zarzadcaBudowniczych.zbijPionek('B', 2, 5, 4, 3));
        assertTrue(zarzadcaBudowniczych.moznaDalejBic('B', 4, 3));
        assertTrue(zarzadcaBudowniczych.zbijPionek('B', 4, 3, 2, 1));

        zarzadcaBudowniczych.zresetujObecneWspolrzedne();
        plansza.ustawPionki(new ArrayList<>());
        plansza.wstawPionek(new Pionek('B', 2, 5));
        plansza.wstawPionek(new Pionek('C', 3, 2));
        plansza.wstawPionek(new Pionek('C', 3, 4));
        assertTrue(zarzadcaBudowniczych.zbijPionek('B', 2, 5, 4, 3));
        assertFalse(zarzadcaBudowniczych.normalnyRuch('B', 4, 3, 5, 2));

        zarzadcaBudowniczych.zresetujObecneWspolrzedne();
        plansza.ustawPionki(new ArrayList<>());
        plansza.wstawPionek(new Pionek('B', 2, 5));
        plansza.wstawPionek(new Pionek('C', 3, 2));
        plansza.wstawPionek(new Pionek('C', 3, 4));
        plansza.wstawPionek(new Pionek('C', 1, 4));
        assertFalse(zarzadcaBudowniczych.zbijPionek('B', 2, 5, 0, 3));
        assertTrue(zarzadcaBudowniczych.zbijPionek('B', 2, 5, 4, 3));
        assertTrue(zarzadcaBudowniczych.zbijPionek('B', 4, 3, 2, 1));
    }

    @Test
    public void testWielokrotnegoBiciaDamkaWariantKlasyczny() {
        ZarzadcaBudowniczych zarzadcaBudowniczych = new ZarzadcaBudowniczych();
        PlanszaBudowniczy planszaBudowniczy = new PlanszaWariantKlasycznyBudowniczy();
        zarzadcaBudowniczych.ustawPlanszaBudowniczy(planszaBudowniczy);
        zarzadcaBudowniczych.skonstruujPlansze();

        Plansza plansza = zarzadcaBudowniczych.pobierzPlansza();
        plansza.ustawPionki(new ArrayList<>());
        plansza.wstawPionek(new Pionek('B', 1, 6));
        plansza.pobierzPionek(1, 6).ustawDamka();
        plansza.wstawPionek(new Pionek('C', 6, 1));
        plansza.wstawPionek(new Pionek('C', 3, 4));
        assertTrue(zarzadcaBudowniczych.zbijPionek('B', 1, 6, 4, 3));
        assertTrue(zarzadcaBudowniczych.zbijPionek('B', 4, 3, 7, 0));

        zarzadcaBudowniczych.zresetujObecneWspolrzedne();
        plansza.ustawPionki(new ArrayList<>());
        plansza.wstawPionek(new Pionek('B', 1, 6));
        plansza.pobierzPionek(1, 6).ustawDamka();
        plansza.wstawPionek(new Pionek('C', 3, 4));
        plansza.wstawPionek(new Pionek('C', 6, 3));
        plansza.wstawPionek(new Pionek('C', 6, 5));
        plansza.wstawPionek(new Pionek('C', 4, 7));
        assertFalse(zarzadcaBudowniczych.zbijPionek('B', 1, 6, 7, 0));

        zarzadcaBudowniczych.zresetujObecneWspolrzedne();
        plansza.ustawPionki(new ArrayList<>());
        plansza.wstawPionek(new Pionek('B', 3, 6));
        plansza.pobierzPionek(3, 6).ustawDamka();
        plansza.wstawPionek(new Pionek('C', 2, 5));
        plansza.wstawPionek(new Pionek('C', 1, 2));
        plansza.wstawPionek(new Pionek('C', 4, 1));
        plansza.wstawPionek(new Pionek('C', 5, 4));
        assertTrue(zarzadcaBudowniczych.zbijPionek('B', 3, 6, 0, 3));
        assertTrue(zarzadcaBudowniczych.zbijPionek('B', 0, 3, 3, 0));
        assertTrue(zarzadcaBudowniczych.zbijPionek('B', 3, 0, 6, 3));
        assertTrue(zarzadcaBudowniczych.zbijPionek('B', 6, 3, 3, 6));

        zarzadcaBudowniczych.zresetujObecneWspolrzedne();
        plansza.ustawPionki(new ArrayList<>());
        plansza.wstawPionek(new Pionek('B', 3, 6));
        plansza.pobierzPionek(3, 6).ustawDamka();
        plansza.wstawPionek(new Pionek('C', 2, 5));
        plansza.wstawPionek(new Pionek('C', 1, 2));
        plansza.wstawPionek(new Pionek('C', 4, 1));
        plansza.wstawPionek(new Pionek('C', 5, 4));
        assertFalse(zarzadcaBudowniczych.zbijPionek('B', 3, 6, 1, 4));
        assertTrue(zarzadcaBudowniczych.zbijPionek('B', 3, 6, 6, 3));
        assertFalse(zarzadcaBudowniczych.normalnyRuch('B', 6, 3, 5, 2));

        zarzadcaBudowniczych.zresetujObecneWspolrzedne();
        plansza.ustawPionki(new ArrayList<>());
        plansza.wstawPionek(new Pionek('B', 3, 6));
        plansza.pobierzPionek(3, 6).ustawDamka();
        plansza.wstawPionek(new Pionek('C', 2, 5));
        plansza.wstawPionek(new Pionek('C', 1, 2));
        plansza.wstawPionek(new Pionek('C', 4, 1));
        plansza.wstawPionek(new Pionek('C', 5, 4));
        assertTrue(zarzadcaBudowniczych.zbijPionek('B', 3, 6, 0, 3));
        assertTrue(zarzadcaBudowniczych.zbijPionek('B', 0, 3, 3, 0));
        assertFalse(zarzadcaBudowniczych.zbijPionek('B', 3, 0, 7, 4));

        zarzadcaBudowniczych.zresetujObecneWspolrzedne();
        plansza.ustawPionki(new ArrayList<>());
        plansza.wstawPionek(new Pionek('B', 3, 6));
        plansza.pobierzPionek(3, 6).ustawDamka();
        plansza.wstawPionek(new Pionek('C', 2, 5));
        plansza.wstawPionek(new Pionek('C', 1, 2));
        plansza.wstawPionek(new Pionek('C', 4, 1));
        plansza.wstawPionek(new Pionek('C', 5, 4));
        assertTrue(zarzadcaBudowniczych.zbijPionek('B', 3, 6, 6, 3));
        assertTrue(zarzadcaBudowniczych.zbijPionek('B', 6, 3, 3, 0));
        assertTrue(zarzadcaBudowniczych.zbijPionek('B', 3, 0, 0, 3));
        assertTrue(zarzadcaBudowniczych.zbijPionek('B', 0, 3, 3, 6));
    }

    @Test
    public void testBiciaDoTyluZwyklymPionkiemWariantHiszpanski() {
        ZarzadcaBudowniczych zarzadcaBudowniczych = new ZarzadcaBudowniczych();
        PlanszaBudowniczy planszaBudowniczy = new PlanszaWariantHiszpanskiBudowniczy();
        zarzadcaBudowniczych.ustawPlanszaBudowniczy(planszaBudowniczy);
        zarzadcaBudowniczych.skonstruujPlansze();

        Plansza plansza = zarzadcaBudowniczych.pobierzPlansza();
        zarzadcaBudowniczych.zresetujObecneWspolrzedne();
        plansza.ustawPionki(new ArrayList<>());
        plansza.wstawPionek(new Pionek('C', 1, 3));
        plansza.wstawPionek(new Pionek('C', 3, 3));
        plansza.wstawPionek(new Pionek('C', 5, 3));
        plansza.wstawPionek(new Pionek('C', 5, 5));
        plansza.wstawPionek(new Pionek('B', 4, 4));
        assertFalse(zarzadcaBudowniczych.zbijPionek('B', 4, 4, 6, 6));

        zarzadcaBudowniczych.zresetujObecneWspolrzedne();
        plansza.ustawPionki(new ArrayList<>());
        plansza.wstawPionek(new Pionek('C', 1, 3));
        plansza.wstawPionek(new Pionek('C', 3, 3));
        plansza.wstawPionek(new Pionek('C', 5, 3));
        plansza.wstawPionek(new Pionek('B', 4, 4));
        assertTrue(zarzadcaBudowniczych.zbijPionek('B', 4, 4, 2, 2));
        assertFalse(zarzadcaBudowniczych.zbijPionek('B', 2, 2, 0, 4));

        zarzadcaBudowniczych.zresetujObecneWspolrzedne();
        plansza.ustawPionki(new ArrayList<>());
        plansza.wstawPionek(new Pionek('C', 1, 3));
        plansza.wstawPionek(new Pionek('C', 3, 3));
        plansza.wstawPionek(new Pionek('C', 5, 3));
        plansza.wstawPionek(new Pionek('B', 4, 4));
        assertTrue(zarzadcaBudowniczych.zbijPionek('B', 4, 4, 6, 2));
    }

    @Test
    public void testWielokrotnegoBiciaPionkiemWariantHiszpanski() {
        ZarzadcaBudowniczych zarzadcaBudowniczych = new ZarzadcaBudowniczych();
        PlanszaBudowniczy planszaBudowniczy = new PlanszaWariantHiszpanskiBudowniczy();
        zarzadcaBudowniczych.ustawPlanszaBudowniczy(planszaBudowniczy);
        zarzadcaBudowniczych.skonstruujPlansze();
        Plansza plansza = zarzadcaBudowniczych.pobierzPlansza();

        zarzadcaBudowniczych.zresetujObecneWspolrzedne();
        plansza.ustawPionki(new ArrayList<>());
        plansza.wstawPionek(new Pionek('C', 3, 1));
        plansza.wstawPionek(new Pionek('C', 3, 3));
        plansza.wstawPionek(new Pionek('C', 3, 5));
        plansza.wstawPionek(new Pionek('C', 1, 3));
        plansza.wstawPionek(new Pionek('C', 5, 5));
        plansza.wstawPionek(new Pionek('B', 6, 6));
        assertTrue(zarzadcaBudowniczych.zbijPionek('B', 6, 6, 4, 4));
        assertFalse(zarzadcaBudowniczych.zbijPionek('B', 4, 4, 2, 6));
        assertTrue(zarzadcaBudowniczych.zbijPionek('B', 4, 4, 2, 2));
        assertFalse(zarzadcaBudowniczych.zbijPionek('B', 2, 2, 0, 4));
        assertTrue(zarzadcaBudowniczych.zbijPionek('B', 2, 2, 4, 0));
    }

    @Test
    public void testPromocjiWariantPolski() {
        ZarzadcaBudowniczych zarzadcaBudowniczych = new ZarzadcaBudowniczych();
        PlanszaBudowniczy planszaBudowniczy = new PlanszaWariantPolskiBudowniczy();
        zarzadcaBudowniczych.ustawPlanszaBudowniczy(planszaBudowniczy);
        zarzadcaBudowniczych.skonstruujPlansze();
        Plansza plansza = zarzadcaBudowniczych.pobierzPlansza();

        plansza.ustawPionki(new ArrayList<>());
        plansza.wstawPionek(new Pionek('B', 3, 3));
        assertTrue(zarzadcaBudowniczych.normalnyRuch('B', 3, 3, 2, 2));
        assertFalse(plansza.pobierzPionek(2, 2).czyDamka());
        assertTrue(zarzadcaBudowniczych.normalnyRuch('B', 2, 2, 1, 1));
        assertFalse(plansza.pobierzPionek(1, 1).czyDamka());
        assertTrue(zarzadcaBudowniczych.normalnyRuch('B', 1, 1, 0, 0));
        plansza.pobierzPionek(0, 0).ustawDamka();
        assertTrue(plansza.pobierzPionek(0, 0).czyDamka());
        assertTrue(zarzadcaBudowniczych.normalnyRuch('B', 0, 0, 5, 5));

    }

}
