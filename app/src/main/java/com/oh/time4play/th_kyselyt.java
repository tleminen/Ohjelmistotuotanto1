package com.oh.time4play;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class th_kyselyt {

    public static void updatePelivaline(Connection connection, Pelivaline_muuttujat muokattavanTiedot) throws SQLException {
        System.out.println("Päivitetään toimipistettä, pelivalineID: " + muokattavanTiedot.pelivalineID + "...");
        System.out.println("Välinenimi: " + muokattavanTiedot.pelivalineNimi);
        System.out.println("Uusi hinta: " + muokattavanTiedot.valineHinta);
        try (PreparedStatement statement = connection.prepareStatement("""
                UPDATE `varausjarjestelma`.`pelivalineet` 
                SET `Valinenimi` = ?, `ValineHinta` = ?
                WHERE  `PelivalineID` = ?
                """)){
            statement.setString(1,muokattavanTiedot.pelivalineNimi);
            statement.setString(2,muokattavanTiedot.valineHinta);
            statement.setInt(3,muokattavanTiedot.pelivalineID);
            statement.executeUpdate();
        }
    }

    public static void poistaPelivaline(Connection connection, int poistettavaPelivalineID) throws SQLException {
        System.out.println("Poistetaan peliväline ensin kuuluu -taulusta...");
        try (PreparedStatement statement1 = connection.prepareStatement("""
                DELETE FROM `varausjarjestelma`.`kuuluu` 
                    WHERE `PelivalineID` = ?
                """)) {
            statement1.setInt(1,poistettavaPelivalineID);
            statement1.executeUpdate();
        }

        System.out.println("Poistetaan peliväline saatavilla -taulusta...");
        try (PreparedStatement statement1 = connection.prepareStatement("""
                DELETE FROM `varausjarjestelma`.`saatavilla` 
                    WHERE `PelivalineID` = ?
                """)) {
            statement1.setInt(1,poistettavaPelivalineID);
            statement1.executeUpdate();
        }

        System.out.println("Poistetaan peliväline jonka PelivalineID: " + poistettavaPelivalineID + "...");
        try (PreparedStatement statement = connection.prepareStatement("""
                DELETE FROM `varausjarjestelma`.`pelivalineet` 
                WHERE PelivalineID = ?;
                """)) {
            statement.setInt(1,poistettavaPelivalineID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Pelivaline_muuttujat getPelivaline(Connection yhdistaTietokantaan, int valittuPelivaline) throws SQLException {
        Pelivaline_muuttujat palautettavaPelivaline = new Pelivaline_muuttujat();
        System.out.println("Lukee dataa... getPelivaline");
        try (PreparedStatement statement = yhdistaTietokantaan.prepareStatement("""
                SELECT PelivalineID, ValineNimi, ValineHinta
                FROM pelivalineet
                WHERE  `PelivalineID` LIKE ?
                ORDER BY ValineNimi
                """)) {
            statement.setInt(1, valittuPelivaline);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                palautettavaPelivaline = new Pelivaline_muuttujat(resultSet.getInt("PelivalineID"), resultSet.getString("ValineNimi"), resultSet.getString("ValineHinta"));
            }
            return palautettavaPelivaline;

        }};


    public static void setLisaaUusiKentta(Connection connection, Kentta_Muuttujat lisattavaKe) throws SQLException {
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
        }

        System.out.println("Haetaan lisätyn kentän kenttaID...");
        int uusiKenttaID = 0;
        try (PreparedStatement statement = connection.prepareStatement("""
                SELECT KenttaID
                    FROM kentat
                    WHERE Lajitunnus LIKE ?
                    AND KenttaHinta LIKE ?
                    AND Kenttanimi LIKE ?
                    AND Toimipistevastaava LIKE ?
                """)) {
            statement.setString(1,lisattavaKe.lajitunnus);
            statement.setString(2,lisattavaKe.kentanHinta);
            statement.setString(3,lisattavaKe.nimi);
            statement.setString(4,lisattavaKe.toimipistevastaava);
            ResultSet resultSet4 = statement.executeQuery();
            while (resultSet4.next()) {
                uusiKenttaID = resultSet4.getInt("KenttaID");
            }
        }

        System.out.println("Haetaan tietokannasta pelivälineet jotka liittyvät lajiin ja toimipisteeseen...");
        ArrayList<Integer> linkitettavatPelivalineIDt = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("""
                SELECT DISTINCT PelivalineID
                    FROM saatavilla, kentat
                    WHERE LajiTunnus LIKE ?
                    AND Toimipistevastaava LIKE ?
                    AND kentat.KenttaID = saatavilla.KenttaID
                """)) {
            statement.setString(1,lisattavaKe.lajitunnus);
            statement.setString(2,lisattavaKe.toimipistevastaava);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                linkitettavatPelivalineIDt.add(resultSet.getInt("PelivalineID"));
                System.out.println("Linkitettävä peliväline löytyi!");
            }
        }

        System.out.println("Lisätään kentälle järjestelmässä jo valmiiksi olevat lajitunnuksen pelivälineet");
        for (int i: linkitettavatPelivalineIDt) {
            try (PreparedStatement statement = connection.prepareStatement("""
                INSERT INTO `varausjarjestelma`.`saatavilla` (`KenttaID`, `PelivalineID`) 
                            VALUES (?, ?)   
                """)) {
                statement.setInt(1,uusiKenttaID);
                statement.setInt(2,i);
                statement.executeUpdate();
            }
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

    static ArrayList<Kentta_Muuttujat> getAllKentatLajilla(Connection yhdistaTietokantaan, String vastaava, String laji) throws SQLException {
        ArrayList<Kentta_Muuttujat> itemArrayList = new ArrayList<>();
        System.out.println("Lukee dataa... getAllKentat");
        try (PreparedStatement statement = yhdistaTietokantaan.prepareStatement("""
                SELECT LajiTunnus, KenttaID, KenttaHinta, Kenttanimi, Toimipistevastaava
                FROM kentat
                WHERE  `toimipistevastaava` LIKE ?
                AND LajiTunnus LIKE ?
                ORDER BY LajiTunnus
                """)) {
            statement.setString(1, vastaava);
            statement.setString(2,laji);
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
        System.out.println("Poistetaan yhteys pelivälineiden saatavuuteen");
        try (PreparedStatement statement = connection.prepareStatement("""
                DELETE FROM `varausjarjestelma`.`saatavilla` 
                    WHERE KenttaID =?;
                """)) {
            statement.setInt(1,poistettavaKenttaID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



        System.out.println("Poistetaan toimipiste jonka kenttaID: " + poistettavaKenttaID + "...");
        try (PreparedStatement statement = connection.prepareStatement("""
                DELETE FROM `varausjarjestelma`.`kentat` 
                    WHERE KenttaID =?;
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

    public static ArrayList<Pelivaline_muuttujat> getLajinPelivalineet(Connection yhdistaTietokantaan, String valittuLaji, String toimipistevastaava) throws SQLException {
        ArrayList<Pelivaline_muuttujat> itemArrayList = new ArrayList<>();
        System.out.println("Lukee dataa... getAllKentat");
        try (PreparedStatement statement = yhdistaTietokantaan.prepareStatement("""
                SELECT DISTINCT pelivalineet.PelivalineID, ValineNimi, ValineHinta
                    FROM pelivalineet, saatavilla, kentat
                    WHERE kentat.LajiTunnus LIKE ?
                    AND Toimipistevastaava LIKE ?
                    AND kentat.KenttaID = saatavilla.KenttaID
                    AND saatavilla.PelivalineID = pelivalineet.PelivalineID
                """)) {
            statement.setString(1, valittuLaji);
            statement.setString(2,toimipistevastaava);
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

    public static ArrayList<VarausAjat> getKenttaVarausAjat(Connection yhdistaTietokantaan, int valittuKentta, String valittuPVM) throws SQLException {
        ArrayList<VarausAjat> itemArrayList = new ArrayList<>();
        System.out.println("getKenttaVarausAjat Lukee dataa... ");
        try (PreparedStatement statement = yhdistaTietokantaan.prepareStatement("""
                SELECT VarauksenAika
                	FROM varaus, toimipiste, kentat
                	WHERE VarauksenPVM LIKE ?
                	AND kentat.KenttaID LIKE ?
                	AND varaus.KenttaID = kentat.KenttaID
                """)) {
            statement.setString(1, valittuPVM);
            statement.setInt(2, valittuKentta);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                itemArrayList.add(new VarausAjat(resultSet.getInt("VarauksenAika")));
                System.out.println("kenttaVarausAjat sisältää dataa!");
            }
            return itemArrayList;
        }
    }
    public static void setLisaaUusiPelivaline(Connection connection, Pelivaline_muuttujat lisattavaValine, String toimipiste) throws SQLException {
        System.out.println("Lisätään uusi peliväline tietokantaan...");
        try (PreparedStatement statement2 = connection.prepareStatement("""
                INSERT INTO `varausjarjestelma`.`Pelivalineet` (`ValineNimi`, `ValineHinta`, `LajiTunnus`)
                VALUES (?, ?, ?);
                """)) {
            statement2.setString(1, lisattavaValine.pelivalineNimi);
            statement2.setString(2, lisattavaValine.valineHinta);
            statement2.setString(3, lisattavaValine.lajiTunnus);
            statement2.executeUpdate();
            System.out.println("Toimipisteen tiedot lisätty tietokantaan.");
        }

        System.out.println("Haetaan lisätyn pelivälineen ID...");
        try (PreparedStatement statement = connection.prepareStatement("""
                SELECT PelivalineID
                    FROM Pelivalineet
                    WHERE ValineNimi LIKE ?
                    AND ValineHinta LIKE ? 
                    AND Lajitunnus LIKE ?               
                """)) {
            statement.setString(1,lisattavaValine.pelivalineNimi);
            statement.setString(2,lisattavaValine.valineHinta);
            statement.setString(3,lisattavaValine.lajiTunnus);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                lisattavaValine.pelivalineID = resultSet.getInt("PelivalineID");
            }
        }

        System.out.println("Haetaan lajin mukaiset kentät... ");
        ArrayList<Integer> kentat = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("""
                SELECT KenttaID
                    FROM kentat
                    WHERE LajiTunnus LIKE ?
                    AND Toimipistevastaava LIKE ?
                """)) {
            statement.setString(1,lisattavaValine.lajiTunnus);
            statement.setString(2,toimipiste);
            ResultSet resultSet1 = statement.executeQuery();
            while (resultSet1.next()) {
                kentat.add(resultSet1.getInt("KenttaID"));
                System.out.println("Kenttiä joihin laji lisätään löytyi!");
            }
        }
        System.out.println("Seuraavaksi lisätään pelivälineen linkitys kenttiin...");
        if (kentat.size() != 0) {
            System.out.println("Kenttiä on olemassa");
            for (int i: kentat) {
                try (PreparedStatement statement3 = connection.prepareStatement("""
                        INSERT INTO `varausjarjestelma`.`saatavilla` (`KenttaID`, `PelivalineID`) 
                            VALUES (?, ?)                       
                        """)){
                    statement3.setInt(1,i);
                    statement3.setInt(2,lisattavaValine.pelivalineID);
                    statement3.executeUpdate();
                }
                System.out.println("Lisätty pelivälineID: " + lisattavaValine.pelivalineID + " kenttään: " + i);
            }
        }
        System.out.println("Lisäys suoritettu!");
    }

    public static Kentta_Muuttujat getKentta(Connection yhdistaTietokantaan, int valittukentta) throws SQLException {
        Kentta_Muuttujat palautettavaKentta = new Kentta_Muuttujat();
        System.out.println("Lukee dataa... getAllKentat");
        try (PreparedStatement statement = yhdistaTietokantaan.prepareStatement("""
                SELECT LajiTunnus, KenttaID, KenttaHinta, Kenttanimi, Toimipistevastaava
                FROM kentat
                WHERE  `KenttaID` LIKE ?
                ORDER BY LajiTunnus
                """)) {
            statement.setInt(1, valittukentta);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                palautettavaKentta = new Kentta_Muuttujat(resultSet.getInt("KenttaID"), resultSet.getString("Kenttanimi"), resultSet.getString("LajiTunnus"), resultSet.getString("KenttaHinta"), resultSet.getString("Toimipistevastaava"));
            }
            return palautettavaKentta;
        }
    }
}
