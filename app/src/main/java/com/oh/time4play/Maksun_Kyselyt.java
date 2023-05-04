package com.oh.time4play;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    //TODO TEE TÄNNE VARAUKSEN TEKEMINEN JA PALUU ETTÄ JOS ONNISTUU NIIN MENNÄÄN MAKSAMAAN
    public static boolean teeVaraus(Connection yhdistaSystemTietokantaan, String valittuPVM, int valittuAika, int valittuKentta, String kayttajatunnus) {


        return true;
    }
}
