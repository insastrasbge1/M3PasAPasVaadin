package fr.insa.beuvron.cours.m3New.pAp.pApVaadin.gui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Main")
@Route(value = "")
public class MainView extends VerticalLayout {

    private HorizontalLayout entete;
    private VerticalLayout content;

    public MainView() {
        this.entete = new HorizontalLayout();
        this.content = new VerticalLayout();
        this.add(this.entete,this.content);
        this.changeEntete(new EnteteLogin(this));
        this.changeContent(new MessageBienvenue());
    }
    
    public void changeEntete(Component c) {
        this.entete.removeAll();
        this.entete.add(c);
    }

    public void changeContent(Component c) {
        this.content.removeAll();
        this.content.add(c);
    }

}
