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
                i++;
            }
            Toimip_hallintaMuuttujat.maara = i;
        }
        return toimipaikat;
    }

    static void setLisaaUusiToimipiste(Connection tietokantayhteys, Toimip_hallintaMuuttujat toimipTiedot) throws SQLException {

        //TODO Tähän tehdään vielä testi onko jo toimipistettä olemassa

        System.out.println("Lisätään uusi toimipiste tietokantaan...");
        try (PreparedStatement statement2 = tietokantayhteys.prepareStatement("""
                INSERT INTO `varausjarjestelma`.`toimipiste` (`Kaupunki`, `Nimi`, `Toimipistevastaava`)
                VALUES (?, ?, ?);
                """)) {
            statement2.setString(1, toimipTiedot.Kaupunki);
            statement2.setString(2, toimipTiedot.Nimi);
            statement2.setString(3, toimipTiedot.ToimipisteVastaava);
            statement2.executeUpdate();
            System.out.println("Toimipisteen tiedot lisätty tietokantaan.");
        }

        //Luodaan käyttäjälle kirjautumistiedot
        System.out.println("Luodaan toimipistevastaavalle kirjautumistiedot... ");
        try (PreparedStatement statement1 = tietokantayhteys.prepareStatement("""
                CREATE OR REPLACE USER ? IDENTIFIED BY ?
                """)) {
            statement1.setString(1, toimipTiedot.ToimipisteVastaava);
            statement1.setString(2, toimipTiedot.Salasana);
            statement1.executeUpdate();
            System.out.println("Toimipistevastaavan kirjautumistiedot luotu!");
        }

        //Annetaan käyttöoikeudet uudelle toimipistevastaavalle
        System.out.println("Lisätään oikeudet kenttiin..");
        try (PreparedStatement statement2 = tietokantayhteys.prepareStatement("""
                GRANT ALL ON kentat TO ?
                """)) {
            statement2.setString(1, toimipTiedot.ToimipisteVastaava);
        }

        System.out.println("Lisätään oikeudet pelivalineisiin..");
        try (PreparedStatement statement3 = tietokantayhteys.prepareStatement("""
                GRANT ALL ON pelivalineet TO ?
                """)) {
            statement3.setString(1, toimipTiedot.ToimipisteVastaava);
        }
    }
}
