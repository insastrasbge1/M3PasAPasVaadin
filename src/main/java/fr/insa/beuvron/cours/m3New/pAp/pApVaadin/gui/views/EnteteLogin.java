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
public class EnteteLogin extends HorizontalLayout {

    private MainView main;

    public Button bLogin;
    public Button bInscription;

    public EnteteLogin(MainView main) {
        this.main = main;
        this.bLogin = new Button("Login");
        this.bLogin.addClickListener((t) -> {
            this.main.changeContent(new LoginForm(this.main));
        });
       this.bInscription = new Button("Inscription");
        this.bInscription.addClickListener((t) -> {
            this.main.changeContent(new InscriptionForm(this.main));
        });
         this.add(this.bLogin, this.bInscription);
    }

}
