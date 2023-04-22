package com.oh.time4play;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KirjautumisKyselyt {
    public static int rooli = 0;

    static void getOnkoRooliAsiakas(Connection tietokantayhteys, String LoginTunnus) throws SQLException {
        System.out.println("Lukee dataa..:");
        try (PreparedStatement statement = tietokantayhteys.prepareStatement("""
                SELECT AsiakasID
                FROM asiakas
                WHERE email LIKE ?
                """)) {
            statement.setString(1,LoginTunnus);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                rooli = 1;
            }
            statement.close();
        }
    }

    static void getOnkoRooliToimipistevastaava(Connection tietokantayhteys, String LoginTunnus) throws SQLException {
        System.out.println("Lukee dataa..:");
        try (PreparedStatement statement = tietokantayhteys.prepareStatement("""
                SELECT toimipisteID
                FROM toimipiste
                WHERE toimipistevastaava LIKE ?
                """)) {
            statement.setString(1,LoginTunnus);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                rooli = 2;
            }
            statement.close();
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
                System.out.println("Kyllä täällä oli dataa");
            }
            statement.close();
        }
    }
}
