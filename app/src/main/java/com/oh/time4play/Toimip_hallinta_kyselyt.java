package com.oh.time4play;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//kesken
public class Toimip_hallinta_kyselyt {

    static Toimip_hallintaMuuttujat[] getAllToimipisteet(Connection tietokantayhteys) throws SQLException {
        System.out.println("Lukee dataa..:");
        Toimip_hallintaMuuttujat[] toimipaikat = new Toimip_hallintaMuuttujat[10];
        try (PreparedStatement statement = tietokantayhteys.prepareStatement("""
                SELECT Kaupunki, Nimi
                FROM toimipiste
                ORDER BY Kaupunki
                """)) {
            ResultSet resultSet = statement.executeQuery();

            int i = 0;
            while (resultSet.next()) {
                Toimip_hallintaMuuttujat toimipaikka = toimipaikat[i];
                toimipaikka.Paikkakunta = resultSet.getString("Kaupunki");
                toimipaikka.Nimi = resultSet.getString("Nimi");
                i ++;
            }
            Toimip_hallintaMuuttujat.maara = i;
        }
        return toimipaikat;
    }
}
