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
            System.out.println(muutettu);
            tietokantayhteys.close();

            //Luodaan käyttäjälle kirjautumistiedot
            System.out.printf("Luodaan käyttäjälle kirjautumistiedot... ");
            try(PreparedStatement statement1 = tietokantayhteys.prepareStatement("""
                    CREATE USER ? IDENTIFIED BY ?
                    """)) {
                statement1.setString(1,loginTunnus);
                statement1.setString(2,salasana);
                muutettu += statement1.executeUpdate();
                System.out.println(muutettu);
            }

            //Lisätään rooli uudelle asiakkaalle
            System.out.println("Lisätään rooli uudelle asiakkaalle...");
            try (PreparedStatement statement2 = tietokantayhteys.prepareStatement("""
                    GRANT asiakasRooli TO ?
                    """)) {
                statement2.setString(1,loginTunnus + "@localhost");
                muutettu += statement2.executeUpdate();
                System.out.println(muutettu);
            }

            //Asetetaan rooli uudelle asiakkaalle
            System.out.println("Asetetaan rooli uudelle asiakkaalle...");
            try (PreparedStatement statement3 = tietokantayhteys.prepareStatement("""
                    SET DEFAULT ROLE asiakasRooli FOR ?
                    """)) {
                statement3.setString(1,loginTunnus+"@localhost");
                muutettu += statement3.executeUpdate();
                System.out.println(muutettu);
            }

            if (muutettu == 4) {
                muutosOnnistui = true;
            }
            return muutosOnnistui;
        }
    }

}
