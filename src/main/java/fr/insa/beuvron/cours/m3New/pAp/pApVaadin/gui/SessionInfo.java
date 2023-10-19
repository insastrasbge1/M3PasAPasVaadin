/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.beuvron.cours.m3New.pAp.pApVaadin.gui;

import fr.insa.beuvron.cours.m3New.pAp.pApVaadin.model.Utilisateur;
import java.sql.Connection;
import java.util.Optional;

/**
 *
 * @author francois
 */
public class SessionInfo {
    
    private Connection connBDD;
    private Optional<Utilisateur> curUser;

    public SessionInfo(Connection connBDD, Optional<Utilisateur> curUser) {
        this.connBDD = connBDD;
        this.curUser = curUser;
    }
    
    public SessionInfo(Connection connBDD) {
        this(connBDD,Optional.empty());
    }

    /**
     * @return the connBDD
     */
    public Connection getConnBDD() {
        return connBDD;
    }

    /**
     * @return the curUser
     */
    public Optional<Utilisateur> getCurUser() {
        return curUser;
    }

    /**
     * @param curUser the curUser to set
     */
    public void setCurUser(Optional<Utilisateur> curUser) {
        this.curUser = curUser;
    }
    
    public boolean isUserLogged() {
        return this.curUser.isPresent();
    }
    
    public void login(Utilisateur u) {
        this.curUser = Optional.of(u);
    }
    
    public void logout() {
        this.curUser = Optional.empty();
    }
      
}
