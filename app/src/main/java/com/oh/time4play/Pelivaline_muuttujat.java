package com.oh.time4play;

public class Pelivaline_muuttujat {
    protected int pelivalineID;
    protected String pelivalineNimi;
    protected String valineHinta;

    public Pelivaline_muuttujat(){}

    public Pelivaline_muuttujat(int pelivalineID, String pelivalineNimi, String valineHinta) {
        this.pelivalineID = pelivalineID;
        this.pelivalineNimi = pelivalineNimi;
        this.valineHinta = valineHinta;
    }
}
