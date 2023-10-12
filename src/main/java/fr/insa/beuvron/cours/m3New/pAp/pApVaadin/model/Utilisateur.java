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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author francois
 */
public class Utilisateur {

    private int id;
    private String nom;
    private String pass;

    protected Utilisateur(int id, String nom, String pass) {
        this.id = id;
        this.nom = nom;
        this.pass = pass;
    }

    public Utilisateur(String nom, String pass) {
        this(-1, nom, pass);
    }

    @Override
    public String toString() {
        return "Utilisateur{" + "id=" + getId() + ", nom=" + getNom() + ", pass=" + getPass() + '}';
    }

    public static Utilisateur demande() {
        String nom = ConsoleFdB.entreeString("nom : ");
        String pass = ConsoleFdB.entreeString("pass : ");
        return new Utilisateur(nom, pass);
    }

    public void saveInDBV1(Connection con) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(
                "insert into li_utilisateur (nom,pass) values (?,?)",
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, this.nom);
            pst.setString(2, this.pass);
            pst.executeUpdate();
            try (ResultSet rid = pst.getGeneratedKeys()) {
                rid.next();
                this.id = rid.getInt(1);
            }
        }
    }

    public static List<Utilisateur> tousLesUtilisateurs(Connection con)
            throws SQLException {
        List<Utilisateur> res = new ArrayList<>();
        try (PreparedStatement pst = con.prepareStatement(
                "select id,nom,pass from li_utilisateur")) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nom = rs.getString("nom");
                    String pass = rs.getString("pass");
                    res.add(new Utilisateur(id, nom, pass));
                }
            }
        }
        return res;
    }

    public static Optional<Utilisateur> login(Connection con, String lenom, String lepass)
            throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(
                "select id,nom,pass from li_utilisateur \n"
                + "  where nom = ? and pass = ?")) {
            pst.setString(1, lenom);
            pst.setString(2, lepass);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String nom = rs.getString(2);
                    String pass = rs.getString("pass");
                    return Optional.of(new Utilisateur(id, nom, pass));
                } else {
                    return Optional.empty();
                }
            }
        }

    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

}
