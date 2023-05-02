package com.oh.time4play;

public class Kentta_Muuttujat {
    public Kentta_Muuttujat() {}

    public Kentta_Muuttujat(String kentanNimi, String kentanLajitunnus, String kentanHinta, String kayttajaTunnus) {
        this.nimi = kentanNimi;
        this.kentanHinta = kentanHinta;
        this.lajitunnus = kentanLajitunnus;
        this.toimipistevastaava = kayttajaTunnus;
    }

    protected int kenttaID;
    protected String nimi;
    protected String lajitunnus;
    protected String kentanHinta;
    protected String toimipistevastaava;

    public int getKenttaID() {
        return kenttaID;
    }

    public void setKenttaID(int kenttaID) {
        this.kenttaID = kenttaID;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getLajitunnus() {
        return lajitunnus;
    }

    public void setLajitunnus(String lajitunnus) {
        this.lajitunnus = lajitunnus;
    }

    public String getKentanHinta() {
        return kentanHinta;
    }

    public void setKentanHinta(String kentanHinta) {
        this.kentanHinta = kentanHinta;
    }
}
