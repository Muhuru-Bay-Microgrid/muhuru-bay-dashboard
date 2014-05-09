package org.mbmg;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;

/**
 * Created by rpomeroy on 5/6/14.
 */
@VaadinComponent
@UIScope
public class ErrorView extends VerticalLayout implements View {

    private Label message;

    ErrorView() {
        setMargin(true);
        addComponent(message = new Label());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        message.setValue(String.format("No such view: %s", event.getViewName()));
    }
}
