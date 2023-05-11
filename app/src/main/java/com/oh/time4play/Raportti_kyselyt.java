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
            System.out.println("Varausraportti luotu");
            while (resultSet.next()) {
                System.out.println("Oli resultsetissä raporttia");
                raportti += resultSet.getInt("VarausID") + "\t|\t" + resultSet.getString("VarauksenPVM") + "\t|\t" + resultSet.getString("VarauksenAika") + ":00" + "\t|\t" + resultSet.getString("kentat.Kenttanimi") + "\t|\t" + resultSet.getString("email") + "\n";
            }
            return raportti;
        }
    }

    public static ArrayList<LaskuMuuttujat> getLocalSahkopostiLaskut(Connection connection, String kayttajatunnus) throws SQLException {
        ArrayList<LaskuMuuttujat> laskut = new ArrayList<>();
        System.out.println("Haetaan sähköpostilla laskutettavat varaukset...");
        try (PreparedStatement statement = connection.prepareStatement("""
                SELECT varaus.VarauksenPVM, varaus.VarauksenAika, varaus.VarausID, varaus.email, kentat.KenttaHinta, kentat.Kenttanimi
                    FROM varaus, kentat
                    WHERE Maksuntila = 3
                    AND Toimipistevastaava LIKE ?
                    AND varaus.KenttaID = kentat.KenttaID
                    ORDER BY VarauksenPVM, VarauksenAika
                """)) {
            statement.setString(1,kayttajatunnus);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                laskut.add(new LaskuMuuttujat(resultSet.getInt("VarausID"),resultSet.getString("VarauksenPVM"),resultSet.getInt("VarauksenAika"),resultSet.getString("email"),resultSet.getString("kenttaHinta"),resultSet.getString("Kenttanimi")));
            }
        }

        for (LaskuMuuttujat lasku: laskut) {
            try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT ValineNimi, ValineHinta
                        FROM pelivalineet, kuuluu
                        WHERE VarausID = ?
                        AND kuuluu.PelivalineID = pelivalineet.PelivalineID
                    """)) {
                statement.setInt(1,lasku.getVarausID());
                ResultSet resultSet = statement.executeQuery();
                lasku.setValitutLisapalvelut("\n\tLisäpalvelut:\n");
                while (resultSet.next()) {
                    lasku.addLisapalveluHinta(resultSet.getString("ValineHinta"));
                    lasku.addValitutLisapalvelut(resultSet.getString("ValineNimi") + " " + resultSet.getString("ValineHinta") + "€\n");
                }
            }
        }
        return laskut;
    }

    public static ArrayList<LaskuMuuttujat> getLocalPostiLaskut(Connection connection, String kayttajatunnus) throws SQLException {
        ArrayList<LaskuMuuttujat> laskut = new ArrayList<>();
        System.out.println("Haetaan sähköpostilla laskutettavat varaukset...");
        try (PreparedStatement statement = connection.prepareStatement("""
                SELECT varaus.VarauksenPVM, varaus.VarauksenAika, varaus.VarausID, varaus.email, kentat.KenttaHinta, kentat.Kenttanimi
                    FROM varaus, kentat
                    WHERE Maksuntila = 2
                    AND Toimipistevastaava LIKE ?
                    AND varaus.KenttaID = kentat.KenttaID
                    ORDER BY VarauksenPVM, VarauksenAika
                """)) {
            statement.setString(1,kayttajatunnus);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                laskut.add(new LaskuMuuttujat(resultSet.getInt("VarausID"),resultSet.getString("VarauksenPVM"),resultSet.getInt("VarauksenAika"),resultSet.getString("email"),resultSet.getString("kenttaHinta"),resultSet.getString("Kenttanimi")));
            }
        }

        for (LaskuMuuttujat lasku: laskut) {
            try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT ValineNimi, ValineHinta
                        FROM pelivalineet, kuuluu
                        WHERE VarausID = ?
                        AND kuuluu.PelivalineID = pelivalineet.PelivalineID
                    """)) {
                statement.setInt(1,lasku.getVarausID());
                ResultSet resultSet = statement.executeQuery();
                lasku.setValitutLisapalvelut("\n\tLisäpalvelut:\n");
                while (resultSet.next()) {
                    lasku.addLisapalveluHinta(resultSet.getString("ValineHinta"));
                    lasku.addValitutLisapalvelut(resultSet.getString("ValineNimi") + " " + resultSet.getString("ValineHinta") + "€\n");
                }
            }
        }
        return laskut;
    }

    public static ArrayList<LaskuMuuttujat> addAsiakkaanTiedot(Connection connection, ArrayList<LaskuMuuttujat> laskuta) throws SQLException {
        for (LaskuMuuttujat asiakas: laskuta) {
            try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT Osoite, Asiakasnimi
                        FROM asiakas, varaus
                        WHERE asiakas.email LIKE varaus.email
                        AND VarausID = ?
                    """)) {
                statement.setInt(1, asiakas.getVarausID());
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    asiakas.setAsiakkaanOsoite(resultSet.getString("Osoite"));
                    asiakas.setAsiakkaanNimi(resultSet.getString("Asiakasnimi"));
                }
            }
        }
        return laskuta;
    }

    public static void setLaskutettu(Connection connection, ArrayList<LaskuMuuttujat> laskuta) throws SQLException {
        System.out.println("Päivitetään laskut tilaan 1...");
        for (LaskuMuuttujat lasku:laskuta) {
            try (PreparedStatement statement = connection.prepareStatement("""
                    UPDATE `varausjarjestelma`.`varaus` 
                        SET `Maksuntila`=1 WHERE  `VarausID`=?;
                    """)) {
                statement.setInt(1,lasku.getVarausID());
                statement.executeUpdate();
            }
        }
        System.out.println("Laskut päivitetty");
    }

    public static String getAvoimetLaskut(Connection connection, String kayttajatunnus) throws SQLException {
        String avoimetLaskut = "";
        System.out.println("Oli resultsetissä raporttia");
        ArrayList<LaskuMuuttujat> laskut = new ArrayList<>();
        System.out.println("Haetaan sähköpostilla laskutettavat varaukset...");
        try (PreparedStatement statement = connection.prepareStatement("""
                SELECT varaus.VarauksenPVM, varaus.VarauksenAika, varaus.VarausID, varaus.email, kentat.KenttaHinta, kentat.Kenttanimi
                    FROM varaus, kentat
                    WHERE Maksuntila = 1
                    AND Toimipistevastaava LIKE ?
                    AND varaus.KenttaID = kentat.KenttaID
                    ORDER BY VarauksenPVM, VarauksenAika
                """)) {
            statement.setString(1,kayttajatunnus);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                laskut.add(new LaskuMuuttujat(resultSet.getInt("VarausID"),resultSet.getString("VarauksenPVM"),resultSet.getInt("VarauksenAika"),resultSet.getString("email"),resultSet.getString("kenttaHinta"),resultSet.getString("Kenttanimi")));
            }
        }

        for (LaskuMuuttujat lasku: laskut) {
            try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT ValineNimi, ValineHinta
                        FROM pelivalineet, kuuluu
                        WHERE VarausID = ?
                        AND kuuluu.PelivalineID = pelivalineet.PelivalineID
                    """)) {
                statement.setInt(1,lasku.getVarausID());
                ResultSet resultSet = statement.executeQuery();
                lasku.setValitutLisapalvelut("\n\tLisäpalvelut:\n");
                while (resultSet.next()) {
                    lasku.addLisapalveluHinta(resultSet.getString("ValineHinta"));
                    lasku.addValitutLisapalvelut(resultSet.getString("ValineNimi") + " " + resultSet.getString("ValineHinta") + "€\n");
                }
            }
        }

        for (LaskuMuuttujat asiakasPuuttuu : laskut) {
            try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT Osoite, Asiakasnimi
                        FROM asiakas, varaus
                        WHERE asiakas.email LIKE varaus.email
                        AND VarausID = ?
                    """)) {
                statement.setInt(1, asiakasPuuttuu.getVarausID());
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    asiakasPuuttuu.setAsiakkaanOsoite(resultSet.getString("Osoite"));
                    asiakasPuuttuu.setAsiakkaanNimi(resultSet.getString("Asiakasnimi"));
                }
            }
        }

        for (LaskuMuuttujat laskuja: laskut) {
            avoimetLaskut += "VarausID: " + laskuja.getVarausID() + " | Varauksen ajankohta: " + laskuja.getVarauksenAjankohta() + ":00\nNimi: " + laskuja.getAsiakkaanNimi() + " | Sähköpostiosoite: " + laskuja.getAsiakkaanEmail() + "\nOsoite: " + laskuja.getAsiakkaanOsoite() + "\nLoppusumma: " + laskuja.getLaskutettavaSumma() + "\n-------------------\n";
        }
        return avoimetLaskut;
    }
}
