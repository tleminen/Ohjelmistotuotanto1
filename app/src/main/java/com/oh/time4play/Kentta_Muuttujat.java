package com.oh.time4play;

/**
 * Kenttaolio sisältää kenttien tiedot, käytetään kenttien käsittelyssä
 * @version 1.0
 */
public class Kentta_Muuttujat {
    public Kentta_Muuttujat() {}

    public Kentta_Muuttujat(String kentanNimi, String kentanLajitunnus, String kentanHinta, String kayttajaTunnus) {
        this.nimi = kentanNimi;
        this.kentanHinta = kentanHinta;
        this.lajitunnus = kentanLajitunnus;
        this.toimipistevastaava = kayttajaTunnus;
    }

    public Kentta_Muuttujat(int kenttaID, String nimi, String lajitunnus, String kentanHinta, String toimipistevastaava) {
        this.kenttaID = kenttaID;
        this.nimi = nimi;
        this.lajitunnus = lajitunnus;
        this.kentanHinta = kentanHinta;
        this.toimipistevastaava = toimipistevastaava;
    }

    protected int kenttaID;
    protected String nimi;
    protected String lajitunnus;
    protected String kentanHinta;
    protected String toimipistevastaava;
    protected boolean valittu = false;

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

    private int klo00 = 0;
    private int klo01 = 0;
    private int klo02 = 0;
    private int klo03 = 0;
    private int klo04 = 0;
    private int klo05 = 0;
    private int klo06 = 0;
    private int klo07 = 0;
    private int klo08 = 0;
    private int klo09 = 0;
    private int klo10 = 0;
    private int klo11 = 0;
    private int klo12 = 0;
    private int klo13 = 0;
    private int klo14 = 0;
    private int klo15 = 0;
    private int klo16 = 0;
    private int klo17 = 0;
    private int klo18 = 0;
    private int klo19 = 0;
    private int klo20 = 0;
    private int klo21 = 0;
    private int klo22 = 0;
    private int klo23 = 0;

    public int getKlo00() {
        return klo00;
    }

    public void setKlo00(int klo00) {
        this.klo00 = klo00;
    }

    public int getKlo01() {
        return klo01;
    }

    public void setKlo01(int klo01) {
        this.klo01 = klo01;
    }

    public int getKlo02() {
        return klo02;
    }

    public void setKlo02(int klo02) {
        this.klo02 = klo02;
    }

    public int getKlo03() {
        return klo03;
    }

    public void setKlo03(int klo03) {
        this.klo03 = klo03;
    }

    public int getKlo04() {
        return klo04;
    }

    public void setKlo04(int klo04) {
        this.klo04 = klo04;
    }

    public int getKlo05() {
        return klo05;
    }

    public void setKlo05(int klo05) {
        this.klo05 = klo05;
    }

    public int getKlo06() {
        return klo06;
    }

    public void setKlo06(int klo06) {
        this.klo06 = klo06;
    }

    public int getKlo07() {
        return klo07;
    }

    public void setKlo07(int klo07) {
        this.klo07 = klo07;
    }

    public int getKlo08() {
        return klo08;
    }

    public void setKlo08(int klo08) {
        this.klo08 = klo08;
    }

    public int getKlo09() {
        return klo09;
    }

    public void setKlo09(int klo09) {
        this.klo09 = klo09;
    }

    public int getKlo10() {
        return klo10;
    }

    public void setKlo10(int klo10) {
        this.klo10 = klo10;
    }

    public int getKlo11() {
        return klo11;
    }

    public void setKlo11(int klo11) {
        this.klo11 = klo11;
    }

    public int getKlo12() {
        return klo12;
    }

    public void setKlo12(int klo12) {
        this.klo12 = klo12;
    }

    public int getKlo13() {
        return klo13;
    }

    public void setKlo13(int klo13) {
        this.klo13 = klo13;
    }

    public int getKlo14() {
        return klo14;
    }

    public void setKlo14(int klo14) {
        this.klo14 = klo14;
    }

    public int getKlo15() {
        return klo15;
    }

    public void setKlo15(int klo15) {
        this.klo15 = klo15;
    }

    public int getKlo16() {
        return klo16;
    }

    public void setKlo16(int klo16) {
        this.klo16 = klo16;
    }

    public int getKlo17() {
        return klo17;
    }

    public void setKlo17(int klo17) {
        this.klo17 = klo17;
    }

    public int getKlo18() {
        return klo18;
    }

    public void setKlo18(int klo18) {
        this.klo18 = klo18;
    }

    public int getKlo19() {
        return klo19;
    }

    public void setKlo19(int klo19) {
        this.klo19 = klo19;
    }

    public int getKlo20() {
        return klo20;
    }

    public void setKlo20(int klo20) {
        this.klo20 = klo20;
    }

    public int getKlo21() {
        return klo21;
    }

    public void setKlo21(int klo21) {
        this.klo21 = klo21;
    }

    public int getKlo22() {
        return klo22;
    }

    public void setKlo22(int klo22) {
        this.klo22 = klo22;
    }

    public int getKlo23() {
        return klo23;
    }

    public void setKlo23(int klo23) {
        this.klo23 = klo23;
    }
}
