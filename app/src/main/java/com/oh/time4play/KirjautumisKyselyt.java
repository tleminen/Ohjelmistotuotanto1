package com.oh.time4play;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * luokka sisältää kyselyitä joilla varmennetaan käyttäjän rooli, se palauttaa 0, mikäli käyttäjää ei löydy
 */
public class KirjautumisKyselyt {
    public static int getRooli() {
        return rooli;
    }
    public static void setRooli(int rooli) {
        KirjautumisKyselyt.rooli = rooli;
    }
    public static int rooli = 0;

    static void getOnkoRooliAsiakas(Connection tietokantayhteys, String LoginTunnus) throws SQLException {
        System.out.println("Lukee dataa..:");
        try (PreparedStatement statement = tietokantayhteys.prepareStatement("""
                SELECT email
                FROM asiakas
                WHERE email LIKE ?
                AND rooli NOT LIKE "TpLuoja"
                """)) {
            statement.setString(1,LoginTunnus);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                rooli = 1;
            }
        }
    }

    static void getOnkoRooliToimipistevastaava(Connection tietokantayhteys, String LoginTunnus) throws SQLException {
        System.out.println("Lukee dataa..:");
        try (PreparedStatement statement = tietokantayhteys.prepareStatement("""
                SELECT Toimipistevastaava
                FROM toimipiste
                WHERE toimipistevastaava LIKE ?
                """)) {
            statement.setString(1,LoginTunnus);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                rooli = 2;
            }
        }
    }

    static void getOnkoRooliToimipisteidenHallinnoitsija(Connection tietokantayhteys, String LoginTunnus) throws SQLException {
        System.out.println("Lukee dataa..:");
        try (PreparedStatement statement = tietokantayhteys.prepareStatement("""
                SELECT *
                FROM asiakas
                WHERE email LIKE ?
                AND rooli LIKE "TpLuoja"
                """)) {
            statement.setString(1,LoginTunnus);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                rooli = 3;
            }
        }
    }
}
