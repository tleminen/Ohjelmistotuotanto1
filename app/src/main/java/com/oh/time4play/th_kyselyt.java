package com.oh.time4play;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class th_kyselyt {
    public static void setLisaaUusiKentta(Connection connection, Kentta_Muuttujat lisattavaKe) {
        System.out.println("Lisätään uusi kenttä tietokantaan...");
        try (PreparedStatement statement2 = connection.prepareStatement("""
                INSERT INTO `varausjarjestelma`.`kentat` (`Lajitunnus`, `KenttaHinta`, `Kenttanimi`, `Toimipistevastaava`)
                VALUES (?, ?, ?, ?);
                """)) {
            statement2.setString(1, lisattavaKe.lajitunnus);
            statement2.setString(2, lisattavaKe.kentanHinta);
            statement2.setString(3, lisattavaKe.nimi);
            statement2.setString(4, lisattavaKe.toimipistevastaava);
            statement2.executeUpdate();
            System.out.println("Toimipisteen tiedot lisätty tietokantaan.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static ArrayList<Kentta_Muuttujat> getAllKentat(Connection yhdistaTietokantaan, String vastaava) throws SQLException {
        ArrayList<Kentta_Muuttujat> itemArrayList = new ArrayList<>();
        System.out.println("Lukee dataa... getAllKentat");
        try (PreparedStatement statement = yhdistaTietokantaan.prepareStatement("""
                SELECT LajiTunnus, KenttaID, KenttaHinta, Kenttanimi, Toimipistevastaava
                FROM kentat
                WHERE  `toimipistevastaava` LIKE ?
                ORDER BY LajiTunnus
                """)) {
            statement.setString(1, vastaava);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                itemArrayList.add(new Kentta_Muuttujat(resultSet.getInt("KenttaID"), resultSet.getString("Kenttanimi"), resultSet.getString("LajiTunnus"), resultSet.getString("KenttaHinta"), resultSet.getString("Toimipistevastaava")));
            }
            return itemArrayList;
        }
    }


    /**
     * Poistaa kentän
     * @param connection Tietokantayhteysolio jolla oikeus DELETE taululle "kentat"
     * @param poistettavaKenttaID Poistettavan kentan KenttaID
     */
    public static void poistaKentta(Connection connection, int poistettavaKenttaID) {
        System.out.println("Poistetaan toimipiste jonka kenttaID: " + poistettavaKenttaID + "...");
        try (PreparedStatement statement = connection.prepareStatement("""
                DELETE FROM `varausjarjestelma`.`kentat` WHERE KenttaID =?;
                """)) {
            statement.setInt(1,poistettavaKenttaID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Päivittää kentän tiedot
     * @param connection Tietokantayhteysolio jolla oikeus UPDATE tauluun "kentat"
     * @param muokattavanTiedot Muokattavan kentän uudet tiedot. KenttaID:tä ei voi vaihtaa
     * @throws SQLException Sql kysely
     */
    public static void updateKentta(Connection connection, Kentta_Muuttujat muokattavanTiedot) throws SQLException {
        System.out.println("Päivitetään toimipistettä, kenttaID: " + muokattavanTiedot.kenttaID + "...");
        System.out.println("Lajitunnus: " + muokattavanTiedot.lajitunnus);
        System.out.println("Uusi hinta: " + muokattavanTiedot.kentanHinta);
        try (PreparedStatement statement = connection.prepareStatement("""
                UPDATE `varausjarjestelma`.`kentat` 
                SET `LajiTunnus`= ?, `KenttaHinta`= ?, `Kenttanimi`= ? 
                WHERE  `KenttaID`= ?;
                """)){
            statement.setString(1,muokattavanTiedot.lajitunnus);
            statement.setString(2,muokattavanTiedot.kentanHinta);
            statement.setString(3,muokattavanTiedot.nimi);
            statement.setInt(4,muokattavanTiedot.kenttaID);
            statement.executeUpdate();
        }
    }

    public static ArrayList<Pelivaline_muuttujat> getLajinPelivalineet(Connection yhdistaTietokantaan, String valittuLaji) throws SQLException {
        ArrayList<Pelivaline_muuttujat> itemArrayList = new ArrayList<>();
        System.out.println("Lukee dataa... getAllKentat");
        try (PreparedStatement statement = yhdistaTietokantaan.prepareStatement("""
                SELECT pelivalineet.PelivalineID, ValineNimi, ValineHinta
                    FROM pelivalineet, saatavilla, kentat
                    WHERE LajiTunnus LIKE ?
                    AND kentat.KenttaID = saatavilla.KenttaID
                    AND saatavilla.PelivalineID = pelivalineet.PelivalineID
                """)) {
            statement.setString(1, valittuLaji);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                itemArrayList.add(new Pelivaline_muuttujat(resultSet.getInt("pelivalineet.PelivalineID"), resultSet.getString("ValineNimi"), resultSet.getString("ValineHinta")));
            }
            return itemArrayList;
        }
    }

    public static ArrayList<VarausAjat> getAllVarausAjat(Connection yhdistaTietokantaan, String valittuToimipiste, String valittuPVM) throws SQLException {
        ArrayList<VarausAjat> itemArrayList = new ArrayList<>();
        System.out.println("getAllVarausAjat Lukee dataa... ");
        try (PreparedStatement statement = yhdistaTietokantaan.prepareStatement("""
                SELECT varaus.KenttaID, VarauksenAika
                	FROM varaus, toimipiste, kentat
                	WHERE VarauksenPVM LIKE ?
                	AND kentat.Toimipistevastaava LIKE ?
                	AND varaus.KenttaID = kentat.KenttaID
                """)) {
            statement.setString(1, valittuPVM);
            statement.setString(2,valittuToimipiste);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                itemArrayList.add(new VarausAjat(resultSet.getInt("varaus.KenttaID"), resultSet.getInt("VarauksenAika")));
            }
            return itemArrayList;
        }
    }
}
