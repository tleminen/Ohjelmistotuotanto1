package com.oh.time4play;


import java.sql.Connection;

/**
 * Tietokantayhteyden muodostaminen
 */
public class Tietokantayhteys {
    /**
     * Tänne tehdään tietokantayhteyden muodostaminen
     *
     * @return palauttaa tietokantayhteyden
     */
    public static Connection yhdistaTietokantaan(String Kayttajatunnus, String Salasana) {

        return null;
    }

    /**
     * tänne tehdään System-käyttäjän yhdistäminen tietokantaan
     * SystemKäyttäjä voi lisätä uusia käyttäjiä ja hakea laskutustietoja
     * @return palauttaa tietokantayhteyden
     */
    public static Connection yhdistaSystemTietokantaan() {
        return null;
    }

    /**
     * Tänne tehdään tietokantayhteyden katkaiseminen
     */
    public static void katkaiseYhteysTietokantaan() {

    }
}
