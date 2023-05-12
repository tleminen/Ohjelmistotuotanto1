package com.oh.time4play;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SystemKyselyt {

    /**
     * Seuraavassa luodaan uusi asiakas ja sen käyttöoikeudet
     * @param loginTunnus Asiakkaan kirjautumistunnus ja sähköpostiosoite
     * @param salasana Asiakkaan salasana
     * @param osoite Asiakkaan osoite
     * @return palauttaa True jos muutos onnistui, tosin ehkä poistetaan vielä tää return
     * @throws SQLException Sisältää sql kyselyitä
     */
    public static boolean setUusiAsiakas(String loginTunnus, String salasana, String osoite, String nimi) throws SQLException {
        boolean muutosOnnistui = false;
        int muutettu;

        Connection tietokantayhteys = Tietokantayhteys.yhdistaSystemTietokantaan();

        System.out.println("Testataan ensin löytyykö jo samalla sähköpostiosoitteella asiakasta...");
        int oliko = 0;
        try (PreparedStatement statement = tietokantayhteys.prepareStatement("""
                SELECT email
                    FROM asiakas
                    WHERE email LIKE ?                
                """)) {
            statement.setString(1, loginTunnus);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                oliko += 1;
            }
        }
        if (oliko == 0) {
            System.out.println("Lisätään asiakkaan tiedot tietokantaan..:");
            try (PreparedStatement statement = tietokantayhteys.prepareStatement("""
                    INSERT INTO asiakas (email, osoite, Asiakasnimi)
                    VALUES (?,?, ?)
                    """)) {
                statement.setString(1, loginTunnus);
                statement.setString(2, osoite);
                statement.setString(3, nimi);
                muutettu = statement.executeUpdate();
                System.out.println("Lisätty asiakas tietokantaan\nSuoritettu vaihe: " + muutettu);
            }

            //Luodaan käyttäjälle kirjautumistiedot
            System.out.println("Luodaan käyttäjälle kirjautumistiedot... ");
            try (PreparedStatement statement1 = tietokantayhteys.prepareStatement("""
                    CREATE OR REPLACE USER ? IDENTIFIED BY ?
                    """)) {
                statement1.setString(1, loginTunnus);
                statement1.setString(2, salasana);
                statement1.executeUpdate();
                System.out.println("Käyttäjän kirjautumistiedot luotu!");
            }

            //Annetaan käyttöoikeudet uudelle asiakkaalle
            System.out.println("Lisätään oikeus1..");
            try (PreparedStatement statement2 = tietokantayhteys.prepareStatement("""
                    GRANT SELECT ON kentat TO ?
                    """)) {
                statement2.setString(1, loginTunnus);
                muutettu += statement2.executeUpdate();
                System.out.println(muutettu);
            }

            System.out.println("Lisätään oikeus2..");
            try (PreparedStatement statement3 = tietokantayhteys.prepareStatement("""
                    GRANT SELECT ON toimipiste TO ?
                    """)) {
                statement3.setString(1, loginTunnus);
                muutettu += statement3.executeUpdate();
                System.out.println(muutettu);
            }

            System.out.println("Lisätään oikeus3..");
            try (PreparedStatement statement4 = tietokantayhteys.prepareStatement("""
                    GRANT SELECT, INSERT ON varaus TO ?
                    """)) {
                statement4.setString(1, loginTunnus);
                muutettu += statement4.executeUpdate();
                System.out.println(muutettu);
            }

            System.out.println("Lisätään oikeus4..");
            try (PreparedStatement statement5 = tietokantayhteys.prepareStatement("""
                    GRANT SELECT ON asiakas TO ?
                    """)) {
                statement5.setString(1, loginTunnus);
                muutettu += statement5.executeUpdate();
                System.out.println(muutettu);
            }

            System.out.println("Lisätään oikeus5..");
            try (PreparedStatement statement6 = tietokantayhteys.prepareStatement("""
                    GRANT SELECT ON pelivalineet TO ?
                    """)) {
                statement6.setString(1, loginTunnus);
                muutettu += statement6.executeUpdate();
                System.out.println(muutettu);
            }

            System.out.println("Lisätään oikeus6..");
            try (PreparedStatement statement7 = tietokantayhteys.prepareStatement("""
                    GRANT SELECT ON saatavilla TO ?
                    """)) {
                statement7.setString(1, loginTunnus);
                muutettu += statement7.executeUpdate();
                System.out.println(muutettu);
            }

            muutosOnnistui = true;

            tietokantayhteys.close();
        }
        return muutosOnnistui;
    }
}