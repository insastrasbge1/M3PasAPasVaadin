package fr.insa.beuvron.cours.m3New.pAp.pApVaadin.gui.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.sql.Connection;
import java.util.concurrent.atomic.AtomicInteger;

@PageTitle("OLDMain")
@Route(value = "old")
public class OldMainView extends HorizontalLayout {
    
    public static AtomicInteger compteur = new AtomicInteger(0);
    
    public Connection conn;

    private TextField name;
    private Button sayHello;
    private NumberField compteurStatic;

    public OldMainView() {
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
            this.compteurStatic.setValue(0.0+compteur.incrementAndGet());
        });
        sayHello.addClickShortcut(Key.ENTER);

        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);

        this.compteurStatic = new NumberField("compteur static");
        this.compteurStatic.setValue(0.0);
        add(name, sayHello,this.compteurStatic);
    }

}
