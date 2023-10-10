/*
Copyright 2000- Francois de Bertrand de Beuvron

This file is part of CoursBeuvron.

CoursBeuvron is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

CoursBeuvron is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with CoursBeuvron.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.insa.beuvron.cours.m3New.pAp.pApVaadin.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author francois
 */
public class GestionBDD {

    public static Connection connectGeneralMySQL(String host,
            int port, String database,
            String user, String pass)
            throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://" + host + ":" + port
                + "/" + database,
                user, pass);
        con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        return con;
    }

    public static String getPassPourServeurM3() {
        // en phase de développement, je vous conseille de mettre votre 
        // mot de passe en clair pour ne pas avoir à le retaper à chaque exécution
        // je ne veux pas mettre le mien dans ce programme que tout le monde
        // peut télécharger
//        return "monpass";
        // vous pouvez aussi le demander à chaque fois
//        return ConsoleFdB.entreeString("pass pour serveur M3 : ");
        // ici je le lit dans un fichier que j'ai exclu de git (.gitignore)
        try (BufferedReader bin = new BufferedReader(new FileReader("pass.txt"))) {
            return bin.readLine();
        } catch (IOException ex) {
            throw new Error("impossible de lire le mot de passe", ex);
        }
    }

    public static Connection connectSurServeurM3() throws SQLException {
        return connectGeneralMySQL("92.222.25.165", 3306,
                "m3_fdebertranddeb01", "m3_fdebertranddeb01",
                getPassPourServeurM3());
    }

    public static void debut() {
        try (Connection con = connectSurServeurM3()) {
            System.out.println("connecté");
        } catch (SQLException ex) {
            throw new Error("Connection impossible", ex);
        }
    }

    public static void main(String[] args) {
        debut();
    }
}
