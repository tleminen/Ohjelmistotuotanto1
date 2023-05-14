package com.oh.time4play;

/**
 * Laskuolio sisältää laskujen tiedot, käytetään laskujen käsittelyssä
 */
public class LaskuMuuttujat {
    private int varausID;
    private String asiakkaanNimi;
    private String valitutLisapalvelut;
    private double lisapalveluHinta = 0;
    private String loppuSumma;
    private int valittuMaksutapa;
    private String asiakkaanEmail;
    private String asiakkaanOsoite;
    private String toimipisteenNimi;
    private double kentanHinta;
    private String kentanNimi;
    private String varauksenAjankohta;

    public LaskuMuuttujat() {
    }

    public int getVarausID() {
        return varausID;
    }

    public void setVarausID(int varausID) {
        this.varausID = varausID;
    }

    public double getKentanHinta() {
        return kentanHinta;
    }

    public void setKentanHinta(double kentanHinta) {
        this.kentanHinta = kentanHinta;
    }

    public LaskuMuuttujat(int varausID, String varauksenPVM, int varauksenAika, String email, double kenttaHinta, String kenttanimi) {
        this.varausID = varausID;
        this.varauksenAjankohta = varauksenPVM + " klo: " + varauksenAika;
        this.asiakkaanEmail = email;
        this.kentanHinta = kenttaHinta;
        this.kentanNimi = kenttanimi;
    }

    public String getToimipisteenNimi() {
        return toimipisteenNimi;
    }

    public void setToimipisteenNimi(String toimipisteenNimi) {
        this.toimipisteenNimi = toimipisteenNimi;
    }

    public String getKentanNimi() {
        return kentanNimi;
    }

    public void setKentanNimi(String kentanNimi) {
        this.kentanNimi = kentanNimi;
    }

    public String getVarauksenAjankohta() {
        return varauksenAjankohta;
    }

    public void setVarauksenAjankohta(String varauksenAjankohta) {
        this.varauksenAjankohta = varauksenAjankohta;
    }

    public String getAsiakkaanNimi() {
        return asiakkaanNimi;
    }

    public void setAsiakkaanNimi(String asiakkaanNimi) {
        this.asiakkaanNimi = asiakkaanNimi;
    }

    public String getValitutLisapalvelut() {
        return valitutLisapalvelut;
    }

    public void setValitutLisapalvelut(String valitutLisapalvelut) {
        this.valitutLisapalvelut = valitutLisapalvelut;
    }
    public void addValitutLisapalvelut(String valitutLisapalvelut) {
        this.valitutLisapalvelut += valitutLisapalvelut;
    }
    public String getLoppuSumma() {
        return loppuSumma;
    }

    public void setLoppuSumma(String loppuSumma) {
        this.loppuSumma = loppuSumma;
    }

    public int getValittuMaksutapa() {
        return valittuMaksutapa;
    }

    public void setValittuMaksutapa(int valittuMaksutapa) {
        this.valittuMaksutapa = valittuMaksutapa;
    }

    public String getAsiakkaanEmail() {
        return asiakkaanEmail;
    }

    public void setAsiakkaanEmail(String asiakkaanEmail) {
        this.asiakkaanEmail = asiakkaanEmail;
    }

    public String getAsiakkaanOsoite() {
        return asiakkaanOsoite;
    }

    public void setAsiakkaanOsoite(String asiakkaanOsoite) {
        this.asiakkaanOsoite = asiakkaanOsoite;
    }

    public void addLisapalveluHinta(String valineHinta) {
        valineHinta = valineHinta.replace(",",".");
        this.lisapalveluHinta += Double.parseDouble(valineHinta);
    }

    public double getLaskutettavaSumma() {
        return kentanHinta + lisapalveluHinta;
    }
}
