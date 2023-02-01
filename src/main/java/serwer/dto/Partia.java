package serwer.dto;

import java.util.HashSet;
import java.util.Set;

public class Partia {
    private int id;
    private Wariant wariant;
    private Set<Ruch> ruchy;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Ruch> getRuchy() {
        return ruchy;
    }

    public void setRuchy(Set<Ruch> ruchy) {
        this.ruchy = ruchy;
    }

    public Partia(Wariant wariant) {
        this.wariant = wariant;
        ruchy = new HashSet<>();
    }

    public Partia() {
        ruchy = new HashSet<>();
    }

    public Wariant getWariant() {
        return wariant;
    }

    public void setWariant(Wariant wariant) {
        this.wariant = wariant;
    }
}
