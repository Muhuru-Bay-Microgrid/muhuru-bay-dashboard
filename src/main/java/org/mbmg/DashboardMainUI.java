package org.mbmg;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DashboardMainUI extends UI implements Button.ClickListener {
    
	private static final long serialVersionUID = 1L;

	@Autowired
    private DashboardService service;

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);

        Button button = new Button("Click Me");
        button.addClickListener(this);
        
        layout.addComponent(button);

        setContent(layout);
    }

	@Override
	public void buttonClick(ClickEvent event) {
		Notification.show(service.sayHello());
		
	}
}