package com.oh.time4play;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KirjautumisKyselyt {

    static boolean getOnkoRooliAsiakas(Connection tietokantayhteys, String LoginTunnus) throws SQLException {
        System.out.println("Lukee dataa..:");
        try (PreparedStatement statement = tietokantayhteys.prepareStatement("""
                SELECT AsiakasID
                FROM asiakas
                WHERE email LIKE ?
                """)) {
            statement.setString(1,LoginTunnus);
            ResultSet resultSet = statement.executeQuery();
            boolean RooliAsiakas = false;
            while (resultSet.next()) {
                RooliAsiakas = true;
            }
            statement.close();
            return RooliAsiakas;
        }
    }

    static boolean getOnkoRooliToimipistevastaava(Connection tietokantayhteys, String LoginTunnus) throws SQLException {
        System.out.println("Lukee dataa..:");
        try (PreparedStatement statement = tietokantayhteys.prepareStatement("""
                SELECT toimipisteID
                FROM toimipiste
                WHERE toimipistevastaava LIKE ?
                """)) {
            statement.setString(1,LoginTunnus);
            ResultSet resultSet = statement.executeQuery();
            boolean RooliToimipistevastaava = false;
            while (resultSet.next()) {
                RooliToimipistevastaava = true;
            }
            statement.close();
            return RooliToimipistevastaava;
        }
    }

    static boolean getOnkoRooliToimipisteidenHallinnoitsija(Connection tietokantayhteys, String LoginTunnus) throws SQLException {
        System.out.println("Lukee dataa..:");
        try (PreparedStatement statement = tietokantayhteys.prepareStatement("""
                SELECT AsiakasID
                FROM asiakas
                WHERE email LIKE ?
                AND rooli LIKE "TpLuoja"
                """)) {
            statement.setString(1,LoginTunnus);
            ResultSet resultSet = statement.executeQuery();
            boolean RooliTpLuoja= false;
            while (resultSet.next()) {
                RooliTpLuoja = true;
            }
            statement.close();
            return RooliTpLuoja;
        }
    }
}
