package com.oh.time4play;

public class Toimip_hallintaMuuttujat {

    public Toimip_hallintaMuuttujat() {

    }

    public Toimip_hallintaMuuttujat(String nimi, String kaupunki, String toimipisteVastaava) {
        Nimi = nimi;
        Kaupunki = kaupunki;
        ToimipisteVastaava = toimipisteVastaava;
    }

    public static int maara = 0;
    public String Nimi = "-";
    public String Kaupunki = "-";
    public String ToimipisteID = "";
    public String ToimipisteVastaava = "";

    public String getToimipisteVastaava() {
        return ToimipisteVastaava;
    }

    public void setToimipisteVastaava(String toimipisteVastaava) {
        ToimipisteVastaava = toimipisteVastaava;
    }

    public static int getMaara() {
        return maara;
    }

    public static void setMaara(int maara) {
        Toimip_hallintaMuuttujat.maara = maara;
    }

    public String getNimi() {
        return Nimi;
    }

    public void setNimi(String nimi) {
        Nimi = nimi;
    }

    public String getKaupunki() {
        return Kaupunki;
    }

    public void setKaupunki(String kaupunki) {
        Kaupunki = kaupunki;
    }

    public String getToimipisteID() {
        return ToimipisteID;
    }

    public void setToimipisteID(String toimipisteID) {
        ToimipisteID = toimipisteID;
    }
}
