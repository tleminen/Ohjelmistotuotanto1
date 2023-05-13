package com.oh.time4play;

/**
 * Varausraporttiolio sisältää varauksien tiedot, käytetään varauksien käsittelyssä
 */
public class Varaus_Raportti_Muuttujat {
    int varausID;
    String varauksenPVM;
    String varauksenAika;
    String KenttaID;
    String email;
    public Varaus_Raportti_Muuttujat(int vID, String vPVM, String vAika, String kID, String varaaja) {
        this.varausID = vID;
        this.varauksenPVM = vPVM;
        this.varauksenAika = vAika;
        this.KenttaID = kID;
        this.email = varaaja;
    }

    public Varaus_Raportti_Muuttujat() {
    }
}
