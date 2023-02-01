package serwer.dto;

import java.util.Set;

public class Wariant {
    private int id;
    private String nazwa;
    private int wymiarPlanszy;
    private Set<Partia> partie;
    public Wariant(String nazwa, int wymiarPlanszy) {
        this.nazwa = nazwa;
        this.wymiarPlanszy = wymiarPlanszy;
    }
    public Wariant() {}

    public int getId () {
        return id;
    }
    public void setId (int id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getWymiarPlanszy() {
        return wymiarPlanszy;
    }

    public void setWymiarPlanszy(int wymiarPlanszy) {
        this.wymiarPlanszy = wymiarPlanszy;
    }

    public Set<Partia> getPartie() {
        return partie;
    }

    public void setPartie(Set<Partia> partie) {
        this.partie = partie;
    }
}
