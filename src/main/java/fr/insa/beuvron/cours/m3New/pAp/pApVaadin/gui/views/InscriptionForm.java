/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.beuvron.cours.m3New.pAp.pApVaadin.gui.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

/**
 *
 * @author francois
 */
public class InscriptionForm extends FormLayout{
    private TextField tfNom;
    private PasswordField pfPass;
    private Button bOK;
    
    public InscriptionForm() {
        this.tfNom = new TextField("nom");
        this.pfPass = new PasswordField("pass");
        this.bOK = new Button("inscription");
        this.bOK.addClickListener((t) -> {
            String nom = this.tfNom.getValue();
            Notification.show("TODO inscription de " + nom + "dans BdD");
        });
        this.add(this.tfNom,this.pfPass,this.bOK);
    }

}
