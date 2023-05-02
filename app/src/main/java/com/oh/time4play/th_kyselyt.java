package com.oh.time4play;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
