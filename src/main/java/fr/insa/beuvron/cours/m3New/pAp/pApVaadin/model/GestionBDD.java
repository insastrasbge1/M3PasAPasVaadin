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

import fr.insa.beuvron.utils.ConsoleFdB;
import fr.insa.beuvron.utils.exceptions.ExceptionsUtils;
import fr.insa.beuvron.utils.list.ListUtils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

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

    /**
     * Creation du schéma. On veut créer tout ou rien, d'où la gestion explicite
     * des transactions.
     *
     * @throws SQLException
     */
    public static void creeSchema(Connection conn) throws SQLException {
        conn.setAutoCommit(false);
        try (Statement st = conn.createStatement()) {
            st.executeUpdate(
                    "create table li_utilisateur (\n"
                    + "    id integer not null primary key AUTO_INCREMENT,\n"
                    + "    nom varchar(30) not null unique,\n"
                    + "    pass varchar(30) not null\n"
                    + ")\n"
            );
            st.executeUpdate(
                    "create table li_likes (\n"
                    + "    u1 integer not null,\n"
                    + "    u2 integer not null\n"
                    + ")\n"
            );
            conn.commit();
            st.executeUpdate(
                    "alter table li_likes \n"
                    + "    add constraint fk_li_likes_u1 \n"
                    + "    foreign key (u1) references li_utilisateur(id) \n"
            );
            st.executeUpdate(
                    "alter table li_likes \n"
                    + "    add constraint fk_li_likes_u2 \n"
                    + "    foreign key (u2) references li_utilisateur(id) \n"
            );
        } catch (SQLException ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    /**
     * Suppression du schéma. Le schéma n'est peut-être pas créé, ou pas
     * entièrement créé, on ne s'arrête donc pas en cas d'erreur : on ne fait
     * que passer à la suite
     *
     * @throws SQLException
     */
    public static void deleteSchema(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement()) {
            // pour être sûr de pouvoir supprimer, il faut d'abord supprimer les liens
            // puis les tables
            // suppression des liens
            try {
                st.executeUpdate("alter table li_likes drop constraint fk_li_likes_u1");
            } catch (SQLException ex) {
                // nothing to do : maybe the constraint was not created
            }
            try {
                st.executeUpdate("alter table li_likes drop constraint fk_li_likes_u2");
            } catch (SQLException ex) {
            }
            // je peux maintenant supprimer les tables
            try {
                st.executeUpdate("drop table li_likes");
            } catch (SQLException ex) {
            }
            try {
                st.executeUpdate("drop table li_utilisateur");
            } catch (SQLException ex) {
            }
        }
    }

    public static void initTest(Connection conn) throws SQLException {
        Utilisateur fdb = new Utilisateur("fdb", "pass");
        fdb.saveInDBV1(conn);
        Utilisateur toto = new Utilisateur("toto", "pass");
        toto.saveInDBV1(conn);
    }

    public static void razBDD(Connection conn) throws SQLException {
        deleteSchema(conn);
        creeSchema(conn);
        initTest(conn);
    }

    public static void menuUtilisateur(Connection conn) {
        int rep = -1;
        while (rep != 0) {
            int i = 1;
            System.out.println("Menu utilisateur");
            System.out.println("================");
            System.out.println((i++) + ") lister les utilisateurs");
            System.out.println((i++) + ") login ");
            System.out.println((i++) + ") ajouter un utilisateur");
            System.out.println("0) Fin");
            rep = ConsoleFdB.entreeEntier("Votre choix : ");
            try {
                int j = 1;
                if (rep == j++) {
                    List<Utilisateur> users = Utilisateur.tousLesUtilisateurs(conn);
                    System.out.println(users.size() + " utilisateurs : ");
                    System.out.println(ListUtils.enumerateList(users));
                } else if (rep == j++) {
                    String nom = ConsoleFdB.entreeString("nom : ");
                    String pass = ConsoleFdB.entreeString("pass : ");
                    Optional<Utilisateur> user = Utilisateur.login(conn, nom, pass);
                    if (user.isPresent()) {
                        System.out.println("ok");
                    } else {
                        System.out.println("NON");
                    }
                } else if (rep == j++) {
                    System.out.println("entrez un nouvel utilisateur : ");
                    Utilisateur nouveau = Utilisateur.demande();
                    nouveau.saveInDBV1(conn);
                }
            } catch (SQLException ex) {
                System.out.println(ExceptionsUtils.messageEtPremiersAppelsDansPackage(ex, "fr.insa.beuvron", 5));
            }
        }
    }

    public static void menuPrincipal(Connection conn) {
        int rep = -1;
        while (rep != 0) {
            int i = 1;
            System.out.println("Menu principal");
            System.out.println("==============");
            System.out.println((i++) + ") supprimer schéma");
            System.out.println((i++) + ") créer schéma");
            System.out.println((i++) + ") RAZ BDD = supp + crée + init");
            System.out.println((i++) + ") gestion des utilisateurs");
            System.out.println("0) Fin");
            rep = ConsoleFdB.entreeEntier("Votre choix : ");
            try {
                int j = 1;
                if (rep == j++) {
                    deleteSchema(conn);
                } else if (rep == j++) {
                    creeSchema(conn);
                } else if (rep == j++) {
                    razBDD(conn);
                } else if (rep == j++) {
                    menuUtilisateur(conn);
                }
            } catch (SQLException ex) {
                System.out.println(ExceptionsUtils.messageEtPremiersAppelsDansPackage(ex, "fr.insa.beuvron", 5));
            }
        }
    }

    public static void debut() {
        try (Connection con = connectSurServeurM3()) {
            System.out.println("connecté");
            menuPrincipal(con);
        } catch (SQLException ex) {
            throw new Error("Connection impossible", ex);
        }
    }

    public static void main(String[] args) {
        debut();
    }

}
