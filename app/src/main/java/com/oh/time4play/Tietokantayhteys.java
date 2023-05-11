package com.oh.time4play;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Tietokantayhteyden muodostaminen
 */
public class Tietokantayhteys {
    private static Connection yhteys;
    /**
     * Tänne tehdään tietokantayhteyden muodostaminen
     *
     * @return palauttaa tietokantayhteyden
     */
    public static Connection yhdistaTietokantaan(String kayttajatunnus,String salasana) throws SQLException {
        System.out.println("Yritetään muodostaa tietokantayhteys käyttäjänä...");
         yhteys = DriverManager.getConnection(
                "jdbc:mariadb://10.143.134.96:3306/varausjarjestelma",
                kayttajatunnus, salasana
        );
        System.out.println("\tTietokantayhteys muodostaminen onnistui: "+yhteys.isValid(5));
         return yhteys;
    }

    /**
     * tänne tehdään System-käyttäjän yhdistäminen tietokantaan
     * SystemKäyttäjä voi lisätä uusia käyttäjiä ja hakea laskutustietoja
     * @return palauttaa tietokantayhteyden
     */
    public static Connection yhdistaSystemTietokantaan() throws SQLException {
        System.out.println("Muodostetaan yhteys tietokantaan. -SystemUser");
        yhteys = DriverManager.getConnection(
                "jdbc:mariadb://10.143.134.96:3306/varausjarjestelma",
                "SystemUser", "salasanaSys"
        );
        return yhteys;
    }

    /**
     * Tänne tehdään tietokantayhteyden katkaiseminen
     */
    public static void katkaiseYhteysTietokantaan() throws SQLException {
        System.out.println("\tTietokantayhteys katkaistaan...");
        yhteys.close();
        System.out.println("\n\tTietokantayhteys muodostettu: "+yhteys.isValid(5));
    }
}
