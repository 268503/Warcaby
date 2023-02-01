package serwer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import serwer.dto.Partia;
import serwer.dto.Ruch;
import serwer.dto.Wariant;

import java.util.List;
import java.util.Set;

public class ZarzadcaBazyDanych {
    private final SessionFactory sf;
    Session factory;
    Partia partia;
    int licznik = 1;
    public ZarzadcaBazyDanych() {
        sf = HibernateUtil.getSessionFactory();
        factory = sf.openSession();
    }

    public void stworzPartie(int id_wariantu) {
        factory.beginTransaction();
        String query = "FROM Wariant WHERE id = " + id_wariantu;
        List<Wariant> warianty = factory.createQuery(query).list();
        Wariant wariant = warianty.get(0);
        partia = new Partia(wariant);
        Set<Partia> partie = wariant.getPartie();
        partie.add(partia);
        wariant.setPartie(partie);
        factory.persist(partia);

        factory.getTransaction().commit();
    }

    public void dodajRuch(int xPocz, int yPocz, int xKonc, int yKonc, int typRuchu, boolean promocja) {
        factory.beginTransaction();
        Ruch ruch = new Ruch(xPocz, yPocz, xKonc, yKonc, licznik, partia, typRuchu, promocja);
        Set<Ruch> ruchy = partia.getRuchy();
        ruchy.add(ruch);
        partia.setRuchy(ruchy);
        factory.persist(partia);
        factory.getTransaction().commit();
        licznik++;
    }

    public void stop() {
        HibernateUtil.shutdown();
    }

    public int pobierzIdPartii() {
        return partia.getId();
    }

    public int pobierzWariantPartii(int id_partii) {
        factory.beginTransaction();
        String zapytanie = "FROM Partia WHERE id = " + id_partii;
        List<Partia> partie = factory.createQuery(zapytanie).list();
        factory.getTransaction().commit();
        return partie.get(0).getWariant().getId();
    }
    public List<Ruch> pobierzRuchy(int id_partii) {
        factory.beginTransaction();
        String zapytanie = "FROM Ruch WHERE partia = " + id_partii + " ORDER BY numerRuchuWPartii";
        List<Ruch> ruchy = factory.createQuery(zapytanie).list();
        factory.getTransaction().commit();
        return ruchy;
    }
}
