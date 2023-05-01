package com.oh.time4play;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Toimip_hallinta_kyselyt {

    /**
     * Palauttaa kaikkien toimipisteiden tiedot
     * @param tietokantayhteys tietokantayhteys, pitää olla SELECT oikeudet toimipisteelle.
     * @return palauttaa ArrayListin Toimip_hallintaMuuttujat
     * @throws SQLException Sisältää SQL komentoja
     */
    static ArrayList<Toimip_hallintaMuuttujat> getAllToimipisteet(Connection tietokantayhteys) throws SQLException {
        ArrayList<Toimip_hallintaMuuttujat> itemArrayList = new ArrayList<>();
        System.out.println("Lukee dataa.. getAllToimipisteet:");
        try (PreparedStatement statement = tietokantayhteys.prepareStatement("""
                SELECT Kaupunki, Nimi, Toimipistevastaava
                FROM toimipiste
                ORDER BY Kaupunki
                """)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                itemArrayList.add(new Toimip_hallintaMuuttujat(resultSet.getString("Kaupunki"), resultSet.getString("Nimi"), resultSet.getString("ToimipisteVastaava")));
            }
            return itemArrayList;
        }
    }

    /**
     * Lisää uuden toimipisteen ja luo toimipisteelle kirjautumistunnukset
     * @param tietokantayhteys Tarvitsee tietokantayhteyden, jolla oikeus INSERT toimipistetauluun ja GRANT oikeuksia.
     * @param toimipTiedot Uuden toimipisteen tiedot Toimip_hallintaMuuttujat -oliona
     * @throws SQLException Sql kyselyitä
     */
    static void setLisaaUusiToimipiste(Connection tietokantayhteys, Toimip_hallintaMuuttujat toimipTiedot) throws SQLException {

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

    /**
     * Poistaa toimipisteen
     * @param connection Tarvitsee yhteyden, jolla on DELETE oikeus toimipiste -tauluun
     * @param poistettavaNimi Poistettavan toimipisteen Toimipistevastaava
     */
    //TODO POISTA MYÖS KIRJAUTUJAN TIEDOT ELI DROP USER TAI JOTAIN
    public static void poistaToimipiste(Connection connection, String poistettavaNimi) {
        System.out.println("Poistetaan toimipiste jonka Toimipistevastaava: " + poistettavaNimi);
        try (PreparedStatement statement7 = connection.prepareStatement("""
                DELETE FROM `varausjarjestelma`.`toimipiste` WHERE Toimipistevastaava =?;
                """)) {
            statement7.setString(1,poistettavaNimi);
            statement7.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO POISTA MYÖS KIRJAUTUJAN TIEDOT ELI DROP USER TAI JOTAIN ja myös varmistus onko avoimia laskuja vois olla ok.

    /**
     * Poistaa asiakkaan
     * @param connection Tarvitsee yhteyden jolla on DELETE oikeus asiakas -tauluun
     * @param asiakkaanTunnus Saa parametrikseen poistettavan asiakkaan sähköpostiosoitteen.
     */
    public static void poistaAsiakas(Connection connection, String asiakkaanTunnus) {
        System.out.println("Poistetaan Asiakas tunnuksella: " + asiakkaanTunnus);
        try (PreparedStatement statement7 = connection.prepareStatement("""
                DELETE FROM `varausjarjestelma`.`asiakas` WHERE  `email`= ?; 
                """)) {
            statement7.setString(1,asiakkaanTunnus);
            statement7.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * PaLauttaa yhden toimipisteen tiedot
     * @param connection Tarvitsee yhteyden jolla SELECT oikeus toimipisteeseen
     * @param muokattavaToimipisteVastaava Valittavan toimipisteen toimipistevastaava
     * @return Palauttaa Toimip_hallintaMuuttujat -olion jolla kentät Kaupunki, Nimi ja Toimipistevastaava
     */
    public static Toimip_hallintaMuuttujat getToimipiste(Connection connection, String muokattavaToimipisteVastaava) {
        Toimip_hallintaMuuttujat pyydetty = new Toimip_hallintaMuuttujat();
        System.out.println("Haetaan pyydetyn toimipisteen tiedot...");
        try (PreparedStatement statement = connection.prepareStatement("""
                SELECT Kaupunki, Nimi, Toimipistevastaava
                FROM toimipiste
                WHERE Toimipistevastaava = ?
                """)) {
            statement.setString(1, muokattavaToimipisteVastaava);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                pyydetty.Kaupunki = resultSet.getString("Kaupunki");
                pyydetty.Nimi = resultSet.getString("Nimi");
                pyydetty.ToimipisteVastaava = resultSet.getString("Toimipistevastaava");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pyydetty;
    }

    /**
     * Päivittää toimipisteen tiedot.
     * @param connection Tarvitsee yhteyden jolla oikeus UPDATE toimipiste
     * @param muokattavanTiedot Saa parametrikseen olion, jolla Kaupunki, Toimipistevastaava ja Nimi
     * @throws SQLException SQL kyselyitä
     */
    public static void updateToimipiste(Connection connection, Toimip_hallintaMuuttujat muokattavanTiedot) throws SQLException {
        System.out.println("Päivitetään toimipistettä, nimi: " + muokattavanTiedot.Nimi + "...");
        System.out.println("Kaupunki: " + muokattavanTiedot.Kaupunki);
        System.out.println("TpVastaava: " + muokattavanTiedot.ToimipisteVastaava);
        try (PreparedStatement statement = connection.prepareStatement("""
                UPDATE `varausjarjestelma`.`toimipiste` 
                SET `Kaupunki`= ?, `Nimi`= ? 
                WHERE  `Toimipistevastaava`= ?;
                """)){
            statement.setString(1,muokattavanTiedot.Kaupunki);
            statement.setString(2,muokattavanTiedot.Nimi);
            statement.setString(3,muokattavanTiedot.ToimipisteVastaava);
            statement.executeUpdate();
        }
    }
}
