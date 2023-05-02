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

    public static ArrayList<Kentta_Muuttujat> getAllKentat(Connection yhdistaTietokantaan, String vastaava) throws SQLException {
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
}
