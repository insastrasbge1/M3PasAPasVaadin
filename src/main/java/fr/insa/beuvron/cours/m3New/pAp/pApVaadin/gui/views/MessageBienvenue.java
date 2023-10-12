/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.beuvron.cours.m3New.pAp.pApVaadin.gui.views;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 *
 * @author francois
 */
public class MessageBienvenue extends VerticalLayout{
    
    public MessageBienvenue() {
        this.add(new H3("super programme"));
        this.add(new Paragraph("et encore je suis modeste"));
    }
    
}
