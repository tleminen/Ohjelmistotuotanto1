package com.oh.time4play;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * maksuikkunaan liittyvät kyselyt
 */

public class Maksun_Kyselyt {

    public static Asiakas_Muuttujat getAsiakas(Connection yhdistaTietokantaan, String kayttajatunnus) throws SQLException {
        Asiakas_Muuttujat asiakasMuuttuja = new Asiakas_Muuttujat();
        System.out.println("Lukee dataa... getAsiakas");
        try (PreparedStatement statement = yhdistaTietokantaan.prepareStatement("""
            SELECT Asiakasnimi, email, Osoite
                FROM asiakas
                WHERE  `email` LIKE ?
            """)) {
            statement.setString(1, kayttajatunnus);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                asiakasMuuttuja = new Asiakas_Muuttujat(resultSet.getString("email"), resultSet.getString("Asiakasnimi"), resultSet.getString("Osoite"));
            }
            return asiakasMuuttuja;
            }
    }

    /**
     * tekee tietokantaan varauksen, mikäli samalla ajalla ja kentällä ei ole jo varausta
     * @param yhdistaSystemTietokantaan tietokantayhteys
     * @param valittuPVM valittu päivämäärä
     * @param valittuAika valittu aika
     * @param valittuKentta valittu kenttä
     * @param kayttajatunnus käyttäjätunnus
     * @param pelivalineIDt pelivälineiden ID
     * @param valittuMaksutapa valittu maksutapa
     * @return
     * @throws SQLException
     */

    public static boolean teeVaraus(Connection yhdistaSystemTietokantaan, String valittuPVM, int valittuAika, int valittuKentta, String kayttajatunnus, int[] pelivalineIDt, int valittuMaksutapa) throws SQLException {
        boolean onnistui = true;
        System.out.println("Tarkistetaan ensin onko varausta samoilla tiedoilla...");
        try (PreparedStatement statement = yhdistaSystemTietokantaan.prepareStatement("""
                SELECT VarausID
                        FROM `varausjarjestelma`.`varaus`
                        WHERE `VarauksenPVM` LIKE ?
                        AND `VarauksenAika` LIKE ?
                        AND `KenttaID` = ?
                """)) {
            statement.setString(1, valittuPVM);
            statement.setInt(2, valittuAika);
            statement.setInt(3, valittuKentta);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println("Ei onnistunut");
                onnistui = false;
            }
        }

        if (onnistui) {

            System.out.println("Lisätään uusi varaus...");
            try (PreparedStatement statement = yhdistaSystemTietokantaan.prepareStatement("""
                    INSERT INTO `varausjarjestelma`.`varaus` (`VarauksenPVM`, `VarauksenAika`, `KenttaID`, `email`, `Maksuntila`) 
                        VALUES (?, ?, ?, ?, ?)
                    """)) {
                statement.setString(1, valittuPVM);
                statement.setInt(2, valittuAika);
                statement.setInt(3, valittuKentta);
                statement.setString(4, kayttajatunnus);
                statement.setInt(5, valittuMaksutapa);
                statement.executeUpdate();
            }

            //Seuraavaksi lisätään mahdollisesti varaukseen tulevat pelivälineet tietokantaan
            if (pelivalineIDt[0] != 0) {
                int varausID = 0;
                try (PreparedStatement statement3 = yhdistaSystemTietokantaan.prepareStatement("""
                        SELECT VarausID
                            FROM `varausjarjestelma`.`varaus`
                            WHERE `VarauksenPVM` LIKE ?
                            AND `VarauksenAika` LIKE ?
                            AND `KenttaID` = ?
                            AND `email` LIKE ?
                        """)) {
                    statement3.setString(1, valittuPVM);
                    statement3.setInt(2, valittuAika);
                    statement3.setInt(3, valittuKentta);
                    statement3.setString(4, kayttajatunnus);
                    ResultSet resultSet = statement3.executeQuery();
                    while (resultSet.next()) {
                        varausID = resultSet.getInt("VarausID");
                    }
                }
                int i = 0;
                while (pelivalineIDt[i] != 0 && i < 10) {

                    try (PreparedStatement statement2 = yhdistaSystemTietokantaan.prepareStatement("""
                            INSERT INTO `varausjarjestelma`.`kuuluu` (`VarausID`, `PelivalineID`) 
                                VALUES (?, ?);
                            """)) {
                        statement2.setInt(1, varausID);
                        statement2.setInt(2, pelivalineIDt[i]);
                        statement2.executeUpdate();
                    }
                    i++;
                }
            }
        } return onnistui;
    }

    /**
     * päivittää asiakkaan tiedot
     * @param yhdistaSystemTietokantaan tietokantayhteys
     * @param kayttajatunnus asiakkaan käyttäjätunnus
     * @param osoite asiakkaan osoite
     * @param nimi asiakkaan nimi
     * @throws SQLException
     */
    public static void updateAsiakas(Connection yhdistaSystemTietokantaan, String kayttajatunnus, String osoite, String nimi) throws SQLException {
        try (PreparedStatement statement = yhdistaSystemTietokantaan.prepareStatement("""
                UPDATE `varausjarjestelma`.`asiakas` 
                    SET `Osoite`= ?, `Asiakasnimi`= ? WHERE  `email` LIKE ?
                """)) {
            statement.setString(1,osoite);
            statement.setString(2,nimi);
            statement.setString(3,kayttajatunnus);
            statement.executeUpdate();
        }
    }
}
