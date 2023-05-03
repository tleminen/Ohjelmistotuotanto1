package com.oh.time4play;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Raportti_kyselyt {

    public static String getLocalVarausraportti(Connection connection, String kayttajatunnus, String alkupvm, String loppupvm) throws SQLException {
        String raportti = "";
        System.out.println("Luodaan varausraportti...");
        try (PreparedStatement statement = connection.prepareStatement("""
                SELECT VarausID, VarauksenPVM, VarauksenAika, varaus.KenttaID, email
                FROM varaus, toimipiste, kentat
                WHERE toimipiste.Toimipistevastaava LIKE ?
                AND VarauksenPVM BETWEEN ? AND ?
                AND varaus.KenttaID = kentat.KenttaID AND kentat.Toimipistevastaava = toimipiste.Toimipistevastaava 
                """)) {
            statement.setString(1,kayttajatunnus);
            statement.setString(2,alkupvm);
            statement.setString(3,loppupvm);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("Varausrapostti luotu");
            while (resultSet.next()) {
                System.out.println("Oli resultsetissä raporttia");
                raportti += resultSet.getInt("VarausID") + "\t" + resultSet.getString("VarauksenPVM") + "\t" + resultSet.getString("VarauksenAika") + "\t" + resultSet.getString("varaus.KenttaID") + "\t" + resultSet.getString("email") + "\n";
            }
            return raportti;

        }
    }
}
