package com.oh.time4play;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Raportti_kyselyt {

    public static ArrayList<Varaus_Raportti_Muuttujat> getLocalVarausraportti(Connection connection, String kayttajatunnus, String alkupvm, String loppupvm) throws SQLException {
        ArrayList<Varaus_Raportti_Muuttujat> itemArrayList = new ArrayList<>();
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
            while (resultSet.next()) {
                itemArrayList.add(new Varaus_Raportti_Muuttujat(resultSet.getInt("VarausID"), resultSet.getString("VarauksenPVM"), resultSet.getString("VarauksenAika"), resultSet.getString("varaus.KenttaID"), resultSet.getString("email")));
            }
            return itemArrayList;

        }
    }
}
