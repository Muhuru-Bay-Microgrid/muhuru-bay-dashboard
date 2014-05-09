/**
 * DISCLAIMER
 *
 * The quality of the code is such that you should not copy any of it as best
 * practice how to build Vaadin applications.
 *
 * @author jouni@vaadin.com
 *
 */

package org.mbmg;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.navigator.VaadinView;

@VaadinView(name = "dashboard")
@UIScope
public class DashboardView extends VerticalLayout implements View {

    public DashboardView() {
        setSizeFull();
        addStyleName("dashboard-view");
        HorizontalLayout top = new HorizontalLayout();
        top.setWidth("100%");
        top.setSpacing(true);
        top.addStyleName("toolbar");
        addComponent(top);
        final Label title = new Label("Muhuru-Bay Microgrid");
        title.setSizeUndefined();
        title.addStyleName("h1");
        top.addComponent(title);
        top.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
        top.setExpandRatio(title, 1);

    }

    @Override
    public void enter(ViewChangeEvent event) {
//        DataProvider dataProvider = ((DashboardUI) getUI()).dataProvider;
//        t.setContainerDataSource(dataProvider.getRevenueByTitle());
    }

}
