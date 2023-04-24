package com.oh.time4play;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SystemKyselyt {

    //pitäisi toimia, ei testattu
    public static boolean setUusiAsiakas(String loginTunnus, String salasana, String osoite) throws SQLException {
        boolean muutosOnnistui = false;
        int muutettu;

        Connection tietokantayhteys = Tietokantayhteys.yhdistaSystemTietokantaan();

        KirjautumisKyselyt.getOnkoRooliAsiakas(tietokantayhteys,loginTunnus);

        System.out.println("Lisätään asiakas tietokantaan..:");
        try (PreparedStatement statement = tietokantayhteys.prepareStatement("""
                INSERT INTO asiakas (email, osoite)
                VALUES (?,?)
                """)) {
            statement.setString(1,loginTunnus);
            statement.setString(2,osoite);
            muutettu = statement.executeUpdate();
            System.out.println("Lisätty asiakas tietokantaan\nSuoritettu vaihe: " + muutettu);
            tietokantayhteys.close();

            //Luodaan käyttäjälle kirjautumistiedot
            System.out.println("Luodaan käyttäjälle kirjautumistiedot... ");
            try(PreparedStatement statement1 = tietokantayhteys.prepareStatement("""
                    CREATE OR REPLACE USER ? IDENTIFIED BY ?
                    """)) {
                statement1.setString(1,loginTunnus+ "@OHi-Syli");
                statement1.setString(2,salasana);
                muutettu += statement1.executeUpdate();
                System.out.println(muutettu);
            }

            //Annetaan käyttöoikeudet uudelle asiakkaalle
            System.out.println("Lisätään rooli uudelle asiakkaalle...");
            try (PreparedStatement statement2 = tietokantayhteys.prepareStatement("""
                    GRANT SELECT ON kentat TO ?
                    """)) {
                statement2.setString(1,loginTunnus + "@OHi-Syli");
                muutettu += statement2.executeUpdate();
                System.out.println(muutettu);
            }

            System.out.println("Lisätään rooli uudelle asiakkaalle...");
            try (PreparedStatement statement3 = tietokantayhteys.prepareStatement("""
                    GRANT SELECT ON toimipiste TO ?
                    """)) {
                statement3.setString(1,loginTunnus + "@OHi-Syli");
                muutettu += statement3.executeUpdate();
                System.out.println(muutettu);
            }

            System.out.println("Lisätään rooli uudelle asiakkaalle...");
            try (PreparedStatement statement4 = tietokantayhteys.prepareStatement("""
                    GRANT SELECT, INSERT ON varaus TO ?
                    """)) {
                statement4.setString(1,loginTunnus + "@OHi-Syli");
                muutettu += statement4.executeUpdate();
                System.out.println(muutettu);
            }

            System.out.println("Lisätään rooli uudelle asiakkaalle...");
            try (PreparedStatement statement5 = tietokantayhteys.prepareStatement("""
                    GRANT SELECT ON asiakas TO ?
                    """)) {
                statement5.setString(1,loginTunnus + "@OHi-Syli");
                muutettu += statement5.executeUpdate();
                System.out.println(muutettu);
            }

            System.out.println("Lisätään rooli uudelle asiakkaalle...");
            try (PreparedStatement statement6 = tietokantayhteys.prepareStatement("""
                    GRANT SELECT ON pelivalineet TO ?
                    """)) {
                statement6.setString(1,loginTunnus + "@OHi-Syli");
                muutettu += statement6.executeUpdate();
                System.out.println(muutettu);
            }

            System.out.println("Lisätään rooli uudelle asiakkaalle...");
            try (PreparedStatement statement7 = tietokantayhteys.prepareStatement("""
                    GRANT SELECT ON saatavilla TO ?
                    """)) {
                statement7.setString(1,loginTunnus + "@OHi-Syli");
                muutettu += statement7.executeUpdate();
                System.out.println(muutettu);
            }


            //Asetetaan rooli uudelle asiakkaalle
            System.out.println("Asetetaan rooli uudelle asiakkaalle...");
            try (PreparedStatement statement8 = tietokantayhteys.prepareStatement("""
                    SET DEFAULT ROLE asiakasRooli FOR ?
                    """)) {
                statement8.setString(1,loginTunnus + "@OHi-Syli");
                muutettu += statement8.executeUpdate();
                System.out.println(muutettu);
            }

            if (muutettu == 8) {
                muutosOnnistui = true;
            }
            return muutosOnnistui;
        }
    }

}
