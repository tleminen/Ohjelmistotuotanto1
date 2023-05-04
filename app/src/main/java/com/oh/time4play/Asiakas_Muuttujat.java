package com.oh.time4play;

public class Asiakas_Muuttujat {
    private String email;
    private String asiakasNimi;
    private String rooli;
    private String osoite;

    public Asiakas_Muuttujat() {
    }

    public Asiakas_Muuttujat(String email, String asiakasNimi, String osoite) {
        this.email = email;
        this.asiakasNimi = asiakasNimi;
        this.osoite = osoite;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAsiakasNimi() {
        return asiakasNimi;
    }

    public void setAsiakasNimi(String asiakasNimi) {
        this.asiakasNimi = asiakasNimi;
    }

    public String getRooli() {
        return rooli;
    }

    public void setRooli(String rooli) {
        this.rooli = rooli;
    }

    public String getOsoite() {
        return osoite;
    }

    public void setOsoite(String osoite) {
        this.osoite = osoite;
    }
}
