package com.oh.time4play;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//kesken
public class Toimip_hallinta_kyselyt {
/*
    //Aloitettu tekemään
    Toimip_hallintaMuuttujat getAllToimipisteet(Connection tietokantayhteys) throws SQLException {
        System.out.println("Lukee dataa..:");
        try (PreparedStatement statement = tietokantayhteys.prepareStatement("""
                SELECT Kaupunki, Nimi
                FROM toimipiste
                ORDER BY paikka
                """)) {
            ResultSet resultSet = statement.executeQuery();
            Toimip_hallintaMuuttujat[] muuttujat = new Toimip_hallintaMuuttujat[resultSet.]{};
            while (resultSet.next()) {
                muuttujat.Paikkakunta = resultSet.getString("Kaupunki");
                muuttujat.Nimi = resultSet.getString("Nimi");
            }
        }

    }
    */
}
