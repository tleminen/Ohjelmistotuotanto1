package com.oh.time4play;

import java.util.ArrayList;

/**
 * Toimip_hallintaolio, käytetään toimipisteiden käsittelyssä
 * @version 1.0
 */
public class Toimip_hallintaMuuttujat {

    public Toimip_hallintaMuuttujat() {
    }

    public Toimip_hallintaMuuttujat(String nimi, String kaupunki, String toimipisteVastaava, String salasana) {
        this.Nimi = nimi;
        this.Kaupunki = kaupunki;
        this.ToimipisteVastaava = toimipisteVastaava;
        this.Salasana = salasana;
    }
    public static ArrayList<Boolean> checked;
    public String Nimi = "-";
    public String Salasana = "";
    public String Kaupunki = "-";
    public String ToimipisteVastaava = "";
    protected boolean valittu = false;

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

