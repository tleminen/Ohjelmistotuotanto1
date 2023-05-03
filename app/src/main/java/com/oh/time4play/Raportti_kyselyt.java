package com.oh.time4play;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Raportti_kyselyt {

    public static String getLocalVarausraportti(Connection connection, String kayttajatunnus, String alkupvm, String loppupvm) throws SQLException {
        String raportti = "ID\t|\tPVM\t|\tAlkamisaika\t|\tKenttä\t|\tAsiakkaan email\n";
        System.out.println("Luodaan varausraportti...");
        try (PreparedStatement statement = connection.prepareStatement("""
                SELECT VarausID, VarauksenPVM, VarauksenAika, kentat.Kenttanimi, email
                FROM varaus, toimipiste, kentat
                WHERE toimipiste.Toimipistevastaava LIKE ?
                AND VarauksenPVM BETWEEN ? AND ?
                AND varaus.KenttaID = kentat.KenttaID AND kentat.Toimipistevastaava = toimipiste.Toimipistevastaava
                ORDER BY VarauksenPVM, VarauksenAika
                """)) {
            statement.setString(1,kayttajatunnus);
            statement.setString(2,alkupvm);
            statement.setString(3,loppupvm);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("Varausrapostti luotu");
            while (resultSet.next()) {
                System.out.println("Oli resultsetissä raporttia");
                raportti += resultSet.getInt("VarausID") + "\t|\t" + resultSet.getString("VarauksenPVM") + "\t|\t" + resultSet.getString("VarauksenAika") + ":00" + "\t|\t" + resultSet.getString("kentat.Kenttanimi") + "\t|\t" + resultSet.getString("email") + "\n";
            }
            return raportti;

        }
    }
}
