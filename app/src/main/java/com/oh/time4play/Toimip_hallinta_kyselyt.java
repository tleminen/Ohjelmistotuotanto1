package com.oh.time4play;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//kesken
public class Toimip_hallinta_kyselyt {

    static Toimip_hallintaMuuttujat[] getAllToimipisteet(Connection tietokantayhteys) throws SQLException {
        System.out.println("Lukee dataa.. getAllToimipisteet:");
        Toimip_hallintaMuuttujat[] toimipaikat = new Toimip_hallintaMuuttujat[10];
        try (PreparedStatement statement = tietokantayhteys.prepareStatement("""
                SELECT Kaupunki, Nimi, ToimipisteID
                FROM toimipiste
                ORDER BY Kaupunki
                """)) {
            ResultSet resultSet = statement.executeQuery();

            int i = 0;
            while (resultSet.next()) {
                Toimip_hallintaMuuttujat toimipaikka = new Toimip_hallintaMuuttujat();
                toimipaikka.Kaupunki = resultSet.getString("Kaupunki");
                toimipaikka.Nimi = resultSet.getString("Nimi");
                toimipaikka.ToimipisteID = resultSet.getString("ToimipisteID");
                toimipaikat[i] = toimipaikka;
                i ++;
            }
            Toimip_hallintaMuuttujat.maara = i;
        }
        return toimipaikat;
    }

    static void setLisaaUusiToimipiste(Connection tietokantayhteys, Toimip_hallintaMuuttujat toimipTiedot) throws SQLException {
        System.out.println("Lisätään uusi toimipiste");
        try (PreparedStatement statement2 = tietokantayhteys.prepareStatement("""
                INSERT
                """)) {
            statement2.executeUpdate();
        }
    }

}
