/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.beuvron.cours.m3New.pAp.pApVaadin.gui.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 *
 * @author francois
 */
public class EnteteLogin extends HorizontalLayout{
    
    public Button bLogin;
    public Button bInscription;
    
    public EnteteLogin() {
        this.bLogin = new Button("Login");
        this.bInscription = new Button("Inscription");
        this.add(this.bLogin,this.bInscription);
    }
    
}
