package com.oh.time4play;

public class Toimip_hallintaMuuttujat {

    public Toimip_hallintaMuuttujat() {
    }

    public Toimip_hallintaMuuttujat(String nimi, String kaupunki, String toimipisteVastaava, String salasana) {
        this.Nimi = nimi;
        this.Kaupunki = kaupunki;
        this.ToimipisteVastaava = toimipisteVastaava;
        this.Salasana = salasana;
    }
    public String Nimi = "-";
    public String Salasana = "";
    public String Kaupunki = "-";
    public String ToimipisteVastaava = "";

    public Toimip_hallintaMuuttujat(String kaupunki, String nimi, String toimipisteVastaava) {
        this.Kaupunki = kaupunki;
        this.Nimi = nimi;
        this.ToimipisteVastaava = toimipisteVastaava;
    }

    public String getToimipisteVastaava() {
        return ToimipisteVastaava;
    }

    public String getNimi() {
        return Nimi;
    }

    public String getSalasana() {
        return Salasana;
    }

    public String getKaupunki() {
        return Kaupunki;
    }

    public void setNimi(String nimi) {
        Nimi = nimi;
    }

    public void setSalasana(String salasana) {
        Salasana = salasana;
    }

    public void setKaupunki(String kaupunki) {
        Kaupunki = kaupunki;
    }

    public void setToimipisteVastaava(String toimipisteVastaava) {
        ToimipisteVastaava = toimipisteVastaava;
    }
}

