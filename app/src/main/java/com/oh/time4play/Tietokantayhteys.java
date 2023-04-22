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
    public static Connection yhdistaTietokantaan() throws SQLException {
         yhteys = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/varausjarjestelma",
                "ToimipisteidenHallinnoitsija", "salasana"
        );
         return yhteys;
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
    public static void katkaiseYhteysTietokantaan() throws SQLException {
        yhteys.close();
    }
}
