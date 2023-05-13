package com.oh.time4play;

/**
 * Pelivalineolio sisältää pelivälineiden tiedot, käytetään pelivälineiden käsittelyssä
 */
public class Pelivaline_muuttujat {
    protected int pelivalineID;
    protected String lajiTunnus;
    protected String pelivalineNimi;
    protected String valineHinta;
    protected boolean valittu = false;

    public Pelivaline_muuttujat(){}

    public Pelivaline_muuttujat(int pelivalineID, String pelivalineNimi, String valineHinta) {
        this.pelivalineID = pelivalineID;
        this.pelivalineNimi = pelivalineNimi;
        this.valineHinta = valineHinta;
    }

    public Pelivaline_muuttujat(String nimi, String hinta, String valittuLajitunnus) {
        this.pelivalineNimi = nimi;
        this.valineHinta = hinta;
        this.lajiTunnus = valittuLajitunnus;
    }
}
