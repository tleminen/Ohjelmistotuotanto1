package com.oh.time4play;

public class VarausAjat {
    private int kenttaID;
    private int varausAika;

    public VarausAjat(int kentanID, int varattuAika) {
        this.kenttaID = kentanID;
        this.varausAika = varattuAika;
    }

    public VarausAjat(int varauksenAika) {
        this.varausAika = varauksenAika;
    }

    public int getKenttaID() {
        return kenttaID;
    }

    public void setKenttaID(int kenttaID) {
        this.kenttaID = kenttaID;
    }

    public int getVarausAika() {
        return varausAika;
    }

    public void setVarausAika(int varausAika) {
        this.varausAika = varausAika;
    }
}
