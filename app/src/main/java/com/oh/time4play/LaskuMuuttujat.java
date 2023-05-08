package com.oh.time4play;

public class LaskuMuuttujat {
    private String asiakkaanNimi;
    private String valitutLisapalvelut;
    private String loppuSumma;
    private int valittuMaksutapa;
    private String asiakkaanEmail;
    private String asiakkaanOsoite;
    private String toimipisteenNimi;
    private String kentanNimi;
    private String varauksenAjankohta;

    public LaskuMuuttujat() {
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
}
