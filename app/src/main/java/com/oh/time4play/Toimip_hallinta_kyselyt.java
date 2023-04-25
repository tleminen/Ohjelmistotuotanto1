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
                GRANT SELECT, INSERT, UPDATE, DELETE ON kentat TO ?
                """)) {
            statement2.setString(1, toimipTiedot.ToimipisteVastaava);
            statement2.executeUpdate();
            System.out.println("Oikeudet kenttiin lisätty");
        }

        System.out.println("Lisätään oikeudet pelivalineisiin..");
        try (PreparedStatement statement3 = tietokantayhteys.prepareStatement("""
                GRANT SELECT, INSERT, UPDATE, DELETE ON pelivalineet TO ?
                """)) {
            statement3.setString(1, toimipTiedot.ToimipisteVastaava);
            statement3.executeUpdate();
            System.out.println("Oikeudet pelivälineisiin lisätty");
        }

        System.out.println("Lisätään oikeudet välitauluun..");
        try (PreparedStatement statement4 = tietokantayhteys.prepareStatement("""
                GRANT SELECT, INSERT, UPDATE, DELETE ON saatavilla TO ?
                """)) {
            statement4.setString(1, toimipTiedot.ToimipisteVastaava);
            statement4.executeUpdate();
            System.out.println("Oikeudet välitauluun lisätty");
        }

        System.out.println("Lisätään oikeudet kyselyihin kirjautumista varten..");
        try (PreparedStatement statement5 = tietokantayhteys.prepareStatement("""
                GRANT SELECT ON asiakas TO ?
                """)) {
            statement5.setString(1, toimipTiedot.ToimipisteVastaava);
            statement5.executeUpdate();
            System.out.println("SELECT asiakas ok.");
        }

        System.out.println("...Lisätään oikeudet kyselyihin kirjautumista varten..");
        try (PreparedStatement statement6 = tietokantayhteys.prepareStatement("""
                GRANT SELECT ON toimipiste TO ?
                """)) {
            statement6.setString(1, toimipTiedot.ToimipisteVastaava);
            statement6.executeUpdate();
            System.out.println("SELECT toimipiste ok.");
        }

    }
}
